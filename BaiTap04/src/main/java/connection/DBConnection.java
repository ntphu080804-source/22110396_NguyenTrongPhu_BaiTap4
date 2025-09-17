package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	 private final String serverName = "LAPTOP-PUEORRNE\\SQLEXPRESS05";
	    private final String dbName = "QuanLyLTWeb";
	    private final String portNumber = "1433";
	    private final String userID = "sa";
	    private final String password = "213456789";

	    public Connection getConnection() throws Exception {
	        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber
	                   + ";databaseName=" + dbName
	                   + ";encrypt=true;trustServerCertificate=true;";
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        return DriverManager.getConnection(url, userID, password);
	    }

	    // Test nhanh
	    public static void main(String[] args) {
	        try (Connection conn = new DBConnection().getConnection()) {
	            if (conn != null && !conn.isClosed()) {
	                System.out.println("✅ Kết nối thành công tới SQL Server!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
