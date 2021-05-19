package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.News;
import ua.knu.persistence.repository.Repository;
import ua.knu.persistence.repository.NewsRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.knu.util.Constants.Group.CATEGORY_ID;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.*;

@WebServlet(name = "newsServlet", value = "/news")
public class NewsServlet extends HttpServlet {

    private final Repository<News> studentRepository;

    public NewsServlet() {
        this.studentRepository = new NewsRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(NEWS, studentRepository.findAll());
        req.setAttribute(NEWS_TITLE, "News: ");

        try {
            req.getRequestDispatcher(INDEX).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            val firstName = req.getParameter(TITLE);
            val lastName = req.getParameter(TEXT);
            val groupId = Integer.parseInt(req.getParameter(CATEGORY_ID));

            studentRepository.save(new News()
                    .setTitle(firstName)
                    .setText(lastName)
                    .setCategoryId(groupId));

            req.getRequestDispatcher(INDEX).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void destroy() {
        studentRepository.close();
        super.destroy();
    }
}
