package persistence.mapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OracleConnection {

	private static OracleConnection inst;
	private static Connection conn;
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	private OracleConnection() {
		startConnection();
	}

	public static OracleConnection getInstance() {
		if (inst == null) {
			inst = new OracleConnection();
		}
		return inst;
	}

	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		OracleConnection.conn = conn;
	}

	public String getDB_URL() {
		return DB_URL;
	}

	public void startConnection() {
		try {
			conn = DriverManager.getConnection(DB_URL, "system", "system");
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement createRequestPS(String request) throws SQLException {
		return conn.prepareStatement(request);
	}
	
	public CallableStatement createRequestCS(String request) throws SQLException {
		return conn.prepareCall(request);
	}

}
