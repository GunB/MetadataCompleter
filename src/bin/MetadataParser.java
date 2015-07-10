/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.SharableContentObject;
import org.xml.sax.SAXException;
import utiility.JFolderChooser;

/**
 *
 * @author hangarita
 */
public class MetadataParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                System.out.println("Program Arguments:");
                for (String arg : args) {
                    System.out.println("\t" + arg);
                }

                ArrayList<String> listFilesForFolder = JFolderChooser.listFilesForFolder(new File(args[0]), true);

                ArrayList<SharableContentObject> arrScoRecurso = new ArrayList<>();
                ArrayList<SharableContentObject> arrScoObjeto = new ArrayList<>();
                ArrayList<SharableContentObject> arrScoLeccion = new ArrayList<>();
                ArrayList<SharableContentObject> arrScoNivel = new ArrayList<>();

                //Creating SCOs
                for (String strNameFolder : listFilesForFolder) {
                    if (strNameFolder.endsWith(".zip")) {
                        try {
                            SharableContentObject scoData = new SharableContentObject(new ZipReader(args[0], strNameFolder));
                            //<editor-fold defaultstate="collapsed" desc="Clasificando">
                            switch (scoData.getStrType()) {
                                case "NIVEL":
                                    arrScoNivel.add(scoData);
                                    break;
                                case "LECCION":
                                    arrScoLeccion.add(scoData);
                                    break;
                                case "OBJETO":
                                    arrScoObjeto.add(scoData);
                                    break;
                                case "RECURSO":
                                    arrScoRecurso.add(scoData);
                                    break;
                            }
                            //</editor-fold>
                        } catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, ex);
                            System.exit(8);
                        }
                    }
                    if (strNameFolder.endsWith(".xml")) {
                        try {
                            SharableContentObject scoData = new SharableContentObject(new XMLReader(args[0], strNameFolder));
                            //<editor-fold defaultstate="collapsed" desc="Clasificando">
                            switch (scoData.getStrType()) {
                                case "NIVEL":
                                    arrScoNivel.add(scoData);
                                    break;
                                case "LECCION":
                                    arrScoLeccion.add(scoData);
                                    break;
                                case "OBJETO":
                                    arrScoObjeto.add(scoData);
                                    break;
                                case "RECURSO":
                                    arrScoRecurso.add(scoData);
                                    break;
                            }
                            //</editor-fold>
                        } catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                            Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, ex);
                            System.exit(8);
                        }
                    }
                }

                Boolean bulHijos, bulPadre;

                try {
                    for (SharableContentObject scoActual : arrScoRecurso) {
                        bulPadre = setRelationsPadreActual(scoActual, arrScoObjeto);

                        if (bulPadre) {
                            try {
                                scoActual.SaveChanges();
                            } catch (IOException | TransformerException ex) {
                                Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex);
                                System.exit(6);
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(9);
                }

                try {
                    for (SharableContentObject scoActual : arrScoObjeto) {
                        bulPadre = setRelationsPadreActual(scoActual, arrScoLeccion);
                        bulHijos = bulPadre ? setRelationsHijoActual(scoActual, arrScoRecurso) : false;

                        if (bulPadre && bulHijos) {
                            try {
                                scoActual.SaveChanges();
                            } catch (IOException | TransformerException ex) {
                                Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex);
                                System.exit(6);
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(9);
                }

                try {
                    for (SharableContentObject scoActual : arrScoLeccion) {
                        bulPadre = setRelationsPadreActual(scoActual, arrScoNivel);
                        bulHijos = bulPadre ? setRelationsHijoActual(scoActual, arrScoObjeto) : false;

                        if (bulPadre && bulHijos) {
                            try {
                                scoActual.SaveChanges();
                            } catch (IOException | TransformerException ex) {
                                Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex);
                                System.exit(6);
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(9);
                }

                try {
                    for (SharableContentObject scoActual : arrScoNivel) {
                        bulHijos = setRelationsHijoActual(scoActual, arrScoLeccion);

                        if (bulHijos) {
                            try {
                                scoActual.SaveChanges();
                            } catch (IOException | TransformerException ex) {
                                Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex);
                                System.exit(6);
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.exit(9);
                }
                
                JOptionPane.showMessageDialog(null, "Terminado exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });

    }

    private static Boolean setRelationsPadreActual(SharableContentObject scoActual, ArrayList<SharableContentObject> arrScoPosiblesPadres) {
        Boolean bulRelacion = false;
        for (SharableContentObject scoPadre : arrScoPosiblesPadres) {
            if (scoActual.getStrID().contains(scoPadre.getStrID())) {
                scoActual.SetRelation(scoPadre, "Es parte de");
                bulRelacion = true;
                break;
            }
        }
        return bulRelacion;
    }

    private static Boolean setRelationsHijoActual(SharableContentObject scoActual, ArrayList<SharableContentObject> arrScoPosiblesHijos) {
        Boolean bulRelacion = false;
        for (SharableContentObject scoHijo : arrScoPosiblesHijos) {
            if (scoHijo.getStrID().contains(scoActual.getStrID())) {
                scoActual.SetRelation(scoHijo, "Está compuesto por");
                bulRelacion = true;
            }
        }
        return bulRelacion;
    }

}
