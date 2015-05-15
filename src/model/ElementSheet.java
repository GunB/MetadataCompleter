/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author hangarita
 */
public class ElementSheet {

    String strType;

    String strNombre;
    String strID;
    String strDesc;
    
    Document docXML;
    
    ArrayList<ElementSheet> objElements;
    
    HashMap<String, String> arrData;
    
    ElementHandler eleData;

    public ElementSheet(String strType, String strID) {
        this.strType = strType;
        this.strID = strID;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
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

    public ArrayList<ElementSheet> getObjElements() {
        return objElements;
    }

    public void setObjElements(ArrayList<ElementSheet> objElements) {
        this.objElements = objElements;
    }

    public HashMap<String, String> getArrData() {
        return arrData;
    }

    public void setArrData(HashMap<String, String> arrData) {
        this.arrData = arrData;
    }

    public Document getDocXML() {
        return docXML;
    }

    public void setDocXML(Document docXML) {
        this.docXML = docXML;
    }

    public ElementHandler getEleData() {
        return eleData;
    }

    public void setEleData(ElementHandler eleData) {
        this.eleData = eleData;
    }

}
