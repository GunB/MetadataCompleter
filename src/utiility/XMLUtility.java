/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.XMLTag;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author hangarita
 */
public class XMLUtility {

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

    public static NodeList ChangeNode(NodeList listNode, ArrayList<String> arrStrCompare, String strNewValue) {

        NodeList tempNode = listNode;
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

}
