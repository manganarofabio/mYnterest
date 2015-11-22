package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {

	
	
	
	public boolean insertUser (User u) throws ClassNotFoundException, SQLException	{
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
//		Statement stat = con.createStatement();
		
		String templateCheck = "select * from Users where name=?";//modello di query
		String templateCreate = "insert into Users (name, password) VALUES (?,?)";

		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		statCheck.setString(1,u.getName());
		
		
	
//		ResultSet rs = stat.executeQuery("select * from Users where name='" + u.getName() + "';");
		
		 if(statCheck.execute() == true){
			 statCheck.close();
			 con.close();
			 return false;
			 
		 }
		 else {
			 statCreate.setString(1,u.getName());
			 statCreate.setString(2,u.getPassword());
			 statCreate.execute();
			 
		
			//stat.executeUpdate("insert into Users (name, password) VALUES ('" + u.getName() + "','" + u.getPassword() +"');");
			 statCreate.close();
			 con.close();
			 return true;
		 }
	    
	}
}
