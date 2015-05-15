/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Leccion;
import model.Objeto;
import model.Recurso;
import model.XMLTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utiility.XMLUtility;
import static utiility.XMLUtility.printDocument;

/**
 *
 * @author hangarita
 */
public class MetadataParser {

    ZipReader xlsRead;
    HashMap<String, HashMap> jConfig;
    public Leccion lecData;
    private Document xmlMetaBase = null;
    Node ndRelacion = null;

    public Document xmlMetaActual;
    File fXmlFile;

    public void ReadingConfig() throws IOException {
        //String strUrl = System.getProperty("user.dir").concat("\\config.json");
        //System.out.println(strUrl);
        //File f = new File(strUrl);
        //this.jConfig = new Gson().fromJson(new FileReader(f).toString(), HashMap.class);
        //System.out.println(jObject);
    }

    public void ReadMetadataBase() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        if (xmlMetaBase == null) {

            String strUrl = System.getProperty("user.dir").concat(File.separator + "metadata.xml");
            System.out.println(strUrl);
            fXmlFile = new File(strUrl);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            //this.jConfig = new Gson().fromJson(new FileReader(f).toString(), HashMap.class);
            //System.out.println(jObject);

            //printDocument(doc, System.out);
            ndRelacion = doc.getElementsByTagName("relation").item(0);
            // retrieve the element 'link'
            Element element = (Element) doc.getElementsByTagName("relation").item(0);
            // remove the specific node
            element.getParentNode().removeChild(element);

            xmlMetaBase = doc;
        }
        xmlMetaActual = xmlMetaBase;
    }

    public void CreateXMLFull(String strPath) throws TransformerException, IOException, ParserConfigurationException, SAXException {
        ReadMetadataBase();
        CreateFullXMLObjeto(strPath, lecData.getObjObjeto());

        Iterator<Recurso> arrRecData = lecData.getObjObjeto().getArrRecursos().iterator();

        while (arrRecData.hasNext()) {
            Recurso recData = arrRecData.next();
            ReadMetadataBase();
            CreateFullXMLRecurso(strPath, recData);
        }

    }

    public void CreateFullXMLObjeto(String strPath, Objeto objObjeto) throws TransformerException, TransformerConfigurationException, IOException, ParserConfigurationException {
        Node staff = xmlMetaActual.getElementsByTagName("lom").item(0).cloneNode(true);
        NodeList list = staff.getChildNodes();

        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "title")), objObjeto.getStrNombre());
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "description")), objObjeto.getStrDesc());
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "identifier", "catalog")), objObjeto.getStrID());

        String[] arrKeyWords = objObjeto.getArrData().get("keyWord").split(",");
        list = XMLUtility.AddNodeList2Node(list, new ArrayList<>(Arrays.asList("general", "keyword")), arrKeyWords, new XMLTag("li"));

        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "learningGoal")), objObjeto.getArrData().get("learningGoal"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "triggerQuestion")), objObjeto.getArrData().get("triggerQuestion"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "pedagogicalAspect")), objObjeto.getArrData().get("pedagogicalAspect"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "recommendedUse")), objObjeto.getArrData().get("recommendedUse"));

        Node ndTemp = ndRelacion.cloneNode(true);
        NodeList ndListTemp = ndTemp.getChildNodes();
        
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("kind")), "Es parte de");
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "identifier", "catalog")), lecData.getStrID());
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "description")), lecData.getStrDesc());
        staff.appendChild(ndTemp);
        
        Iterator<Recurso> ite = objObjeto.getArrRecursos().iterator();
        while(ite.hasNext()){
            Recurso recTemp = ite.next();
            staff.appendChild(RelationObjetoRecurso(recTemp));
        }
        
        //staff.appendChild(RelationObjetoRecurso())
        // write the content into xml file
        lecData.setObjObjeto(objObjeto);
        SaveChangesXML(strPath + File.separator + objObjeto.getStrID() + File.separator + "metadata.xml", staff);
    }
    
    private Node RelationObjetoRecurso(Recurso recData){
        Node ndTemp = ndRelacion.cloneNode(true);
        NodeList ndListTemp = ndTemp.getChildNodes();
        
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("kind")), "Esta compuesto por");
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "identifier", "catalog")), recData.getStrID());
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "description")), recData.getStrDesc());
        
        return ndTemp;
    }

    public void CreateFullXMLRecurso(String strPath, Recurso recData) throws TransformerException, TransformerConfigurationException, IOException, ParserConfigurationException {
        Node staff = xmlMetaActual.getElementsByTagName("lom").item(0).cloneNode(true);
        NodeList list = staff.getChildNodes();

        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "title")), recData.getStrNombre());
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "description")), recData.getStrDesc());
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("general", "identifier", "catalog")), recData.getStrID());

        String[] arrKeyWords = recData.getArrData().get("keyWord").split(",");
        list = XMLUtility.AddNodeList2Node(list, new ArrayList<>(Arrays.asList("general", "keyword")), arrKeyWords, new XMLTag("li"));

        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "learningGoal")), recData.getArrData().get("learningGoal"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "triggerQuestion")), recData.getArrData().get("triggerQuestion"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "pedagogicalAspect")), recData.getArrData().get("pedagogicalAspect"));
        list = XMLUtility.ChangeNode(list, new ArrayList<>(Arrays.asList("educational", "description", "recommendedUse")), recData.getArrData().get("recommendedUse"));
        
        Node ndTemp = ndRelacion.cloneNode(true);
        NodeList ndListTemp = ndTemp.getChildNodes();
        
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("kind")), "Es parte de");
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "identifier", "catalog")), recData.getObjObjeto().getStrID());
        ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList<>(Arrays.asList("resource", "description")), recData.getObjObjeto().getStrDesc());
        
        staff.appendChild(ndTemp);
        
        SaveChangesXML(strPath + File.separator + recData.getStrID() + File.separator + "metadata.xml", staff);
    }

    public void SaveChangesXML(String strPath, Node ndSave) throws TransformerConfigurationException, TransformerException, IOException {
        String path = strPath;

        //(use relative path for Unix systems)
        File f = new File(path);
        //(works for both Windows and Linux)
        f.getParentFile().mkdirs();
        f.createNewFile();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(ndSave);
        StreamResult result = new StreamResult(f);
        transformer.transform(source, result);
    }

    private void pruebaPrint() {
        try {
            printDocument(xmlMetaActual, System.out);
        } catch (IOException | TransformerException ex) {
            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ReadObject(String strUrl) throws IOException {
        /*xlsRead = new ZipReader(strUrl);

        XSSFSheet readedSheet = xlsRead.ReadSheetbyId(0);
        xlsRead.arrSheetNames.remove(0);

        HashMap<String, String> objSheet = ExcelReader.turnSheetToObject(readedSheet);

        lecData = new Leccion(
                objSheet.get("Título"),
                objSheet.get("Nomenclatura"),
                objSheet.get("Descripción")
        );

        readedSheet = xlsRead.ReadSheetbyId(1);
        xlsRead.arrSheetNames.remove(0);

        objSheet = ExcelReader.turnSheetToObject(readedSheet);

        Objeto objTemp = new Objeto(
                objSheet.get("Título"),
                objSheet.get("Nomenclatura"),
                objSheet.get("Descripción")
        );
        
        objTemp.setLecLeccion(lecData);

        HashMap<String, String> objData = new HashMap<>();

        objData.put("keyWord", objSheet.get("Palabras Claves"));
        objData.put("learningGoal", objSheet.get("Objetivo de Aprendizaje\n" + " (Learning Goal)"));
        objData.put("triggerQuestion", objSheet.get("Pregunta Detonante\n" + "(Trigger Question)"));
        objData.put("pedagogicalAspect", objSheet.get("Aspectos Pedagógicos \n" + "(Pedagogical Aspects)"));
        objData.put("recommendedUse", objSheet.get("Sugerencia de Uso\n" + "(Recommended Use)"));

        objTemp.setArrData(objData);

        //lecData.setObjObjeto(objTemp);
        ArrayList<Recurso> recData = new ArrayList<>();

        for (String strName : xlsRead.arrSheetNames) {
            readedSheet = xlsRead.ReadSheetbyName(strName);
            objSheet = ExcelReader.turnSheetToObject(readedSheet);
            Recurso createRecurso = createRecurso(objSheet, objTemp);
            recData.add(createRecurso);
        }

        objTemp.setArrRecursos(recData);
        lecData.setObjObjeto(objTemp);
    }

    private Recurso createRecurso(HashMap<String, String> objSheet, Objeto objLeccion) {

        HashMap<String, String> objData = new HashMap<>();

        objData.put("keyWord", objSheet.get("Palabras Claves"));
        objData.put("learningGoal", objSheet.get("Objetivo de Aprendizaje\n" + " (Learning Goal)"));
        objData.put("triggerQuestion", objSheet.get("Pregunta Detonante\n" + "(Trigger Question)"));
        objData.put("pedagogicalAspect", objSheet.get("Aspectos Pedagógicos \n" + "(Pedagogical Aspects)"));
        objData.put("recommendedUse", objSheet.get("Sugerencia de Uso\n" + "(Recommended Use)"));

        Recurso recTemp = new Recurso(objSheet.get("Título"),
                objSheet.get("Nomenclatura"),
                objSheet.get("Descripción"));
        
        recTemp.setObjObjeto(objLeccion);

        recTemp.setArrData(objData);

        return recTemp;*/
    }
}
