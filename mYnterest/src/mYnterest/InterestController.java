package mYnterest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.HomeModel;
import Model.Interest;
import Model.InterestModel;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InterestController implements Initializable {

	private InterestModel im;
	
    @FXML
    private Label lbl;

    @FXML
    private Button btnBack;
    
    
	public InterestController (InterestModel im)	{
		this.im=im;
	}
	

	 

    @FXML
    void backToHome(ActionEvent event) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
		
		HomeController hc = new HomeController(new HomeModel(im.getU()));
		
		loader.setController(hc);
		
		BorderPane root = null;
		try {
			root = (BorderPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Scene home_scene = new Scene(root);
		
		Stage primaryStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(home_scene);
		
		
		
		primaryStage.show();
		
	
		
		
	    }


	@Override
	public void initialize(URL url, ResourceBundle resource) {
	
		System.out.println(im.getI().getName());
		lbl.setText(im.getI().getName());
		
	}
	
	
}
