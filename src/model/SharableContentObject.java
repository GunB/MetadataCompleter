/*     */ package model;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ import utiility.XMLUtility;
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
/*     */ public class SharableContentObject
/*     */ {
/*     */   String strType;
/*     */   String strNombre;
/*     */   String strID;
/*     */   String strDesc;
/*     */   Document docXML;
/*     */   Node ndRelation;
/*  39 */   HashMap<String, String> arrRelations = new HashMap();
/*     */   
/*  41 */   ArrayList<SharableContentObject> objElements = new ArrayList();
/*     */   
/*  43 */   HashMap<String, String> arrData = new HashMap();
/*     */   
/*     */   ElementHandler eleData;
/*     */   
/*     */   public String getStrType()
/*     */   {
/*  49 */     return this.strType;
/*     */   }
/*     */   
/*     */   public void setStrType(String strType) {
/*  53 */     this.strType = strType;
/*     */   }
/*     */   
/*     */   public String getStrNombre() {
/*  57 */     return this.strNombre;
/*     */   }
/*     */   
/*     */   public void setStrNombre(String strNombre) {
/*  61 */     this.strNombre = strNombre;
/*     */   }
/*     */   
/*     */   public String getStrID() {
/*  65 */     return this.strID;
/*     */   }
/*     */   
/*     */   public void setStrID(String strID) {
/*  69 */     this.strID = strID;
/*     */   }
/*     */   
/*     */   public String getStrDesc() {
/*  73 */     return this.strDesc;
/*     */   }
/*     */   
/*     */   public void setStrDesc(String strDesc) {
/*  77 */     this.strDesc = strDesc;
/*     */   }
/*     */   
/*     */   public Document getDocXML() {
/*  81 */     return this.docXML;
/*     */   }
/*     */   
/*     */   public static String GetType(SharableContentObject scoData) throws NullPointerException {
/*  85 */     String strID1 = scoData.getStrID();
/*  86 */     String strResp = "";
/*     */     
/*  88 */     if (strID1.contains("re")) {
/*  89 */       strResp = "Recurso".toUpperCase();
/*  90 */     } else if (strID1.contains("ob")) {
/*  91 */       strResp = "Objeto".toUpperCase();
/*  92 */     } else if (strID1.contains("le")) {
/*  93 */       strResp = "Leccion".toUpperCase();
/*     */     } else {
/*  95 */       strResp = "Nivel".toUpperCase();
/*     */     }
/*     */     
/*  98 */     System.out.println("Found DATA [Type]: " + strResp);
/*     */     
/* 100 */     return strResp;
/*     */   }
/*     */   
/*     */   public SharableContentObject(ElementHandler eleData) throws IOException, SAXException, ParserConfigurationException, NullPointerException
/*     */   {
/* 105 */     this.eleData = eleData;
/*     */     
/* 107 */     this.docXML = eleData.Read();
/*     */     
/*     */ 
/* 110 */     this.docXML.getDocumentElement().normalize();
/* 111 */     System.out.println("Root element :" + this.docXML.getDocumentElement().getNodeName());
/*     */     
/* 113 */     String strNode = "<relation>\n        <kind schema=\"\"/>\n        <resource>\n            <identifier>\n                <catalog catName=\"\" catSource=\"\"/>\n            </identifier>\n            <description lang=\"\"/>\n        </resource>\n</relation>";
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
/*     */ 
/*     */ 
/* 130 */     this.ndRelation = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strNode.getBytes())).getDocumentElement();
/*     */     
/* 132 */     this.ndRelation.normalize();
/*     */     
/* 134 */     ReadElement();
/*     */   }
/*     */   
/*     */   private void ReadElement()
/*     */   {
/* 139 */     Node ndRoot = this.docXML.getDocumentElement().cloneNode(true);
/* 140 */     NodeList list = ndRoot.getChildNodes();
/*     */     
/* 142 */     this.strID = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[] { "general", "identifier", "catalog" })));
/* 143 */     this.strNombre = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[] { "general", "title" })));
/* 144 */     this.strDesc = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[] { "general", "description" })));
/* 145 */     this.strType = GetType(this);
/*     */   }
/*     */   
/*     */   public boolean isRelationed()
/*     */   {
/* 150 */     Node ndRoot = this.docXML.getDocumentElement().cloneNode(true);
/* 151 */     NodeList list = ndRoot.getChildNodes();
/* 152 */     String ReadNode = XMLUtility.ReadNode(list, new ArrayList(Arrays.asList(new String[] { "relation" })));
/*     */     
/* 154 */     if (ReadNode == null) {
/* 155 */       return false;
/*     */     }
/* 157 */     return true;
/*     */   }
/*     */   
/*     */   public void SetRelation(SharableContentObject scoObjeto, String strKind)
/*     */   {
/* 162 */     Node ndData = this.ndRelation.cloneNode(true);
/* 163 */     NodeList ndListTemp = ndData.getChildNodes();
/* 164 */     ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[] { "kind" })), strKind);
/* 165 */     ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[] { "resource", "identifier", "catalog" })), scoObjeto.strID);
/* 166 */     ndListTemp = XMLUtility.ChangeNode(ndListTemp, new ArrayList(Arrays.asList(new String[] { "resource", "description" })), scoObjeto.strDesc);
/*     */     
/* 168 */     this.docXML.adoptNode(ndData);
/* 169 */     this.docXML.getDocumentElement().appendChild(ndData);
/*     */   }
/*     */   
/*     */   public void SaveChanges() throws IOException, TransformerException {
/* 173 */     this.eleData.WriteFinish(this.docXML);
/*     */   }
/*     */ }


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\model\SharableContentObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */