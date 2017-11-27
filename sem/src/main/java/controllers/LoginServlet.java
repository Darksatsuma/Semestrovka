package controllers;

import models.pojo.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = -8889013342089998973L;
    private static final Logger l = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("username");
        String password = req.getParameter("password");

        l.info("User " + email + " sent the password.");
        boolean remember = "on".equals(req.getParameter("remember_me"));
        l.info("Remember me mode is " + remember + ".");
        try {

            User user = new User();
            try {
                user.setPasswordRaw(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user.setLogin(email);
            l.info("Signing in " + user.getLogin());
            req.login(user.getLogin(), user.getPassword());
            l.info("If user succeed with login for email " + email + " the random UUID is generated.");
            /*
            String randomUUID = UUID.randomUUID().toString();
            try {
                if (remember) {
                    UserDAO.INSTANCE.setUUID(user.getEmail(), randomUUID);
                    l.info("For user  " + email + " uuid is stored to db.");
                    Cookies.addCookie(req, resp, Cookies.COOKIE_NAME, randomUUID, Cookies.COOKIE_AGE);
                    l.info("For user " + email + " uuid cookie is added to the forwarded response.");
                } else {
                    UserDAO.INSTANCE.deleteUUID(user.getEmail());
                    l.info("For user " + email + " uuid is cleared from the db");
                    Cookies.removeCookie(req, resp, Cookies.COOKIE_NAME);
                    l.info("For user " + email + " uuid cookie is removed i.e. set to 0 age in forwarded response");
                }
            } catch (SQLException e) {
                l.error(e.getMessage());
                l.error(e.getSQLState());
                l.error(e.getLocalizedMessage());
            }*/
        } catch (ServletException e) {
            String errorMessage = "Login or password is incorrect, please try again.";
            l.info("Some error occurs for " + email + "; the error message is: " + e.getLocalizedMessage());
            if (!e.getLocalizedMessage().equals("Login failed")) {
                errorMessage = e.getLocalizedMessage();
            }
            req.setAttribute("error", errorMessage);
            getServletContext().getRequestDispatcher("/logon.jsp").forward(req, resp);
            return;
        }
        String redirectTo = req.getParameter("url");
        l.info("For user " + email + " the page is redirected to " + "the initial url user tried to access.");
        resp.sendRedirect(redirectTo);
    }

}