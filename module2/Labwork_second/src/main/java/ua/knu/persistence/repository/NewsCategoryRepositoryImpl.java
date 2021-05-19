package ua.knu.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import ua.knu.persistence.model.NewsCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class NewsCategoryRepositoryImpl implements Repository<NewsCategory> {

    private Connection connection;

    @Override
    public Collection<NewsCategory> findAll() {
        val categories = new ArrayList<NewsCategory>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM categories")) {

            while (resultSet.next()) {
                val group = new NewsCategory()
                        .setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setAgeLimit(resultSet.getInt("age_limit"));

                categories.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public boolean save(NewsCategory newsCategory) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT  INTO categories(name, age_limit) VALUES(?,?)")) {

            preparedStatement.setString(1, newsCategory.getName());
            preparedStatement.setInt(2, newsCategory.getAgeLimit());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(NewsCategory newsCategory) {
        val oldGroup = findById(newsCategory.getId());

        if (oldGroup == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE categories SET name = ?, age_limit = ? WHERE id = ?")) {

            preparedStatement.setString(1, Optional.ofNullable(newsCategory.getName()).orElse(oldGroup.getName()));
            preparedStatement.setInt(2, Optional.ofNullable(newsCategory.getAgeLimit()).orElse(oldGroup.getAgeLimit()));
            preparedStatement.setInt(3, newsCategory.getId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NewsCategory findById(int id) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM categories WHERE id = " + id)) {

            resultSet.next();
            return new NewsCategory()
                    .setId(resultSet.getInt("id"))
                    .setName(resultSet.getString("name"))
                    .setAgeLimit(resultSet.getInt("age_limit"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @SneakyThrows
    public void close() {
        connection.close();
    }
}
