/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.ElementHandler;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utiility.XMLUtility;

/**
 *
 * @author hangarita
 */
public class ZipReader implements ElementHandler {

    String strPath;
    String strName;
    String strNewSufij = "New_";
    String strFile2Change = "metadata.xml";

    //ZipOutputStream zos;
    public ZipReader(String strPath, String strName) throws IOException {
        this.strPath = strPath;
        this.strName = strName;
    }

    @Override
    public Document Read() throws IOException, SAXException, ParserConfigurationException {
        String strFile = strPath.concat(File.separator).concat(strName);
        ZipFile zipFile = new ZipFile(strFile);

        Document doc = null;

        String strNewFile = strFile.concat(strNewSufij);
        //zos = new ZipOutputStream(new FileOutputStream(strNewFile));

        for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = (ZipEntry) e.nextElement();
            String strName = entryIn.getName();
            if (strName.equalsIgnoreCase(strFile2Change)) {
                doc = XMLUtility.newDocumentFromInputStream(zipFile.getInputStream(entryIn));
            }
            //zos.closeEntry();
        }
        //zos.close();
        return doc;
    }

    /**
     *
     * @param doc
     * @throws IOException
     */
    @Override
    public void WriteFinish(Document doc) throws IOException, TransformerException {
        String strZipFile = strPath.concat(File.separator).concat(strName);
        Path pathZipFile = Paths.get(strZipFile);
        
        
        //Path myFilePath = Paths.get("c:/dump2/mytextfile.txt");

        
        
        try (FileSystem fs = FileSystems.newFileSystem(pathZipFile, null)) {
            Path fileInsideZipPath = fs.getPath(File.separator.concat(strFile2Change));
            Files.copy(XMLUtility.newInputStreamFromDocument(doc), pathZipFile, new CopyOption() {});
            //Files.copy(myFilePath, fileInsideZipPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        /*Path myFilePath = Paths.get("c:/dump2/mytextfile.txt");

        Path zipFilePath = Paths.get("c:/dump2/myarchive.zip");
        try (FileSystem fs = FileSystems.newFileSystem(zipFilePath, null)) {
            Path fileInsideZipPath = fs.getPath("/mytextfile.txt");
            Files.copy(null, myFilePath, options)
            //Files.copy(myFilePath, fileInsideZipPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

}
