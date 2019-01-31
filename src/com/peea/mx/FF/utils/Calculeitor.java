/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.utils;

/**
 *
 * @author Sistemas
 */
public class Calculeitor {

    private final int DECIMALES_ROUND = 3;

    public static void main(String[] args) {
        Calculeitor cal = new Calculeitor();
        double x = cal.calcularCPK(6.71, 5.99, 6.42, 0.186);
        System.out.println("CPK:" + x);
    }

    public double calcularCPK(double eps, double epi, double x, double desv) {
        double v1 = formatearDecimales(eps - x, DECIMALES_ROUND);
        double v2 = formatearDecimales(x - epi, DECIMALES_ROUND);
        double v3 = formatearDecimales(desv * 3, DECIMALES_ROUND);
        double result = 0;
        System.out.println(v1 + "//" + v2 + "//" + v3);
        v1 = formatearDecimales(v1 / v3, DECIMALES_ROUND);
        v2 = formatearDecimales(v2 / v3, DECIMALES_ROUND);
        
        System.out.print("El menor es:");
        if (v1 < v2) {
            System.out.println("V1");
            result = v1;
        } else {
            result = v2;
            System.out.println("V2");
        }
        System.out.println("V1//V2//DESV");
        System.out.println(v1 + "//" + v2 + "//" + v3);

        return result;
    }

    public Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }
}
