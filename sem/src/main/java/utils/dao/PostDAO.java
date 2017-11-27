package utils.dao;

import models.pojo.Post;
import models.pojo.User;
import org.apache.log4j.Logger;
import utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public enum PostDAO {
    INSTANCE;
    private static final Logger l = Logger.getLogger(UserDAO.class);

    public void post(Post post) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        c.setAutoCommit(false);
        PreparedStatement stmt = c.prepareStatement("INSERT INTO post(discription,user_id, "
                + ") VALUES (?, ?);");
        stmt.setString(1, post.getDiscription());
        stmt.setInt(2, post.getUser_id());
        stmt.execute();
        c.commit();
        stmt.close();
        c.close();
    }

    public ArrayList<Post> findPostsByUser(User user) throws SQLException {
        ArrayList<Post> r = new ArrayList<Post>();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT pic,discription,id FROM post WHERE user_id=?;");
        stmt.setInt(1, user.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Post a = new Post();
            a.setDiscription(rs.getString(2));
            a.setId(rs.getInt(3));
            a.setUser_id(user.getId());
            r.add(a);
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }

    public Post findPostById(int id) throws SQLException {
        Post r = new Post();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT user_id,pic,discription FROM post WHERE id=?;");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            r.setDiscription(rs.getString(3));
            r.setId(id);
            r.setUser_id(rs.getInt(1));
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }
}

