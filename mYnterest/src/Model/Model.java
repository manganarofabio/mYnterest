package Model;

import java.io.File;
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
		
		String templateCheck = "select * from Users where name=?";//modello di query
		String templateCreate = "insert into Users (name, password) VALUES (?,?)";

		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		statCheck.setString(1,u.getName());
		
		ResultSet rs = statCheck.executeQuery();
		
		 if(rs.next()){
			 
			 statCheck.close();
			 con.close();
			 return false;    //ritorna falso se l'utente esiste già altrimenti lo crea e torna true
			 
		 }
		 else {
			 statCreate.setString(1,u.getName());
			 statCreate.setString(2,u.getPassword());
			 statCreate.execute();
			 	  	
			 statCreate.close();
			 con.close();
			 return true;
		 }
	    
	}
	
	
	
	public boolean logInUser(User u) throws ClassNotFoundException, SQLException{  //da ricordare la gestione degli errori
		
		ResultSet rs;
		
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
		
		String templateCheck = "select * from Users where name=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,u.getName());
		
		rs = statCheck.executeQuery();
		if(rs.next()){
		
			
			//l'utente esiste quindi controllo la password
			if(rs.getString("password").equals(u.getPassword())){
				
			
				return true;  //utente verificato
			}
			    
			
		}
			
		return false;  //utente non esitente oppure password sbagliata
	}
	
	
	//Implementare per ogni SO!!
	public boolean createDirDb (User u) throws ClassNotFoundException, SQLException	{
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.indexOf("win") >= 0) {
			System.out.println("This is Windows");
			File dir = new File("C:\\InterestOf" + u.getName()); 
			
			//CREAZIONE CARTELLA
			if(dir.mkdir())	{
			
			//CREAZIONE DB INTERESSI
			Class.forName("org.sqlite.JDBC"); 
			Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + u.getName() + "\\Interessi.db"); 
			Statement stat = con.createStatement();
			stat.executeUpdate("create table Interesse (name varchar PRIMARY KEY)");
			
			return true;
			}
			
		} else if (OS.indexOf("mac") >= 0) {
			System.out.println("This is Mac");
		} else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
			System.out.println("This is Unix or Linux");
		} else {
			System.out.println("Your OS is not support!!");
		}
		
	
		return false;
		
	}
	
	
	
	
	
}


