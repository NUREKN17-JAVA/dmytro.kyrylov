package ua.nure.kn.kyrylov.usermanagement.web;

import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;

public class AddServlet extends EditServlet {

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

    @Override
    protected User getUser(HttpServletRequest req) throws ParseException, ValidationException {
        return super.getUser(req);
    }

    @Override
    protected void processUser(User user) throws DatabaseException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        DaoFactory.getInstance().getUserDao().createUser(user);
    }
}
