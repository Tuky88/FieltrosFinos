/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.Serial;

import static com.peea.mx.FF.Serial.LecturaSerial.entrada;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Sistemas
 */
public class LecSer {

    CommPortIdentifier portId;
    Enumeration puertos;
    SerialPort serialport;
    InputStream entrada = null;
    JTextField label;
    int baudrate;
    String numport;

    public LecSer(JTextField label, int baudrate, String numport) {
        this.label = label;
        this.baudrate = baudrate;
        this.numport = numport;
        puertos = CommPortIdentifier.getPortIdentifiers();
        while (puertos.hasMoreElements()) { //para recorrer el numero de los puertos, y especificar con cual quiero trabajar 
            //hasmorelements mientras tenga mas eleementos
            portId = (CommPortIdentifier) puertos.nextElement(); //next elemento recorre uno por uno
            System.out.println(portId.getName()); //puertos disponbibles
            if (portId.getName().equalsIgnoreCase(this.numport)) {
                try {
                    serialport = (SerialPort) portId.open("LecturaSerial", 100);//tiempo en ms
                    serialport.setSerialPortParams(baudrate, 8, 1, 0);
                    serialport.setDTR(true);
                    System.out.println(serialport.getBaudRate() + "//" + serialport.getDataBits() + "//");
                    entrada = serialport.getInputStream();//esta variable del tipo InputStream obtiene el dato serial
                    System.out.println("dfsdfsdf");// inciamos el hilo para realizar nuestra accion de imprimir el dato serial

                } catch (Exception e) {
                }
            }
        }
    }

    public String capturar() {

        String valor = "", valorsito = "/";
        int aux = 1;
        System.out.println("Lectura:");
        label.setText("");
        while (aux!=13) {
            try {
                valorsito = valor;
                aux = entrada.read(); // aqui estamos obteniendo nuestro dato serial
                Thread.sleep(5);
                //System.out.println(aux);
                if (aux > 0) {

                    //System.out.print();//imprimimos el dato serial
                    //System.out.print(Integer.decode(Integer.toHexString(aux)));
                    //System.out.print((char)(aux));
                    valor += (char) (aux);
                    //System.out.println(valor +"//"+valorsito);

                } else {
                    System.out.println("termino la lectura.");
                }
            } catch (Exception e) {
            }

            
        }
        System.out.println(valorsito);
        return valorsito;
    }

}
