/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.OutputKeys;
import model.XMLTag;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author hangarita
 */
public class XMLUtility {

    /**
     *
     * @param listNode 
     * @param arrStrCompare new ArrayList<>(Arrays.asList("general", "identifier", "catalog"))
     * @param arrNewData
     * @param xmlTag
     * @return 
     * @throws ParserConfigurationException
     */
    public static NodeList AddNodeList2Node(NodeList listNode, ArrayList<String> arrStrCompare, String[] arrNewData, XMLTag xmlTag) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        //root Elements -- Response
        Document doc = docBuilder.newDocument();

        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);
                // get the salary element, and update the value

                //System.out.println(node.getNodeName() + " " + strCompare);
                if (strCompare.equals(node.getNodeName())) {

                    if (iterator.hasNext()) {
                        node = AddNodeList2Node(node.getChildNodes(), arrTempList, arrNewData, xmlTag).item(0);
                    } else {

                        for (String s : arrNewData) {
                            s = s.trim();

                            if (!s.isEmpty()) {
                                Node item = doc.createElement(xmlTag.getStrName());
                                item.appendChild(doc.createTextNode(s));
                                node.appendChild(item);
                            }
                        }
                        //node.setTextContent(strNewValue);
                    }

                    break;
                }
            }

        }

        return listNode;
    }

    /**
     *
     * @param listNode
     * @param arrStrCompare
     * @param strNewValue
     * @return
     */
    public static NodeList ChangeNode(NodeList listNode, ArrayList<String> arrStrCompare, String strNewValue) {

        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);
                // get the salary element, and update the value

                //System.out.println(node.getNodeName() + " " + strCompare);
                if (strCompare.equals(node.getNodeName())) {

                    if (iterator.hasNext()) {
                        node = ChangeNode(node.getChildNodes(), arrTempList, strNewValue).item(0);
                    } else {
                        node.setTextContent(strNewValue);
                    }

                    break;
                }
            }

        }

        return listNode;
    }
    
    /**
     *
     * @param listNode
     * @param arrStrCompare
     * @return
     */
    public static String ReadNode(NodeList listNode, ArrayList<String> arrStrCompare) {

        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);
                // get the salary element, and update the value

                //System.out.println(node.getNodeName() + " " + strCompare);
                if (strCompare.equals(node.getNodeName())) {

                    if (iterator.hasNext()) {
                        return ReadNode(node.getChildNodes(), arrTempList);
                    } else {
                        String strResp = node.getTextContent();
                        System.out.println("Found DATA [" + strCompare + "]: " + strResp);
                        return strResp;
                    }
                }
            }
        }

        return null;
    }

    /**
     *
     * @param doc
     * @param out
     * @throws IOException
     * @throws TransformerException
     */
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc),
                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    /**
     * Various XML utilities.
     *
     * @author simonjsmith, ksim
     * @version 1.1 - ksim - March 6th, 2007 - Added functions regarding
     * streaming
     * @version 1.2 - ksim - March 10th, 2007 - Added functions regarding DOM
     * manipulation
     * @param in
     * @return
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static Document newDocumentFromInputStream(InputStream in) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document ret = builder.parse(new InputSource(in));
        return ret;
    }

    /**
     *
     * @param doc
     * @return
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public static InputStream newInputStreamFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(outputStream);
        TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     *
     * @param doc
     * @return
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public static String newStringFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        return output;
    }

}
