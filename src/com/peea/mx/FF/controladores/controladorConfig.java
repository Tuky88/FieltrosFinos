/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.controladores;

import com.peea.mx.FF.iFrame.configuracioniFrame;
import com.peea.mx.FF.modelos.Cliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Sistemas
 */
public class controladorConfig {

    private configuracioniFrame ci;
    LinkedList ls;
    Cliente cd;

    public controladorConfig(configuracioniFrame ci) {
        this.ci = ci;
        ls = leerArchivo();
        cargarRegistros(this.ci.comboCliente);
        this.ci.comboCliente.addItemListener(new CambiarCliente());
        this.ci.btnGuardarCliente.addActionListener(new CargarCliente());
        this.ci.btnEliminarCliente.addActionListener(new EliminarCliente());
        this.ci.btnGuardar.addActionListener(new GuardarCambios());
        this.ci.btnReset.addActionListener(new Restablecer());
         cd = (Cliente) ls.get(0);
        mostrarCliente(cd);

    }

    private LinkedList leerArchivo() {
        try {
            ls = new LinkedList();
            File archivo = new File("C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\Archivos\\clientes.axe");
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String lectura;
            while ((lectura = br.readLine()) != null) {
                String separeitor[] = lectura.split(";");
                Cliente c = new Cliente(separeitor[0], separeitor[1], separeitor[2], separeitor[3], separeitor[4]);
                System.out.println(c.toString());
                ls.add(c);
            }
            fr.close();
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(controladorConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    private void vaciarArchivo() {
        FileWriter fw = null;
        try {
            File archivo = new File("C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\Archivos\\clientes.axe");
            fw = new FileWriter(archivo);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            pw.close();
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controladorConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controladorConfig.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void guardarArchivo(Cliente c) {
        FileWriter fw = null;
        try {
            File archivo = new File("C:\\FieltrosFinos\\src\\com\\peea\\mx\\FF\\Archivos\\clientes.axe");
            fw = new FileWriter(archivo, true);
            PrintWriter pw = new PrintWriter(fw);
            String data = c.getNombre() + ";" + c.getDireccion() + ";" + c.getTelefono() + ";" + c.getPrefMed() + ";" + c.getPrefPrint();
            pw.println(data);
            pw.close();
            fw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(controladorConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(controladorConfig.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void cargarRegistros(JComboBox combo) {

        for (int i = 0; i < ls.size(); i++) {
            Cliente nc = (Cliente) ls.get(i);
            combo.addItem((Object) nc.getNombre());
        }
    }

    private void guardarRegistros() {
        vaciarArchivo();
        for (int i = 0; i < ls.size(); i++) {
            guardarArchivo((Cliente) ls.get(i));
        }
    }

    private  class Restablecer implements ActionListener {

        public Restablecer() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            mostrarCliente(cd);
        }
        
    }

    private class GuardarCambios implements ActionListener {

        public GuardarCambios() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String pref, imp;
            if (ci.rbIN.isSelected()) {
                pref = "2";
            } else {
                pref = "1";
            }
            if (ci.chkImprimir.isSelected()) {
                imp = "2";
            } else {
                imp = "1";
            }
            Cliente c = new Cliente(ci.txtEmpresa.getText(), ci.txtDir.getText(), ci.txtTel.getText(), pref, imp);
            ls.set(ci.comboCliente.getSelectedIndex(), c);
            guardarRegistros();
        }
    }

    private class EliminarCliente implements ActionListener {

        public EliminarCliente() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int removido = ci.comboCliente.getSelectedIndex();
            if (removido == 0) {
                JOptionPane.showMessageDialog(ci, "NO SE PUEDE ELIMINAR EL ELEMENTO POR DEFECTO", "ERROR", 2);
            } else {
                ls.remove(removido);
                ci.comboCliente.removeItemAt(removido);
                guardarRegistros();
            }
        }
    }

    public void mostrarCliente(Cliente nc) {
        ci.txtEmpresa.setText(nc.getNombre());
        ci.txtTel.setText(nc.getTelefono());
        ci.txtDir.setText(nc.getDireccion());
        if (nc.getPrefMed() == 1) {
            ci.rbMM.setSelected(true);
        } else {
            ci.rbIN.setSelected(true);
        }
        if (nc.getPrefPrint() == 1) {
            ci.chkImprimir.setSelected(false);
        } else {
            ci.chkImprimir.setSelected(true);
        }
    }

    private class CambiarCliente implements ItemListener {

        public CambiarCliente() {
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            Cliente nc = (Cliente) ls.get(ci.comboCliente.getSelectedIndex());
            mostrarCliente(nc);
        }
    }

    private class CargarCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String pref, imp;
            int bandera = 99;
            if (ci.rbIN.isSelected()) {
                pref = "2";
            } else {
                pref = "1";
            }
            if (ci.chkImprimir.isSelected()) {
                imp = "2";
            } else {
                imp = "1";
            }
            System.out.println(pref);
            for (int i = 0; i < ls.size(); i++) {

                if (ci.txtEmpresa.getText().equals((String) ci.comboCliente.getSelectedItem())) {
                    bandera = 0;
                }
            }
            if (bandera == 99) {

                Cliente c = new Cliente(ci.txtEmpresa.getText(), ci.txtDir.getText(), ci.txtTel.getText(), pref, imp);
                ls.add(c);
                ci.comboCliente.addItem(c.getNombre());
                guardarArchivo(c);
                System.out.println(c.toString());
            } else {
                JOptionPane.showMessageDialog(ci, "YA EXISTE UN ELEMENTO CON ESTE NOMBRE", "ERROR", 1);
            }
        }

    }

}
