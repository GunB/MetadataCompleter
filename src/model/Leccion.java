/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author hangarita
 */
public class Leccion {

    String strNombre;
    String strID;
    String strDesc;

    Objeto objObjeto;

    public Leccion(String strNombre, String strID, String strDesc) {
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

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getStrDesc() {
        return strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public Objeto getObjObjeto() {
        return objObjeto;
    }

    public void setObjObjeto(Objeto objObjeto) {
        this.objObjeto = objObjeto;
    }

}
