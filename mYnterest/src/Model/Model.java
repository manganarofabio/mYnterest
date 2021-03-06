package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;

public class Model {

	
	
	
	public boolean insertUser (User u) throws ClassNotFoundException, SQLException	{
		//Class.forName("org.sqlite.JDBC"); 
		//Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
		
		Connection con = MyConnection.connectToUtenti();
		
		String templateCheck = "select * from Users where name=?";//modello di query
		String templateCreate = "insert into Users (name, password) VALUES (?,?)";

		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		statCheck.setString(1,u.getName());
		
		ResultSet rs = statCheck.executeQuery();
		
		 if(rs.next()){
			 
			 statCheck.close();
			 con.close();
			 return false;    //ritorna falso se l'utente esiste gi� altrimenti lo crea e torna true
			 
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
		
		//Class.forName("org.sqlite.JDBC"); 
		//Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
		
		Connection con = MyConnection.connectToUtenti();
		
		String templateCheck = "select * from Users where name=?";
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		statCheck.setString(1,u.getName());
		
		rs = statCheck.executeQuery();
		if(rs.next()){
		
			
			//l'utente esiste quindi controllo la password
			if(rs.getString("password").equals(u.getPassword())){
				
				con.close();
				return true;  //utente verificato
			}
			    
			
		}
		con.close();	
		return false;  //utente non esitente oppure password sbagliata
	}
	
	
	
	public boolean deleteUserDb(User u) throws SQLException, IOException{
		
		//System.out.println("delete ok");
		int rs;
		Connection con = MyConnection.connectToUtenti();
		
		String templateDelete = "delete from Users where name=?";
		PreparedStatement statDelete= con.prepareStatement(templateDelete);
		statDelete.setString(1,u.getName());
		rs = statDelete.executeUpdate();
		
		
		if(rs == 1){ //utente cancellato dal db ->elimino la cartella
			
			//System.out.println("delete ok");
			
			
		
			File[] roots = File.listRoots();  //valido per tutti gli os
			
			File dir = new File(roots[0] + "InterestOf" + u.getName());
			
			Path path = Paths.get(roots[0] + "InterestOf" + u.getName());
			
			if(Files.exists(path, LinkOption.NOFOLLOW_LINKS)){
				
							FileUtils.forceDelete(dir);
							System.out.println("cartella eliminata");
							
							con.close();
							return true;
							
			}
			
			
			else{   //cartella gi� eliminata
				con.close();
				return true;
			}
			
		}
		
		con.close();
		return false;
	}
	
	
	public boolean createDirDb (User u) throws ClassNotFoundException, SQLException	{
		
		
		//String OS = System.getProperty("os.name").toLowerCase();
		
		//if (OS.indexOf("win") >= 0) {
			//System.out.println("This is Windows");
		

			File[] roots = File.listRoots();  //valido per tutti gli os
			File dir = new File(roots[0] + "InterestOf" + u.getName()); 
			
			//CREAZIONE CARTELLA
			if(dir.mkdir())	{
			
			//CREAZIONE DB INTERESSI
			//Class.forName("org.sqlite.JDBC"); 
			//Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + u.getName() + "\\Interessi.db"); 
			
			Connection con = MyConnection.connectToInteressi(u);
			
			Statement stat = con.createStatement();
			stat.executeUpdate("create table Interesse (name varchar PRIMARY KEY)");
			stat.executeUpdate("create table Notizia (titolo varchar, "
													+ "URL varchar,"
													+ "contenuto varchar, "
													+ "interesse varchar references Interesse, "
													+ "PRIMARY KEY (titolo, interesse))");
			
			con.close();
			return true;
			}
			
		/*} else if (OS.indexOf("mac") >= 0) {
			System.out.println("This is Mac");
		} else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
			System.out.println("This is Unix or Linux");
		} else {
			System.out.println("Your OS is not support!!");
		}*/
		
	
			else{
			
				return false;
			}
	}
		
}
	
	
	
	
	



