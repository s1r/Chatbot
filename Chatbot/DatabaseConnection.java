import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	Connection conn;
	String dir = System.getProperty("user.dir");
	String connectStr = "jdbc:derby:" + dir + "/ChatbotDB;create=true";
	
	public DatabaseConnection(){
		try{
			conn = DriverManager.getConnection(connectStr);
		} catch(SQLException se) {
			for (SQLException e = se; e != null; e = e.getNextException()) {
				System.err.printf("%s: %s%n", e.getSQLState(), e.getMessage());
			}
		}
	}
	
	public boolean getState() {
		try {
			return !conn.isClosed();
		} catch (SQLException se) {
			for (SQLException e = se; e != null; e = e.getNextException()) {
				System.err.printf("%s: %s%n", e.getSQLState(), e.getMessage());
			}
		}
		return false;
	}
}
