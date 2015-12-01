package Model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	
	
	public static Connection connectToUtenti() {  //da rivedere perchè forse rallenta il programma
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:sqlite:Utenti.db");
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return con;
	}
	
	
	public static Connection connectToInteressi(User u){
		
		//per tutti O.S.
		
		File[] roots = File.listRoots();  //prende le directories radici
			
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:sqlite:"+ roots[0] +"InterestOf" + u.getName() + "\\Interessi.db");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return con;
	}
	
	/*static ResultSet ExecuteQuery(String query,Connection con){
		
		String template = query;
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,u.getName());
	*/
		
}
		

