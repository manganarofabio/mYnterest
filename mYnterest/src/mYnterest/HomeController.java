package mYnterest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.HomeModel;
import Model.Interest;
import Model.InterestModel;
import Model.MyConnection;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
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
		    	buttons.get(buttons.size()-1).setOnAction(new EventHandler<ActionEvent>() {
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
		    	
		    	gridBtn.add(buttons.get(buttons.size()-1), (buttons.size()-1)%3, (buttons.size()-1)/3); //modificare numero colonne in initialize()
		    }
		    else 
		    	System.out.println("Errore in creazione interesse");
		}
	
	}
	
	
	@FXML
	void deleteInterest(ActionEvent event) throws ClassNotFoundException, SQLException	{
		
		ArrayList<String> choices = new ArrayList<String>();
		for (Button b : buttons)	{
			choices.add(b.getText());
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<>(buttons.get(0).getText(), choices);
		dialog.setTitle("Elimina interesse");
		dialog.setHeaderText("Quale interesse vuoi eliminare?");
		dialog.setContentText("Scegli l'interesse:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your choice: " + result.get());
		
		    Alert alert = new Alert(AlertType.CONFIRMATION);
		    alert.setTitle("Conferma eliminazione");
		    alert.setHeaderText("Attenzione, stai per eliminare un interesse e tutte le notizie ad esso associate");
		    alert.setContentText("Sei sicuro di voler continuare? Le notizie raccolte non saranno più disponibili");

		    Optional<ButtonType> result2 = alert.showAndWait();
		   	if (result2.get() == ButtonType.OK){
		   		model.removeInterest(result.get(), buttons, gridBtn);
		   	} else {
		   		System.out.println("Si è deciso di non eliminare alcun interesse");
		   	}
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

	


	@Override
	public void initialize(URL url, ResourceBundle resource) {
		
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try {
			//Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\InterestOf" + model.getU().getName() + "\\Interessi.db"); 
			
			Connection con = MyConnection.connectToInteressi(model.getU());
			
		
			String templateLoad = "select * from Interesse";//modello di query
			PreparedStatement statLoad = con.prepareStatement(templateLoad);
			ResultSet rs = statLoad.executeQuery();
			
			
			
		
			while(rs.next())	{  //ciclo di creazione dell'ArrayList dei pulsanti
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

			model.createGrid(buttons, gridBtn); //attacchiamo i bottoni dell'arrayList al pannello
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	
	
	
	


    
	
}
	
	
