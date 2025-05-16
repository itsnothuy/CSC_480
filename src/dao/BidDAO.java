package dao;

import model.*;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

public class BidDAO {
    private final PostDAO pdao=new PostDAO();
    private final UserDAO udao=new UserDAO();

    public void createTable() throws SQLException {
        String sql = """
          CREATE TABLE IF NOT EXISTS Bid (
            bid_id TEXT PRIMARY KEY,
            post_id TEXT NOT NULL,
            supplier_id TEXT NOT NULL,
            price REAL NOT NULL,
            message TEXT,
            status TEXT NOT NULL,
            created_at TEXT NOT NULL,
            FOREIGN KEY(post_id) REFERENCES Post(post_id),
            FOREIGN KEY(supplier_id) REFERENCES User(user_id)
          )
        """;
        DBConnection.connect().createStatement().execute(sql);
    }

    public void add(Bid b) throws SQLException {
        String sql="INSERT INTO Bid VALUES (?,?,?,?,?,?,?)";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,b.getId());
            ps.setString(2,b.getPost().getId());
            ps.setString(3,b.getSupplier().getId());
            ps.setBigDecimal(4,b.getPrice());
            ps.setString(5,b.getMessage());
            ps.setString(6,b.getStatus());
            ps.setString(7,b.getCreatedAt().toString());
            ps.executeUpdate();
        }
    }

    public List<Bid> forPost(Post p) throws SQLException {
        List<Bid> list=new ArrayList<>();
        String sql="SELECT * FROM Bid WHERE post_id=?";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p.getId());
            ResultSet rs=ps.executeQuery();
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Bid map(ResultSet rs) throws SQLException {
        Post post = pdao.all().stream()
                .filter(po -> po.getId().equals(rs.getString("post_id")))
                .findFirst().orElse(null);
        User sup  = udao.get(rs.getString("supplier_id"));
        return new Bid(
            rs.getString("bid_id"),
            post,
            sup,
            rs.getBigDecimal("price"),
            rs.getString("message"),
            rs.getString("status"),
            LocalDateTime.parse(rs.getString("created_at"))
        );
    }
}
