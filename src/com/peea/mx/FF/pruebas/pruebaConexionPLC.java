/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.pruebas;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;

/**
 *
 * @author user
 */
public class pruebaConexionPLC {

    S7Connector connector;

    public void conectar(String ip) {

        //Open TCP Connection
        connector
                = S7ConnectorFactory
                        .buildTCPConnector()
                        .withHost(ip)
                        .withRack(0) //optional
                        .withSlot(2) //optional
                        .build();
    }
    public String leer(int position,int buffer)
    {
            //Read from DB100 10 bytes
    byte[] bs = connector.read(DaveArea.DB, position, buffer, 0);
    return String.valueOf(bs);
    }
    public static void main(String[] args) {
        pruebaConexionPLC pcp=new pruebaConexionPLC();
        pcp.conectar("192.168.1.1");
        
    }
}
