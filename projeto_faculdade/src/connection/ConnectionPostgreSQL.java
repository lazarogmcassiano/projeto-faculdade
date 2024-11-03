package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPostgreSQL {
	private static final String url = "jdbc:postgresql://localhost:5432/projeto-faculdade";
	private static final String password = "123123";
	private static final String user = "postgres";
	private static Connection connection = null;

	private static void conectar() {
		try {
			if (connection == null) {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
			System.out.println("conectou com sucesso!");
			}
		} catch (Exception e) {
			System.out.println("Não conectou");
			e.printStackTrace();
		}
		
	}
	
	static {
		conectar();
	}
	
	public ConnectionPostgreSQL (){
		conectar();
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
