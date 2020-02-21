/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.modelo;

/**
 *
 * @author josem
 */
public class Token {
    private int idToken;
    private String lexema;
    private String descripcion;
    private int columna;
    private int fila;

    public Token() {
    }

    public Token(int idToken, String lexema, String descripcion, int columna, int fila) {
        this.idToken = idToken;
        this.lexema = lexema;
        this.descripcion = descripcion;
        this.columna = columna;
        this.fila = fila;
    }

    /**
     * @return the idToken
     */
    public int getIdToken() {
        return idToken;
    }

    /**
     * @param idToken the idToken to set
     */
    public void setIdToken(int idToken) {
        this.idToken = idToken;
    }

    /**
     * @return the lexema
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @param lexema the lexema to set
     */
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }
    
    @Override
    public String toString() {
        return "TOKEN { \n"
            + "    Fila: "+fila+"\n"
            + "    Columna: "+columna+"\n"
            + "    Lexema: "+lexema+"\n"
            + "    Descripcion: "+descripcion+"\n"
            + "}";
    }
    
}
