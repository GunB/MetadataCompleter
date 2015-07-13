/*     */ package view;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import utiility.AccionesVentana;
/*     */ import utiility.JFolderChooser;
/*     */ 
/*     */ public class Index extends javax.swing.JFrame
/*     */ {
/*     */   AccionesVentana nuevo;
/*  29 */   String strRoot = System.getProperty("user.dir");
/*     */   JFolderChooser chooser;
/*     */   java.util.ArrayList<String> fileList;
/*     */   private JButton btnFix;
/*     */   private JButton btnRead;
/*     */   
/*     */   public Index()
/*     */   {
/*  37 */     AccionesVentana.LooknFeel();
/*  38 */     initComponents();
/*  39 */     this.nuevo = new AccionesVentana(this, "eFixer");
/*  40 */     PrintStream con = new PrintStream(new utiility.TextAreaOutputStream(this.txtConsole, 400));
/*  41 */     System.setOut(con);
/*     */   }
/*     */   
/*     */ 
/*     */   private JButton btnSearch;
/*     */   private JCheckBox chkCopy;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JPanel jPanel4;
/*     */   private JScrollPane jScrollPane1;
/*     */   
/*     */   private void initComponents()
/*     */   {
/*  54 */     this.jPanel4 = new JPanel();
/*  55 */     this.pnlFolder = new JPanel();
/*  56 */     this.btnSearch = new JButton();
/*  57 */     this.txtFolderProyect = new JTextField();
/*  58 */     this.btnRead = new JButton();
/*  59 */     this.tbbData = new JTabbedPane();
/*  60 */     this.pnlData = new JScrollPane();
/*  61 */     this.tblData = new JTable();
/*  62 */     this.jPanel2 = new JPanel();
/*  63 */     this.jScrollPane1 = new JScrollPane();
/*  64 */     this.txtConsole = new JTextArea();
/*  65 */     this.jPanel3 = new JPanel();
/*  66 */     this.chkCopy = new JCheckBox();
/*  67 */     this.btnFix = new JButton();
/*  68 */     this.lblMessage = new JLabel();
/*     */     
/*  70 */     setDefaultCloseOperation(3);
/*     */     
/*  72 */     this.pnlFolder.setBorder(javax.swing.BorderFactory.createTitledBorder("Object Folder"));
/*     */     
/*  74 */     this.btnSearch.setText("Search");
/*  75 */     this.btnSearch.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  77 */         Index.this.btnSearchActionPerformed(evt);
/*     */       }
/*     */       
/*  80 */     });
/*  81 */     this.txtFolderProyect.setEditable(false);
/*  82 */     this.txtFolderProyect.setBackground(new java.awt.Color(255, 255, 255));
/*  83 */     this.txtFolderProyect.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  85 */         Index.this.txtFolderProyectActionPerformed(evt);
/*     */       }
/*     */       
/*  88 */     });
/*  89 */     this.btnRead.setText("Read");
/*  90 */     this.btnRead.setEnabled(false);
/*  91 */     this.btnRead.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  93 */         Index.this.btnReadActionPerformed(evt);
/*     */       }
/*     */       
/*  96 */     });
/*  97 */     GroupLayout pnlFolderLayout = new GroupLayout(this.pnlFolder);
/*  98 */     this.pnlFolder.setLayout(pnlFolderLayout);
/*  99 */     pnlFolderLayout.setHorizontalGroup(pnlFolderLayout
/* 100 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 101 */       .addGroup(pnlFolderLayout.createSequentialGroup()
/* 102 */       .addContainerGap()
/* 103 */       .addComponent(this.btnSearch)
/* 104 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 105 */       .addComponent(this.txtFolderProyect)
/* 106 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/* 107 */       .addComponent(this.btnRead)
/* 108 */       .addContainerGap()));
/*     */     
/* 110 */     pnlFolderLayout.setVerticalGroup(pnlFolderLayout
/* 111 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 112 */       .addGroup(pnlFolderLayout.createSequentialGroup()
/* 113 */       .addContainerGap()
/* 114 */       .addGroup(pnlFolderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 115 */       .addComponent(this.btnSearch)
/* 116 */       .addComponent(this.txtFolderProyect, -2, -1, -2)
/* 117 */       .addComponent(this.btnRead))
/* 118 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 121 */     this.tblData.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null }, { null } }, new String[] { "FILES" })
/*     */     {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 228 */       Class[] types = { String.class };
/*     */       
/*     */ 
/* 231 */       boolean[] canEdit = { false };
/*     */       
/*     */ 
/*     */       public Class getColumnClass(int columnIndex)
/*     */       {
/* 236 */         return this.types[columnIndex];
/*     */       }
/*     */       
/*     */       public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 240 */         return this.canEdit[columnIndex];
/*     */       }
/* 242 */     });
/* 243 */     this.pnlData.setViewportView(this.tblData);
/* 244 */     if (this.tblData.getColumnModel().getColumnCount() > 0) {
/* 245 */       this.tblData.getColumnModel().getColumn(0).setResizable(false);
/*     */     }
/*     */     
/* 248 */     this.tbbData.addTab("Red files", this.pnlData);
/*     */     
/* 250 */     this.txtConsole.setEditable(false);
/* 251 */     this.txtConsole.setBackground(new java.awt.Color(0, 51, 51));
/* 252 */     this.txtConsole.setColumns(20);
/* 253 */     this.txtConsole.setFont(new java.awt.Font("Arial Unicode MS", 1, 12));
/* 254 */     this.txtConsole.setForeground(new java.awt.Color(0, 204, 102));
/* 255 */     this.txtConsole.setRows(5);
/* 256 */     this.jScrollPane1.setViewportView(this.txtConsole);
/*     */     
/* 258 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 259 */     this.jPanel2.setLayout(jPanel2Layout);
/* 260 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/* 261 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 262 */       .addComponent(this.jScrollPane1));
/*     */     
/* 264 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/* 265 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 266 */       .addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING));
/*     */     
/*     */ 
/* 269 */     this.tbbData.addTab("Console", this.jPanel2);
/*     */     
/* 271 */     this.chkCopy.setSelected(true);
/* 272 */     this.chkCopy.setText("BackUp Data");
/* 273 */     this.chkCopy.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 275 */         Index.this.chkCopyActionPerformed(evt);
/*     */       }
/*     */       
/* 278 */     });
/* 279 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 280 */     this.jPanel3.setLayout(jPanel3Layout);
/* 281 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/* 282 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 283 */       .addGroup(jPanel3Layout.createSequentialGroup()
/* 284 */       .addContainerGap()
/* 285 */       .addComponent(this.chkCopy)
/* 286 */       .addContainerGap(-1, 32767)));
/*     */     
/* 288 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/* 289 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 290 */       .addGroup(jPanel3Layout.createSequentialGroup()
/* 291 */       .addContainerGap()
/* 292 */       .addComponent(this.chkCopy)
/* 293 */       .addContainerGap(-1, 32767)));
/*     */     
/*     */ 
/* 296 */     this.tbbData.addTab("Options", this.jPanel3);
/*     */     
/* 298 */     this.btnFix.setText("Create!");
/* 299 */     this.btnFix.setEnabled(false);
/* 300 */     this.btnFix.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 302 */         Index.this.btnFixActionPerformed(evt);
/*     */       }
/*     */       
/* 305 */     });
/* 306 */     GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
/* 307 */     this.jPanel4.setLayout(jPanel4Layout);
/* 308 */     jPanel4Layout.setHorizontalGroup(jPanel4Layout
/* 309 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 310 */       .addGroup(jPanel4Layout.createSequentialGroup()
/* 311 */       .addContainerGap()
/* 312 */       .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 313 */       .addComponent(this.pnlFolder, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
/* 314 */       .addComponent(this.tbbData)
/* 315 */       .addGroup(jPanel4Layout.createSequentialGroup()
/* 316 */       .addComponent(this.lblMessage)
/* 317 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/* 318 */       .addComponent(this.btnFix)
/* 319 */       .addGap(4, 4, 4)))
/* 320 */       .addContainerGap()));
/*     */     
/* 322 */     jPanel4Layout.setVerticalGroup(jPanel4Layout
/* 323 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 324 */       .addGroup(jPanel4Layout.createSequentialGroup()
/* 325 */       .addContainerGap()
/* 326 */       .addComponent(this.pnlFolder, -2, -1, -2)
/* 327 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 328 */       .addComponent(this.tbbData, -1, 231, 32767)
/* 329 */       .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/* 330 */       .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 331 */       .addComponent(this.btnFix)
/* 332 */       .addComponent(this.lblMessage))
/* 333 */       .addContainerGap()));
/*     */     
/*     */ 
/* 336 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 337 */     getContentPane().setLayout(layout);
/* 338 */     layout.setHorizontalGroup(layout
/* 339 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 340 */       .addComponent(this.jPanel4, -1, -1, 32767));
/*     */     
/* 342 */     layout.setVerticalGroup(layout
/* 343 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 344 */       .addComponent(this.jPanel4, -1, -1, 32767));
/*     */     
/*     */ 
/* 347 */     pack();
/*     */   }
/*     */   
/*     */   private void SearchingFolder() {
/*     */     try {
/* 352 */       this.txtFolderProyect.setText(this.chooser.getStrPath());
/* 353 */       this.btnRead.setEnabled(true);
/*     */     } catch (Exception ex) {
/* 355 */       this.btnRead.setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void btnSearchActionPerformed(ActionEvent evt)
/*     */   {
/* 361 */     this.chooser = new JFolderChooser();
/* 362 */     this.chooser.OpenChooser("");
/* 363 */     SearchingFolder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void txtFolderProyectActionPerformed(ActionEvent evt) {}
/*     */   
/*     */ 
/*     */   private void btnReadActionPerformed(ActionEvent evt)
/*     */   {
/* 373 */     java.io.File file = new java.io.File(this.txtFolderProyect.getText());
/*     */     
/* 375 */     String DriveDesc = utiility.FilesUtility.PathRootDesc(file.getPath());
/* 376 */     System.out.println(DriveDesc);
/*     */     
/* 378 */     if (!DriveDesc.toLowerCase().contains("disco local")) {
/* 379 */       javax.swing.JOptionPane.showMessageDialog(null, "El proceso debe realizarse en un disco local");
/*     */       
/* 381 */       return;
/*     */     }
/*     */     
/*     */ 
/* 385 */     this.fileList = this.chooser.getFileList(false);
/*     */     
/* 387 */     javax.swing.table.TableModel model = this.tblData.getModel();
/*     */     
/* 389 */     java.util.Iterator<String> ite = this.fileList.iterator();
/* 390 */     int cont = 0;
/*     */     
/* 392 */     while (ite.hasNext()) {
/* 393 */       String ele = (String)ite.next();
/* 394 */       model.setValueAt(ele, cont, 0);
/* 395 */       cont++;
/*     */     }
/*     */     
/* 398 */     this.pnlFolder.setEnabled(false);
/* 399 */     this.btnRead.setEnabled(false);
/* 400 */     this.btnSearch.setEnabled(false);
/*     */     
/* 402 */     this.btnFix.setEnabled(true);
/*     */   }
/*     */   
/*     */ 
/*     */   private void btnFixActionPerformed(ActionEvent evt)
/*     */   {
/* 408 */     this.btnFix.setEnabled(false);
/*     */     
/* 410 */     String strPath = this.txtFolderProyect.getText();
/*     */     
/* 412 */     if (!this.chkCopy.isSelected()) {
/* 413 */       String strMessaje = "¿Seguro que desea SOBREESCRIBIR los archivos originales?\n(Una vez iniciado el proceso los archivos orignales serán irrecuperables)";
/*     */       
/*     */ 
/*     */ 
/* 417 */       int showConfirmDialog = javax.swing.JOptionPane.showConfirmDialog(this.rootPane, strMessaje);
/*     */       
/* 419 */       if (showConfirmDialog != 0) {
/* 420 */         this.chkCopy.setSelected(false);
/*     */       }
/*     */     }
/*     */     
/* 424 */     this.lblMessage.setText("Trabajando...");
/*     */     
/* 426 */     Object[] objParams = { Boolean.valueOf(this.chkCopy.isSelected()), strPath, this.lblMessage };
/*     */     
/* 428 */     this.tbbData.setSelectedIndex(1);
/*     */     
/* 430 */     Thread main = new Thread(new bin.MetadataParser(objParams));
/* 431 */     main.start();
/*     */   }
/*     */   
/*     */ 
/*     */   private JLabel lblMessage;
/*     */   
/*     */   private JScrollPane pnlData;
/*     */   private JPanel pnlFolder;
/*     */   private JTabbedPane tbbData;
/*     */   private JTable tblData;
/*     */   private JTextArea txtConsole;
/*     */   private JTextField txtFolderProyect;
/*     */   private void chkCopyActionPerformed(ActionEvent evt) {}
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*     */ 
/*     */ 
/* 467 */     java.awt.EventQueue.invokeLater(new Runnable() {
/*     */       public void run() {
/* 469 */         new Index().setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }
