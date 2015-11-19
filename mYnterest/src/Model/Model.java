package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {

	
	
	
	public boolean insertUser (User u) throws ClassNotFoundException, SQLException	{
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
		Statement stat = con.createStatement();
	
		ResultSet rs = stat.executeQuery("select * from Users where name='" + u.getName() + "';");
		
		 if(rs.next())	
			 {
			 con.close();
			 return false;
		 }
		 else {
		
			stat.executeUpdate("insert into Users (name, password) VALUES ('" + u.getName() + "','" + u.getPassword() +"');");
			
			 con.close();
			 return true;
		 }
	    
	}
}
