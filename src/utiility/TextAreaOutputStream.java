/*     */ package utiility;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.JTextArea;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextAreaOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private byte[] oneByte;
/*     */   private Appender appender;
/*     */   
/*     */   public TextAreaOutputStream(JTextArea txtara)
/*     */   {
/*  26 */     this(txtara, 1000);
/*     */   }
/*     */   
/*     */   public TextAreaOutputStream(JTextArea txtara, int maxlin) {
/*  30 */     if (maxlin < 1) {
/*  31 */       throw new IllegalArgumentException("TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
/*     */     }
/*  33 */     this.oneByte = new byte[1];
/*  34 */     this.appender = new Appender(txtara, maxlin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/*  41 */     if (this.appender != null) {
/*  42 */       this.appender.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void close()
/*     */   {
/*  48 */     this.appender = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void flush() {}
/*     */   
/*     */ 
/*     */   public synchronized void write(int val)
/*     */   {
/*  57 */     this.oneByte[0] = ((byte)val);
/*  58 */     write(this.oneByte, 0, 1);
/*     */   }
/*     */   
/*     */   public synchronized void write(byte[] ba)
/*     */   {
/*  63 */     write(ba, 0, ba.length);
/*     */   }
/*     */   
/*     */   public synchronized void write(byte[] ba, int str, int len)
/*     */   {
/*  68 */     if (this.appender != null) {
/*  69 */       this.appender.append(bytesToString(ba, str, len));
/*     */     }
/*     */   }
/*     */   
/*     */   private static String bytesToString(byte[] ba, int str, int len) {
/*     */     try {
/*  75 */       return new String(ba, str, len, "UTF-8");
/*     */     } catch (UnsupportedEncodingException thr) {}
/*  77 */     return new String(ba, str, len);
/*     */   }
/*     */   
/*     */ 
/*     */   static class Appender
/*     */     implements Runnable
/*     */   {
/*     */     private final JTextArea textArea;
/*     */     
/*     */     private final int maxLines;
/*     */     
/*     */     private final LinkedList<Integer> lengths;
/*     */     private final List<String> values;
/*     */     private int curLength;
/*     */     private boolean clear;
/*     */     private boolean queue;
/*     */     private static final String EOL1 = "\n";
/*     */     
/*     */     Appender(JTextArea txtara, int maxlin)
/*     */     {
/*  97 */       this.textArea = txtara;
/*  98 */       this.maxLines = maxlin;
/*  99 */       this.lengths = new LinkedList();
/* 100 */       this.values = new ArrayList();
/*     */       
/* 102 */       this.curLength = 0;
/* 103 */       this.clear = false;
/* 104 */       this.queue = true;
/*     */     }
/*     */     
/*     */     synchronized void append(String val) {
/* 108 */       this.values.add(val);
/* 109 */       if (this.queue) {
/* 110 */         this.queue = false;
/* 111 */         EventQueue.invokeLater(this);
/*     */       }
/*     */     }
/*     */     
/*     */     synchronized void clear() {
/* 116 */       this.clear = true;
/* 117 */       this.curLength = 0;
/* 118 */       this.lengths.clear();
/* 119 */       this.values.clear();
/* 120 */       if (this.queue) {
/* 121 */         this.queue = false;
/* 122 */         EventQueue.invokeLater(this);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public synchronized void run()
/*     */     {
/* 129 */       if (this.clear) {
/* 130 */         this.textArea.setText("");
/*     */       }
/* 132 */       for (String val : this.values)
/*     */       {
/* 134 */         if ((!val.contains("\r")) && (!val.contains("\n"))) {
/* 135 */           DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/* 136 */           Date date = new Date();
/* 137 */           String string = dateFormat.format(date) + " - ";
/*     */           
/* 139 */           val = string.concat(val);
/*     */         }
/*     */         
/* 142 */         this.curLength += val.length();
/*     */         
/* 144 */         if ((val.endsWith("\n")) || (val.endsWith(EOL2))) {
/* 145 */           if (this.lengths.size() >= this.maxLines) {
/* 146 */             this.textArea.replaceRange("", 0, ((Integer)this.lengths.removeFirst()).intValue());
/*     */           }
/* 148 */           this.lengths.addLast(Integer.valueOf(this.curLength));
/* 149 */           this.curLength = 0;
/*     */         }
/* 151 */         this.textArea.append(val);
/*     */       }
/*     */       
/*     */ 
/* 155 */       this.values.clear();
/* 156 */       this.clear = false;
/* 157 */       this.queue = true;
/*     */     }
/*     */     
/*     */ 
/* 161 */     private static final String EOL2 = System.getProperty("line.separator", "\n");
/*     */   }
/*     */ }

