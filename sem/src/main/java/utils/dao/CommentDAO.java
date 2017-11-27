package utils.dao;

import models.pojo.Comment;
import models.pojo.Post;
import org.apache.log4j.Logger;
import utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public enum CommentDAO {
    INSTANCE;
    private static final Logger l = Logger.getLogger(UserDAO.class);

    public void comment(Comment comment) throws SQLException {
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        c.setAutoCommit(false);
        PreparedStatement stmt = c.prepareStatement("INSERT INTO comment(user_id,post_id,description "
                + ") VALUES (?, ?, ?);");
        stmt.setString(3, comment.getDescription());
        stmt.setInt(1, comment.getUser_id());
        stmt.setInt(2, comment.getPost_id());
        stmt.execute();
        c.commit();
        stmt.close();
        c.close();
    }

    public ArrayList<Comment> findCommentsByPost(Post post) throws SQLException {
        ArrayList<Comment> r = new ArrayList<Comment>();
        Connection c = ConnectionManager.INSTANCE.getConnection();
        l.trace("Connection established");
        PreparedStatement stmt = c.prepareStatement("SELECT user_id,description FROM comment WHERE post_id=?;");
        stmt.setInt(1, post.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Comment a = new Comment();
            a.setDescription(rs.getString(2));
            a.setUser_id(rs.getInt(1));
            a.setPost_id(post.getId());
            r.add(a);
        }
        rs.close();
        stmt.close();
        c.close();
        return r;
    }
}
