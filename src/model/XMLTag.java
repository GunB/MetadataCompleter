package model;

import java.util.ArrayList;

public class XMLTag {

    String strName;
    ArrayList<String> arrAttr;

    public XMLTag(String strName) {
        this.strName = strName;
    }

    public String getStrName() {
        return this.strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public ArrayList<String> getArrAttr() {
        return this.arrAttr;
    }

    public void setArrAttr(ArrayList<String> arrAttr) {
        this.arrAttr = arrAttr;
    }

    public String returnFullTagData(String strData) {
        return "<" + this.strName + ">" + strData + "</" + this.strName + ">";
    }
}
