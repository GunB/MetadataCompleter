package bin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.SharableContentObject;
import org.xml.sax.SAXException;
import utiility.FilesUtility;
import utiility.JFolderChooser;

public class MetadataParser
        implements Runnable {

    private boolean isCopy = true;
    private String strPath;
    private JLabel lblData = null;

    PrintWriter newLog;
    String strNameLog = "READLOG";

    long unixTime = System.currentTimeMillis() / 1000L;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());

    public MetadataParser(Object params) {
        Object[] objData = (Object[]) params;

        this.isCopy = ((Boolean) objData[0]).booleanValue();
        this.strPath = ((String) objData[1]);
        this.lblData = ((JLabel) objData[2]);
    }

    public MetadataParser() {
    }

    public File CopyFolder(String strPathFolder) {
        Log("Copiando archivos...");

        File baseFileDirectory = new File(strPathFolder);

        File newFileDirectory = new File(baseFileDirectory.getParent() + File.separator + "eFIXED_" + this.unixTime + " " + this.timeStamp);

        newFileDirectory.mkdirs();
        try {
            FilesUtility.copyFolder(baseFileDirectory, newFileDirectory);
        } catch (IOException ex) {
            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            System.exit(9);
        }

        return newFileDirectory;
    }

    public static void main(String[] args) {
        new MetadataParser().Exec(args);
    }

    public void Exec(String[] args) {
        System.out.println("Program Arguments:");
        for (String arg : args) {
            System.out.println("\t" + arg);
        }

        File baseFileDirectory = new File(args[0]);

        this.strNameLog = baseFileDirectory.getPath().concat(File.separator).concat(this.strNameLog + "_" + this.unixTime).concat(".txt");
        try {
            this.newLog = new PrintWriter(this.strNameLog, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<File> listFilesForFolder = JFolderChooser.listRawFilesForFolder(baseFileDirectory, true);

        Object arrRec = new ArrayList();
        ArrayList<SharableContentObject> arrObj = new ArrayList();
        ArrayList<SharableContentObject> arrLec = new ArrayList();
        ArrayList<SharableContentObject> arrLvl = new ArrayList();

        String strMessage = "Leyendo SCO(s)... ";
        String strMessage2 = "REL: ";

        Log(strMessage);

        for (File strNameFolder : listFilesForFolder) {
            SharableContentObject scoData = null;

            if (strNameFolder.getName().endsWith(".zip")) {
                try {
                    scoData = new SharableContentObject(new ZipReader(strNameFolder));

                    Log(strMessage + scoData.getStrID());
                } catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                    System.err.println("ERROR [NOT METADATA]: " + strNameFolder.getName());
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (strNameFolder.getName().endsWith(".xml")) {
                try {
                    scoData = new SharableContentObject(new XMLReader(strNameFolder));

                    Log(strMessage + scoData.getStrID());
                } catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                    System.err.println("ERROR [NOT METADATA]: " + strNameFolder.getName());
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                if ((strNameFolder.getName().endsWith(".xml")) || (strNameFolder.getName().endsWith(".zip"))) {
                    switch (scoData.getStrType()) {
                        case "NIVEL":
                            arrLvl.add(scoData);
                            break;
                        case "LECCION":
                            arrLec.add(scoData);
                            break;
                        case "OBJETO":
                            arrObj.add(scoData);
                            break;
                        case "RECURSO":
                            ((ArrayList) arrRec).add(scoData);
                    }
                }
            } catch (NullPointerException ex) {
                System.err.println("Failed: " + strNameFolder.getPath());
                Log("Failed: " + strNameFolder.getPath() + " " + ex);
                Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        strMessage = "Creando relaciones en los recursos... ";

        for (SharableContentObject scoData : (ArrayList<SharableContentObject>) arrRec) {
            if (scoData.isRelationed()) {
                Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
            } else {
                Log(strMessage + scoData.getStrID());
                int cont = 0;
                for (SharableContentObject scoSCO : arrObj) {
                    if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        cont++;
                        Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        scoData.SetRelation(scoSCO, "Es parte de");
                    }
                }
                try {
                    Log("Guardando cambios... \t" + scoData.getStrID());
                    scoData.SaveChanges();
                } catch (IOException | TransformerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(6);
                }
            }
        }

        strMessage = "Creando relaciones en los Objetos... ";

        for (SharableContentObject scoData : arrObj) {
            if (scoData.isRelationed()) {
                Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
            } else {
                boolean bulPadre = false;

                int cont = 0;
                for (SharableContentObject scoSCO : arrLec) {
                    if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        bulPadre = true;
                        cont++;
                        Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        scoData.SetRelation(scoSCO, "Es parte de");
                    }
                }

                if (bulPadre) {
                    cont = 0;
                    Log(strMessage + scoData.getStrID());

                    for (SharableContentObject scoSCO : (ArrayList<SharableContentObject>) arrRec) {
                        if (scoSCO.getStrID().contains(scoData.getStrID())) {
                            cont++;
                            Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                            scoData.SetRelation(scoSCO, "Está compuesto por");
                        }
                    }
                    try {
                        Log("Guardando cambios... \t" + scoData.getStrID());
                        scoData.SaveChanges();
                    } catch (IOException | TransformerException ex) {
                        Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                        System.exit(6);
                    }
                } else {
                    Log("No fué identificado el padre... relaciones abortadas: " + scoData.getStrID());
                }
            }
        }

        strMessage = "Creando relaciones en las Lecciones... ";

        for (SharableContentObject scoData : arrLec) {
            if (scoData.isRelationed()) {
                Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
            } else {
                Log(strMessage + scoData.getStrID());
                boolean bulPadre = false;

                int cont = 0;
                for (SharableContentObject scoSCO : arrLvl) {
                    if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        bulPadre = true;
                        cont++;
                        Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        scoData.SetRelation(scoSCO, "Es parte de");
                    }
                }

                if (bulPadre) {
                    cont = 0;
                    for (SharableContentObject scoSCO : arrObj) {
                        if (scoSCO.getStrID().contains(scoData.getStrID())) {
                            cont++;
                            Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                            scoData.SetRelation(scoSCO, "Está compuesto por");
                        }
                    }
                    try {
                        Log("Guardando cambios... \t" + scoData.getStrID());
                        scoData.SaveChanges();
                    } catch (IOException | TransformerException ex) {
                        Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex);
                        System.exit(6);
                    }
                } else {
                    Log("No fué identificado el padre... relaciones abortadas: " + scoData.getStrID());
                }
            }
        }

        strMessage = "Creando relaciones en los niveles... ";

        for (SharableContentObject scoData : arrLvl) {
            if (scoData.isRelationed()) {
                Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
            } else {
                Log(strMessage + scoData.getStrID());

                int cont = 0;
                for (SharableContentObject scoSCO : arrLec) {
                    if (scoSCO.getStrID().contains(scoData.getStrID())) {
                        cont++;
                        Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                        scoData.SetRelation(scoSCO, "Está compuesto por");
                    }
                }
                try {
                    Log("Guardando cambios... \t" + scoData.getStrID());
                    scoData.SaveChanges();
                } catch (IOException | TransformerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(6);
                }
            }
        }

        this.newLog.close();
        JOptionPane.showMessageDialog(null, "Terminado exitosamente", "Mensaje", 1);
        System.exit(0);
    }

    private void Log(String strLog) {
        try {
            this.lblData.setText(strLog);
        } catch (NullPointerException localNullPointerException1) {
        }

        try {
            this.newLog.println(strLog);
        } catch (NullPointerException ex) {
            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        if (this.isCopy) {
            File CopyFolder = CopyFolder(this.strPath);
            this.strPath = CopyFolder.getPath();
            String[] strparams = {this.strPath};

            Exec(strparams);
        } else {
            String[] strparams = {this.strPath};
            Exec(strparams);
        }
    }
}
