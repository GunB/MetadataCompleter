/*     */ package utiility;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import javax.swing.filechooser.FileSystemView;
/*     */ 
/*     */ public class FilesUtility
/*     */ {
/*     */   public static void copyFolder(File src, File dest)
/*     */     throws IOException
/*     */   {
/*  23 */     if (src.isDirectory())
/*     */     {
/*     */ 
/*  26 */       if (!dest.exists()) {
/*  27 */         dest.mkdir();
/*  28 */         System.out.println("Directory copied from " + src + "  to " + dest);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  33 */       String[] files = src.list();
/*     */       
/*  35 */       for (String file : files)
/*     */       {
/*  37 */         File srcFile = new File(src, file);
/*  38 */         File destFile = new File(dest, file);
/*     */         
/*  40 */         copyFolder(srcFile, destFile);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/*  46 */       InputStream in = new FileInputStream(src);
/*  47 */       Object out = new FileOutputStream(dest);
/*     */       
/*  49 */       byte[] buffer = new byte['Ѐ'];
/*     */       
/*     */       int length;
/*     */       
/*  53 */       while ((length = in.read(buffer)) > 0) {
/*  54 */         ((OutputStream)out).write(buffer, 0, length);
/*     */       }
/*     */       
/*  57 */       in.close();
/*  58 */       ((OutputStream)out).close();
/*  59 */       System.out.println("File copied from " + src + " to " + dest);
/*     */     }
/*     */   }
/*     */   
/*     */   public static FileStore getPathFilesystem(String path) throws URISyntaxException, IOException {
/*  64 */     URI rootURI = new URI("file:///");
/*  65 */     Path rootPath = Paths.get(rootURI);
/*  66 */     Path dirPath = rootPath.resolve(path);
/*  67 */     FileStore dirFileStore = Files.getFileStore(dirPath);
/*  68 */     return dirFileStore;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void DrivesnDesc()
/*     */   {
/*  74 */     FileSystemView fsv = FileSystemView.getFileSystemView();
/*     */     
/*     */ 
/*  77 */     File[] paths = File.listRoots();
/*     */     
/*     */ 
/*  80 */     for (File path : paths)
/*     */     {
/*  82 */       System.out.println("Drive Name: " + path);
/*  83 */       System.out.println("Description: " + fsv.getSystemTypeDescription(path));
/*     */     }
/*     */   }
/*     */   
/*     */   public static String PathRootDesc(String strFilePath) {
/*  88 */     FileSystemView fsv = FileSystemView.getFileSystemView();
/*     */     
/*  90 */     File newFile = new File(strFilePath);
/*  91 */     newFile = GetFullParent(newFile);
/*     */     
/*  93 */     return fsv.getSystemTypeDescription(newFile);
/*     */   }
/*     */   
/*     */   public static File GetFullParent(File file) {
/*     */     try {
/*  98 */       file = GetFullParent(new File(file.getParent()));
/*     */     } catch (NullPointerException ex) {
/* 100 */       return file;
/*     */     }
/*     */     
/* 103 */     return file;
/*     */   }
/*     */   
/*     */   public static void printFileStore(FileStore filestore, String path) throws IOException {
/* 107 */     System.out.println("Name: " + filestore.name());
/* 108 */     System.out.println("\tPath: " + path);
/* 109 */     System.out.println("\tSize: " + filestore.getTotalSpace());
/* 110 */     System.out.println("\tUnallocated: " + filestore.getUnallocatedSpace());
/* 111 */     System.out.println("\tUsable: " + filestore.getUsableSpace());
/* 112 */     System.out.println("\tType: " + filestore.type());
/*     */   }
/*     */ }

