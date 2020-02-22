/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.modelo;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author josem
 */
public class Nodo {
    private int indice;
    private String elemento;
    private String primero;
    private String ultimo;
    private boolean esAnulable;
    private boolean esHoja;
    private Nodo nodoDerecho;
    private Nodo nodoIzquierdo;
    private static int contador = 1;

    public Nodo() {
    }

    public Nodo(String elemento, String primero, String ultimo, boolean esAnulable, boolean esHoja, Nodo nodoDerecho, Nodo nodoIzquierdo) {
        this.indice = contador++;
        this.elemento = elemento;
        this.primero = primero;
        this.ultimo = ultimo;
        this.esAnulable = esAnulable;
        this.esHoja = esHoja;
        this.nodoDerecho = nodoDerecho;
        this.nodoIzquierdo = nodoIzquierdo;
    }
    
    public Nodo(String elemento, boolean esAnulable, Nodo nodoDerecho, Nodo nodoIzquierdo) {
        this.indice = contador++;
        this.elemento = elemento;
        this.primero = "";
        this.ultimo = "";
        this.esAnulable = esAnulable;
        this.esHoja = false;
        this.nodoDerecho = nodoDerecho;
        this.nodoIzquierdo = nodoIzquierdo;
    }
    
    public Nodo(String elemento) {
        this.indice = contador++;
        this.elemento = elemento;
        this.primero = "";
        this.ultimo = "";
        this.esAnulable = false;
        this.esHoja = false;
        this.nodoDerecho = null;
        this.nodoIzquierdo = null;
    }

    /**
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * @return the elemento
     */
    public String getElemento() {
        return elemento;
    }

    /**
     * @param elemento the elemento to set
     */
    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    /**
     * @return the primero
     */
    public String getPrimero() {
        return primero;
    }

    /**
     * @param primero the primero to set
     */
    public void setPrimero(String primero) {
        this.primero = primero;
    }

    /**
     * @return the ultimo
     */
    public String getUltimo() {
        return ultimo;
    }

    /**
     * @param ultimo the ultimo to set
     */
    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    /**
     * @return the esAnulable
     */
    public boolean isEsAnulable() {
        return esAnulable;
    }

    /**
     * @param esAnulable the esAnulable to set
     */
    public void setEsAnulable(boolean esAnulable) {
        this.esAnulable = esAnulable;
    }

    /**
     * @return the esHoja
     */
    public boolean isEsHoja() {
        return esHoja;
    }

    /**
     * @param esHoja the esHoja to set
     */
    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    /**
     * @return the nodoDerecho
     */
    public Nodo getNodoDerecho() {
        return nodoDerecho;
    }

    /**
     * @param nodoDerecho the nodoDerecho to set
     */
    public void setNodoDerecho(Nodo nodoDerecho) {
        this.nodoDerecho = nodoDerecho;
    }

    /**
     * @return the nodoIzquierdo
     */
    public Nodo getNodoIzquierdo() {
        return nodoIzquierdo;
    }

    /**
     * @param nodoIzquierdo the nodoIzquierdo to set
     */
    public void setNodoIzquierdo(Nodo nodoIzquierdo) {
        this.nodoIzquierdo = nodoIzquierdo;
    }
    
    /**
     * @param nombre
     */
    public void generarGrafoArbol(String nombre) {    
        FileWriter fileWriter;
        PrintWriter printWriter;
        Runtime runtime;
        fileWriter = null;
        
        try {
            fileWriter = new FileWriter("grafo-arbol.dot");
            
            printWriter = new PrintWriter(fileWriter);
            
            printWriter.print(obtenerDocumento());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileWriter != null) {
                    fileWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        try {
            runtime = Runtime.getRuntime();
            runtime.exec( "dot -Tjpg -o "+nombre+"Arbol.png grafo-arbol.dot");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return String
     */
    private String obtenerDocumento() {
        String cabezaGrafo = "digraph grafica{\n" +
            "rankdir=TB;\n" +
            "node [fillcolor=firebrick3, shape=record, style=bold];\n";
                
        String cuerpoGrafo = obtenerNodo();
        
        String pieGrafo = "}";
        
        return cabezaGrafo + cuerpoGrafo + pieGrafo;
    }
    
    /**
     * @return lineaTexto
     */
    public String obtenerNodo() {
        String elementoNodo = this.getElemento().replace('"', ' ');
        String lineaTexto;
        
        if (elementoNodo.equals("|")) {
            elementoNodo = "O";
        }
        
        if(nodoIzquierdo == null && nodoDerecho == null){
            if(isEsAnulable()) {
                lineaTexto = "Hoja"+indice+" [ label =\""+primero+"|"+elementoNodo +"\\lV|"+ultimo+"\"];\n";
            } else {
                lineaTexto = "Hoja"+indice+" [ label =\""+primero+"|"+elementoNodo +"\\lF|"+ultimo+"\"];\n";
            }
             
        }else{
            if(isEsAnulable()) {
                lineaTexto = "Hoja"+indice+" [ label =\""+primero+"|"+elementoNodo +"\\lV|"+ultimo+"\"];\n";
            } else {
                lineaTexto = "Hoja"+indice+" [ label =\""+primero+"|"+elementoNodo +"\\lF|"+ultimo+"\"];\n";
            }
        }
        if(nodoIzquierdo != null){
            lineaTexto = lineaTexto + nodoIzquierdo.obtenerNodo() +
               "Hoja"+indice+"->Hoja"+nodoIzquierdo.indice +"\n";
        }
        if(nodoDerecho != null){
            lineaTexto = lineaTexto + nodoDerecho.obtenerNodo() +
               "Hoja"+indice+"->Hoja"+nodoDerecho.indice+"\n";                    
        }
        
        return lineaTexto;
    } 
    
}
