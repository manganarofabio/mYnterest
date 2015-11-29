package mYnterest;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.HomeModel;
import Model.InterestModel;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
	
public class HomeController implements Initializable {

	
	 @FXML
	 private Button btnAddInt;
	 
	HomeModel model;
	
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public HomeController(HomeModel hm)	{
		this.model=hm;
	}
	
	
	
	
	public void setModel(HomeModel model)	{
		this.model=model;
	}
	
    @FXML
    private GridPane gridBtn;

	

	   

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
		    	buttons.add(new Button(result.get()));
		    	gridBtn.add(buttons.get(buttons.size()-1), (buttons.size()-1)%3, (buttons.size()-1)/3); //modificare numero colonne in initialize()
		    }
		    else 
		    	System.out.println("Errore in creazione interesse");
		}
	
	}
	
	
	@FXML
	void deleteInterest(ActionEvent event)	{
		
	}


	@Override
	public void initialize(URL url, ResourceBundle resource) {
		
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try {
			System.out.println(model.getU().getName());
			Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + model.getU().getName() + "\\Interessi.db"); //DA MODIFICARE A SECONDA DEL SISTEMA OPERATIVO!!!!
			String templateLoad = "select * from Interesse";//modello di query
			PreparedStatement statLoad = con.prepareStatement(templateLoad);
			ResultSet rs = statLoad.executeQuery();
			
			
			
			while(rs.next())	{
				System.out.println(model.getU().getName());
				String interesse = rs.getString(1);
				Button but = new Button(interesse);
				buttons.add(but);
				but.setPrefSize(100, 100);;
				System.out.println("ciaooooouuuuuuuuuuuu");
			}
			
			System.out.println(buttons.size());
			
			for(int i=0; i<buttons.size(); i++)	{
			buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						goToInterest(event);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			}

			int j=0;
			for(int i=0; i<buttons.size(); i++)	{
				gridBtn.add(buttons.get(i), i, j);
				if (i==2)	
					j++;
					
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	public void goToInterest(ActionEvent event) throws IOException	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Interest.fxml"));
		
		Interest i = new Interest(((Labeled) event.getSource()).getText());
		
		InterestController ic = new InterestController(new InterestModel(model.getU(), i));
			
		loader.setController(ic);
		
		System.out.println(i.getName());
			
		BorderPane root = (BorderPane)loader.load(); //qui viene eseguito il metodo initialize() che inizializza il controller
		
	
		Scene home_scene = new Scene(root);
		
		Stage primaryStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(home_scene);
		
		primaryStage.show();
		
	
	}


    
	
	}
	
	
