/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;import java.util.Stack;
import org.josemorente.modelo.Nodo;
import org.josemorente.modelo.Siguiente;

/**
 *
 * @author josem
 */
public class ControladorNodo {
    /**
     * PROPIEDADES 
     */
    ArrayList<String> arrayListER;
    ArrayList<String> arrayListAuxiliarER;
    ArrayList<Siguiente> arrayListS;
    Stack stackArbol;
    int contadorSiguiente = 0;
    int contadorHijo = 1; 
    Nodo nodoRaiz;
    String nombreHoja = "";
    
    private ControladorNodo() {
        arrayListER = new ArrayList<>();        
        arrayListAuxiliarER = new ArrayList<>();
        arrayListS = new ArrayList<>();
        stackArbol = new Stack();
        nodoRaiz = null;
    }
    
    public static ControladorNodo getInstance() {
        return ControladorNodoHolder.INSTANCE;
    }
    
    private static class ControladorNodoHolder {

        private static final ControladorNodo INSTANCE = new ControladorNodo();
    }
    
    /**
     * LIMPIAR VARIABLES 
     */
    public void limpiarVariables() {
        nodoRaiz = null;
        contadorSiguiente = 0;
        contadorHijo = 1;
        arrayListER.clear();
        arrayListAuxiliarER.clear();
        arrayListS.clear();
    }
    
    
    /**
     * AGREGA EXPRESION REGULAR
     * @param nombre
     */
    public void agregarER(String nombre) {
        arrayListER.add(nombre);
    }
    
    public void construirArbol(String nombre) {
        ArrayList<String> arrayList = arrayListER;
        if(nombre.length() == 0) {
            nombre = "null" + contadorSiguiente;
        }
        
        for (int i = arrayListER.size() - 1; i >= 0; i--) {
            arrayListAuxiliarER.add(arrayListER.get(i));
        }
        
        for (String cuerpoGrafo: arrayListAuxiliarER) {
            agregarPila(cuerpoGrafo);
        }
        
        nodoRaiz = new Nodo(".", false, new Nodo("#"), (Nodo)stackArbol.pop());
        
        //NUMERACION NODO HOJA
        nodoHoja(nodoRaiz);
        nodoPadre(nodoRaiz);
        concatenarRaiz(nodoRaiz);
        
        //SIGUIENTE
        construirTablaSiguiente(nodoRaiz);
        
        //ARBOL
        nodoRaiz.generarGrafoArbol(nombre);
        generarTablaSiguiente(nombre);
        contadorSiguiente++;
    }
    
    
    /**
     * AGREGAR ELEMENTOS PILA
     * @param nombre
     */
    public void agregarPila(String nombre) {
        Nodo auxDerecha = new Nodo();
        Nodo auxIzquierda = new Nodo();
        boolean esAnulable = false;

        if (nombre.equals("|") || nombre.equals(".")) {
            auxIzquierda = (Nodo) stackArbol.pop();
            auxDerecha = (Nodo) stackArbol.pop();

            if (nombre.equals("|")) {
                if (auxIzquierda.isEsAnulable() || auxDerecha.isEsAnulable()) {
                    esAnulable = true;
                }
            } else if (nombre.equals(".")) {
                if (auxIzquierda.isEsAnulable() && auxDerecha.isEsAnulable()) {
                    esAnulable = true;
                }
            }            
            stackArbol.push(new Nodo(nombre, esAnulable, auxDerecha, auxIzquierda));
            esAnulable = false;
        } else if (nombre.equals("*") || nombre.equals("+") || nombre.equals("?")) {
            auxIzquierda = (Nodo) stackArbol.pop();

            if (nombre.equals("*") || nombre.equals("?")) {
                esAnulable = true;
            } else if (nombre.equals("+")) {
                if (auxIzquierda.isEsAnulable()) {
                    esAnulable = true;
                } else {
                    esAnulable = false;
                }
            } else {
                esAnulable = true;
            }
            stackArbol.push(new Nodo(nombre, esAnulable, null, auxIzquierda));
            esAnulable = false;
        } else {
            stackArbol.push(new Nodo(nombre));
        }
    }
    
    /**
     * NUMERAR NODOS HIJOS 
     * @param nodo
     */
    private void nodoHoja(Nodo nodo) {
        if (nodo != null) {
            if (nodo.getNodoIzquierdo() == null && nodo.getNodoDerecho() == null) {
                

                //Le ingresa el anuable siguiente no
                if (!nodo.getElemento().equals("epsilon")) {
                    nodo.setEsAnulable(false);
                } else {
                    nodo.setEsAnulable(true);
                }
                nodo.setEsHoja(true);
                //le da numeracion
                nodo.setPrimero(String.valueOf(contadorHijo));
                nodo.setUltimo(String.valueOf(contadorHijo));
                contadorHijo++;
            }
            nodoHoja(nodo.getNodoIzquierdo());
            nodoHoja(nodo.getNodoDerecho());
        }
    }

    /**
     * DECISION NODOS 
     * @param nodo
     */
    private void nodoPadre(Nodo nodo) {
        if (nodo != null) {
            if (nodo.getElemento().equals("|")) {
                alternacion(nodo);
            } else if ((nodo.getElemento().equals("*") || nodo.getElemento().equals("?") || nodo.getElemento().equals("+")) && nodo.isEsHoja() == false  ) {
                cuantificacion(nodo);
            } else if (nodo.getElemento().equals(".") && nodo.isEsHoja() == false) {
                concatenacion(nodo);
            }
        }
    }

    /**
     * DECISIÓN OR
     * @param nodo
     */
    private void alternacion(Nodo nodo) {
        if (nodo != null) {
            nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero() + "," + nodo.getNodoDerecho().getPrimero());
            nodo.setUltimo(nodo.getNodoIzquierdo().getUltimo() + "," + nodo.getNodoDerecho().getUltimo());
            nodoPadre(nodo.getNodoIzquierdo());
            nodoPadre(nodo.getNodoDerecho());
        }
    }
    
    /**
     * DECISION + * ? 
     * @param nodo
     */
    private void cuantificacion(Nodo nodo) {
        if (nodo != null) {
            nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero());
            nodo.setUltimo(nodo.getNodoIzquierdo().getUltimo());
            nodoPadre(nodo.getNodoIzquierdo());
            nodoPadre(nodo.getNodoDerecho());
        }
    }
    
    /**
     * DECISION . 
     * @param nodo
     */
    private void concatenacion(Nodo nodo) {
        if (nodo != null) {
            //Parte izquierda y derecha
            if (nodo.getNodoIzquierdo().getElemento().equals(".") 
                || nodo.getNodoDerecho().getElemento().equals(".")
                || nodo.getNodoDerecho().getElemento().contains("0") 
                || nodo.getNodoIzquierdo().getElemento().contains("0")
                || nodo.getPrimero().contains("0")
                || nodo.getUltimo().contains("0")) {
                nodoPadre(nodo.getNodoIzquierdo());
                nodoPadre(nodo.getNodoDerecho());
            }  
            //set first
            if (nodo.getNodoIzquierdo().isEsAnulable()) {
                nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero() + "," + nodo.getNodoDerecho().getPrimero());
            } else {
                nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero());
            }

            //set last
            if (nodo.getNodoDerecho().isEsAnulable()) {
                nodo.setUltimo(nodo.getNodoIzquierdo().getUltimo() + "," + nodo.getNodoDerecho().getUltimo());
            } else {
                nodo.setUltimo(nodo.getNodoDerecho().getUltimo());
            }

            nodoPadre(nodo.getNodoIzquierdo());
            nodoPadre(nodo.getNodoDerecho());
        }
    }
    
    /**
     * CONCATENAR RAIZ
     * @param nodo
     */
    public void concatenarRaiz(Nodo nodo) {
        if (nodo.getNodoIzquierdo().isEsAnulable()) {
                nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero() + "," + nodo.getNodoDerecho().getPrimero());
            } else {
                nodo.setPrimero(nodo.getNodoIzquierdo().getPrimero());
            }
        nodo.setUltimo(nodo.getNodoDerecho().getUltimo());
    }
    
    /**
     * CONSTRUCCION DE TABLA DE SIGUIENTESS
     * @param nodo
     */
    public void construirTablaSiguiente(Nodo nodo){
        if (nodo != null) {
            if (nodo.getNodoIzquierdo()!= null) {
                String[] f = nodo.getNodoIzquierdo().getUltimo().split(",");
                for (int i = 0; i < f.length; i++) {
                    if (nodo.getElemento().equals("*") || nodo.getElemento().equals("+")) {
                        obtenerElementoHoja(nodoRaiz, f[i]);  
                        obtenerTabla(f[i], nombreHoja, nodo.getNodoIzquierdo().getPrimero());                    
                    } else if (nodo.getElemento().equals(".")) {
                        obtenerElementoHoja(nodoRaiz, f[i]);
                        obtenerTabla(f[i], nombreHoja, nodo.getNodoDerecho().getPrimero());
                    }
                }
            }
            construirTablaSiguiente(nodo.getNodoIzquierdo());
            construirTablaSiguiente(nodo.getNodoDerecho());
        }
    }
    
    /**
     * OBTENER TABLA DE SIGUIENTES
     * @param idSiguiente 
     * @param nombre
     * @param siguientes
     */    
    public void obtenerTabla(String idSiguiente, String nombre, String siguientes){

        int contadorSiguiente = 0;
        if (arrayListS.isEmpty()) {
            arrayListS.add(new Siguiente(idSiguiente, nombre, siguientes));
        } else {
            for (Siguiente siguiente : arrayListS) {
                if (siguiente.getIdSiguiente().equals(idSiguiente)) {
                    contadorSiguiente++;
                }
            }
            if (contadorSiguiente == 0) {
                arrayListS.add(new Siguiente(idSiguiente, nombre, siguientes));
            }else {
                for (Siguiente siguiente : arrayListS) {
                    if (siguiente.getIdSiguiente().equals(idSiguiente)) {
                        siguiente.setSiguientes(siguientes + "," +siguiente.getSiguientes());
                    }
                }
            }
        }
        
        for (Siguiente object : arrayListS) {
            System.out.println(object);
        }
        
        int tamanio = arrayListS.size();
        for (int i = 0; i < tamanio-1; i++){
            for (int j = 0; j < tamanio-i-1; j++){
                int a = Integer.parseInt(((Siguiente)arrayListS.get(j)).getIdSiguiente());
                int b = Integer.parseInt(((Siguiente)arrayListS.get(j+1)).getIdSiguiente());
                
                if (a > b)
                {
                    Siguiente siguiente = arrayListS.get(j);
                    arrayListS.set(j, arrayListS.get(j + 1));
                    arrayListS.set(j+1, siguiente);
                }
            }  
        }
    }
    
    /**
     * OBTENER HOJAS
     * @param nodo 
     * @param nombre
     */
    public void obtenerElementoHoja(Nodo nodo, String nombre){
        if (nodo != null) {
            if (nodo.isEsHoja()) {
                if (nodo.getPrimero().equals(nombre) && nodo.getUltimo().equals(nombre)) {
                    nombreHoja = nodo.getElemento();    
                }
            } else {
                obtenerElementoHoja(nodo.getNodoIzquierdo(), nombre);
                obtenerElementoHoja(nodo.getNodoDerecho(), nombre);   
            }
        }
    }
    
    /**
     * @param nombre
     */
    public void generarTablaSiguiente(String nombre) {    
        FileWriter fileWriter;
        PrintWriter printWriter;
        Runtime runtime;
        fileWriter = null;
        
        try {
            fileWriter = new FileWriter("grafo-siguiente.dot");
            
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
            runtime.exec( "dot -Tjpg -o "+nombre+"Tabla.png grafo-siguiente.dot");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String obtenerDocumento() {
        String cabezaGrafo = "digraph grafica{\n"
            +"rankdir=TB;\n"
            +"node [fillcolor=firebrick3, shape=record, style=bold];\n"
            +"Tabla"+contadorHijo+" [ label =\""
            + "{ Tabla de Siguientes |";
        
        String cuerpoGrafo = "";
        String pieGrafo = "}"
                + "\"];\n}\n";;
        
        
        for (Siguiente rowTable : arrayListS) {
            String str = rowTable.getNombre().replace('"', ' ');
            cuerpoGrafo = cuerpoGrafo + "{"+str+"|<here> "+rowTable.getIdSiguiente()+"|"+rowTable.getSiguientes()+"}|";         
        }
        Siguiente r = arrayListS.get(arrayListS.size()-1);
        int a = Integer.parseInt(r.getIdSiguiente())+1 ;
        cuerpoGrafo = cuerpoGrafo + "{#|<here> "+ a +"|---}|"; 
        
         
        return cabezaGrafo + cuerpoGrafo + pieGrafo;
    }
    
}
