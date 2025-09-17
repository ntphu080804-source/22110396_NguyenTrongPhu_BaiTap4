package dao;

import entity.User;
import connection.DBConnection;
import java.sql.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UserDAO {

    public User login(String username, String password) {
        User user = null;
        try (Connection conn = new DBConnection().getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("roleid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
