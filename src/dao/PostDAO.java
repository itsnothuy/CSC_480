package dao;

import model.*;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class PostDAO {
    private final ServiceDAO sdao=new ServiceDAO();
    private final UserDAO    udao=new UserDAO();

    public void createTable() throws SQLException {
        String sql = """
          CREATE TABLE IF NOT EXISTS Post (
            post_id TEXT PRIMARY KEY,
            title TEXT NOT NULL,
            description TEXT NOT NULL,
            status TEXT NOT NULL,
            service_id TEXT NOT NULL,
            requester_id TEXT NOT NULL,
            created_at TEXT NOT NULL,
            FOREIGN KEY(service_id) REFERENCES Service(service_id),
            FOREIGN KEY(requester_id) REFERENCES User(user_id)
          )
        """;
        DBConnection.connect().createStatement().execute(sql);
    }

    public void add(Post p) throws SQLException {
        String sql="INSERT INTO Post VALUES (?,?,?,?,?,?,?)";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p.getId());
            ps.setString(2,p.getTitle());
            ps.setString(3,p.getDescription());
            ps.setString(4,p.getStatus());
            ps.setString(5,p.getService().getId());
            ps.setString(6,p.getRequester().getId());
            ps.setString(7,p.getCreatedAt().toString());
            ps.executeUpdate();
        }
    }

    public List<Post> open() throws SQLException { return fetch("status='open'"); }
    public List<Post> all()  throws SQLException { return fetch("1=1"); }

    private List<Post> fetch(String where) throws SQLException {
        List<Post> list=new ArrayList<>();
        String sql="SELECT * FROM Post WHERE "+where;
        try (Connection c=DBConnection.connect();
             Statement st=c.createStatement();
             ResultSet rs=st.executeQuery(sql)){
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Post map(ResultSet rs) throws SQLException {
        Service svc=sdao.get(rs.getString("service_id"));
        User req=udao.get(rs.getString("requester_id"));
        return new Post(
            rs.getString("post_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("status"),
            svc, req,
            LocalDateTime.parse(rs.getString("created_at"))
        );
    }
}
