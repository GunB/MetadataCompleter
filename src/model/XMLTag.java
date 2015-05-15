/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author hangarita
 */
public class XMLTag {

    String strName;
    ArrayList<String> arrAttr;

    public XMLTag(String strName) {
        this.strName = strName;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public ArrayList<String> getArrAttr() {
        return arrAttr;
    }

    public void setArrAttr(ArrayList<String> arrAttr) {
        this.arrAttr = arrAttr;
    }

    public String returnFullTagData(String strData) {
        return "<" + this.strName + ">" + strData + "</" + this.strName + ">";
    }
}
