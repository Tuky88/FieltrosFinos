/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.peea.mx.FF.iFrame.medicioniFrame;
import com.peea.mx.FF.utils.Convertidor;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Sistemas
 */
public class controladorMedir {

    public medicioniFrame mi;

    public controladorMedir(medicioniFrame mi) {
        this.mi = mi;
        this.mi.btnCargarDatos.addActionListener(new Cargar(this.mi.jt, this.mi.valor));
        this.mi.btnIniciarMed.addActionListener(new Medir());
        this.mi.btnFinalizarMed.addActionListener(new FinMed());
    }

    private class FinMed implements ActionListener {

        public FinMed() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!mi.btnIniciarMed.isEnabled()) {
                JOptionPane.showMessageDialog(mi, "¡Fin de medición!", "AVISO", 2);
                mi.txtTiempoFin.setText(mi.lblHora.getText());
                mi.btnFinalizarMed.setEnabled(false);
                mi.btnIniciarMed.setEnabled(true);
                mi.btnCargarDatos.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(mi, "¡No hay ninguna medición activa!", "ERROR", 0);
            }
        }
    }

    private class Medir implements ActionListener {

        public Medir() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mi.txtAnchoB.getText().isEmpty() || mi.txtAnchoM.getText().isEmpty() || mi.txtCliente.getText().isEmpty() || mi.txtComB.getText().isEmpty()
                    || mi.txtComP.getText().isEmpty() || mi.txtDensidad.getText().isEmpty() || mi.txtEspesorIn.getText().isEmpty() || mi.txtEspesorMM.getText().isEmpty()
                    || mi.txtEstilo.getText().isEmpty() || mi.txtMetrosB.getText().isEmpty() || mi.txtMetrosM.getText().isEmpty() || mi.txtPO.getText().isEmpty()
                    || mi.txtPeso.getText().isEmpty() || mi.txtPieza.getText().isEmpty() || mi.txtRangoMM.getText().isEmpty() || mi.txtToleranciaNegIn.getText().isEmpty()
                    || mi.txtToleranciaNegMM.getText().isEmpty() || mi.txtToleranciaPosIn.getText().isEmpty() || mi.txtToleranciaPosMM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mi, "¡Faltan campos!", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(mi, "¡Comenzando medición!", "AVISO", 2);
                mi.txtTiempoInicio.setText(mi.lblHora.getText());
                mi.btnIniciarMed.setEnabled(false);
                mi.btnFinalizarMed.setEnabled(true);
                mi.btnCargarDatos.setEnabled(false);

            }
        }
    }

    private class Cargar implements ActionListener {

        LinkedList jf;
        String[] valor;

        public void Convertir() {
            DecimalFormat df = new DecimalFormat("#.0000");
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
        }

        public Cargar(LinkedList jf, String[] valor) {
            this.jf = jf;
            this.valor = valor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("chi");
            System.out.println(jf.size());
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

}
