/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

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
    int contador;
    int contadorHijo; 
    Nodo nodoRaiz;
    String nombreHoja;
    
    private ControladorNodo() {
        arrayListER = new ArrayList<>();        
        arrayListAuxiliarER = new ArrayList<>();
        arrayListS = new ArrayList<>();
        stackArbol = new Stack();
        nodoRaiz = null;
        contador = 0;
        contadorHijo = 1;
        nombreHoja = "";
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
        contador = 0;
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
            nombre = "null" + contador;
        }
        
        for (int i = arrayListER.size() - 1; i >= 0; i--) {
            arrayListAuxiliarER.add(arrayListER.get(i));
        }
        
        for (String texto: arrayListAuxiliarER) {
            agregarPila(texto);
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
        
        
        
        contador++;
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
                

                //Le ingresa el anuable o no
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
                        obtenerTabla(f[i], nombreHoja, nodo.getNodoIzquierdo().getPrimero());
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
        int contador = 0;
        if (arrayListS.isEmpty()) {
            arrayListS.add(new Siguiente(idSiguiente, nombre, siguientes));
        } else {
            for (Siguiente o : arrayListS) {
                if (o.getIdSiguiente().equals(idSiguiente)) {
                    contador++;
                }
            }
            if (contador == 0) {
                arrayListS.add(new Siguiente(idSiguiente, nombre, siguientes));
            }else {
                for (Siguiente o : arrayListS) {
                    if (o.getIdSiguiente().equals(idSiguiente)) {
                        o.setSiguientes(siguientes + "," +o.getSiguientes());
                    }
                }
            }
        }
        int nodo = arrayListS.size();
        for (int i = 0; i < nodo-1; i++){
            for (int j = 0; j < nodo-i-1; j++){
                if (Integer.parseInt(((Siguiente)arrayListS.get(j)).getIdSiguiente()) > Integer.parseInt(((Siguiente)arrayListS.get(j+1)).getIdSiguiente()))
                {
                    Siguiente swap = arrayListS.get(j);
                    arrayListS.set(j, arrayListS.get(j + 1));
                    arrayListS.set(j+1, swap);
                    
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
    
    
}
