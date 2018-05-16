/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Sistemas
 */
public class graficadorLineal  {

    private XYSeries[] indices;
    private String[] values;
    private DefaultCategoryDataset dataset;
    private JPanel grafica;
    private double valor;
    private String x;
    private String y;
    private JTextField j1, j2, j3, j4, j5, j6;
    private ChartPanel panelito;
    JFreeChart chart;

    public JFreeChart getChart() {
        return chart;
    }
    

    public graficadorLineal(String[] indices, JPanel panelin, JTextField j1, JTextField j2, JTextField j3, JTextField j4, JTextField j5, JTextField j6) {
        this.values = indices;
        this.grafica = panelin;
        this.indices = new XYSeries[indices.length];
        for (int i = 0; i < indices.length; i++) {
            this.indices[i] = new XYSeries(indices[i]);
        }
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(
                "", // Título
                "Tiempo", // Etiqueta Coordenada X
                "Grosor", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                true,
                true
        );
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.j4 = j4;
        this.j5 = j5;
        this.j6 = j6;

        panelito = new ChartPanel(chart);
        panelito.setBackground(Color.black);
        grafica.setLayout(new java.awt.BorderLayout());
        grafica.setBackground(Color.black);
        grafica.add(panelito, BorderLayout.CENTER);
        grafica.validate();
    }
    public void repaintGrafica()
    {
                panelito = new ChartPanel(chart);
        panelito.setBackground(Color.black);
        grafica.setLayout(new java.awt.BorderLayout());
        grafica.setBackground(Color.black);
        grafica.add(panelito, BorderLayout.CENTER);
        grafica.validate();
    }

    public JTextField getJ1() {
        return j1;
    }

    public void setJ1(JTextField j1) {
        this.j1 = j1;
    }

    public JTextField getJ2() {
        return j2;
    }

    public void setJ2(JTextField j2) {
        this.j2 = j2;
    }

    public JTextField getJ3() {
        return j3;
    }

    public void setJ3(JTextField j3) {
        this.j3 = j3;
    }

    public void graficar() {
        if (!this.j1.getText().isEmpty() && !this.j2.getText().isEmpty() && !this.j3.getText().isEmpty() && !this.j5.getText().isEmpty()
                && !this.j6.getText().isEmpty()) {
            this.j4.setText(String.valueOf(Double.parseDouble(this.j4.getText()) + 1));
            System.out.println(j1.getText() + "//" + j2.getText() + "//" + j3.getText() + "//" + j5.getText() + "//" + j6.getText());
            dataset.addValue(Double.parseDouble(this.j1.getText()), "IZQUIERDA", this.j4.getText());
            dataset.addValue(Double.parseDouble(this.j2.getText()), "CENTRO", this.j4.getText());
            dataset.addValue(Double.parseDouble(this.j3.getText()), "DERECHA", this.j4.getText());
            dataset.addValue(Double.parseDouble(this.j5.getText()), "GROSOR(+)", this.j4.getText());
            dataset.addValue(Double.parseDouble(this.j6.getText()), "GROSOR(-)", this.j4.getText());
            //this.j4.setText(String.valueOf(Double.parseDouble(this.j4.getText()) + 1));

        }
    }

    public void agregarPunto(double valor, String x, String y) {
        this.valor = valor;
        this.x = x;
        this.y = y;
        dataset.addValue(this.valor, this.x, this.y);
    }

//    @Override
//    public void run() {
//
//        while (true) {
//            synchronized (this) {
//
//                try {
//                    wait(2000);
//                    if (!this.j1.getText().isEmpty() && !this.j2.getText().isEmpty() && !this.j3.getText().isEmpty() && !this.j5.getText().isEmpty()
//                            && !this.j6.getText().isEmpty()) {
//                        this.j4.setText(String.valueOf(Double.parseDouble(this.j4.getText()) + 1));
//                        System.out.println(j1.getText() + "//" + j2.getText() + "//" + j3.getText() + "//" + j5.getText() + "//" + j6.getText());
//                        dataset.addValue(Double.parseDouble(this.j1.getText()), "IZQUIERDA", this.j4.getText());
//                        dataset.addValue(Double.parseDouble(this.j2.getText()), "CENTRO", this.j4.getText());
//                        dataset.addValue(Double.parseDouble(this.j3.getText()), "DERECHA", this.j4.getText());
//                        dataset.addValue(Double.parseDouble(this.j5.getText()), "GROSOR(+)", this.j4.getText());
//                        dataset.addValue(Double.parseDouble(this.j6.getText()), "GROSOR(-)", this.j4.getText());
//                        //this.j4.setText(String.valueOf(Double.parseDouble(this.j4.getText()) + 1));
//
//                    } else {
//                        System.out.println("f:" + j1.getText() + "//" + j2.getText() + "//" + j3.getText() + "//" + j5.getText() + "//" + j6.getText());
//                    }
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(graficadorLineal.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//
//    }
}
