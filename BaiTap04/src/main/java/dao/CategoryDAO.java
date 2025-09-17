package dao;

import connection.DBConnection;
import entity.Category;

import java.sql.*;
import java.util.*;

public class CategoryDAO {
	public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = new DBConnection().getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("user_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
	// Lấy category theo id và user
	public Category getById(int id, int userId) {
	    String sql = "SELECT * FROM Category WHERE id=? AND user_id=?";
	    try (Connection conn = new DBConnection().getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ps.setInt(2, userId);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	            return new Category(
	                rs.getInt("id"),
	                rs.getString("name"),
	                rs.getInt("user_id")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

    public List<Category> getByUserId(int userId) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE user_id=?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("user_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 // Thêm category mới
    public void insert(Category c) {
        String sql = "INSERT INTO Category(name, user_id) VALUES (?, ?)";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật category (chỉ được cập nhật của chính mình)
    public void update(Category c) {
        String sql = "UPDATE Category SET name=? WHERE id=? AND user_id=?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            ps.setInt(3, c.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa category (chỉ xóa được của chính mình)
    public void delete(int id, int userId) {
        String sql = "DELETE FROM Category WHERE id=? AND user_id=?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
