package persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connexion {
		
	
	static Connexion inst;

    static final String DB_URL = "jdbc:oracle:thin:@oracle.fil.univ-lille1.fr:1521:filora";

    static public Connexion getInstance() {
        if (inst == null) {
            inst = new Connexion();
        }
        return inst;
    }

    public static Connection conn;

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        Connexion.conn = conn;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public void startConnection() throws SQLException, Exception {
        conn = DriverManager.getConnection(DB_URL, "LEQUERTIER", "saturnin");
        conn.setAutoCommit(true);
    }

    private Connexion() {

    }
	
	public PreparedStatement createRequest(String request) throws SQLException
	{
		return conn.prepareStatement(request);
    }
}

