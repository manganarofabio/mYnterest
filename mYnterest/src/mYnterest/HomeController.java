package mYnterest;

import java.sql.SQLException;
import java.util.Optional;

import Model.HomeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
	import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
	
public class HomeController {

	 @FXML
	 private Button btnAddInt;
	 
	HomeModel model;
	
	public void setModel(HomeModel model)	{
		this.model=model;
	}
	
	

	   

	@FXML
	void addInterest(ActionEvent event) throws ClassNotFoundException, SQLException {
		TextInputDialog dialog = new TextInputDialog("Interesse");
		dialog.setTitle("Nuovo interesse");
		dialog.setHeaderText("Inserisci il nome del nuovo interesse");
		dialog.setContentText("Nuovo interesse");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Nuovo interesse: " + result.get());
		
		    if(model.createInterest(new Interest(result.get())))	{
		    	System.out.println("Creato nuovo interesse:" + result.get());
		    }
		    else 
		    	System.out.println("Errore in creazione interesse");
		}
	
	}
	
	
	@FXML
	void deleteInterest(ActionEvent event)	{
		
	}

	}
