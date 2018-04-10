/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.Serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.Enumeration;
import javax.swing.JLabel;

/**
 *
 * @author Sistemas
 */
public class LecturaSer extends Thread {
    
    	CommPortIdentifier portId;
	Enumeration puertos;
	SerialPort serialport;
	InputStream entrada = null;
        JLabel label;
        int baudrate;
        String numport;

    public LecturaSer(JLabel label, int baudrate, String numport) {
        this.label = label;
        this.baudrate = baudrate;
        this.numport = numport;
    }
            @Override
    public void run()
    {
        while(true)
        {
            
        }
    }

        

}

