/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.Serial;

import com.peea.mx.FF.modelos.Medicion;
import com.peea.mx.FF.utils.CustomRenderer;
import com.peea.mx.FF.utils.graficadorLineal;
import com.peea.mx.FF.utils.graficadorUnico;
import com.peea.mx.FF.utils.tablaChida;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Sistemas
 */
public class LecturaSer extends Thread {

    int contadorPulsos = 0;
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
    graficadorUnico grafIzq, grafCentro, grafDer;
    JLabel estado;
    LinkedList linked;
    String[] indices;
    JPanel panelin;
    JPanel panelizq, panelcentro, panelder;
    public tablaChida tc;
    private JTable jtable;

    public graficadorLineal getGraf() {
        return graf;
    }

    public void setGraf(graficadorLineal graf) {
        this.graf = graf;
    }

    public LecturaSer(String[] indices, JPanel panelin, JTextField label1, JTextField label2, JTextField label3, JTextField cont, JTextField label5,
            JTextField label6, int baudrate, String numport, JLabel lbl, LinkedList ls, tablaChida tc, JTable jt,
            JPanel j1, JPanel j2, JPanel j3) {
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
        this.cont = cont;
        this.jtable = jt;
        this.baudrate = baudrate;
        this.numport = numport;
        this.estado = lbl;
        bandera = false;
        this.linked = ls;
        this.indices = indices;
        this.panelin = panelin;
        this.label5 = label5;
        this.label6 = label6;
        this.tc = tc;
        this.panelizq = j1;
        this.panelcentro = j2;
        this.panelder = j3;
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

    public void iniGrafica() {
        graf = new graficadorLineal(this.indices, this.panelin, this.label1, this.label2, this.label3, this.cont, this.label5, this.label6);
        grafIzq = new graficadorUnico(this.indices, this.panelizq, this.label1, this.label5, this.label6, this.cont, "Izquierda", 1);
        grafCentro = new graficadorUnico(this.indices, this.panelcentro, this.label2, this.label5, this.label6, this.cont, "Centro", 2);
        grafDer = new graficadorUnico(this.indices, this.panelder, this.label3, this.label5, this.label6, this.cont, "Derecha", 3);
    }

    @Override
    public void run() {

        String valor = "", valorsito = "";
        int aux;
        int contador = 0;

        while (true) {
            //System.out.println("esperando...");
            try {
                valorsito = valor;
                aux = entrada.read(); // aqui estamos obteniendo nuestro dato serial
                //Thread.sleep(5);
                //System.out.println(aux);
                if (aux > 0) {

                    //System.out.print();//imprimimos el dato serial
                    //System.out.print(Integer.decode(Integer.toHexString(aux)));
                    System.out.print((char) (aux));
                    valor += (char) (aux);
                    contador++;

                }
            } catch (Exception e) {
            }

            //System.out.println(valorsito +"//" +valorsito.length());
            if (valorsito.length() == 32 && !estado.getText().equals("X")) {
                String[] c = valorsito.split(":");
                System.out.println(valorsito);
                //01A+0006.789
                //1:+004.825
                //JOptionPan e.showMessageDialog(cont,valorsito);
                //JOptionPane.showMessageDialog(cont, valorsito);

                if (valorsito.charAt(0) == '1') {
                    label1.setText(valorsito.substring(2, 10));
                }
                if (valorsito.charAt(0) == '2') {
                    label2.setText(valorsito.substring(2, 10));
                }
                if (valorsito.charAt(0) == '3') {
                    label3.setText(valorsito.substring(2, 10));
                }
                if (valorsito.charAt(11) == '1') {
                    label1.setText(valorsito.substring(13, 21));
                }
                if (valorsito.charAt(11) == '2') {
                    label2.setText(valorsito.substring(13, 21));
                }
                if (valorsito.charAt(11) == '3') {
                    label3.setText(valorsito.substring(13, 21));
                }
                if (valorsito.charAt(22) == '1') {
                    label1.setText(valorsito.substring(24, 32));
                }
                if (valorsito.charAt(22) == '2') {
                    label2.setText(valorsito.substring(24, 32));
                }
                if (valorsito.charAt(22) == '3') {
                    label3.setText(valorsito.substring(24, 32));
                }
                // JOptionPane.showMessageDialog(null, valorsito);
                valorsito = "";

            }
            //System.out.println("Contador:" + contador);
            if (estado.getText().equals("X")) {
                contadorPulsos = 0;
            }
            if (contador == 33) {
                contador = 0;
                if (!estado.getText().equals("X")) {
                    contadorPulsos++;
                    Medicion md = new Medicion(contadorPulsos, Double.parseDouble(graf.getJ1().getText()), Double.parseDouble(graf.getJ2().getText()),
                            Double.parseDouble(graf.getJ3().getText()));
                    this.linked.add(md);
                    addToTable(contadorPulsos, md, 1.0, 2.0);
                    graf.graficar();
                    grafIzq.graficar();
                    grafCentro.graficar();
                    grafDer.graficar();
                    contador = 0;
                    valorsito = "";
                    valor = "";

                }
            }

        }

    }

    public void addToTable(int contadorPulsos, Medicion md, Double arriba, Double abajo) {
        if (tc.getColumnName(this.contadorPulsos).equals(String.valueOf(contadorPulsos))) {

            tc.setValueAt(md.getDerecha(), 2, contadorPulsos);
            tc.setValueAt(md.getCentro(), 1, contadorPulsos);
            tc.setValueAt(md.getIzquierda(), 0, contadorPulsos);

        } else {
            tc.addColumn("" + contadorPulsos, md.getDatos());
        }
        jtable.setAutoscrolls(true);
        TableColumn col = jtable.getColumnModel().getColumn(contadorPulsos);
        col.setCellRenderer(new CustomRenderer());

    }

    public void close() {
        bandera = false;
        serialport.close();
    }

    public void restarContador(int valor) {
        this.setContadorPulsos(this.getContadorPulsos() - valor);
    }

    public int getContadorPulsos() {
        return contadorPulsos;
    }

    public void setContadorPulsos(int contadorPulsos) {
        this.contadorPulsos = contadorPulsos;
    }

}
