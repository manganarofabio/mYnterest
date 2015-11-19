package mYnterest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Model;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    void signIn(ActionEvent event) {

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
