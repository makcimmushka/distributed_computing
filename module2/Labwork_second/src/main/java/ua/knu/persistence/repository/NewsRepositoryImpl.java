package ua.knu.persistence.repository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import ua.knu.persistence.model.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class NewsRepositoryImpl implements Repository<News> {

    private Connection connection;

    @Override
    public Collection<News> findAll() {
        val news = new ArrayList<News>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM news")) {

            while (resultSet.next()) {
                val student = new News()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setText(resultSet.getString("text"))
                        .setCategoryId(resultSet.getInt("category_id"));

                news.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return news;
    }

    @Override
    public boolean save(News news) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO news(title, text, category_id) VALUES(?,?,?)")) {

            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getText());
            preparedStatement.setInt(3, news.getCategoryId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(News news) {
        val oldStudent = findById(news.getId());

        if (oldStudent == null) return false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE news SET title = ?, text = ?, category_id = ? WHERE id = ?")) {

            preparedStatement.setString(1, Optional.ofNullable(news.getTitle()).orElse(oldStudent.getTitle()));
            preparedStatement.setString(2, Optional.ofNullable(news.getText()).orElse(oldStudent.getText()));
            preparedStatement.setInt(3, Optional.ofNullable(news.getCategoryId()).orElse(oldStudent.getCategoryId()));
            preparedStatement.setInt(4, news.getId());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public News findById(int id) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM news WHERE id = " + id)) {

            resultSet.next();
            return new News()
                    .setId(resultSet.getInt("id"))
                    .setTitle(resultSet.getString("title"))
                    .setText(resultSet.getString("text"))
                    .setCategoryId(resultSet.getInt("category_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM news WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
