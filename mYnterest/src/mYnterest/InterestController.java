package mYnterest;

import java.net.URL;
import java.util.ResourceBundle;

import Model.InterestModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class InterestController implements Initializable {

	private InterestModel im;
	
    @FXML
    private Label lbl;

	public InterestController (InterestModel im)	{
		this.im=im;
	}
	
	

	@Override
	public void initialize(URL url, ResourceBundle resource) {
	
		System.out.println(im.getI().getName());
		lbl.setText(im.getI().getName());
		
	}
	
	
}
