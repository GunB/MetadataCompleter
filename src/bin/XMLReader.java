/*    */ package bin;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.transform.Transformer;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.transform.TransformerFactory;
/*    */ import javax.xml.transform.dom.DOMSource;
/*    */ import javax.xml.transform.stream.StreamResult;
/*    */ import model.ElementHandler;
/*    */ import org.w3c.dom.Document;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLReader
/*    */   implements ElementHandler
/*    */ {
/*    */   String strPath;
/*    */   String strName;
/* 30 */   String strNewSufij = "New" + File.separator;
/* 31 */   String strFile2Change = "metadata.xml";
/* 32 */   File fileData = null;
/*    */   
/*    */   public XMLReader(String strPath, String strName) throws IOException
/*    */   {
/* 36 */     this.strPath = strPath;
/* 37 */     this.strName = strName;
/* 38 */     this.fileData = new File(this.strPath + File.separator + this.strName);
/*    */   }
/*    */   
/*    */   public XMLReader(File fileData) throws IOException {
/* 42 */     this.strPath = fileData.getParent();
/* 43 */     this.strName = fileData.getName();
/* 44 */     this.fileData = fileData;
/* 45 */     System.out.println("Path: " + this.strPath);
/* 46 */     System.out.println("Name: " + this.strName);
/* 47 */     System.out.println("File: " + this.fileData.getName());
/*    */   }
/*    */   
/*    */   public Document Read() throws IOException, SAXException, ParserConfigurationException
/*    */   {
/* 52 */     File fXmlFile = this.fileData;
/* 53 */     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/* 54 */     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
/* 55 */     return dBuilder.parse(fXmlFile);
/*    */   }
/*    */   
/*    */   public void WriteFinish(Document doc) throws IOException, TransformerException
/*    */   {
/* 60 */     System.out.println("Saving [File]: " + this.fileData.getPath());
/*    */     
/* 62 */     File f = this.fileData;
/*    */     
/* 64 */     f.getParentFile().mkdirs();
/* 65 */     f.createNewFile();
/* 66 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 67 */     Transformer transformer = transformerFactory.newTransformer();
/* 68 */     DOMSource source = new DOMSource(doc);
/* 69 */     StreamResult result = new StreamResult(f);
/* 70 */     transformer.transform(source, result);
/* 71 */     System.out.println("Saved [File]: " + this.fileData.getPath());
/*    */   }
/*    */ }


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\bin\XMLReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */