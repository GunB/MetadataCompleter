/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import model.ZipXMLFile;
import utiility.JFolderChooser;

/**
 *
 * @author hangarita
 */
public class ZipReader {
    
    ArrayList<String> arrListFiles;
    String strPath;
    ArrayList<ZipXMLFile> arrFiles;

    public ZipReader(ArrayList<String> arrListFiles, String strPath) {
        this.arrListFiles = arrListFiles;
        this.strPath = strPath;
    }

    public ZipReader(String strPath) {
        arrListFiles = JFolderChooser.listFilesForFolder(new File(strPath));
        this.strPath = strPath;
    }
    
    private void Read(){
        
    }

    public void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("C:/test.zip");

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
        }
    }
    
    
}
