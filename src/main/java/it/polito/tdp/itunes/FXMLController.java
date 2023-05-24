/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.BilancioAlbum;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Album input = cmbA1.getValue();
    	
    	List<BilancioAlbum> listaAdiacenzeOrdinata = this.model.getAdiacenze(input);
    	
    	for(BilancioAlbum ba: listaAdiacenzeOrdinata) {
    		txtResult.appendText(ba.getAlbum().toString()+ " "+ ba.getBilancio()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Album target = cmbA2.getValue();
    	Album source = cmbA1.getValue();
    	
    	int minimoPeso = 0;
    	
    	if(txtX.getText().equals("")) {
    		txtResult.appendText("inserire un numero valido in X");
    	}
    	else {
    		minimoPeso = Integer.parseInt(txtX.getText());
    	}
    	
    	List<Album> bestPath = this.model.getPath(source, target, minimoPeso);
    	
    	for(Album a: bestPath) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String durata = txtN.getText();
    	int durataNum = 0;
    	
    	if(durata.equals("")) {
    		txtResult.appendText("inserisci un numero");
    	}
    	else {
    		durataNum = Integer.parseInt(durata);
    	}
    	
    	model.buildGraph(durataNum);
    	
    	txtResult.appendText("# vertici: "+ model.getNumVert()+"\n");
    	txtResult.appendText("# archi: "+ model.getNumEdge()+"\n");
    	
    	cmbA1.getItems().setAll(this.model.getVertici());
    	cmbA2.getItems().setAll(this.model.getVertici());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
