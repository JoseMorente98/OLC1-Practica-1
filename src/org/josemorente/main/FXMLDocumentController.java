/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author josem
 */
public class FXMLDocumentController implements Initializable {
    
    /*VARIABLES*/
    private ObservableList<String> opciones;
    @FXML ComboBox combo;
    @FXML TextArea arearegular;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    /*CARGA DE ARCHIVO*/
    @FXML
    private void cargar_archivo(ActionEvent event) {
         Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OLC FILES", "*.er"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            FileReader fr = null;
            BufferedReader br = null;

        try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         File archivo = selectedFile;
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String line;
         while((line=br.readLine())!=null){
             arearegular.appendText(line+ "\n");
         }
            
            
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }            
        }
        
    }
    
    @FXML
    private void analizar_entrada(ActionEvent event) {
        /*LexicoAnalizer.getInstance().Analizador(arearegular.getText());
        /*for(Token token: TokenController.getInstancia().getArrayListTokens()) {
            if (token.getDescripcion().equals("TK_Simbolo")) {
                System.out.println(token);            
            }
        }*/
        //SetController.getInstancia().assemble_Sets();
    }
    
}
