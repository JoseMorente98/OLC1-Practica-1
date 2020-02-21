/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

import java.util.ArrayList;
import org.josemorente.modelo.Conjunto;
import org.josemorente.modelo.Token;

/**
 *
 * @author josem
 */
public class ControladorConjunto {
    //PROPIEDADES
    private ArrayList<Conjunto> arrayListConjunto = new ArrayList();
    
    private ControladorConjunto() {
    }
    
    public static ControladorConjunto getInstance() {
        return ControladorConjuntoHolder.INSTANCE;
    }
    
    private static class ControladorConjuntoHolder {

        private static final ControladorConjunto INSTANCE = new ControladorConjunto();
    }
       
    /**
     * MÉTODO PARA DEFINIR CONJUNTOS
     */
    public void definirConjunto(){
        boolean esConjunto = false;
        for (int x = 0; x < ControladorToken.getInstance().getArrayListTokens().size(); x++) {
            Token token = ControladorToken.getInstance().getArrayListTokens().get(x);
            if(token.getLexema().toUpperCase().equals("CONJ") ){
                Token nombre = ControladorToken.getInstance().getArrayListTokens().get(x+2);
                
                ArrayList<String> conjuntos = new ArrayList();
                
                for (int j = x+5; j < ControladorToken.getInstance().getArrayListTokens().size(); j++) {
                    Token t = ControladorToken.getInstance().getArrayListTokens().get(j);
                    if(!t.getLexema().equals(";")){
                        if (!t.getLexema().equals(",")) {
                            if (t.getLexema().equals("~")) {
                                esConjunto = true;
                            }
                            conjuntos.add(t.getLexema());                            
                        }
                    } else {
                        break;
                    }
                }
                agregarConjunto(nombre.getLexema(), conjuntos, esConjunto);
            }
        }
    }
    
    /**
     * INSERTAR CONJUNTO
     * @param nombre
     * @param conjuntos
     * @param esConjunto 
     */
    public void agregarConjunto(String nombre, ArrayList<String> conjuntos, boolean esConjunto ){
        ArrayList<String> arrayListConjuntos = new ArrayList();
        
        if (esConjunto) {
            for (int x = 0; x < conjuntos.size(); x++) {
                if (conjuntos.get(x).equals("~")) {
                    arrayListConjuntos = obtenerConjunto(conjuntos.get(x-1).charAt(0), conjuntos.get(x+1).charAt(0));
                    break;
                }
            }
            Conjunto conjunto = new Conjunto(nombre, arrayListConjuntos);
            getArrayListConjunto().add(conjunto);
        } else{
            Conjunto conjunto = new Conjunto(nombre, conjuntos);
            getArrayListConjunto().add(conjunto);
        }
    }
    
    /**
     * OBTENER CONJUNTO
     * @param inicio
     * @param fin
     * @return arrayList
     */
    public ArrayList<String> obtenerConjunto(char inicio, char fin){
        ArrayList<String> arrayList = new ArrayList();
        
        //CONJUNTO DE LETRAS
        if(Character.isLetter(inicio)){
            int valorInicial = (int)inicio;
            int valorFinal = (int)fin;
            
            for (int x = valorInicial; x <= valorFinal; x++) {
                arrayList.add(String.valueOf((char)x));
            }
        }
        //CONJUNTO DE DIGITO
        else if (Character.isDigit(inicio)) {
            int valorInicial = (int)inicio;
            int valorFinal = (int)fin;
            
            for (int x = valorInicial; x <= valorFinal; x++) {
                arrayList.add(String.valueOf(x));
            }
        }        
        //CONJUNTO ASCII 32 A 125
        else if((int)inicio >= 32 && (int)fin <= 125){
            int valorInicial = (int)inicio;
            int valorFinal = (int)fin;
            
            for (int x = valorInicial; x <= valorFinal; x++) {
                if (!Character.isDigit(inicio) && !Character.isDigit(fin) && !Character.isLetter(inicio) && !Character.isLetter(fin)) {
                    arrayList.add(Character.toString((char)x));
                }
            }
        }
        return arrayList;
    }
    
    /**
     * MOSTRAR CONJUNTOS
     */
    public void imprimirConjuntos() {
        for (Conjunto conjunto : getArrayListConjunto()) {
            System.out.println(conjunto);
        }
    }

    /**
     * @return the arrayListConjunto
     */
    public ArrayList<Conjunto> getArrayListConjunto() {
        return arrayListConjunto;
    }
    
}
