package ua.knu.servlet;

import lombok.SneakyThrows;
import lombok.val;
import ua.knu.persistence.ConnectionFactory;
import ua.knu.persistence.model.NewsCategory;
import ua.knu.persistence.model.News;
import ua.knu.persistence.repository.NewsCategoryRepositoryImpl;
import ua.knu.persistence.repository.Repository;
import ua.knu.persistence.repository.NewsRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static ua.knu.util.Constants.Group.CATEGORY_ID;
import static ua.knu.util.Constants.Other.INDEX;
import static ua.knu.util.Constants.Student.NEWS_ID;

@WebServlet(name = "DeleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {

    private final Repository<News> studentRepository;
    private final Repository<NewsCategory> groupRepository;

    public DeleteServlet() {
        this.studentRepository = new NewsRepositoryImpl(ConnectionFactory.getConnection());
        this.groupRepository = new NewsCategoryRepositoryImpl(ConnectionFactory.getConnection());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        val studentId = Optional.ofNullable(req.getParameter(NEWS_ID));
        val groupId = Optional.ofNullable(req.getParameter(CATEGORY_ID));

        try {
            if (studentId.isPresent()) {
                studentRepository.removeById(Integer.parseInt(studentId.get()));
            } else {
                groupId.ifPresent(s -> groupRepository.removeById(Integer.parseInt(s)));
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
}
