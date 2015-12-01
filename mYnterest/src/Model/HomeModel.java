package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import mYnterest.Interest;

public class HomeModel {
	
	User u;
	
	public HomeModel(User u) {
		super();
		this.u = u;
	}

	
	
	public User getU() {
		return u;
	}



	public void setU(User u) {
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
	
	
	
	public void removeInterest (String interestName, ArrayList<Button> buttons, GridPane gridBtn) throws SQLException, ClassNotFoundException	{
		
		System.out.println(buttons.size());
		gridBtn.getChildren().clear();
		
		
		for(int i=0; i<buttons.size(); i++)	{
			if(buttons.get(i).getText()==interestName)	{
				buttons.remove(i);
			}
		}
		
		Class.forName("org.sqlite.JDBC"); 
		Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + u.getName() + "\\Interessi.db"); 
		
		String templateDelN = "delete from Notizia where interesse=?";
		String templateDelI = "delete from Interesse where name=?";
		
		PreparedStatement statDelN = con.prepareStatement(templateDelN);
		PreparedStatement statDelI = con.prepareStatement(templateDelI);
		
		statDelN.setString(1, interestName);
		statDelI.setString(1, interestName);
		
		
		/*ResultSet rs = */statDelN.execute();
		/*ResultSet rs1 = */statDelI.execute();
		
		statDelN.close();
		statDelI.close();
		
		con.close();
		
		
		
		this.createGrid(buttons, gridBtn); //aggiungiamo i pulsanti modificati al gridPane
		
		
	}

	public void createGrid (ArrayList<Button> buttons, GridPane gp)	{
		System.out.println(buttons.size());
		
		for(int k=0; k<buttons.size(); k++)	{
			System.out.println(buttons.get(k).getText());
		}
		
		int c=0;
		int r=0;
		for(int i=0; i<buttons.size(); i++)	{
			gp.add(buttons.get(i), c, r);
			
			if (c==2)	{
				c=0;
				r++;
			}
			else
				c++;
		}	
	}
	
}
