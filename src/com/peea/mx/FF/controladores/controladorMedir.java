/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.barcodelib.barcode.QRCode;
import com.peea.mx.FF.Serial.LecturaSer;
import com.peea.mx.FF.iFrame.medicioniFrame;
import com.peea.mx.FF.iFrame.tablasSeparadasiFrame;
import com.peea.mx.FF.modelos.Medicion;
import com.peea.mx.FF.pruebas.CrearFicherosExcel;
import com.peea.mx.FF.utils.Convertidor;
import com.peea.mx.FF.utils.graficadorLineal;
import com.peea.mx.FF.utils.mensajeAutomatico;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jfree.chart.ChartUtilities;

/**
 *
 * @author Sistemas
 */
public class controladorMedir {

    public medicioniFrame mi;
    public tablasSeparadasiFrame tsi;
    String[] conjunto = {"GROSOR(+)", "GROSOR(-)", "IZQUIERDA", "CENTRO", "DERECHA"};
    CrearFicherosExcel cfe;

    public controladorMedir(medicioniFrame mi, tablasSeparadasiFrame tsi) {
        this.mi = mi;
        this.tsi = tsi;
        this.mi.btnCargarDatos.addActionListener(new Cargar(this.mi.jt, this.mi.valor));
        this.mi.btnIniciarMed.addActionListener(new Medir());
        this.mi.btnFinalizarMed.addActionListener(new FinMed());
        this.mi.btnCalcularDen.addActionListener(new CalcularDen());
        this.mi.chkAPlanchado.addActionListener(new checkExcluyente(this.mi.chkAPlanchado, this.mi.chkDPlanchado));
        this.mi.chkDPlanchado.addActionListener(new checkExcluyente(this.mi.chkDPlanchado, this.mi.chkAPlanchado));
        this.mi.chkDPlanchado.addActionListener(new bloquearDen());
        this.mi.chkAPlanchado.addActionListener(new desbloquearDen());
        this.mi.med = new LinkedList();
        this.mi.txtEspesorMM.addKeyListener(new VerificarNumero());
        this.mi.txtDensidad.addKeyListener(new VerificarNumero());
        this.mi.txtEspesorIn.addKeyListener(new VerificarNumero());
        this.mi.txtMetrosM.addKeyListener(new VerificarNumero());
        this.mi.txtPeso.addKeyListener(new VerificarNumero());
        this.mi.txtToleranciaNegMM.addKeyListener(new VerificarNumero());
        this.mi.txtToleranciaNegIn.addKeyListener(new VerificarNumero());
        this.mi.txtToleranciaPosMM.addKeyListener(new VerificarNumero());
        this.mi.txtToleranciaPosIn.addKeyListener(new VerificarNumero());
        this.mi.txtEspesorMM.addKeyListener(new Convertir(mi.txtEspesorMM, mi.txtEspesorIn, 1));
        this.mi.txtToleranciaPosMM.addKeyListener(new Convertir(mi.txtToleranciaPosMM, mi.txtToleranciaPosIn, 1));
        this.mi.txtToleranciaNegMM.addKeyListener(new Convertir(mi.txtToleranciaNegMM, mi.txtToleranciaNegIn, 1));
        this.mi.txtEspesorIn.addKeyListener(new Convertir(mi.txtEspesorIn, mi.txtEspesorMM, 2));
        this.mi.txtToleranciaPosIn.addKeyListener(new Convertir(mi.txtToleranciaPosIn, mi.txtToleranciaPosMM, 2));
        this.mi.txtToleranciaNegIn.addKeyListener(new Convertir(mi.txtToleranciaNegIn, mi.txtToleranciaNegMM, 2));
        this.mi.btnGenerarRepo.addActionListener(new GenerarRepo());
        this.mi.btnRetroceder.addActionListener(new Retroceder());
        this.mi.btnQr.addActionListener(new generarQR());
//this.mi.txtObtenerMed.addActionListener(new Obtener());

        mi.ls = new LecturaSer(conjunto, mi.grafica, mi.txtMedidaIzq, mi.txtMedidaCentro, mi.txtMedidaDerecha,
                mi.txtPulsos, mi.txtToleranciaPosMM, mi.txtToleranciaNegMM, mi.getVel(), mi.getCom(), mi.lblExtado, mi.med,
                mi.tc, mi.jtable, tsi.panelIzq, tsi.panelCentro, tsi.panelDer);

        mi.lblExtado.setText("X");
        mi.btnFinalizarMed.setEnabled(false);
        mi.btnGenerarRepo.setEnabled(false);

        this.correrHilo();
    }

    private class generarQR implements ActionListener {

        public generarQR() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                QRCode qr = new QRCode();
                qr.setData(mi.txtEspesorMM.getText() + ","
                        + mi.txtToleranciaPosMM.getText() + ","
                        + mi.txtToleranciaNegMM.getText() + ","
                        + mi.txtRangoMM.getText() + ","
                        + mi.txtEstilo.getText() + ","
                        + mi.txtCliente.getText() + ","
                        + mi.txtPO.getText() + ","
                        + mi.txtPieza.getText() + ","
                        + mi.txtPeso.getText() + ","
                        + mi.txtDensidad.getText() + ","
                        + mi.txtMetrosM.getText() + ","
                        + mi.txtAnchoM.getText() + ","
                        + mi.lblFecha.getText() + ","
                        + mi.lblHora.getText()
                );
                qr.setDataMode(QRCode.MODE_BYTE);
                qr.setUOM(0);
                qr.setResolution(100000);
                qr.setRightMargin(0.000f);
                qr.setTopMargin(0.000f);
                qr.setLeftMargin(0.000f);
                qr.setBottomMargin(0.000f);
                qr.setModuleSize(1);
                String f = "C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\imagenes\\codigo.png";
                qr.renderBarcode(f);
                Desktop d = Desktop.getDesktop();
                d.open(new File(f));
//To change body of generated methods, choose Tools | Templates.
            } catch (Exception ex) {
                Logger.getLogger(controladorInformacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void imprimirArchivo(File archivo) {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.PRINT)) {
            try {
                desktop.print(archivo);
            } catch (Exception e) {
                System.out.print("El sistema no permite imprimir usando la clase Desktop");
                e.printStackTrace();
            }
        } else {
            System.out.print("El sistema no permite imprimir usando la clase Desktop");
        }
    }

    public void abrirArchivo() {
        String nombreArchivo;
        Date hoy = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat d = new SimpleDateFormat("hh-mm-ss");
        String nombreG = mi.txtCliente.getText() + "_" + mi.txtPieza.getText();
        nombreArchivo = "Reporte_" + s.format(hoy) + "_" + d.format(hoy) + "_" + nombreG;
        cfe = new CrearFicherosExcel(nombreArchivo + ".xlsx", "C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\Reportes\\",
                "Hoja1", "C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\Archivos\\rf.xlsx");
        if (mi.lblMed.getText().equals("IN")) {
            cfe.setStatus(false);
        } else {
            cfe.setStatus(true);
        }
    }

    public void generarEncabezado(int inicio) {
        cfe.Sescribir(inicio - 1, 0, mi.lblEmpresa.getText(), 1);

        cfe.Sescribir(inicio, 8, mi.txtCliente.getText(), 1);
        cfe.Sescribir(inicio + 2, 8, mi.txtPieza.getText(), 1);
        cfe.Sescribir(inicio + 3, 8, mi.txtPeso.getText(), 1);
        //cfe.Sescribir(3, 8, mi..getText(), 1);
        cfe.Sescribir(inicio + 1, 8, mi.txtPO.getText(), 1);
        cfe.Sescribir(inicio + 3, 1, mi.lblFecha.getText(), 1);
        cfe.Sescribir(inicio + 4, 1, mi.txtTiempoInicio.getText(), 1);
        cfe.Sescribir(inicio + 5, 1, mi.txtTiempoFin.getText(), 1);
        cfe.Sescribir(inicio + 2, 1, mi.txtEstilo.getText(), 1);
        cfe.Sescribir(inicio + 2, 4, mi.txtEspesorMM.getText(), 0);
        cfe.Sescribir(inicio + 2, 5, mi.txtEspesorIn.getText(), 0);
        cfe.Sescribir(inicio + 3, 4, mi.txtToleranciaPosMM.getText(), 0);
        cfe.Sescribir(inicio + 3, 5, mi.txtToleranciaPosIn.getText(), 0);
        cfe.Sescribir(inicio + 4, 4, mi.txtToleranciaNegMM.getText(), 0);
        cfe.Sescribir(inicio + 4, 5, mi.txtToleranciaNegIn.getText(), 0);
        cfe.Sescribir(inicio + 5, 4, mi.txtRangoMM.getText(), 0);
        cfe.Sescribir(inicio + 3, 10, mi.txtMetrosM.getText(), 0);
        cfe.Sescribir(inicio + 4, 10, mi.txtAnchoM.getText(), 0);
    }

    public void generarArchivo(int iniciox, int numeroFilas) {

        int contadorFilas = 0;
        double superior, inferior;
        superior = Double.parseDouble(mi.txtToleranciaPosMM.getText());
        inferior = Double.parseDouble(mi.txtToleranciaNegMM.getText());
        while (!mi.med.isEmpty()) {
            Medicion medi = (Medicion) mi.med.pop();
            System.out.println(medi.toString());
            cfe.escribir(medi, iniciox, superior, inferior);
            //cfe.escribir(iniciox, inicioy, String.valueOf(medi.getIzquierda()));
            iniciox++;
            contadorFilas++;
            if (contadorFilas == numeroFilas) {
                generarEncabezado(iniciox);
            }
        }
        String formula = "AVERAGE(D10:D" + iniciox + ")";
        System.out.println(formula);

        ////PROMEDIO
        cfe.escribir(iniciox + 2, 1, "PROMEDIO:", 1);
        cfe.escribir(iniciox + 2, 3, formula, 0);
        formula = "AVERAGE(E10:E" + iniciox + ")";
        cfe.escribir(iniciox + 2, 4, formula, 0);
        formula = "AVERAGE(G10:G" + iniciox + ")";
        cfe.escribir(iniciox + 2, 6, formula, 0);
        formula = "AVERAGE(H10:H" + iniciox + ")";
        cfe.escribir(iniciox + 2, 7, formula, 0);
        formula = "AVERAGE(J10:J" + iniciox + ")";
        cfe.escribir(iniciox + 2, 9, formula, 0);
        formula = "AVERAGE(K10:K" + iniciox + ")";
        cfe.escribir(iniciox + 2, 10, formula, 0);
        /////PROMEDIO GRAL
        cfe.escribir(iniciox + 4, 1, "PROMEDIO GRAL:", 1);
        formula = "AVERAGE(D" + iniciox + ",G" + iniciox + ",J" + iniciox + ")";
        cfe.escribir(iniciox + 4, 3, formula, 0);
        formula = "AVERAGE(E" + iniciox + ",H" + iniciox + ",K" + iniciox + ")";
        cfe.escribir(iniciox + 4, 4, formula, 0);
        /////MIN MAX
        cfe.escribir(iniciox + 5, 1, "LECTURA MINIMA:", 1);
        formula = "MIN(D10:D" + iniciox + ",G10:G" + iniciox + ",J10:J" + iniciox + ")";
        cfe.escribir(iniciox + 5, 3, formula, 0);
        formula = "MIN(E10:E" + iniciox + ",H10:H" + iniciox + ",K10:K" + iniciox + ")";
        cfe.escribir(iniciox + 5, 4, formula, 0);
        cfe.escribir(iniciox + 6, 1, "LECTURA MAXIMA:", 1);
        formula = "MAX(D10:D" + iniciox + ",G10:G" + iniciox + ",J10:J" + iniciox + ")";
        cfe.escribir(iniciox + 6, 3, formula, 0);
        formula = "MAX(E10:E" + iniciox + ",H10:H" + iniciox + ",K10:K" + iniciox + ")";
        cfe.escribir(iniciox + 6, 4, formula, 0);

        /////DESVIACION ESTANDAR
        cfe.escribir(iniciox + 7, 1, "DESVIACION ESTANDAR:", 1);
        formula = "STDEV(D10:D" + iniciox + ",G10:G" + iniciox + ",J10:J" + iniciox + ")";
        cfe.escribir(iniciox + 7, 3, formula, 0);
        System.out.println(iniciox + 7);
        double desv = cfe.leer(iniciox + 7, 3);

        /////CAPACIDAD DEL PROCESO CP
        cfe.escribir(iniciox + 8, 1, "CAPACIDAD DEL PROCESO CP:", 1);
        formula = "(E5-E6)/(6*" + desv + ")";
        System.out.println(formula);
        cfe.escribir(iniciox + 8, 3, formula, 0);
        /////CAPACIDAD DEL PROCESO CPK
        cfe.escribir(iniciox + 9, 1, "CAPACIDAD DEL PROCESO CPK:", 1);
        formula = "MIN((E5-D" + (iniciox + 5) + ")/(3*" + desv + "),(D" + (iniciox + 5) + "-E6/(3*" + desv + ")))";
        System.out.println(formula);
        cfe.escribir(iniciox + 9, 3, formula, 0);
        /////%DES
        cfe.escribir(iniciox + 4, 6, "%DES:", 1);
        formula = "(D" + iniciox + "/E4)*100";
        System.out.println(formula);
        cfe.escribir(iniciox + 4, 7, formula, 0);
        ///////%RAN
        cfe.escribir(iniciox + 5, 6, "%RAN:", 1);
        formula = "((D" + (iniciox + 7) + "-D" + (iniciox + 6) + ")/D" + iniciox + ")*100";
        System.out.println(formula);
        cfe.escribir(iniciox + 5, 7, formula, 0);
        //////%ACEP
        cfe.escribir(iniciox + 6, 6, "%ACEP:", 1);
        formula = "(" + cfe.leer(iniciox + 5, 7) + "/E7)*100";
        System.out.println(formula);
        cfe.escribir(iniciox + 6, 7, formula, 0);
        ///DENSIDAD
        if (mi.chkAPlanchado.isSelected()) {
            cfe.Sescribir(3, 10, mi.txtDensidad.getText(), 0);
        } else {
            double x = cfe.leer(iniciox + 2, 3);
            double densidad = calcularDensidad(Double.parseDouble(mi.txtPeso.getText()), Double.parseDouble(mi.txtAnchoM.getText()),
                    Double.parseDouble(mi.txtMetrosM.getText()), x);
            cfe.Sescribir(3, 10, String.valueOf(densidad), 0);
            JOptionPane.showMessageDialog(mi, "Densidad obtenida:" + x);

        }

        System.out.println("terminando de cargar los datos...");
        cfe.cargarArchivo();

        if (mi.chkImprimir.isSelected()) {
            cfe.imprimirArchivo();
        }
    }

    private void correrHilo() {
        System.out.println("corriendo...");
        mi.ls.setBandera(true);
        mi.ls.start();
    }

    public void calcularRango() {
        double rango = (Double.parseDouble(mi.txtToleranciaPosMM.getText()) - Double.parseDouble(mi.txtToleranciaNegMM.getText()))
                / Double.parseDouble(mi.txtEspesorMM.getText());

        mi.txtRangoMM.setText("" + Math.round(rango * 100));
    }

    public double calcularDensidad(double peso, double ancho, double metros, double espesor) {
        double densidad;
        densidad = (peso) / (ancho) * metros * espesor;
        DecimalFormat df = new DecimalFormat("#0.0000");
        return Double.parseDouble(df.format(densidad));
    }

    private class Retroceder implements ActionListener {

        public Retroceder() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            double valorARestar, valorActual;
            try {
                valorARestar = Double.parseDouble(mi.txtRecorrer.getText());
                valorActual = Double.parseDouble(mi.txtPulsos.getText());
                if (valorARestar > 0 && valorARestar <= valorActual) {
                    mi.txtPulsos.setText(Double.toString(valorActual - valorARestar));
                    mi.ls.restarContador((int) valorARestar);
                }
            } catch (NumberFormatException ne) {
            }
//To change body of generated methods, choose Tools | Templates.
        }
    }

    private class VerificarNumero implements KeyListener {

        public VerificarNumero() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char x = e.getKeyChar();
            if (Character.isAlphabetic(x)) {
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private class bloquearDen implements ActionListener {

        public bloquearDen() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mi.btnCalcularDen.setEnabled(false);
            JOptionPane.showMessageDialog(mi, "La densidad se calculará al final de la medición");
        }
    }

    private class desbloquearDen implements ActionListener {

        public desbloquearDen() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            mi.btnCalcularDen.setEnabled(true);
            //JOptionPane.showMessageDialog(mi, "La densidad se calculará al final de la medición");
        }
    }

    private class checkExcluyente implements ActionListener {

        private JCheckBox cha, chd;

        public checkExcluyente(JCheckBox cha, JCheckBox chd) {
            this.cha = cha;
            this.chd = chd;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cha.isSelected()) {
                chd.setSelected(false);
            }
        }
    }

    private class CalcularDen implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            double densidad = 0;
            if (mi.txtPeso.getText().isEmpty() || mi.txtAnchoM.getText().isEmpty()
                    || mi.txtMetrosM.getText().isEmpty() || mi.txtEspesorMM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mi, "¡Faltan campos!", "ERROR", 0);
            } else {
                densidad = (Double.parseDouble(mi.txtPeso.getText())) / (Double.parseDouble(mi.txtAnchoM.getText())
                        * Double.parseDouble(mi.txtMetrosM.getText()) * Double.parseDouble(mi.txtEspesorMM.getText()));
                DecimalFormat df = new DecimalFormat("#0.0000");
                mi.txtDensidad.setText("" + df.format(densidad));
            }
        }

    }

    private class FinMed implements ActionListener {

        public FinMed() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((mi.chkAPlanchado.isSelected() && !mi.txtMetrosM.getText().isEmpty() && !mi.txtAnchoM.getText().isEmpty() && !mi.txtDensidad.getText().isEmpty())
                    || (mi.chkDPlanchado.isSelected() && !mi.txtMetrosM.getText().isEmpty() && !mi.txtAnchoM.getText().isEmpty())) {

                //mi.ls.wait();
                if (!mi.btnIniciarMed.isEnabled()) {
                    mi.ls.setBandera(false);
                    System.out.println(mi.med.size());
                    mi.txtTiempoFin.setText(mi.lblHora.getText());
                    mi.btnFinalizarMed.setEnabled(false);
                    //mi.btnIniciarMed.setEnabled(true);
                    //mi.btnCargarDatos.setEnabled(true);
                    mi.lblExtado.setText("X");
                    mi.btnGenerarRepo.setEnabled(true);
                    //JOptionPane.showMessageDialog(mi, "¡Fin de medición!", "AVISO", 2);

                } else {
                    JOptionPane.showMessageDialog(mi, "¡No hay ninguna medición activa!", "ERROR", 0);
                }

            } else {
                JOptionPane.showMessageDialog(mi, "FALTAN CAMPOS PARA FINALIZAR LA MEDICION!");
            }

        }

    }

    private class Medir implements ActionListener {

        public Medir() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //mi.ls=new LecturaSer(mi.txtMedidaIzq,4800,"COM4");
            //mi.ls.start();
            if (mi.txtCliente.getText().isEmpty() || mi.txtEspesorIn.getText().isEmpty() || mi.txtEspesorMM.getText().isEmpty()
                    || mi.txtEstilo.getText().isEmpty() || mi.txtPO.getText().isEmpty()
                    || mi.txtPeso.getText().isEmpty() || mi.txtPieza.getText().isEmpty() || mi.txtRangoMM.getText().isEmpty() || mi.txtToleranciaNegIn.getText().isEmpty()
                    || mi.txtToleranciaNegMM.getText().isEmpty() || mi.txtToleranciaPosIn.getText().isEmpty() || mi.txtToleranciaPosMM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mi, "¡Faltan campos!", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(mi, "¡Comenzando medición!", "AVISO", 2);
                mi.txtTiempoInicio.setText(mi.lblHora.getText());
                mi.btnIniciarMed.setEnabled(false);
                mi.btnFinalizarMed.setEnabled(true);
                mi.btnCargarDatos.setEnabled(false);
                mi.lblExtado.setText("-");
                if (!mi.txtPulsos.getText().equals("0.0")) {

                    //mi.ls.iniGrafica();
                    mi.txtPulsos.setText("0.0");
                    //mi.ls.getGraf().getChart().
                    mi.ls.setBandera(true);
                    graficadorLineal gf;
                    if (mi.lblMed.getText().equals("MM")) {

                        gf = new graficadorLineal(conjunto, mi.grafica, mi.txtMedidaIzq, mi.txtMedidaCentro, mi.txtMedidaDerecha,
                                mi.txtPulsos, mi.txtToleranciaPosMM, mi.txtToleranciaNegMM);
                    } else {
                        gf = new graficadorLineal(conjunto, mi.grafica, mi.txtMedidaIzq, mi.txtMedidaCentro, mi.txtMedidaDerecha,
                                mi.txtPulsos, mi.txtToleranciaPosIn, mi.txtToleranciaNegIn);
                    }
                    mi.ls.setGraf(gf);
                    //mi.ls.getGraf().iniGrafica();

                }
//
            }
        }
    }

    private class Cargar implements ActionListener {

        LinkedList jf;
        String[] valor;

        public void Convertir() {
            DecimalFormat df = new DecimalFormat("#0.0000");
            DecimalFormat df1 = new DecimalFormat("#0.00");
            Convertidor cv = new Convertidor();
            if (mi.lblMed.getText().equals("IN")) {
                mi.txtEspesorMM.setText("" + df.format(cv.INtoMM(Double.parseDouble(mi.txtEspesorIn.getText()))));
                mi.txtToleranciaNegMM.setText("" + df.format(cv.INtoMM(Double.parseDouble(mi.txtToleranciaNegIn.getText()))));
                mi.txtToleranciaPosMM.setText("" + df.format(cv.INtoMM(Double.parseDouble(mi.txtToleranciaPosIn.getText()))));

            } else {
                mi.txtEspesorMM.setText("" + mi.txtEspesorIn.getText());
                mi.txtToleranciaNegMM.setText("" + mi.txtToleranciaNegIn.getText());
                mi.txtToleranciaPosMM.setText("" + mi.txtToleranciaPosIn.getText());
                mi.txtEspesorIn.setText("" + df.format(cv.MMtoIN(Double.parseDouble(mi.txtEspesorMM.getText()))));
                mi.txtToleranciaNegIn.setText("" + df.format(cv.MMtoIN(Double.parseDouble(mi.txtToleranciaNegMM.getText()))));
                mi.txtToleranciaPosIn.setText("" + df.format(cv.MMtoIN(Double.parseDouble(mi.txtToleranciaPosMM.getText()))));

            }
            calcularRango();

        }

        public Cargar(LinkedList jf, String[] valor) {
            this.jf = jf;
            this.valor = valor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Object[] obj = new Object[jf.size()];
            jf.toArray(obj);
            int i = 0;
            for (int j = 0; j < jf.size(); j++) {

                JTextField text = (JTextField) obj[i];
                String value = "-";
                try {
                    while (value.equals("-")) {
                        value = JOptionPane.showInputDialog(mi, "Valor del " + valor[i] + ":", valor[i] + ":", 2);

                        if (value != null) {
                            if (value.isEmpty()) {
                                value = "-";
                            }
                            text.setText(value);
                        }
                        if (i == 2) {
                            Convertir();
                        }

                    }
                    i++;
                } catch (NullPointerException np) {
                    JOptionPane.showMessageDialog(mi, "Valor incorrecto");
                }
            }

        }
    }

    private class Convertir implements KeyListener {

        private JTextField j1, j2;
        private int tipo;

        public Convertir(JTextField j1, JTextField j2, int tipo) {
            this.j1 = j1;
            this.j2 = j2;
            this.tipo = tipo;

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Convertidor c = new Convertidor();
                if (tipo == 1) {
                    j2.setText(Double.toString(c.MMtoIN(Double.parseDouble(j1.getText()))));
                } else {
                    j2.setText(Double.toString(c.INtoMM(Double.parseDouble(j1.getText()))));
                }
                calcularRango();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        }

    }

    private class GenerarRepo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!mi.txtPulsos.getText().equals("0.0") || true) {
                try {
                    mensajeAutomatico ma = new mensajeAutomatico();
                    //ma.mostrar_pregunta("Generando reporte...", 4);
                    String nombreArchivo;
                    Date hoy = new Date();
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy");
                    SimpleDateFormat d = new SimpleDateFormat("hh-mm-ss");
                    String nombreG = mi.txtCliente.getText() + "_" + mi.txtPieza.getText();
                    nombreArchivo = "Grafica_" + s.format(hoy) + "_" + d.format(hoy) + "_" + nombreG;
                    ChartUtilities.saveChartAsPNG(
                            new File("C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\grafica\\" + nombreArchivo + ".png"),
                            mi.ls.getGraf().getChart(), 1200, 480);
                    abrirArchivo();
                    generarEncabezado(1);
                    generarEncabezado(11);

                    generarArchivo(9, 32);
                    ma.mostrar_pregunta("Medición finalizada.", 10000);
                } catch (IOException ex) {
                    Logger.getLogger(controladorMedir.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(mi, "NO SE CAPTURÓ NINGUNA MEDICIÓN", "ERROR", 1);
            }

            mi.btnCargarDatos.setEnabled(true);
            mi.btnFinalizarMed.setEnabled(false);
            mi.btnIniciarMed.setEnabled(true);
            mi.btnGenerarRepo.setEnabled(false);
        }

    }

}
