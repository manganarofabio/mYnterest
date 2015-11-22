package mYnterest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Model;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LogInController {

	Model logInModel;
	
    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Button btnSignIn;
    
    @FXML
    private TextField errTxt;

    @FXML
    void logIn(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	
    	String user = txtUser.getText();
    	String password = txtPass.getText();
    	
	if(user.isEmpty() || password.isEmpty())	{
    		
    	errTxt.setText("Non valido!");
    	errTxt.setVisible(true);
    }
	
	else{   //lavori in corso
		
		if(logInModel.logInUser(new User(user,password)) == true){
			//change scene
			
			Parent home_parent = FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene home_scene = new Scene(home_parent);

			
			Stage primaryStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(home_scene);
			primaryStage.show();

			
			
		}
		else 
			errTxt.setText("utente o password errati");
			
		
		
		
	}
    	
    	
    	
    	

    }

    @FXML
    void newUser(ActionEvent event) throws InterruptedException, ClassNotFoundException, SQLException {
    	String user = txtUser.getText();
    	String password = txtPass.getText();
    	
    	if(user.isEmpty() || password.isEmpty())	{
    		
    		errTxt.setText("Non valido!");
    		errTxt.setVisible(true);
     	}
    	else {
    		if(logInModel.insertUser(new User(user, password))==false)	{
    			errTxt.setText("Utente già esistente");
    		}
    		else {
    			errTxt.setText("utente creato");
    		}
    	}
    	
    	
    	

    }
    
    public void setModel (Model m)	{
    	this.logInModel=m;
    }

    
    
    
}
