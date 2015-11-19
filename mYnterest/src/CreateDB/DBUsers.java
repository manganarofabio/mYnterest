package CreateDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUsers {

	
	
	
	public static void main (String[] args) throws ClassNotFoundException, SQLException	{
		
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
		Statement stat = con.createStatement();
		stat.executeUpdate("create table Users (name varchar PRIMARY KEY," + 
												"password varchar);");
	
	    con.close(); 
		
		
	}
	
}
