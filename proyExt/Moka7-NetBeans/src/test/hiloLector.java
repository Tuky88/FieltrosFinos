/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Axel Reyes
 */
public class hiloLector extends Thread {

    public PlcToJAva ptj;

    public hiloLector() {

    }

    @Override
    public void run() {
        while (true) {
            ptj = new PlcToJAva();
            ptj.conectar();
            int adelante = ptj.leer(0);
            int valor = ptj.leer(2);
            ptj.desconectar();
            System.out.println(adelante + "->>" + valor);
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(hiloLector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
