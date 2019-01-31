/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.utils;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Sistemas
 */
public class graficadorUnico {

    private XYSeries[] indices;
    private String[] values;
    private XYDataset dataset;
    private JPanel grafica;
    private double valor;
    private String x;
    private String y;
    private JTextField j1, j2, j3, j4;
    private ChartPanel panelito;
    private static Color COLOR_GRAFICA = Color.WHITE;
    private static Color COLOR_ROJO = Color.RED;
    private static Color COLOR_AZUL = Color.BLUE;
    private static Color COLOR_VERDE = Color.GREEN;
    private static Color COLOR_GRAFICA_FONDO = Color.BLACK;
    private int color;
    JFreeChart chart;
    public XYSeriesCollection seriesXY;

    public JFreeChart getChart() {
        return chart;
    }

    public graficadorUnico(String[] indices, JPanel panelin, JTextField j1, JTextField j2, JTextField j3, JTextField j4, String nombre,int color) {
        this.values = indices;
        this.grafica = panelin;
        XYSeries xys1 = new XYSeries(nombre,false,false);
        XYSeries xys2 = new XYSeries("POSITIVO (+)",false,false);
        XYSeries xys3 = new XYSeries("NEGATIVO (-)",false,false);
        this.color=color;
        seriesXY = new XYSeriesCollection();
        seriesXY.addSeries(xys1);
        seriesXY.addSeries(xys2);
        seriesXY.addSeries(xys3);

        dataset = seriesXY;

        chart = ChartFactory.createXYLineChart("", // Título
                "Tiempo", // Etiqueta Coordenada X
                "Grosor", (XYDataset) dataset, // Datos
                PlotOrientation.VERTICAL,
                false, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(COLOR_GRAFICA_FONDO);
        plot.setOutlinePaint(COLOR_GRAFICA_FONDO);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        // XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        XYItemRenderer renderer = plot.getRenderer();

        Stroke stroke = new BasicStroke(4);
        Stroke stroken = new BasicStroke(2);
        
        renderer.setSeriesStroke(0, stroken);
        renderer.setSeriesStroke(1, stroke);
        renderer.setSeriesStroke(2, stroke);
        renderer.setSeriesPaint(1, COLOR_GRAFICA);
        renderer.setSeriesPaint(2, COLOR_GRAFICA);
        switch(this.color)
        {
            case 1:
                renderer.setSeriesPaint(0, COLOR_ROJO);
                break;
            case 2:
                renderer.setSeriesPaint(0, COLOR_AZUL);
                break;
            case 3:
                renderer.setSeriesPaint(0, COLOR_VERDE);
                break;
                
        }
        plot.setRenderer(renderer);
        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chart.setBackgroundPaint(COLOR_GRAFICA);
        chart.setBorderPaint(COLOR_GRAFICA);

        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.j4 = j4;

        panelito = new ChartPanel(chart);

        panelito.setBackground(Color.black);
        grafica.setLayout(new java.awt.BorderLayout());
        grafica.setBackground(Color.black);

        grafica.add(panelito, BorderLayout.CENTER);

        panelito.setMaximumSize(new Dimension(400, 300));
        grafica.setMaximumSize(new Dimension(400, 300));
        grafica.validate();
    }

//    public void repaintGrafica() {
//        panelito = new ChartPanel(chart);
//        panelito.setBackground(Color.black);
//
//        grafica.setLayout(new java.awt.BorderLayout());
//        grafica.setBackground(Color.black);
//        grafica.add(panelito, BorderLayout.CENTER);
//        panelito.setMaximumSize(new Dimension(200, 300));
//        grafica.setMaximumSize(new Dimension(200, 300));
//        grafica.validate();
//    }

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
        if (!this.j1.getText().isEmpty() && !this.j2.getText().isEmpty() && !this.j3.getText().isEmpty()) {
            
            System.out.println(j1.getText() + "//" + j2.getText() + "//" + j3.getText() + "//");

            XYSeries xymodi = seriesXY.getSeries(0);
            xymodi.addOrUpdate(Double.parseDouble(this.j4.getText()), Double.parseDouble(this.j1.getText()));
            xymodi = seriesXY.getSeries(1);
            xymodi.addOrUpdate(Double.parseDouble(this.j4.getText()), Double.parseDouble(this.j2.getText()));
            xymodi = seriesXY.getSeries(2);
            xymodi.addOrUpdate(Double.parseDouble(this.j4.getText()), Double.parseDouble(this.j3.getText()));
            //this.j4.setText(String.valueOf(Double.parseDouble(this.j4.getText()) + 1));

        }
    }
}
