package utiility;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class JFolderChooser {

    JFileChooser chooser = new JFileChooser();
    String strPath = null;

    public JFolderChooser() {
    }

    public JFolderChooser(String strPath) {
        this.chooser.setSelectedFile(new File(strPath));
    }

    public String getStrPathUp() throws Exception {
        this.strPath = this.chooser.getSelectedFile().getAbsolutePath();
        return this.strPath;
    }

    public String getStrPath() throws Exception {
        this.strPath = this.chooser.getSelectedFile().getAbsolutePath();
        return this.strPath;
    }

    public void OpenChooser(String strTitle) {
        this.chooser.setCurrentDirectory(new File("."));
        this.chooser.setDialogTitle(strTitle);
        this.chooser.setFileSelectionMode(1);

        this.chooser.setAcceptAllFileFilterUsed(false);

        if (this.chooser.showOpenDialog(null) == 0) {
            System.out.println("getCurrentDirectory(): " + this.chooser
                    .getCurrentDirectory());
            System.out.println("getSelectedFile() : " + this.chooser
                    .getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
    }

    public ArrayList<String> getFileList(boolean isRecursive) {
        if (this.strPath.isEmpty()) {
            return null;
        }
        return listFilesForFolder(new File(this.strPath), isRecursive);
    }

    public static ArrayList<String> listFilesForFolder(File folder, boolean isRecursive) {
        ArrayList<String> arrData = new ArrayList();

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                if (isRecursive) {
                    arrData.addAll(listFilesForFolder(fileEntry, isRecursive));
                } else {
                    arrData.add(fileEntry.getName());
                }
            } else {
                arrData.add(fileEntry.getName());
            }
        }

        return arrData;
    }

    public static ArrayList<File> listRawFilesForFolder(File folder, boolean isRecursive) {
        ArrayList<File> arrData = new ArrayList();

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                if (isRecursive) {
                    arrData.addAll(listRawFilesForFolder(fileEntry, isRecursive));
                }
            } else {
                arrData.add(fileEntry);
            }
        }

        return arrData;
    }
}
