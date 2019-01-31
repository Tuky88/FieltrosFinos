/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.barcodelib.barcode.QRCode;
import com.peea.mx.FF.iFrame.informacioniFrame;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            try {
                QRCode qr= new QRCode();
                qr.setData("Nombre:HOLA PERRO"
                        + "Edad: 19");
                qr.setDataMode(QRCode.MODE_BYTE);
                qr.setUOM(0);
                qr.setResolution(500);
                qr.setRightMargin(0.000f);
                qr.setTopMargin(0.000f);
                qr.setLeftMargin(0.000f);
                qr.setBottomMargin(0.000f);
                qr.setModuleSize(10);
                String f="C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\imagenes\\codigo.png";
                qr.renderBarcode(f);
                Desktop d= Desktop.getDesktop();
                d.open(new File(f));
//To change body of generated methods, choose Tools | Templates.
            } catch (Exception ex) {
                Logger.getLogger(controladorInformacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
