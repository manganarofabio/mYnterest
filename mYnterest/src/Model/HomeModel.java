package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mYnterest.Interest;

public class HomeModel {
	
	User u;
	
	public HomeModel(User u) {
		super();
		this.u = u;
	}

	public boolean createInterest(Interest i) throws ClassNotFoundException, SQLException {
	
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + u.getName() + "\\Interessi.db"); 
		
		String templateCheck = "select * from Interesse where name=?";  //modello di querys
		String templateCreate = "insert into Interesse (name) VALUES (?)";
		
		PreparedStatement statCheck = con.prepareStatement(templateCheck);
		
		PreparedStatement statCreate = con.prepareStatement(templateCreate);
		
		statCheck.setString(1, i.getName());
	
		
		ResultSet rs = statCheck.executeQuery();
		
		 if(rs.next()){
			 
			 statCheck.close();
			 con.close();
			 return false;    //ritorna falso se l'interesse già esiste
			 
		 }
		 else {
			 statCreate.setString(1,i.getName());
			 statCreate.execute();
			 	  	
			 statCreate.close();
			 con.close();
			 return true;
		 }
	}
}
