import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
//import org.apache.derby.*;
//derby.jarがライブラリーにあるかどうかチェック用。なければ↑これでエラーになる。
//起動時にclasspath指定必要かも
//java -classpath "c:\Program Files\Java\db-derby-bin\lib\derby.jar;." Main
//@SuppressWarnings("unused")

public class DatabaseConnection {
	Connection conn;
	String dir = System.getProperty("user.dir");
	String connectStr = "jdbc:derby:" + dir + "/ChatbotDB;create=true";

	public DatabaseConnection(){
		try{
			conn = DriverManager.getConnection(connectStr);
			System.out.print( checkInitial() );
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

	/*
	 * checkInitial()
	 * DB初期値のチェック
	 */
	private HashSet<String> checkInitial() {
		// データベースのAPPスキーマに登録されているテーブル一覧
		HashSet<String> existTableName = new HashSet<String>();
		try {
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getTables(null,"APP","%",null); // 大文字
			try {
				while (rs.next()) {
					String tableName = rs.getString("TABLE_NAME");
					existTableName.add(tableName);
				}
			} finally {
			    rs.close();
			}
		} catch (SQLException se) {
			for (SQLException e = se; e != null; e = e.getNextException()) {
				System.err.printf("%s: %s%n", e.getSQLState(), e.getMessage());
			}
		}
	    return existTableName;
	}
}
