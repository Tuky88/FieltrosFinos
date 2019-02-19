/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.Serial;

import com.peea.mx.FF.modelos.Medicion;
import com.peea.mx.FF.utils.graficadorLineal;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sistemas
 */
public class LecturaSer extends Thread {

    CommPortIdentifier portId;
    Enumeration puertos;
    SerialPort serialport;
    InputStream entrada = null;
    JTextField label1;
    JTextField label2;
    JTextField label3;
    JTextField label5;
    JTextField label6;
    JTextField cont;
    int baudrate;
    String numport;
    Boolean bandera;
    graficadorLineal graf;
    JLabel estado;
    LinkedList linked;
    String[] indices;
    JPanel panelin;

    public graficadorLineal getGraf() {
        return graf;
    }

    public void setGraf(graficadorLineal graf) {
        this.graf = graf;
    }

    public LecturaSer(String[] indices, JPanel panelin, JTextField label1, JTextField label2, JTextField label3, JTextField cont, JTextField label5,
            JTextField label6, int baudrate, String numport, JLabel lbl, LinkedList ls) {
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.cont = cont;
        this.baudrate = baudrate;
        this.numport = numport;
        this.estado = lbl;
        bandera = false;
        this.linked = ls;
        this.indices=indices;
        this.panelin=panelin;
        this.label5=label5;
        this.label6=label6;
        this.iniGrafica();
       puertos = CommPortIdentifier.getPortIdentifiers();
        while (puertos.hasMoreElements()) { //para recorrer el numero de los puertos, y especificar con cual quiero trabajar 
            //hasmorelements mientras tenga mas eleementos
            portId = (CommPortIdentifier) puertos.nextElement(); //next elemento recorre uno por uno
            System.out.println(portId.getName()); //puertos disponbibles
            if (portId.getName().equalsIgnoreCase(this.numport)) {
                //System.out.println(portId.getName());
                try {
                    serialport = (SerialPort) portId.open("LecturaSerial", 100);//tiempo en ms
                    serialport.setSerialPortParams(baudrate, 8, 1, 0);
                    serialport.setDTR(true);
                    System.out.println(serialport.getBaudRate() + "//" + serialport.getDataBits() + "//");
                    entrada = serialport.getInputStream();//esta variable del tipo InputStream obtiene el dato serial
                    //System.out.println("dfsdfsdf");// inciamos el hilo para realizar nuestra accion de imprimir el dato serial
                } catch (Exception e) {
                }
            }
        }
    }

    public Boolean getBandera() {
        return bandera;
    }

    public void setBandera(Boolean bandera) {
        this.bandera = bandera;
    }
    public void iniGrafica()
    {
         graf = new graficadorLineal(this.indices, this.panelin, this.label1, this.label2, this.label3, this.cont, this.label5, this.label6);
        
    }

    @Override
    public void run() {

        String valor = "", valorsito = "";
        int aux;
        int contador = 0;
        int contadorPulsos=0;

        while (true) {
            System.out.println("esperando...");
            try {
                valorsito = valor;
                aux = entrada.read(); // aqui estamos obteniendo nuestro dato serial
                //Thread.sleep(5);
                //System.out.println(aux);
                if (aux > 0) {

                    //System.out.print();//imprimimos el dato serial
                    //System.out.print(Integer.decode(Integer.toHexString(aux)));
                    System.out.print((char)(aux));
                    valor += (char) (aux);
                    contador++;
                    //System.out.println(valor +"//"+valorsito);

                } else {
                    System.out.println("termino la lectura.");
                }
            } catch (Exception e) {
            }

            System.out.println(valorsito +"//" +valorsito.length());
            if (valorsito.length() == 32 && !estado.getText().equals("X")) {
                String[] c = valorsito.split(":");
                System.out.println(valorsito);
                //JOptionPane.showMessageDialog(cont,valorsito);
                if(valorsito.charAt(0)=='1')
                   label1.setText(valorsito.substring(2,10)); 
                if(valorsito.charAt(0)=='2')
                   label2.setText(valorsito.substring(2,10)); 
                if(valorsito.charAt(0)=='3')
                   label3.setText(valorsito.substring(2,10)); 
                 if(valorsito.charAt(11)=='1')
                   label1.setText(valorsito.substring(13,21)); 
                if(valorsito.charAt(11)=='2')
                   label2.setText(valorsito.substring(13,21)); 
                if(valorsito.charAt(11)=='3')
                   label3.setText(valorsito.substring(13,21));
                if(valorsito.charAt(22)=='1')
                   label1.setText(valorsito.substring(24,32)); 
                if(valorsito.charAt(22)=='2')
                   label2.setText(valorsito.substring(24,32)); 
                if(valorsito.charAt(22)=='3')
                   label3.setText(valorsito.substring(24,32));
                
 
               
               // System.out.println("JJJJJ:"+valorsito);
                //JOptionPane.showMessageDialog(cont,"//"+valorsito.charAt(0)+"//"+valorsito.charAt(11)+"//"+valorsito.charAt(22) );
                valorsito="";
                    
                
//                label1.setText(x);
//                label2.setText(y);
//                label3.setText(z);
                
                //linked.add(md);
//            label1.setText(valorsito.substring( valorsito.length()-6,valorsito.length()));
//            label2.setText(valorsito.substring( valorsito.length()-16,valorsito.length()-10));
//            label3.setText(valorsito.substring( valorsito.length()-22,valorsito.length()-17));
                //cont.setText(String.valueOf(Double.parseDouble(cont.getText()) + 0.1));
            }
            System.out.println("Contador:" + contador);
            if (estado.getText().equals("X")) {
                contadorPulsos=0;
            }
            if (contador == 33 ){
                contador=0;
                if(!estado.getText().equals("X")) {
                
                contadorPulsos++;
                Medicion md = new Medicion(contadorPulsos,Double.parseDouble(graf.getJ1().getText()), Double.parseDouble(graf.getJ2().getText()),
                        Double.parseDouble(graf.getJ3().getText()));
                this.linked.add(md);
                graf.graficar();
                contador = 0;
                valorsito="";
                valor="";
                
                } 
            }

            
        }
        
    }

    public void close() {
        bandera = false;
        serialport.close();
    }
}
