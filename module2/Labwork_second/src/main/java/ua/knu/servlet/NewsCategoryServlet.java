package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.NewsCategory;
import ua.knu.persistence.repository.NewsCategoryRepositoryImpl;
import ua.knu.persistence.repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.knu.util.Constants.Group.*;
import static ua.knu.util.Constants.Other.INDEX;

@WebServlet(name = "newsCategoriesServlet", value = "/categories")
public class NewsCategoryServlet extends HttpServlet {

    private final Repository<NewsCategory> groupRepository;

    public NewsCategoryServlet() {
        this.groupRepository = new NewsCategoryRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(CATEGORIES, groupRepository.findAll());
        req.setAttribute(CATEGORIES_TITLE, "Categories: ");

        try {
            req.getRequestDispatcher(INDEX).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            val name = req.getParameter(CATEGORY_NAME);
            val course = Integer.parseInt(req.getParameter(AGE_LIMIT));

            groupRepository.save(new NewsCategory()
                    .setName(name)
                    .setAgeLimit(course));

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        groupRepository.close();
        super.destroy();
    }
}
