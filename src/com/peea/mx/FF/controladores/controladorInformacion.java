/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.barcodelib.barcode.QRCode;
import com.peea.mx.FF.iFrame.informacioniFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Sistemas
 */
public class controladorInformacion {
    private informacioniFrame ii;

    public controladorInformacion(informacioniFrame ii) {
        this.ii = ii;
        this.ii.btnQR.addActionListener(new generarQR());
    }

    private  class generarQR implements ActionListener {

        public generarQR() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            QRCode qr= new QRCode();
            qr.setData("HOLA PERRO");
            qr.setDataMode(QRCode.MODE_BYTE);
            qr.setUOM(50);
            qr.setLeftMargin(0);
            
//To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
}
