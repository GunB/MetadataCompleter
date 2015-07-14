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
import utiility.XMLUtility;

public class SharableContentObject {

    String strType;
    String strNombre;
    String strID;
    String strDesc;
    Document docXML;
    Node ndRelation;
    HashMap<String, String> arrRelations = new HashMap();

    ArrayList<SharableContentObject> objElements = new ArrayList();

    HashMap<String, String> arrData = new HashMap();

    ElementHandler eleData;

    public String getStrType() {
        return this.strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrNombre() {
        return this.strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrID() {
        return this.strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getStrDesc() {
        return this.strDesc;
    }

    public void setStrDesc(String strDesc) {
        this.strDesc = strDesc;
    }

    public Document getDocXML() {
        return this.docXML;
    }

    public static String GetType(SharableContentObject scoData) throws NullPointerException {
        String strID1 = scoData.getStrID();
        String strResp = "";

        if (strID1.contains("re")) {
            strResp = "Recurso".toUpperCase();
        } else if (strID1.contains("ob")) {
            strResp = "Objeto".toUpperCase();
        } else if (strID1.contains("le")) {
            strResp = "Leccion".toUpperCase();
        } else {
            strResp = "Nivel".toUpperCase();
        }

        System.out.println("Found DATA [Type]: " + strResp);

        return strResp;
    }

    public SharableContentObject(ElementHandler eleData) throws IOException, SAXException, ParserConfigurationException, NullPointerException {
        this.eleData = eleData;

        this.docXML = eleData.Read();

        this.docXML.getDocumentElement().normalize();
        System.out.println("Root element :" + this.docXML.getDocumentElement().getNodeName());

        String strNode = "<relation>\n        <kind schema=\"\"/>\n        <resource>\n            <identifier>\n                <catalog catName=\"\" catSource=\"\"/>\n            </identifier>\n            <description lang=\"\"/>\n        </resource>\n</relation>";

        this.ndRelation = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strNode.getBytes())).getDocumentElement();

        this.ndRelation.normalize();

        ReadElement();
    }

    private void ReadElement() {
        Node ndRoot = this.docXML.getDocumentElement().cloneNode(true);
        NodeList list = ndRoot.getChildNodes();

        this.strID = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[]{"general", "identifier", "catalog"})));
        this.strNombre = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[]{"general", "title"})));
        this.strDesc = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[]{"general", "description"})));
        this.strType = GetType(this);
    }

    public boolean isRelationed() {
        Node ndRoot = this.docXML.getDocumentElement().cloneNode(true);
        NodeList list = ndRoot.getChildNodes();
        String ReadNode = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[]{"relation"})));

        if (ReadNode == null) {
            return false;
        }
        return true;
    }

    public void SetRelation(SharableContentObject scoObjeto, String strKind) {
        Node ndData = this.ndRelation.cloneNode(true);
        NodeList ndListTemp = ndData.getChildNodes();
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[]{"kind"})), strKind);
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[]{"resource", "identifier", "catalog"})), scoObjeto.strID);
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[]{"resource", "description"})), scoObjeto.strDesc);

        this.docXML.adoptNode(ndData);
        this.docXML.getDocumentElement().appendChild(ndData);
    }

    public void SaveChanges() throws IOException, TransformerException {
        this.eleData.WriteFinish(this.docXML);
    }
}
