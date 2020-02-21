/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.modelo;

import java.util.ArrayList;

/**
 *
 * @author josem
 */
public class Conjunto {
    private String nombre;
    private ArrayList<String> conjuntos;

    public Conjunto() {
    }

    public Conjunto(String nombre, ArrayList<String> conjuntos) {
        this.nombre = nombre;
        this.conjuntos = conjuntos;
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
     * @return the conjuntos
     */
    public ArrayList<String> getConjuntos() {
        return conjuntos;
    }

    /**
     * @param conjuntos the conjuntos to set
     */
    public void setConjuntos(ArrayList<String> conjuntos) {
        this.conjuntos = conjuntos;
    }
    
    @Override
    public String toString() {
        return "CONJUNTO { \n"
            + "    Nombre: "+ nombre + "\n"
            + "    Elementos: " + conjuntos + "\n"
            + "}";
    }
}
