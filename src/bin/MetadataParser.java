/*     */ package bin;
/*     */
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import model.SharableContentObject;
/*     */ import org.xml.sax.SAXException;
/*     */ import utiility.FilesUtility;
/*     */ import utiility.JFolderChooser;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class MetadataParser
        /*     */ implements Runnable /*     */ {
    /*  33 */ private boolean isCopy = true;
    /*     */    private String strPath;
    /*  35 */    private JLabel lblData = null;
    /*     */
    /*     */    PrintWriter newLog;
    /*  38 */    String strNameLog = "READLOG";
    /*     */
    /*  40 */    long unixTime = System.currentTimeMillis() / 1000L;
    /*  41 */    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
    /*     */
    /*     */ public MetadataParser(Object params) {
        /*  44 */ Object[] objData = (Object[]) params;
        /*     */
        /*  46 */ this.isCopy = ((Boolean) objData[0]).booleanValue();
        /*  47 */ this.strPath = ((String) objData[1]);
        /*  48 */ this.lblData = ((JLabel) objData[2]);
        /*     */    }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */ public MetadataParser() {
    }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */ public File CopyFolder(String strPathFolder) /*     */ {
        /*  63 */ Log("Copiando archivos...");
        /*     */
        /*     */
        /*  66 */ File baseFileDirectory = new File(strPathFolder);
        /*     */
        /*  68 */ File newFileDirectory = new File(baseFileDirectory.getParent() + File.separator + "eFIXED_" + this.unixTime + " " + this.timeStamp);
        /*     */
        /*  70 */ newFileDirectory.mkdirs();
        /*     */ try /*     */ {
            /*  73 */ FilesUtility.copyFolder(baseFileDirectory, newFileDirectory);
            /*     */        } catch (IOException ex) {
            /*  75 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
            /*  76 */ JOptionPane.showMessageDialog(null, ex);
            /*  77 */ System.exit(9);
            /*     */        }
        /*     */
        /*  80 */ return newFileDirectory;
        /*     */    }
    /*     */
    /*     */ public static void main(String[] args) {
        /*  84 */ new MetadataParser().Exec(args);
        /*     */    }
    /*     */
    /*     */ public void Exec(String[] args) /*     */ {
        /*  89 */ System.out.println("Program Arguments:");
        /*  90 */ for (String arg : args) {
            /*  91 */ System.out.println("\t" + arg);
            /*     */        }
        /*     */
        /*  94 */ File baseFileDirectory = new File(args[0]);
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 100 */ this.strNameLog = baseFileDirectory.getPath().concat(File.separator).concat(this.strNameLog + "_" + this.unixTime).concat(".txt");
        /*     */ try /*     */ {
            /* 103 */ this.newLog = new PrintWriter(this.strNameLog, "UTF-8");
            /*     */        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            /* 105 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
            /*     */        }
        /*     */
        /* 108 */ ArrayList<File> listFilesForFolder = JFolderChooser.listRawFilesForFolder(baseFileDirectory, true);
        /*     */
        /* 110 */ Object arrRec = new ArrayList();
        /* 111 */ ArrayList<SharableContentObject> arrObj = new ArrayList();
        /* 112 */ ArrayList<SharableContentObject> arrLec = new ArrayList();
        /* 113 */ ArrayList<SharableContentObject> arrLvl = new ArrayList();
        /*     */
        /* 115 */ String strMessage = "Leyendo SCO(s)... ";
        /* 116 */ String strMessage2 = "REL: ";
        /*     */
        /*     */
        /*     */
        /* 120 */ Log(strMessage);
        /*     */
        /*     */
        /*     */
        /*     */
        /* 125 */ for (File strNameFolder : listFilesForFolder) /*     */ {
            /* 127 */ scoData = null;
            /*     */
            /* 129 */ if (strNameFolder.getName().endsWith(".zip")) {
                /*     */ try {
                    /* 131 */ scoData = new SharableContentObject(new ZipReader(strNameFolder));
                    /*     */
                    /* 133 */ Log(strMessage + scoData.getStrID());
                    /*     */                } /*     */ catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                    /* 136 */ System.err.println("ERROR [NOT METADATA]: " + strNameFolder.getName());
                    /* 137 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    /*     */                }
                /*     */            }
            /*     */
            /*     */
            /*     */
            /* 143 */ if (strNameFolder.getName().endsWith(".xml")) {
                /*     */ try /*     */ {
                    /* 146 */ scoData = new SharableContentObject(new XMLReader(strNameFolder));
                    /*     */
                    /* 148 */ Log(strMessage + scoData.getStrID());
                    /*     */                } /*     */ catch (IOException | SAXException | ParserConfigurationException | NullPointerException ex) {
                    /* 151 */ System.err.println("ERROR [NOT METADATA]: " + strNameFolder.getName());
                    /* 152 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null,  ?  ?  ?);
                    /*     */                }
                /*     */            }
            /*     */
            /*     */
            /*     */
            /*     */ try /*     */ {
                /* 160 */ if ((strNameFolder.getName().endsWith(".xml")) || (strNameFolder.getName().endsWith(".zip"))) {
                    /* 161 */ switch (scoData.getStrType()) {
                        /*     */ case "NIVEL":
                            /* 163 */ arrLvl.add(scoData);
                            /* 164 */ break;
                        /*     */ case "LECCION":
                            /* 166 */ arrLec.add(scoData);
                            /* 167 */ break;
                        /*     */ case "OBJETO":
                            /* 169 */ arrObj.add(scoData);
                            /* 170 */ break;
                        /*     */ case "RECURSO":
                            /* 172 */ ((ArrayList) arrRec).add(scoData);
                        /*     */                    }
                    /*     */                }
                /*     */            } /*     */ catch (NullPointerException ex) /*     */ {
                /* 178 */ System.err.println("Failed: " + strNameFolder.getPath());
                /* 179 */ Log("Failed: " + strNameFolder.getPath() + " " + ex);
                /* 180 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                /*     */            }
            /*     */        }
        /*     */
        /*     */
        /*     */ SharableContentObject scoData;
        /*     */
        /* 187 */ strMessage = "Creando relaciones en los recursos... ";
        /*     */
        /*     */
        /* 190 */ for (SharableContentObject scoData : (ArrayList) arrRec) {
            /* 191 */ if (scoData.isRelationed()) {
                /* 192 */ Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
                /*     */            } /*     */ else /*     */ {
                /* 196 */ Log(strMessage + scoData.getStrID());
                /* 197 */ int cont = 0;
                /* 198 */ for (SharableContentObject scoSCO : arrObj) {
                    /* 199 */ if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        /* 200 */ cont++;
                        /* 201 */ Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        /* 202 */ scoData.SetRelation(scoSCO, "Es parte de");
                        /*     */                    }
                    /*     */                }
                /*     */ try /*     */ {
                    /* 207 */ Log("Guardando cambios... \t" + scoData.getStrID());
                    /* 208 */ scoData.SaveChanges();
                    /*     */                } catch (IOException | TransformerException ex) {
                    /* 210 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    /* 211 */ JOptionPane.showMessageDialog(null, ex);
                    /* 212 */ System.exit(6);
                    /*     */                }
                /*     */            }
            /*     */        }
        /*     */
        /* 217 */ strMessage = "Creando relaciones en los Objetos... ";
        /*     */
        /*     */
        /* 220 */ for (SharableContentObject scoData : arrObj) {
            /* 221 */ if (scoData.isRelationed()) {
                /* 222 */ Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
                /*     */            } /*     */ else {
                /* 225 */ boolean bulPadre = false;
                /*     */
                /* 227 */ int cont = 0;
                /* 228 */ for (SharableContentObject scoSCO : arrLec) /*     */ {
                    /* 230 */ if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        /* 231 */ bulPadre = true;
                        /* 232 */ cont++;
                        /* 233 */ Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        /* 234 */ scoData.SetRelation(scoSCO, "Es parte de");
                        /*     */                    }
                    /*     */                }
                /*     */
                /* 238 */ if (bulPadre) {
                    /* 239 */ cont = 0;
                    /* 240 */ Log(strMessage + scoData.getStrID());
                    /*     */
                    /* 242 */ for (SharableContentObject scoSCO : (ArrayList) arrRec) /*     */ {
                        /* 244 */ if (scoSCO.getStrID().contains(scoData.getStrID())) {
                            /* 245 */ cont++;
                            /* 246 */ Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                            /* 247 */ scoData.SetRelation(scoSCO, "Está compuesto por");
                            /*     */                        }
                        /*     */                    }
                    /*     */ try /*     */ {
                        /* 252 */ Log("Guardando cambios... \t" + scoData.getStrID());
                        /* 253 */ scoData.SaveChanges();
                        /*     */                    } catch (IOException | TransformerException ex) {
                        /* 255 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                        /* 256 */ JOptionPane.showMessageDialog(null, ex);
                        /* 257 */ System.exit(6);
                        /*     */                    }
                    /*     */                } /*     */ else {
                    /* 261 */ Log("No fué identificado el padre... relaciones abortadas: " + scoData.getStrID());
                    /*     */                }
                /*     */            }
            /*     */        }
        /*     */
        /*     */
        /* 267 */ strMessage = "Creando relaciones en las Lecciones... ";
        /*     */
        /*     */
        /* 270 */ for (SharableContentObject scoData : arrLec) {
            /* 271 */ if (scoData.isRelationed()) {
                /* 272 */ Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
                /*     */            } /*     */ else {
                /* 275 */ Log(strMessage + scoData.getStrID());
                /* 276 */ boolean bulPadre = false;
                /*     */
                /* 278 */ int cont = 0;
                /* 279 */ for (SharableContentObject scoSCO : arrLvl) /*     */ {
                    /* 281 */ if (scoData.getStrID().contains(scoSCO.getStrID())) {
                        /* 282 */ bulPadre = true;
                        /* 283 */ cont++;
                        /* 284 */ Log(strMessage2 + cont + " Es parte de \t" + scoSCO.getStrID());
                        /* 285 */ scoData.SetRelation(scoSCO, "Es parte de");
                        /*     */                    }
                    /*     */                }
                /*     */
                /* 289 */ if (bulPadre) {
                    /* 290 */ cont = 0;
                    /* 291 */ for (SharableContentObject scoSCO : arrObj) /*     */ {
                        /* 293 */ if (scoSCO.getStrID().contains(scoData.getStrID())) {
                            /* 294 */ cont++;
                            /* 295 */ Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                            /* 296 */ scoData.SetRelation(scoSCO, "Está compuesto por");
                            /*     */                        }
                        /*     */                    }
                    /*     */ try /*     */ {
                        /* 301 */ Log("Guardando cambios... \t" + scoData.getStrID());
                        /* 302 */ scoData.SaveChanges();
                        /*     */                    } catch (IOException | TransformerException ex) {
                        /* 304 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                        /* 305 */ JOptionPane.showMessageDialog(null, ex);
                        /* 306 */ System.exit(6);
                        /*     */                    }
                    /*     */                } /*     */ else {
                    /* 310 */ Log("No fué identificado el padre... relaciones abortadas: " + scoData.getStrID());
                    /*     */                }
                /*     */            }
            /*     */        }
        /*     */
        /*     */
        /* 316 */ strMessage = "Creando relaciones en los niveles... ";
        /*     */
        /*     */
        /* 319 */ for (SharableContentObject scoData : arrLvl) {
            /* 320 */ if (scoData.isRelationed()) {
                /* 321 */ Log("YA SE ENCUENTRA CON RELACIONES, SCO NO ACTUALIZADO " + scoData.getStrID());
                /*     */            } /*     */ else {
                /* 324 */ Log(strMessage + scoData.getStrID());
                /*     */
                /* 326 */ int cont = 0;
                /* 327 */ for (SharableContentObject scoSCO : arrLec) /*     */ {
                    /* 329 */ if (scoSCO.getStrID().contains(scoData.getStrID())) {
                        /* 330 */ cont++;
                        /* 331 */ Log(strMessage2 + cont + " Está compuesto por \t" + scoSCO.getStrID());
                        /* 332 */ scoData.SetRelation(scoSCO, "Está compuesto por");
                        /*     */                    }
                    /*     */                }
                /*     */ try /*     */ {
                    /* 337 */ Log("Guardando cambios... \t" + scoData.getStrID());
                    /* 338 */ scoData.SaveChanges();
                    /*     */                } catch (IOException | TransformerException ex) {
                    /* 340 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
                    /* 341 */ JOptionPane.showMessageDialog(null, ex);
                    /* 342 */ System.exit(6);
                    /*     */                }
                /*     */            }
            /*     */        }
        /*     */
        /* 347 */ this.newLog.close();
        /* 348 */ JOptionPane.showMessageDialog(null, "Terminado exitosamente", "Mensaje", 1);
        /* 349 */ System.exit(0);
        /*     */    }
    /*     */
    /*     */ private void Log(String strLog) {
        /*     */ try {
            /* 354 */ this.lblData.setText(strLog);
            /*     */        } /*     */ catch (NullPointerException localNullPointerException1) {
        }
        /*     */
        /*     */ try /*     */ {
            /* 360 */ this.newLog.println(strLog);
            /*     */        } catch (NullPointerException ex) {
            /* 362 */ Logger.getLogger(MetadataParser.class.getName()).log(Level.SEVERE, null, ex);
            /*     */        }
        /*     */    }
    /*     */
    /*     */
    /*     */ public void run() /*     */ {
        /* 369 */ if (this.isCopy) {
            /* 370 */ File CopyFolder = CopyFolder(this.strPath);
            /* 371 */ this.strPath = CopyFolder.getPath();
            /* 372 */ String[] strparams = {this.strPath};
            /*     */
            /* 374 */ Exec(strparams);
            /*     */        } else {
            /* 376 */ String[] strparams = {this.strPath};
            /* 377 */ Exec(strparams);
            /*     */        }
        /*     */    }
    /*     */ }


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\bin\MetadataParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
