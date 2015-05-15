/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.w3c.dom.Document;

/**
 *
 * @author hangarita
 */
public class ZipXMLFile {
    String strParent;
    String strPath;
    
    Document docXML;

    public ZipXMLFile(String strParent, String strPath, Document docXML) {
        this.strParent = strParent;
        this.strPath = strPath;
        this.docXML = docXML;
    }

    public String getStrParent() {
        return strParent;
    }

    public void setStrParent(String strParent) {
        this.strParent = strParent;
    }

    public String getStrPath() {
        return strPath;
    }

    public void setStrPath(String strPath) {
        this.strPath = strPath;
    }

    public Document getDocXML() {
        return docXML;
    }

    public void setDocXML(Document docXML) {
        this.docXML = docXML;
    }
    
    
}
