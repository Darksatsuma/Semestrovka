package controllers;

import models.pojo.User;
import org.apache.log4j.Logger;
import utils.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger l = Logger.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        String login=req.getParameter("login");
        boolean remember = "on".equals(req.getParameter("remember_me"));

        if (!password.equals(repassword)) {
            req.setAttribute("error", "Typed passwords does not match.");
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        User user=new User();
        user.setLogin(login);
        try {
            user.setPasswordRaw(password);
        } catch (NoSuchAlgorithmException e) {
            l.error(e.getLocalizedMessage());
            l.error(e.getMessage());
            req.setAttribute("error", "Some troubles with encrypting your password. Please try again.");
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        try {
            UserDAO.INSTANCE.register(user);
        } catch (SQLException e) {
            String sqlErrorState = e.getSQLState();
            l.error(sqlErrorState);
            l.error(e.getErrorCode());
            l.error(e.getLocalizedMessage());
            l.error(e.getMessage());
            String errorMessage = "Some troubles occured in database. Please try again.";
            if (sqlErrorState.equals("23505")) {
                errorMessage = "This email is already registered, please use another.";
            }
            req.setAttribute("error", errorMessage);
            getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/");

    }
}