/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.ejecutables;

import com.peea.mx.FF.controladores.controladorPrincipal;
import com.peea.mx.FF.vistas.principalVista;

/**
 *
 * @author Sistemas
 */
public class ejecutableMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        principalVista pv=new principalVista();
        pv.show();
        controladorPrincipal cp=new controladorPrincipal(pv);
    }
    
}
