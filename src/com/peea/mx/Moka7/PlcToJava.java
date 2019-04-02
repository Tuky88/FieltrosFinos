/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.Moka7;

/**
 *
 * @author Axel Reyes
 */
public class PlcToJava {

    public S7Client client = new S7Client();
    public byte[] Buffer = new byte[65536];

    public PlcToJava() {
        
    }

    public void conectar() {
        client.SetConnectionType(S7.OP);
        client.ConnectTo("192.168.1.1", 0, 1);
        //System.out.println("Conexion exitosa...");
        if (client.Connected) {
            System.out.println("conectado...");
        } else {
            System.out.println("No se ");
        }
    }

    public int leer(int pos) {
        int readData = 0;
        if (client.Connected) {

            client.ReadArea(S7.S7AreaDB, pos, 0, 11, Buffer);
            readData = S7.GetWordAt(Buffer, 6);
            //System.out.println(readData);
            //System.out.println(Integer.toString(readData));
        } else {
            //System.out.println("NO CONECTADO...");
        }
        return readData;
    }
    public void desconectar()
    {
        client.Disconnect();
    }
}
