package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import utiility.AccionesVentana;
import utiility.JFolderChooser;

public class Index extends javax.swing.JFrame {

    AccionesVentana nuevo;
    String strRoot = System.getProperty("user.dir");
    JFolderChooser chooser;
    java.util.ArrayList<String> fileList;
    private JButton btnFix;
    private JButton btnRead;

    public Index() {
        AccionesVentana.LooknFeel();
        initComponents();
        this.nuevo = new AccionesVentana(this, "eFixer");
        PrintStream con = new PrintStream(new utiility.TextAreaOutputStream(this.txtConsole, 400));
        System.setOut(con);
    }

    private JButton btnSearch;
    private JCheckBox chkCopy;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.pnlFolder = new JPanel();
        this.btnSearch = new JButton();
        this.txtFolderProyect = new JTextField();
        this.btnRead = new JButton();
        this.tbbData = new JTabbedPane();
        this.pnlData = new JScrollPane();
        this.tblData = new JTable();
        this.jPanel2 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.txtConsole = new JTextArea();
        this.jPanel3 = new JPanel();
        this.chkCopy = new JCheckBox();
        this.btnFix = new JButton();
        this.lblMessage = new JLabel();

        setDefaultCloseOperation(3);

        this.pnlFolder.setBorder(javax.swing.BorderFactory.createTitledBorder("Object Folder"));

        this.btnSearch.setText("Search");
        this.btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Index.this.btnSearchActionPerformed(evt);
            }

        });
        this.txtFolderProyect.setEditable(false);
        this.txtFolderProyect.setBackground(new java.awt.Color(255, 255, 255));
        this.txtFolderProyect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Index.this.txtFolderProyectActionPerformed(evt);
            }

        });
        this.btnRead.setText("Read");
        this.btnRead.setEnabled(false);
        this.btnRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Index.this.btnReadActionPerformed(evt);
            }

        });
        GroupLayout pnlFolderLayout = new GroupLayout(this.pnlFolder);
        this.pnlFolder.setLayout(pnlFolderLayout);
        pnlFolderLayout.setHorizontalGroup(pnlFolderLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlFolderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.btnSearch)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.txtFolderProyect)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.btnRead)
                        .addContainerGap()));

        pnlFolderLayout.setVerticalGroup(pnlFolderLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlFolderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlFolderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(this.btnSearch)
                                .addComponent(this.txtFolderProyect, -2, -1, -2)
                                .addComponent(this.btnRead))
                        .addContainerGap(-1, 32767)));

        this.tblData.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{{null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}, {null}}, new String[]{"FILES"}) {

            Class[] types = {String.class};

            boolean[] canEdit = {false};

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.pnlData.setViewportView(this.tblData);
        if (this.tblData.getColumnModel().getColumnCount() > 0) {
            this.tblData.getColumnModel().getColumn(0).setResizable(false);
        }

        this.tbbData.addTab("Red files", this.pnlData);

        this.txtConsole.setEditable(false);
        this.txtConsole.setBackground(new java.awt.Color(0, 51, 51));
        this.txtConsole.setColumns(20);
        this.txtConsole.setFont(new java.awt.Font("Arial Unicode MS", 1, 12));
        this.txtConsole.setForeground(new java.awt.Color(0, 204, 102));
        this.txtConsole.setRows(5);
        this.jScrollPane1.setViewportView(this.txtConsole);

        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jScrollPane1));

        jPanel2Layout.setVerticalGroup(jPanel2Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING));

        this.tbbData.addTab("Console", this.jPanel2);

        this.chkCopy.setSelected(true);
        this.chkCopy.setText("BackUp Data");
        this.chkCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Index.this.chkCopyActionPerformed(evt);
            }

        });
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.chkCopy)
                        .addContainerGap(-1, 32767)));

        jPanel3Layout.setVerticalGroup(jPanel3Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.chkCopy)
                        .addContainerGap(-1, 32767)));

        this.tbbData.addTab("Options", this.jPanel3);

        this.btnFix.setText("Create!");
        this.btnFix.setEnabled(false);
        this.btnFix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Index.this.btnFixActionPerformed(evt);
            }

        });
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(this.pnlFolder, GroupLayout.Alignment.TRAILING, -1, -1, 32767)
                                .addComponent(this.tbbData)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(this.lblMessage)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
                                        .addComponent(this.btnFix)
                                        .addGap(4, 4, 4)))
                        .addContainerGap()));

        jPanel4Layout.setVerticalGroup(jPanel4Layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.pnlFolder, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(this.tbbData, -1, 231, 32767)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(this.btnFix)
                                .addComponent(this.lblMessage))
                        .addContainerGap()));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jPanel4, -1, -1, 32767));

        layout.setVerticalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jPanel4, -1, -1, 32767));

        pack();
    }

    private void SearchingFolder() {
        try {
            this.txtFolderProyect.setText(this.chooser.getStrPath());
            this.btnRead.setEnabled(true);
        } catch (Exception ex) {
            this.btnRead.setEnabled(false);
        }
    }

    private void btnSearchActionPerformed(ActionEvent evt) {
        this.chooser = new JFolderChooser();
        this.chooser.OpenChooser("");
        SearchingFolder();
    }

    private void txtFolderProyectActionPerformed(ActionEvent evt) {
    }

    private void btnReadActionPerformed(ActionEvent evt) {
        java.io.File file = new java.io.File(this.txtFolderProyect.getText());

        String DriveDesc = utiility.FilesUtility.PathRootDesc(file.getPath());
        System.out.println(DriveDesc);

        if (!DriveDesc.toLowerCase().contains("disco local")) {
            javax.swing.JOptionPane.showMessageDialog(null, "El proceso debe realizarse en un disco local");

            return;
        }

        this.fileList = this.chooser.getFileList(false);

        javax.swing.table.TableModel model = this.tblData.getModel();

        java.util.Iterator<String> ite = this.fileList.iterator();
        int cont = 0;

        while (ite.hasNext()) {
            String ele = (String) ite.next();
            model.setValueAt(ele, cont, 0);
            cont++;
        }

        this.pnlFolder.setEnabled(false);
        this.btnRead.setEnabled(false);
        this.btnSearch.setEnabled(false);

        this.btnFix.setEnabled(true);
    }

    private void btnFixActionPerformed(ActionEvent evt) {
        this.btnFix.setEnabled(false);

        String strPath = this.txtFolderProyect.getText();

        if (!this.chkCopy.isSelected()) {
            String strMessaje = "¿Seguro que desea SOBREESCRIBIR los archivos originales?\n(Una vez iniciado el proceso los archivos orignales serán irrecuperables)";

            int showConfirmDialog = javax.swing.JOptionPane.showConfirmDialog(this.rootPane, strMessaje);

            if (showConfirmDialog != 0) {
                this.chkCopy.setSelected(false);
            }
        }

        this.lblMessage.setText("Trabajando...");

        Object[] objParams = {Boolean.valueOf(this.chkCopy.isSelected()), strPath, this.lblMessage};

        this.tbbData.setSelectedIndex(1);

        Thread main = new Thread(new bin.MetadataParser(objParams));
        main.start();
    }

    private JLabel lblMessage;

    private JScrollPane pnlData;
    private JPanel pnlFolder;
    private JTabbedPane tbbData;
    private JTable tblData;
    private JTextArea txtConsole;
    private JTextField txtFolderProyect;

    private void chkCopyActionPerformed(ActionEvent evt) {
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Index().setVisible(true);
            }
        });
    }
}
