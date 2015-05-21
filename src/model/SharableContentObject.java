/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static utiility.XMLUtility.ReadNode;
import static utiility.XMLUtility.ChangeNode;

/**
 *
 * @author hangarita
 */
public class SharableContentObject {

    String strType;

    String strNombre;
    String strID;
    String strDesc;

    Document docXML;
    Node ndRelation;

    ArrayList<SharableContentObject> objElements;

    HashMap<String, String> arrData;

    ElementHandler eleData;

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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

    public Document getDocXML() {
        return docXML;
    }

    public static String GetType(SharableContentObject scoData) {
        String strID1 = scoData.getStrID();
        String strResp = "";

        if (strID1.contains("re")) {
            strResp = "Recurso".toUpperCase();
        } else if(strID1.contains("ob")) {
            strResp = "Objeto".toUpperCase();
        } else if(strID1.contains("le")){
            strResp = "Leccion".toUpperCase();
        }

        System.out.println("Found DATA [" + "Type" + "]: " + strResp);

        return strResp;
    }

//</editor-fold>
    
    public SharableContentObject(ElementHandler eleData) throws IOException, SAXException, ParserConfigurationException {
        this.eleData = eleData;

        docXML = eleData.Read();
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        docXML.getDocumentElement().normalize();
        System.out.println("Root element :" + docXML.getDocumentElement().getNodeName());

        String strNode
                = "<relation>\n"
                + "        <kind schema=\"\"/>\n"
                + "        <resource>\n"
                + "            <identifier>\n"
                + "                <catalog catName=\"\" catSource=\"\"/>\n"
                + "            </identifier>\n"
                + "            <description lang=\"\"/>\n"
                + "        </resource>\n"
                + "</relation>";

        ndRelation = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(strNode.getBytes()))
                .getDocumentElement();

        ReadElement();
    }

    private void ReadElement() {

        Node ndRoot = docXML.getDocumentElement().cloneNode(true);
        NodeList list = ndRoot.getChildNodes();

        this.strID = ReadNode(list, new ArrayList<>(Arrays.asList("general", "identifier", "catalog")));
        this.strNombre = ReadNode(list, new ArrayList<>(Arrays.asList("general", "title")));
        this.strDesc = ReadNode(list, new ArrayList<>(Arrays.asList("general", "description")));
        this.strType = GetType(this);

    }
    
    public void SetRelation(SharableContentObject scoObjeto, String strKind){
        Node ndData = ndRelation.cloneNode(true);
        NodeList ndListTemp = ndData.getChildNodes();
        ndListTemp = ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("kind")), strKind);
        ndListTemp = ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "identifier", "catalog")), scoObjeto.strID);
        ndListTemp = ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "description")), scoObjeto.strDesc);
        //scoObjeto.getStrID();
        docXML.adoptNode(ndData);
        docXML.getDocumentElement().appendChild(ndData);
    }
    
    public void SaveChanges() throws IOException, TransformerException{
        eleData.WriteFinish(docXML);
    }

}
