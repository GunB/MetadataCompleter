/*     */ package bin;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import model.ElementHandler;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class ZipReader
/*     */   implements ElementHandler
/*     */ {
/*     */   String strPath;
/*     */   String strName;
/*  31 */   String strNewSufij = ".eFixed";
/*  32 */   String strFile2Change = "metadata.xml";
/*     */   
/*  34 */   ZipFile zipFile = null;
/*     */   
/*     */   public ZipReader(String strPath, String strName) throws IOException
/*     */   {
/*  38 */     this.strPath = strPath;
/*  39 */     this.strName = strName;
/*  40 */     String strFile = strPath.concat(File.separator).concat(strName);
/*  41 */     this.zipFile = new ZipFile(strFile);
/*     */   }
/*     */   
/*     */   public ZipReader(File fileData) throws IOException {
/*  45 */     this.strPath = fileData.getParent();
/*  46 */     this.strName = fileData.getName();
/*  47 */     this.zipFile = new ZipFile(fileData);
/*  48 */     System.out.println("Path: " + this.strPath);
/*  49 */     System.out.println("Name: " + this.strName);
/*  50 */     System.out.println("File: " + this.zipFile.getName());
/*     */   }
/*     */   
/*     */   public Document Read()
/*     */     throws IOException, SAXException, ParserConfigurationException
/*     */   {
/*  56 */     Document doc = null;
/*     */     
/*  58 */     for (Enumeration e = this.zipFile.entries(); e.hasMoreElements();) {
/*  59 */       ZipEntry entryIn = (ZipEntry)e.nextElement();
/*  60 */       String strName = entryIn.getName();
/*  61 */       if (strName.equalsIgnoreCase(this.strFile2Change)) {
/*  62 */         doc = XMLUtility.newDocumentFromInputStream(this.zipFile.getInputStream(entryIn));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  67 */     return doc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void WriteFinish(Document doc)
/*     */     throws IOException, TransformerException
/*     */   {
/*  79 */     String strOldFile = this.strPath.concat(File.separator).concat(this.strName);
/*     */     
/*     */ 
/*     */ 
/*  83 */     String strNewFile = this.strPath.concat(File.separator).concat(this.strName).concat(this.strNewSufij);
/*  84 */     File newFile = new File(strNewFile);
/*     */     
/*  86 */     ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFile));
/*  87 */     System.out.println("Saving [File]: " + strNewFile);
/*     */     
/*  89 */     for (Enumeration e = this.zipFile.entries(); e.hasMoreElements();) {
/*  90 */       ZipEntry entryIn = new ZipEntry((ZipEntry)e.nextElement());
/*     */       
/*  92 */       if (!entryIn.getName().equalsIgnoreCase(this.strFile2Change))
/*     */       {
/*     */ 
/*  95 */         ZipEntry destEntry = new ZipEntry(entryIn.getName());
/*  96 */         zos.putNextEntry(destEntry);
/*     */         
/*  98 */         InputStream is = this.zipFile.getInputStream(entryIn);
/*  99 */         byte[] buf = new byte['Ѐ'];
/*     */         int len;
/* 101 */         while ((len = is.read(buf)) > 0) {
/* 102 */           zos.write(buf, 0, len);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 107 */         ZipEntry destEntry = new ZipEntry(this.strFile2Change);
/*     */         
/* 109 */         zos.putNextEntry(destEntry);
/*     */         
/* 111 */         InputStream is = XMLUtility.newInputStreamFromDocument(doc);
/*     */         
/* 113 */         byte[] buf = new byte['Ѐ'];
/*     */         int len;
/* 115 */         while ((len = is.read(buf)) > 0)
/*     */         {
/* 117 */           zos.write(buf, 0, len < buf.length ? len : buf.length);
/*     */         }
/*     */       }
/* 120 */       zos.closeEntry();
/*     */     }
/* 122 */     zos.close();
/*     */     
/* 124 */     this.zipFile.close();
/*     */     
/* 126 */     File oldFile = new File(strOldFile);
/* 127 */     oldFile.delete();
/*     */     
/*     */ 
/* 130 */     newFile.renameTo(oldFile);
/*     */   }
/*     */ }


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\bin\ZipReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */