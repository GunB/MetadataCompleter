/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiility;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author hangarita
 */
public class JFolderChooser {

    JFileChooser chooser = new JFileChooser();
    String strPath = null;

    public JFolderChooser() {

    }

    public JFolderChooser(String strPath) {
        chooser.setSelectedFile(new File(strPath));
        
    }
    
    public String getStrPathUp() throws Exception {
        this.strPath = chooser.getSelectedFile().getAbsolutePath();
        return strPath;
    }

    public String getStrPath() throws Exception {
        this.strPath = chooser.getSelectedFile().getAbsolutePath();
        return strPath;
    }

    public void OpenChooser(String strTitle) {
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(strTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
    }

    public ArrayList<String> getFileList() {
        if (strPath.isEmpty()) {
            return null;
        } else {
            return listFilesForFolder(new File(strPath));
        }
    }

    public static ArrayList<String> listFilesForFolder(File folder) {
        ArrayList<String> arrData = new ArrayList<>();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                //listFilesForFolder(fileEntry);
            } else {
                arrData.add(fileEntry.getName());
                //System.out.println(fileEntry.getName());
            }
        }

        return arrData;
    }
}
