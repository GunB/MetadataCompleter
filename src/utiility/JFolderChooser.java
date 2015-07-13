/*     */ package utiility;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JFileChooser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JFolderChooser
/*     */ {
/*  18 */   JFileChooser chooser = new JFileChooser();
/*  19 */   String strPath = null;
/*     */   
/*     */ 
/*     */   public JFolderChooser() {}
/*     */   
/*     */   public JFolderChooser(String strPath)
/*     */   {
/*  26 */     this.chooser.setSelectedFile(new File(strPath));
/*     */   }
/*     */   
/*     */   public String getStrPathUp() throws Exception
/*     */   {
/*  31 */     this.strPath = this.chooser.getSelectedFile().getAbsolutePath();
/*  32 */     return this.strPath;
/*     */   }
/*     */   
/*     */   public String getStrPath() throws Exception {
/*  36 */     this.strPath = this.chooser.getSelectedFile().getAbsolutePath();
/*  37 */     return this.strPath;
/*     */   }
/*     */   
/*     */   public void OpenChooser(String strTitle) {
/*  41 */     this.chooser.setCurrentDirectory(new File("."));
/*  42 */     this.chooser.setDialogTitle(strTitle);
/*  43 */     this.chooser.setFileSelectionMode(1);
/*     */     
/*     */ 
/*     */ 
/*  47 */     this.chooser.setAcceptAllFileFilterUsed(false);
/*     */     
/*  49 */     if (this.chooser.showOpenDialog(null) == 0) {
/*  50 */       System.out.println("getCurrentDirectory(): " + this.chooser
/*  51 */         .getCurrentDirectory());
/*  52 */       System.out.println("getSelectedFile() : " + this.chooser
/*  53 */         .getSelectedFile());
/*     */     } else {
/*  55 */       System.out.println("No Selection ");
/*     */     }
/*     */   }
/*     */   
/*     */   public ArrayList<String> getFileList(boolean isRecursive) {
/*  60 */     if (this.strPath.isEmpty()) {
/*  61 */       return null;
/*     */     }
/*  63 */     return listFilesForFolder(new File(this.strPath), isRecursive);
/*     */   }
/*     */   
/*     */   public static ArrayList<String> listFilesForFolder(File folder, boolean isRecursive)
/*     */   {
/*  68 */     ArrayList<String> arrData = new ArrayList();
/*     */     
/*  70 */     for (File fileEntry : folder.listFiles()) {
/*  71 */       if (fileEntry.isDirectory()) {
/*  72 */         if (isRecursive) {
/*  73 */           arrData.addAll(listFilesForFolder(fileEntry, isRecursive));
/*     */         } else {
/*  75 */           arrData.add(fileEntry.getName());
/*     */         }
/*     */       } else {
/*  78 */         arrData.add(fileEntry.getName());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  83 */     return arrData;
/*     */   }
/*     */   
/*     */   public static ArrayList<File> listRawFilesForFolder(File folder, boolean isRecursive) {
/*  87 */     ArrayList<File> arrData = new ArrayList();
/*     */     
/*  89 */     for (File fileEntry : folder.listFiles()) {
/*  90 */       if (fileEntry.isDirectory()) {
/*  91 */         if (isRecursive) {
/*  92 */           arrData.addAll(listRawFilesForFolder(fileEntry, isRecursive));
/*     */         }
/*     */       } else {
/*  95 */         arrData.add(fileEntry);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 100 */     return arrData;
/*     */   }
/*     */ }

