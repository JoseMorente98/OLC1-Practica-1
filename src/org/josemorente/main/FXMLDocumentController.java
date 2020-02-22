/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template archivo, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import org.josemorente.controlador.ControladorArbol;
import org.josemorente.controlador.ControladorLexico;
import org.josemorente.controlador.ControladorToken;

/**
 *
 * @author josem
 */
public class FXMLDocumentController implements Initializable {
    
    /*VARIABLES*/
    private ObservableList<String> opciones;
    @FXML ComboBox combo;
    @FXML TextArea textAreaEntrada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    /**
     * CARGAR ARCHIVO
     * @param event
     */
    @FXML
    private void cargarArchivo(ActionEvent event) {
        textAreaEntrada.clear();
        Stage stage = new Stage();
        FileChooser selectorArchivo = new FileChooser();
        selectorArchivo.setTitle("Abrir Recurso de Archivos");
        selectorArchivo.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ER File", "*.er"));
        File seleccionArchivo = selectorArchivo.showOpenDialog(stage);
        
        if (seleccionArchivo != null) {
            FileReader lectorArchivo = null;
            BufferedReader bufferedReader = null;
            try {
                File archivo = seleccionArchivo;
                lectorArchivo = new FileReader (archivo);
                bufferedReader = new BufferedReader(lectorArchivo);

                String lineaTexto;
                while((lineaTexto = bufferedReader.readLine())!= null){
                    textAreaEntrada.appendText(lineaTexto+ "\n");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }finally{
                try{                    
                   if( lectorArchivo != null ){   
                      lectorArchivo.close();     
                   }
                }catch (Exception e2){ 
                   e2.printStackTrace();
                }
            }            
        }
    }
    
    /**
     * GUARDAR ARCHIVO
     * @param event
     */
    @FXML
    private void guardarComoArchivo(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser selectorArchivo = new FileChooser();
        selectorArchivo.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ER File", "*.er"));
	File archivo = selectorArchivo.showSaveDialog(stage);
	if(archivo != null){
            FileWriter escritorArchivo = null;
            BufferedWriter bufferedWriter = null;
            try {
                escritorArchivo = new FileWriter(archivo, false);
                bufferedWriter = new BufferedWriter(escritorArchivo);

                String texto = textAreaEntrada.getText();
                bufferedWriter.write(texto, 0, texto.length());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.close();
                } catch (Exception e2) {
                    textAreaEntrada.appendText(e2.toString());
                }
            }
	}
    }
    
    /**
     * SCANNEAR ENTRADA
     * @param event
     */
    @FXML
    private void scannearEntrada(ActionEvent event) {
        ControladorToken.getInstance().limpiarArrayList();
        ControladorLexico.getInstance().scanner(textAreaEntrada.getText());
        
        if(ControladorToken.getInstance().getArrayListTokenError().size() > 0) {
            
        } else {
            ControladorArbol.getInstance().generarArbol();
        }
        
        /*for(Token token: TokenController.getInstancia().getArrayListTokens()) {
            if (token.getDescripcion().equals("TK_Simbolo")) {
                System.out.println(token);            
            }
        }*/
        //SetController.getInstancia().assemble_Sets();
    }
    
}
