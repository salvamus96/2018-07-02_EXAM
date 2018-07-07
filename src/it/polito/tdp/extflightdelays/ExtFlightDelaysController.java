

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.AirportDistance;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
	    
    	try {
	    	
    		try {
    			
    			double minDistance = Double.parseDouble(this.distanzaMinima.getText());
    			
    			model.creaGrafo(minDistance);
    			
    			
    			this.cmbBoxAeroportoPartenza.getItems().clear();
    			this.cmbBoxAeroportoPartenza.getItems().addAll(model.getAllAirport());
    			
    			// abilitazione dei bottoni successivi
    			this.btnAeroportiConnessi.setDisable(false);
    			this.btnCercaItinerario.setDisable(false);
    			
    			this.txtResult.appendText("Grafico creato correttamente!\n");
  
    			
    		}catch(NumberFormatException e) {
    			this.txtResult.appendText("ERRORE: inserire valori numerici validi!\n");
    		}
    		
	    }catch (RuntimeException e) {
	    	this.txtResult.appendText("ERRORE: caricamento voli non effettuato!\n");
	    }
    	
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
	    	
    		try {
    			
    			Airport partenza = this.cmbBoxAeroportoPartenza.getValue();
    			if (partenza == null) {
    	    		this.txtResult.appendText("ERRORE: selezionare un aeroporto di partenza!\n");
    	    		return;
    	    	}
    			
    			List <AirportDistance> adiacenti = this.model.getAdiacenti(partenza);
    			if (adiacenti.isEmpty()) {
    				this.txtResult.appendText("Non ci sono aeroporti adiacenti a " + partenza + "\n");
    	    		return;
    			}
    			
    			this.txtResult.appendText("Aeroporti adiacenti a " + partenza + "\n");
    			Collections.sort(adiacenti);
    			for (AirportDistance a : adiacenti) {
    				this.txtResult.appendText(a + "\n");
    				
    			}
    			
    		}catch(NumberFormatException e) {
    			this.txtResult.appendText("ERRORE: inserire valori numerici validi!\n");
    		}
 
	    }catch (RuntimeException e) {
	    	this.txtResult.appendText("ERRORE: caricamento voli non effettuato!\n");
	    }
    	
    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
	    	
    		try {
    			
    			Airport partenza = this.cmbBoxAeroportoPartenza.getValue();
    			if (partenza == null) {
    	    		this.txtResult.appendText("ERRORE: selezionare un aeroporto di partenza!\n");
    	    		return;
    	    	}
    			
    			double maxDistanceTot = Double.parseDouble(this.numeroVoliTxtInput.getText());
    			
    			List <AirportDistance> camminoMassimo = model.getCamminoMassimo(partenza, maxDistanceTot);
    			
    			this.txtResult.appendText("Il cammino massimo da " + partenza + " è: \n");
    			
    			if (camminoMassimo.isEmpty()) {
    				this.txtResult.appendText("Cammino inesistente\n");
    				return;
    			}
    			
    			for (AirportDistance a : camminoMassimo)
    				this.txtResult.appendText(a + "\n");
    			
    			this.txtResult.appendText("\nLa distanza totale percorsa è " + model.getDistanceTot());    				
    		
    			
    		}catch(NumberFormatException e) {
    			this.txtResult.appendText("ERRORE: inserire valori numerici validi!\n");
    		}
    		
    		
    		
	    }catch (RuntimeException e) {
	    	this.txtResult.appendText("ERRORE: caricamento voli non effettuato!\n");
	    }
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        
        this.txtResult.setStyle("-fx-font-family: monospace");
        this.btnAeroportiConnessi.setDisable(true);
		this.btnCercaItinerario.setDisable(true);
		
		
    }
    
    public void setModel(Model model) {
		this.model = model;
		
		
	}
}

