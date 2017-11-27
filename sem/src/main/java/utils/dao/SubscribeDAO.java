package utils.dao;
import models.pojo.Subscribe;
import models.pojo.Post;
import models.pojo.User;
import org.apache.log4j.Logger;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public enum SubscribeDAO {
    INSTANCE;
    private static final Logger l = Logger.getLogger(UserDAO.class);

    public void subscribe(Subscribe subscribe) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        c.setAutoCommit(false);
        PreparedStatement stmt = c.prepareStatement("INSERT INTO subscribe(follower_id,user_id, "
                + ") VALUES (?, ?);");
        stmt.setInt(1, subscribe.getFollower_id());
        stmt.setInt(2, subscribe.getUser_id());
        stmt.execute();
        c.commit();
        stmt.close();
        c.close();
    }

    public ArrayList<User> findFollowersByUser(Subscribe subscribe) throws SQLException {
        ArrayList<User> r = new ArrayList<User>();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT follower_id FROM subscribe WHERE user_id=?;");
        stmt.setInt(1, subscribe.getFollower_id());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r.add(UserDAO.INSTANCE.findUserById(rs.getInt(1)));
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }

    public ArrayList<User> findFollowingByUser(Subscribe subscribe) throws SQLException {
        ArrayList<User> r = new ArrayList<User>();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT user_id FROM subscribe WHERE follower_id=?;");
        stmt.setInt(1, subscribe.getFollower_id());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r.add(UserDAO.INSTANCE.findUserById(rs.getInt(1)));
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }
}
