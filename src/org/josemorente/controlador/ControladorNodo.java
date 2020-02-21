/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josemorente.controlador;

/**
 *
 * @author josem
 */
public class ControladorNodo {
    
    private ControladorNodo() {
    }
    
    public static ControladorNodo getInstance() {
        return ControladorNodoHolder.INSTANCE;
    }
    
    private static class ControladorNodoHolder {

        private static final ControladorNodo INSTANCE = new ControladorNodo();
    }
    
    
}
