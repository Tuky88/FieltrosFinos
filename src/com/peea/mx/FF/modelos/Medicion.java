/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.modelos;

import java.util.Arrays;

/**
 *
 * @author Sistemas
 */
public class Medicion {

    private int indice;
    private double izquierda;
    private double centro;
    private double derecha;

    public Medicion(int indice, double izquierda, double centro, double derecha) {
        this.indice = indice;
        this.izquierda = izquierda;
        this.centro = centro;
        this.derecha = derecha;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public double getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(double izquierda) {
        this.izquierda = izquierda;
    }

    public double getCentro() {
        return centro;
    }

    public void setCentro(double centro) {
        this.centro = centro;
    }

    public double getDerecha() {
        return derecha;
    }

    public void setDerecha(double derecha) {
        this.derecha = derecha;
    }

    public String[] getDatos() {
        String[] s = {Double.toString(izquierda), Double.toString(centro), Double.toString(derecha)};

        System.out.println(Arrays.toString(s));
        return s;
    }

    @Override
    public String toString() {
        return "Medicion{" + "izquierda=" + izquierda + ", centro=" + centro + ", derecha=" + derecha + '}';
    }

}
