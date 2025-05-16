package dao;

import model.Service;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ServiceDAO {
    public void createTable() throws SQLException {
        String sql = """
          CREATE TABLE IF NOT EXISTS Service (
            service_id TEXT PRIMARY KEY,
            name TEXT UNIQUE NOT NULL,
            description TEXT
          )
        """;
        DBConnection.connect().createStatement().execute(sql);
    }
    public void add(Service s) throws SQLException {
        String sql="INSERT INTO Service VALUES (?,?,?)";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,s.getId());
            ps.setString(2,s.getName());
            ps.setString(3,s.getDescription());
            ps.executeUpdate();
        }
    }
    public Service get(String id) throws SQLException {
        String sql="SELECT * FROM Service WHERE service_id=?";
        try (Connection c=DBConnection.connect();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,id);
            ResultSet rs=ps.executeQuery();
            return rs.next()? new Service(rs.getString("service_id"),
                                          rs.getString("name"),
                                          rs.getString("description")):null;
        }
    }
}
