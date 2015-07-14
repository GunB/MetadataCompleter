package utiility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import model.XMLTag;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtility {

    public static NodeList AddNodeList2Node(NodeList listNode, ArrayList<String> arrStrCompare, String[] arrNewData, XMLTag xmlTag)
            throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = (String) iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);

                if (strCompare.equals(node.getNodeName())) {
                    if (iterator.hasNext()) {
                        node = AddNodeList2Node(node.getChildNodes(), arrTempList, arrNewData, xmlTag).item(0);
                        break;
                    }

                    for (String s : arrNewData) {
                        s = s.trim();

                        if (!s.isEmpty()) {
                            Node item = doc.createElement(xmlTag.getStrName());
                            item.appendChild(doc.createTextNode(s));
                            node.appendChild(item);
                        }
                    }

                    break;
                }
            }
        }

        return listNode;
    }

    public static NodeList ChangeNode(NodeList listNode, ArrayList<String> arrStrCompare, String strNewValue) {
        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = (String) iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);

                if (strCompare.equals(node.getNodeName())) {
                    if (iterator.hasNext()) {
                        node = ChangeNode(node.getChildNodes(), arrTempList, strNewValue).item(0);
                        break;
                    }
                    node.setTextContent(strNewValue);

                    break;
                }
            }
        }

        return listNode;
    }

    public static String ReadNode(NodeList listNode, ArrayList<String> arrStrCompare) {
        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = (String) iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);

                if (strCompare.equals(node.getNodeName())) {
                    if (iterator.hasNext()) {
                        return ReadNode(node.getChildNodes(), arrTempList);
                    }
                    String strResp = node.getTextContent();
                    System.out.println("Red NODE [" + strCompare + "]: " + strResp);
                    return strResp;
                }
            }
        }

        return null;
    }

    public static String FindNode(NodeList listNode, ArrayList<String> arrStrCompare, String strData) {
        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = (String) iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);

                if (strCompare.equals(node.getNodeName())) {
                    if (iterator.hasNext()) {
                        return FindNode(node.getChildNodes(), arrTempList, strData);
                    }
                    String strResp = node.getTextContent();
                    System.out.println("Found NODE [" + strCompare + "]: " + strResp);
                    return strResp;
                }
            }
        }

        return null;
    }

    public static Node SearchNode(NodeList listNode, ArrayList<String> arrStrCompare) {
        ArrayList<String> arrTempList = arrStrCompare;
        Iterator<String> iterator = arrTempList.iterator();

        while (iterator.hasNext()) {
            String strCompare = (String) iterator.next();
            iterator.remove();

            for (int i = 0; i < listNode.getLength(); i++) {
                Node node = listNode.item(i);

                if (strCompare.equals(node.getNodeName())) {
                    if (iterator.hasNext()) {
                        return SearchNode(node.getChildNodes(), arrTempList);
                    }
                    String strResp = node.getTextContent();
                    System.out.println("Found DATA [" + strCompare + "]: " + strResp);
                    return node;
                }
            }
        }

        return null;
    }

    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "no");
        transformer.setOutputProperty("method", "xml");
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    public static Document newDocumentFromInputStream(InputStream in)
            throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document ret = builder.parse(new InputSource(in));
        return ret;
    }

    public static InputStream newInputStreamFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Source xmlSource = new DOMSource(doc);
        Result outputTarget = new StreamResult(outputStream);
        TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public static String newStringFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        return output;
    }
}
