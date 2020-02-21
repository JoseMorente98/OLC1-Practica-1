/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

import java.util.ArrayList;
import org.josemorente.modelo.Token;

/**
 *
 * @author josem
 */
public class ControladorToken {
    private ArrayList<Token> arrayListTokens = new ArrayList();
    private ArrayList<Token> arrayListTokenError = new ArrayList();
    private int idToken = 1;
    private int idTokenError = 1;
    
    private ControladorToken() {
    }
    
    public static ControladorToken getInstance() {
        return ControladorTokenHolder.INSTANCE;
    }
    
    private static class ControladorTokenHolder {

        private static final ControladorToken INSTANCE = new ControladorToken();
    }
    
    public void agregarToken(int fila, int columna, String lexema, String descripcion)
    {
        Token token = new Token(idToken, lexema, descripcion, columna, fila);
        arrayListTokens.add(token);
        idToken++;
    }

    public void agregarError(int fila, int columna, String lexema, String descripcion)
    {
        Token token = new Token(idTokenError, lexema, descripcion, columna, fila);
        arrayListTokenError.add(token);
        idTokenError++;
    }

    public void limpiarArrayList()
    {
        arrayListTokenError.clear();
        arrayListTokens.clear();
        idToken = 1;
        idTokenError = 1;
    }

    public ArrayList<Token> getArrayListTokens() {
        return arrayListTokens;
    }

    public ArrayList<Token> getArrayListTokenError() {
        return arrayListTokenError;
    }
        
}
