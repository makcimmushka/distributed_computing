package ua.knu.servlet;

import jdk.internal.joptsimple.internal.Strings;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.News;
import ua.knu.persistence.model.NewsCategory;
import ua.knu.persistence.repository.NewsCategoryRepositoryImpl;
import ua.knu.persistence.repository.Repository;
import ua.knu.persistence.repository.NewsRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.knu.util.Constants.Group.*;
import static ua.knu.util.Constants.Other.ENTITY;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.*;

@WebServlet(name = "UpdateServlet", value = "/update")
public class UpdateServlet extends HttpServlet {

    private final Repository<News> studentRepository;
    private final Repository<NewsCategory> groupRepository;

    public UpdateServlet() {
        this.studentRepository = new NewsRepositoryImpl(ConnectionFactory.getConnection());
        this.groupRepository = new NewsCategoryRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getParameter(ENTITY).equals(NEWS_ENTITY)) {
                val newsId = Integer.parseInt(req.getParameter(NEWS_ID));

                studentRepository.update(new News()
                        .setId(newsId)
                        .setTitle(getUpdatedStringValue(req, UPDATED_TITLE))
                        .setText(getUpdatedStringValue(req, UPDATED_TEXT))
                        .setCategoryId(getUpdatedIntegerValue(req, UPDATED_CATEGORY_ID)));

            } else if (req.getParameter(ENTITY).equals(NEWS_CATEGORY_ENTITY)) {
                val categoryId = Integer.parseInt(req.getParameter(CATEGORY_ID));

                groupRepository.update(new NewsCategory()
                        .setId(categoryId)
                        .setName(getUpdatedStringValue(req, UPDATED_CATEGORY_NAME))
                        .setAgeLimit(getUpdatedIntegerValue(req, UPDATED_CATEGORY_AGE_LIMIT)));
            }

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        studentRepository.close();
        groupRepository.close();
        super.destroy();
    }

    private String getUpdatedStringValue(@NonNull HttpServletRequest req, @NonNull String updatedGroupName) {
        val parameter = req.getParameter(updatedGroupName);

        return parameter.equals(Strings.EMPTY) ? null : parameter;
    }

    private Integer getUpdatedIntegerValue(@NonNull HttpServletRequest req, @NonNull String parameter) {
        int updatedGroupId;

        try {
            updatedGroupId = Integer.parseInt(req.getParameter(parameter));
        } catch (NumberFormatException e) {
            return null;
        }

        return updatedGroupId;
    }
}
