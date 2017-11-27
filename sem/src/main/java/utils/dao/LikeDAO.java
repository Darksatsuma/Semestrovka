package utils.dao;

import models.pojo.Like;
import models.pojo.Post;
import org.apache.log4j.Logger;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public enum LikeDAO {
    INSTANCE;
    private static final Logger l = Logger.getLogger(UserDAO.class);

    public void like(Like like) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        c.setAutoCommit(false);
        PreparedStatement stmt = c.prepareStatement("INSERT INTO like(liker_id,post_id "
                + ") VALUES (?, ?);");
        stmt.setInt(1, like.getLiker_id());
        stmt.setInt(2, like.getPost_id());
        stmt.execute();
        c.commit();
        stmt.close();
        c.close();
    }

    public ArrayList<Like> findLikesByPost(Post post) throws SQLException {
        ArrayList<Like> r = new ArrayList<Like>();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT liker_id FROM like WHERE post_id=?;");
        stmt.setInt(1, post.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Like a = new Like();
            a.setPost_id(post.getId());
            a.setLiker_id(rs.getInt(1));
            r.add(a);
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }
}
