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
public class ZipReader implements ElementHandler{

    String strPath;
    String strName;
    String strNewSufij = "_new.zip";
    String strFile2Change = "metadata.xml";

    ZipOutputStream zos;

    public ZipReader(String strPath, String strName) throws IOException {
        this.strPath = strPath;
        this.strName = strName;
    }

    @Override
    public Document Read() throws IOException, SAXException, ParserConfigurationException {
        String strFile = strPath.concat(File.separator).concat(strName);
        ZipFile zipFile = new ZipFile(strFile);

        String strNewFile = strFile.concat(strNewSufij);
        zos = new ZipOutputStream(new FileOutputStream(strNewFile));
        InputStream is = null;

        for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = (ZipEntry) e.nextElement();
            if (!entryIn.getName().equalsIgnoreCase(strFile2Change)) {
                zos.putNextEntry(entryIn);
                is = zipFile.getInputStream(entryIn);
                byte[] buf = new byte[1024];
                int len;
                while ((len = (is.read(buf))) > 0) {
                    zos.write(buf, 0, len);
                }
                
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(strFile2Change));
                is = zipFile.getInputStream(entryIn);
            }
            //zos.closeEntry();
        }
        //zos.close();
        return XMLUtility.newDocumentFromInputStream(is);
    }

    /**
     *
     * @param doc
     * @throws IOException
     * @throws javax.xml.transform.TransformerException
     */
    @Override
    public void WriteFinish(Document doc) throws IOException, TransformerException {
        InputStream is = XMLUtility.newInputStreamFromDocument(doc);
        byte[] buf = new byte[1024];
        int len;
        while ((len = (is.read(buf))) > 0) {
            /*String s = new String(buf);
            if (s.contains("key1=value1")) {
                buf = s.replaceAll("key1=value1", "key1=val2").getBytes();
            }*/
            zos.write(buf, 0, (len < buf.length) ? len : buf.length);
        }
        
        zos.closeEntry();
        zos.close();
    }

}
