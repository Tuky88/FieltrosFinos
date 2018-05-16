/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peea.mx.FF.pruebas;

/**
 *
 * @author Sistemas
 */
import com.peea.mx.FF.modelos.Medicion;
import com.peea.mx.FF.utils.Convertidor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CrearFicherosExcel {

    private String nombreArchivo;
    private String rutaArchivo;
    private String hoja;
    private XSSFWorkbook libro;
    private XSSFSheet hoja1;
    private File plantilla;
    private File file;
    FormulaEvaluator evaluator;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CrearFicherosExcel(String nombreArchivo, String rutaArchivo, String hoja, String plantilla) {
        this.status = true;
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo + "\\" + this.nombreArchivo;
        this.hoja = hoja;
        this.plantilla = new File(plantilla);
        try {
            libro = new XSSFWorkbook(this.plantilla);

        } catch (IOException ex) {
            Logger.getLogger(CrearFicherosExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(CrearFicherosExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.hoja1 = libro.getSheet(this.hoja);
        evaluator = libro.getCreationHelper().createFormulaEvaluator();
        libro.setForceFormulaRecalculation(true);
    }

    public CrearFicherosExcel() {
    }

    public void Sescribir(int x, int y, String valor, int tipo) {
        String valorsito = valor;
        XSSFRow row = hoja1.getRow(x);
        XSSFCell cell = row.getCell(y);
        if (tipo == 1) {
            cell.setCellType(CellType.STRING);

        } else {

            cell.setCellType(CellType.NUMERIC);

        }
        cell.setCellValue(valorsito);

    }

    public void escribir(int x, int y, String valor, int tipo) {
        String valorsito = valor;
        XSSFRow row = hoja1.getRow(x);
        if (row == null) {
            row = hoja1.createRow(x);
        }
        if (tipo == 0) {
            XSSFCell cell = row.createCell(y);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula(valorsito);
//        System.out.println(cell.getCellFormula());
            evaluator.evaluateInCell(cell);
        } else {
            XSSFCell cell = row.createCell(y);
            cell.setCellType(CellType.STRING);
            
            cell.setCellValue(valorsito);
        }

        //System.out.println(cell.getRichStringCellValue());
    }

    public double leer(int x, int y) {

        XSSFRow row = hoja1.getRow(x);

        XSSFCell cell;

        cell = row.getCell(y);
        DecimalFormat df = new DecimalFormat("#.000");
        return Double.parseDouble(df.format(cell.getNumericCellValue()));
    }

    public void escribir(Medicion m, int x, double superior, double inferior) {

        XSSFRow row = hoja1.getRow(x);
        if (row == null) {
            row = hoja1.createRow(x);
        }
        XSSFCell cell;
        for (int i = 0; i <= 10; i++) {
            cell = row.createCell(i);
            Convertidor cv = new Convertidor();
            switch (i) {
                case 0:
                    cell.setCellValue(m.getIndice() * 3.141592);
                    cell.setCellType(CellType.NUMERIC);
                    break;
                case 1:
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(cv.MMtoIN(m.getIndice() * 3.141592));
                    break;
                case 2:
                    if (m.getIzquierda() > superior) {
                        cell.setCellValue("(+)");
                    } else if (m.getIzquierda() < inferior) {
                        cell.setCellValue("(-)");
                    }
                    break;
                case 3:
                    if (isStatus()) {
                        cell.setCellValue(m.getIzquierda());
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(cv.INtoMM(m.getIzquierda()));
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                case 4:
                    if (isStatus()) {
                        cell.setCellValue(cv.MMtoIN(m.getIzquierda()));
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(m.getIzquierda());
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                case 5:
                    if (m.getCentro() > superior) {
                        cell.setCellValue("(+)");
                    } else if (m.getCentro() < inferior) {
                        cell.setCellValue("(-)");
                    }
                    break;
                case 6:
                    if (isStatus()) {
                        cell.setCellValue(m.getCentro());
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(cv.INtoMM(m.getCentro()));
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                case 7:
                    if (isStatus()) {
                        cell.setCellValue(cv.MMtoIN(m.getCentro()));
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(m.getCentro());
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                case 8:
                    if (m.getDerecha() > superior) {
                        cell.setCellValue("(+)");
                    } else if (m.getDerecha() < inferior) {
                        cell.setCellValue("(-)");
                    }
                    break;
                case 9:
                    if (isStatus()) {
                        cell.setCellValue(m.getDerecha());
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(cv.INtoMM(m.getDerecha()));
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;
                case 10:
                    if (isStatus()) {
                        cell.setCellValue(cv.MMtoIN(m.getDerecha()));
                        cell.setCellType(CellType.NUMERIC);
                    } else {
                        cell.setCellValue(m.getDerecha());
                        cell.setCellType(CellType.NUMERIC);
                    }
                    break;

            }
        }

    }

    public void cargarArchivo() {
        
        file = new File(rutaArchivo);
        try (FileOutputStream fileOuS = new FileOutputStream(file)) {
            if (file.exists()) {// si el archivo existe se elimina
//                file.delete();
//                System.out.println("Archivo eliminado");
            }
            libro.write(fileOuS);
            fileOuS.flush();
            fileOuS.close();
            System.out.println("Archivo Creado");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void imprimirArchivo()
    {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop(); 
if (desktop.isSupported(Desktop.Action.PRINT)){ 
try {
desktop.print(file);
} catch (Exception e){
System.out.print("El sistema no permite imprimir usando la clase Desktop"); 
e.printStackTrace();
}
}else{ 
System.out.print("El sistema no permite imprimir usando la clase Desktop"); 
} 
    }
}
