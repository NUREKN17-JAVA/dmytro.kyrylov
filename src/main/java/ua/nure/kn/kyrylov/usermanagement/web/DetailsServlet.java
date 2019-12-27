package ua.nure.kn.kyrylov.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends EditServlet {

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/details.jsp").forward(req, resp);
    }

    @Override
    protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doCancel(req, resp);
    }
}
