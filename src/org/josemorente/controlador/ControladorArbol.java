/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

import java.util.ArrayList;
import org.josemorente.modelo.Nodo;
import org.josemorente.modelo.Token;

/**
 *
 * @author josem
 */
public class ControladorArbol {
    
    private ControladorArbol() {
    }
    
    public static ControladorArbol getInstance() {
        return ControladorArbolHolder.INSTANCE;
    }
    
    private static class ControladorArbolHolder {

        private static final ControladorArbol INSTANCE = new ControladorArbol();
    }
    
    /**
     * GENERAR ARBOL 
     */
    public void generarArbol() {
        ArrayList<Token> arrayList =  ControladorToken.getInstance().getArrayListTokens();
        for (int x = 0; x < arrayList.size(); x++) {
            if (arrayList.get(x).getLexema().equals(">")) {
                String texto = "";
                
                for (int y = x; y > 0; y--) {
                    if (arrayList.get(y).getDescripcion().equals("Identificador")) {
                        texto = arrayList.get(y).getLexema();
                        break;
                    }
                }

                if (arrayList.get(x+1) != null && arrayList.get(x+1).getLexema().equals(".")) {
                    for (int y = x+1; y < arrayList.size(); y++) {
                        if (!arrayList.get(y).getLexema().equals(";")) {
                            if (!arrayList.get(y).getLexema().equals("{") && !arrayList.get(y).getLexema().equals("}")) {
                                ControladorNodo.getInstance().agregarER(arrayList.get(y).getLexema());
                            }
                        } else {
                            ControladorNodo.getInstance().construirArbol(texto);
                            ControladorNodo.getInstance().limpiarVariables();
                            x = y;
                            break;
                        }
                    }
                }
            }
        }
    }
}
