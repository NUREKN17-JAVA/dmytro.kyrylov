package ua.nure.kn.kyrylov.usermanagement.web;

import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("addButton") != null) {
            add(req, resp);
        } else if (req.getParameter("editButton") != null) {
            edit(req, resp);
        } else if (req.getParameter("deleteButton") != null) {
            delete(req, resp);
        } else if (req.getParameter("detailsButton") != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        browse(req, resp);
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (prepareAnotherUserSrvlet(req, resp)) return;
        req.getRequestDispatcher("/details").forward(req, resp);
    }

    private boolean prepareAnotherUserSrvlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().length() == 0) {
            req.setAttribute("error", "You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return true;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().findUser(new Long(idStr));
            req.getSession().setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error", "ERROR:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return true;
        }
        return false;
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (prepareAnotherUserSrvlet(req, resp)) return;
        req.getRequestDispatcher("/delete").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (prepareAnotherUserSrvlet(req, resp)) return;
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    protected void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users;
        try {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            users = userDao.findAllUsers();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
