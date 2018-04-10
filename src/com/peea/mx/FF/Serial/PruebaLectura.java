/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.Serial;

import java.util.Enumeration;



public class PruebaLectura {

    String ruta = System.getProperty("java.home");
String sistemaOperativo = System.getProperty("os.name");
    public PruebaLectura() {
        System.out.println(ruta +"//"+sistemaOperativo);
    }

    
    public static void main(String[] args) {
        PruebaLectura p=new PruebaLectura();
    }
    
}
