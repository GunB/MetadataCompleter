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
public class ZipReader implements ElementHandler {

    String strPath;
    String strName;
    String strNewSufij = "New" + File.separator;
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
     * @throws javax.xml.transform.TransformerException
     */
    @Override
    public void WriteFinish(Document doc) throws IOException, TransformerException {
        String strFile = strPath.concat(File.separator).concat(strName);
        ZipFile zipFile = new ZipFile(strFile);

        String strNewFile = strPath.concat(File.separator).concat(strNewSufij).concat(strName);

        (new File(strPath.concat(File.separator).concat(strNewSufij))).mkdirs();
        
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(strNewFile));

        for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = new ZipEntry((ZipEntry) e.nextElement());

            if (!entryIn.getName().equalsIgnoreCase(strFile2Change)) {
                //zos.putNextEntry(entryIn);
                ZipEntry destEntry
                        = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);

                InputStream is = zipFile.getInputStream(entryIn);
                byte[] buf = new byte[1024];
                int len;
                while ((len = (is.read(buf))) > 0) {
                    zos.write(buf, 0, len);
                }
            } else {
                //String strData = XMLUtility.newStringFromDocument(doc);

                ZipEntry destEntry
                        = new ZipEntry(strFile2Change);
                zos.putNextEntry(destEntry);

                InputStream is = XMLUtility.newInputStreamFromDocument(doc);
                //zipFile.getInputStream(entryIn);
                byte[] buf = new byte[1024];
                int len;
                while ((len = (is.read(buf))) > 0) {
                    //buf = strData.getBytes();
                    zos.write(buf, 0, (len < buf.length) ? len : buf.length);
                }//*/
            }
            zos.closeEntry();
        }
        zos.close();
    }

}
