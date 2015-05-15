/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author hangarita
 */
public class Objeto {

    String strNombre;
    String strID;
    String strDesc;
    Leccion lecLeccion;

    HashMap<String, String> arrData = new HashMap<>();

    ArrayList<Recurso> arrRecursos;

    public Objeto(String strNombre, String strID, String strDesc) {
        this.strNombre = strNombre;
        this.strID = strID;
        this.strDesc = strDesc;

        System.out.println(this.getClass() + ": " + strNombre + "\t" + strID + "\t" + strDesc);
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrID() {
        return strID;
    }

    public void setStrID(String sttrID) {
        this.strID = sttrID;
    }

    public String getStrDesc() {
        return strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public HashMap<String, String> getArrData() {
        return arrData;
    }

    public void setArrData(HashMap<String, String> arrData) {
        this.arrData = arrData;
    }

    public ArrayList<Recurso> getArrRecursos() {
        return arrRecursos;
    }

    public void setArrRecursos(ArrayList<Recurso> arrRecursos) {
        this.arrRecursos = arrRecursos;
    }

    public Leccion getLecLeccion() {
        return lecLeccion;
    }

    public void setLecLeccion(Leccion lecLeccion) {
        this.lecLeccion = lecLeccion;
    }

}
