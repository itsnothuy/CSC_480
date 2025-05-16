package dao;

import model.User;
import util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserDAO {
    /* schema */
    public void createTable() throws SQLException {
        String sql = """
          CREATE TABLE IF NOT EXISTS User (
            user_id TEXT PRIMARY KEY,
            username TEXT UNIQUE NOT NULL,
            email    TEXT UNIQUE NOT NULL,
            hashed_password TEXT NOT NULL,
            is_supplier INTEGER NOT NULL,
            is_verified INTEGER NOT NULL,
            created_at TEXT NOT NULL
          )
        """;
        try (Connection c=DBConnection.connect();
             Statement st=c.createStatement()){ st.execute(sql); }
    }

    /* insert */
    public void add(User u) throws SQLException {
        String sql = "INSERT INTO User VALUES (?,?,?,?,?,?,?)";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,u.getId());
            ps.setString(2,u.getUsername());
            ps.setString(3,u.getEmail());
            ps.setString(4,u.getHashedPassword());
            ps.setBoolean(5,u.isSupplier());
            ps.setBoolean(6,u.isVerified());
            ps.setString(7,u.getCreatedAt().toString());
            ps.executeUpdate();
        }
    }

    /* get & list */
    public User get(String id) throws SQLException {
        String sql="SELECT * FROM User WHERE user_id=?";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,id);
            ResultSet rs=ps.executeQuery();
            return rs.next()? map(rs):null;
        }
    }
    public List<User> getAll() throws SQLException {
        List<User> list=new ArrayList<>();
        try (Connection c=DBConnection.connect();
             Statement st=c.createStatement();
             ResultSet rs=st.executeQuery("SELECT * FROM User")){
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    private User map(ResultSet rs) throws SQLException {
        return new User(
            rs.getString("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("hashed_password"),
            rs.getBoolean("is_supplier"),
            rs.getBoolean("is_verified"),
            LocalDateTime.parse(rs.getString("created_at"))
        );
    }
}
