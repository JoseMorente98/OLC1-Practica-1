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
public class Siguiente {
    private String idSiguiente;
    private String nombre;
    private String siguientes;

    public Siguiente() {
    }

    public Siguiente(String idSiguiente, String nombre, String siguientes) {
        this.idSiguiente = idSiguiente;
        this.nombre = nombre;
        this.siguientes = siguientes;
    }

    /**
     * @return the idSiguiente
     */
    public String getIdSiguiente() {
        return idSiguiente;
    }

    /**
     * @param idSiguiente the idSiguiente to set
     */
    public void setIdSiguiente(String idSiguiente) {
        this.idSiguiente = idSiguiente;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the siguientes
     */
    public String getSiguientes() {
        return siguientes;
    }

    /**
     * @param siguientes the siguientes to set
     */
    public void setSiguientes(String siguientes) {
        this.siguientes = siguientes;
    }
    
}
