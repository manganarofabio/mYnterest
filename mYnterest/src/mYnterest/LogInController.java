package mYnterest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.HomeModel;
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
import javafx.scene.layout.BorderPane;
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
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
			BorderPane root = (BorderPane)loader.load();
			HomeController controller = loader.getController();
			controller.setModel(new HomeModel(new User(user,password)));
			
			
			Scene home_scene = new Scene(root);
			
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
    	User u = new User(user, password);
    	
    	if(user.isEmpty() || password.isEmpty())	{
    		
    		errTxt.setText("Non valido!");
    		errTxt.setVisible(true);
     	}
    	else {
    		if(logInModel.insertUser(u)==false)	{
    			errTxt.setText("Utente già esistente");
    			
    		}
    		else {
    			errTxt.setText("utente creato");
    			if (logInModel.createDirDb(u))	{
    				System.out.println("directory e Db creati\n"); //creati directory personale e database interessi
    			}
    			
    		}
    	}
    	
    	
    	

    }
    
    public void setModel (Model m)	{
    	this.logInModel=m;
    }

    
    
    
}
