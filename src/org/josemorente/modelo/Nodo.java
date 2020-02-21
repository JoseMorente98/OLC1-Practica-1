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
    private boolean anulable;
    private Nodo nodoDerecho;
    private Nodo nodoIzquierdo;

    public Nodo() {
    }

    public Nodo(int indice, String elemento, String primero, String ultimo, boolean anulable, Nodo nodoDerecho, Nodo nodoIzquierdo) {
        this.indice = indice;
        this.elemento = elemento;
        this.primero = primero;
        this.ultimo = ultimo;
        this.anulable = anulable;
        this.nodoDerecho = nodoDerecho;
        this.nodoIzquierdo = nodoIzquierdo;
    }
    
    public Nodo(int indice, String elemento) {
        this.indice = indice;
        this.elemento = elemento;
        this.primero = "0";
        this.ultimo = "0";
        this.anulable = false;
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
     * @return the anulable
     */
    public boolean isAnulable() {
        return anulable;
    }

    /**
     * @param anulable the anulable to set
     */
    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
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
     * IMPRIMIR NODOS
     * @param path
     */
    public void Imprimir(String path) {
        FileWriter file = null;
        PrintWriter writer;
        try
        {
            file = new FileWriter("aux_grafico.dot");
            writer = new PrintWriter(file);
            writer.print(getGrafoArbol());
        } 
        catch (Exception e){
            System.err.println("Error al escribir el archivo aux_grafico.dot");
        }finally{
           try {
                if (null != file)
                    file.close();
           }catch (Exception e2){
               System.err.println("Error al cerrar el archivo aux_grafico.dot");
           } 
        }                        
        try{
          Runtime rt = Runtime.getRuntime();          
          rt.exec( "dot -Tjpg -o "+path+" aux_grafico.dot");
          Thread.sleep(500);
        } catch (Exception ex) {
            System.out.println(ex);
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
        }            
    }
    
    /**
     * GENERAR ARBOL
     * @param path
     */
    private String getGrafoArbol() {
        return "digraph grafica{\n" +
               "rankdir=TB;\n" +
               "node [shape = record, style=filled, fillcolor=seashell2];\n"+
                getCuerpo()+
                "}\n";
    }
    
    /**
     * OBTENER CUERPO GRAFO
     */
    private String getCuerpo() {
        String textoNodo;
        String str = elemento.replace('"', ' ');
        String anuable  = "F";
        
        if (str.equals("|")) {
            str = "or";
        } 
        if (isAnulable()) {
            anuable = "V";
        }
        
        if(nodoIzquierdo==null && nodoDerecho==null){
             textoNodo="nodo"+indice+" [ label =\""+primero+"|"+str +"\\l"+ anuable  +"|"+ultimo+"\"];\n";
        }else{
             textoNodo="nodo"+indice+" [ label =\""+primero+"|"+ str +"\\l"+ anuable   +"|"+ultimo+"\"];\n";
        }
        if(nodoIzquierdo!=null){
            textoNodo=textoNodo + nodoIzquierdo.getCuerpo() +
               "nodo"+indice+"->nodo"+nodoIzquierdo.indice +"\n";
        }
        if(nodoDerecho!=null){
            textoNodo=textoNodo + nodoDerecho.getCuerpo() +
               "nodo"+indice+"->nodo"+nodoDerecho.indice+"\n";                    
        }
        return textoNodo;
    } 
}
