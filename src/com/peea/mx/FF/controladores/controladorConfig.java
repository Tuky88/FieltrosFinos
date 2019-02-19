/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.peea.mx.FF.iFrame.configuracioniFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Sistemas
 */
public class controladorConfig {
    private configuracioniFrame ci;

    public controladorConfig(configuracioniFrame ci) {
        this.ci = ci;
        this.ci.btnGuardar.addActionListener(new GuardarArchivo());
    }

    private  class GuardarArchivo implements ActionListener {

        public GuardarArchivo() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    
}
