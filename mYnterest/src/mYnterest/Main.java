package mYnterest;
	
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			File f = new File("Utenti.db");
			
			if(!f.exists()) { //controllo esistenza db
		
			Class.forName("org.sqlite.JDBC"); 
			Connection con = DriverManager.getConnection("jdbc:sqlite:Utenti.db"); 
			Statement stat = con.createStatement();
			stat.executeUpdate("create table Users (name varchar PRIMARY KEY," + 
													"password varchar);");
		
		    con.close(); 
		    
			}
			
		    
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			BorderPane root = (BorderPane)loader.load();
			LogInController controller = loader.getController();
			controller.setModel(new Model());
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
