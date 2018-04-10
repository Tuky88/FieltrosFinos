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
public class Convertidor {
    private double valor;

    public Convertidor() {
        
    }
    public double MMtoIN(double valor)
    {
        System.out.println(valor/25.4);
        return valor/25.4;
    }
    
    public double INtoMM(double valor)
    {
        System.out.println(valor*25.4);
        return valor*25.4;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

   
    
    
}
