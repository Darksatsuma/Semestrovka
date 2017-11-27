package utils.dao;

import models.pojo.User;
import org.apache.log4j.Logger;
import utils.ConnectionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Calendar;

public enum  UserDAO {
    INSTANCE;

    private static final Logger l = Logger.getLogger(UserDAO.class);

    public void register(User user) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        c.setAutoCommit(false);
        PreparedStatement stmt = c.prepareStatement("INSERT INTO users(login,password, "
                + ") VALUES (?, ?);");
        stmt.setString(1, user.getLogin());
        stmt.setString(2, user.getPassword());
        /*
        Calendar calen = Calendar.getInstance();
        calen.setTime(new java.util.Date());
        calen.set(Calendar.DAY_OF_MONTH, calen.get(Calendar.DAY_OF_MONTH) - 1);
        java.util.Date expireDate = calen.getTime();
        stmt.setDate(3, new Date(expireDate.getTime()));
        */
        stmt.execute();
        c.commit();
        stmt.close();
        c.close();
    }

    public boolean ifLoginRegistered(String login) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE login=?;");
        stmt.setString(1, login);
        ResultSet data = stmt.executeQuery();
        while (data.next()) {
            return true;
        }
        data.close();
        stmt.close();
        c.close();
        return false;
    }
/*
    public String findEmailByUUID(String uuid) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        String username = null;
        PreparedStatement stmt = c.prepareStatement("SELECT user_email FROM users WHERE user_uuid=?;");
        stmt.setString(1, uuid);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            username = rs.getString("user_email");
        }
        rs.close();
        stmt.close();
        c.close();
        return username;
    }
*/
    public User findUserByLogin(String login) throws SQLException {
        User r = new User();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT password,id FROM users WHERE login=?;");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r.setLogin(login);
            r.setPassword(rs.getString(1));
            r.setId(rs.getInt(2));
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }

    public User findUserById(int id) throws SQLException {
        User r = new User();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT password,login FROM users WHERE id=?;");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r.setId(id);
            r.setPassword(rs.getString(1));
            r.setLogin(rs.getString(2));
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }
/*
    public void setUUID(String email, String uuid) throws SQLException {
        Connection connection = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = null;
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());
        if (uuid.equals("0")) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
        } else {
            c.add(Calendar.DATE, 30);
        }
        java.util.Date expireDate = c.getTime();
        stmt = connection.prepareStatement("UPDATE users SET user_uuid=?, user_uuid_expire_date=? " + "WHERE login = ?;");
        stmt.setString(1, uuid);
        stmt.setDate(2, new Date(expireDate.getTime()));
        stmt.setString(3, email);
        stmt.execute();
        l.trace("Added UUID to user " + email);
        stmt.close();
        connection.close();
    }

    public void deleteUUID(String email) throws SQLException {
        setUUID(email,"0");
    }
*/
}
