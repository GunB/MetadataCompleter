/*     */ package utiility;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import model.XMLTag;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLUtility
/*     */ {
/*     */   public static NodeList AddNodeList2Node(NodeList listNode, ArrayList<String> arrStrCompare, String[] arrNewData, XMLTag xmlTag)
/*     */     throws ParserConfigurationException
/*     */   {
/*  47 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/*  48 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/*     */     
/*     */ 
/*  51 */     Document doc = docBuilder.newDocument();
/*     */     
/*  53 */     ArrayList<String> arrTempList = arrStrCompare;
/*  54 */     Iterator<String> iterator = arrTempList.iterator();
/*     */     
/*  56 */     while (iterator.hasNext()) {
/*  57 */       String strCompare = (String)iterator.next();
/*  58 */       iterator.remove();
/*     */       
/*  60 */       for (int i = 0; i < listNode.getLength(); i++) {
/*  61 */         Node node = listNode.item(i);
/*     */         
/*     */ 
/*     */ 
/*  65 */         if (strCompare.equals(node.getNodeName()))
/*     */         {
/*  67 */           if (iterator.hasNext()) {
/*  68 */             node = AddNodeList2Node(node.getChildNodes(), arrTempList, arrNewData, xmlTag).item(0); break;
/*     */           }
/*     */           
/*  71 */           for (String s : arrNewData) {
/*  72 */             s = s.trim();
/*     */             
/*  74 */             if (!s.isEmpty()) {
/*  75 */               Node item = doc.createElement(xmlTag.getStrName());
/*  76 */               item.appendChild(doc.createTextNode(s));
/*  77 */               node.appendChild(item);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*  83 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  89 */     return listNode;
/*     */   }
/*     */   
/*     */   public static NodeList ChangeNode(NodeList listNode, ArrayList<String> arrStrCompare, String strNewValue)
/*     */   {
/*  94 */     ArrayList<String> arrTempList = arrStrCompare;
/*  95 */     Iterator<String> iterator = arrTempList.iterator();
/*     */     
/*  97 */     while (iterator.hasNext()) {
/*  98 */       String strCompare = (String)iterator.next();
/*  99 */       iterator.remove();
/*     */       
/* 101 */       for (int i = 0; i < listNode.getLength(); i++) {
/* 102 */         Node node = listNode.item(i);
/*     */         
/*     */ 
/*     */ 
/* 106 */         if (strCompare.equals(node.getNodeName()))
/*     */         {
/* 108 */           if (iterator.hasNext()) {
/* 109 */             node = ChangeNode(node.getChildNodes(), arrTempList, strNewValue).item(0); break;
/*     */           }
/* 111 */           node.setTextContent(strNewValue);
/*     */           
/*     */ 
/* 114 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 120 */     return listNode;
/*     */   }
/*     */   
/*     */   public static String ReadNode(NodeList listNode, ArrayList<String> arrStrCompare)
/*     */   {
/* 125 */     ArrayList<String> arrTempList = arrStrCompare;
/* 126 */     Iterator<String> iterator = arrTempList.iterator();
/*     */     
/* 128 */     while (iterator.hasNext()) {
/* 129 */       String strCompare = (String)iterator.next();
/* 130 */       iterator.remove();
/*     */       
/* 132 */       for (int i = 0; i < listNode.getLength(); i++) {
/* 133 */         Node node = listNode.item(i);
/*     */         
/*     */ 
/*     */ 
/* 137 */         if (strCompare.equals(node.getNodeName()))
/*     */         {
/* 139 */           if (iterator.hasNext()) {
/* 140 */             return ReadNode(node.getChildNodes(), arrTempList);
/*     */           }
/* 142 */           String strResp = node.getTextContent();
/* 143 */           System.out.println("Red NODE [" + strCompare + "]: " + strResp);
/* 144 */           return strResp;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   public static String FindNode(NodeList listNode, ArrayList<String> arrStrCompare, String strData)
/*     */   {
/* 155 */     ArrayList<String> arrTempList = arrStrCompare;
/* 156 */     Iterator<String> iterator = arrTempList.iterator();
/*     */     
/* 158 */     while (iterator.hasNext()) {
/* 159 */       String strCompare = (String)iterator.next();
/* 160 */       iterator.remove();
/*     */       
/* 162 */       for (int i = 0; i < listNode.getLength(); i++) {
/* 163 */         Node node = listNode.item(i);
/*     */         
/*     */ 
/*     */ 
/* 167 */         if (strCompare.equals(node.getNodeName()))
/*     */         {
/* 169 */           if (iterator.hasNext()) {
/* 170 */             return FindNode(node.getChildNodes(), arrTempList, strData);
/*     */           }
/* 172 */           String strResp = node.getTextContent();
/* 173 */           System.out.println("Found NODE [" + strCompare + "]: " + strResp);
/* 174 */           return strResp;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 180 */     return null;
/*     */   }
/*     */   
/*     */   public static Node SearchNode(NodeList listNode, ArrayList<String> arrStrCompare)
/*     */   {
/* 185 */     ArrayList<String> arrTempList = arrStrCompare;
/* 186 */     Iterator<String> iterator = arrTempList.iterator();
/*     */     
/* 188 */     while (iterator.hasNext()) {
/* 189 */       String strCompare = (String)iterator.next();
/* 190 */       iterator.remove();
/*     */       
/* 192 */       for (int i = 0; i < listNode.getLength(); i++) {
/* 193 */         Node node = listNode.item(i);
/*     */         
/*     */ 
/*     */ 
/* 197 */         if (strCompare.equals(node.getNodeName()))
/*     */         {
/* 199 */           if (iterator.hasNext()) {
/* 200 */             return SearchNode(node.getChildNodes(), arrTempList);
/*     */           }
/* 202 */           String strResp = node.getTextContent();
/* 203 */           System.out.println("Found DATA [" + strCompare + "]: " + strResp);
/* 204 */           return node;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 210 */     return null;
/*     */   }
/*     */   
/*     */   public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
/* 214 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 215 */     Transformer transformer = tf.newTransformer();
/* 216 */     transformer.setOutputProperty("omit-xml-declaration", "no");
/* 217 */     transformer.setOutputProperty("method", "xml");
/* 218 */     transformer.setOutputProperty("indent", "yes");
/* 219 */     transformer.setOutputProperty("encoding", "UTF-8");
/* 220 */     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
/*     */     
/* 222 */     transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Document newDocumentFromInputStream(InputStream in)
/*     */     throws SAXException, IOException, ParserConfigurationException
/*     */   {
/* 241 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 242 */     DocumentBuilder builder = factory.newDocumentBuilder();
/* 243 */     Document ret = builder.parse(new InputSource(in));
/* 244 */     return ret;
/*     */   }
/*     */   
/*     */   public static InputStream newInputStreamFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
/* 248 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 249 */     Source xmlSource = new DOMSource(doc);
/* 250 */     Result outputTarget = new StreamResult(outputStream);
/* 251 */     TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
/* 252 */     return new ByteArrayInputStream(outputStream.toByteArray());
/*     */   }
/*     */   
/*     */   public static String newStringFromDocument(Document doc) throws TransformerConfigurationException, TransformerException {
/* 256 */     TransformerFactory tf = TransformerFactory.newInstance();
/* 257 */     Transformer transformer = tf.newTransformer();
/* 258 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/* 259 */     StringWriter writer = new StringWriter();
/* 260 */     transformer.transform(new DOMSource(doc), new StreamResult(writer));
/* 261 */     String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
/* 262 */     return output;
/*     */   }
/*     */ }

