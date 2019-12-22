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

public class DeleteServlet extends EditServlet {

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/delete.jsp").forward(req, resp);
    }

    @Override
    protected User getUser(HttpServletRequest req) throws ParseException, ValidationException {
        User user = new User();
        String idStr = req.getParameter("id");
        if (idStr != null) {
            user.setId(new Long(idStr));
        }
        return user;
    }

    @Override
    protected void processUser(User user) throws DatabaseException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        DaoFactory.getInstance().getUserDao().deleteUser(user);
    }
}
