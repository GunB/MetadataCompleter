package bin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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

public class ZipReader
        implements ElementHandler {

    String strPath;
    String strName;
    String strNewSufij = ".eFixed";
    String strFile2Change = "metadata.xml";

    ZipFile zipFile = null;

    public ZipReader(String strPath, String strName) throws IOException {
        this.strPath = strPath;
        this.strName = strName;
        String strFile = strPath.concat(File.separator).concat(strName);
        this.zipFile = new ZipFile(strFile);
    }

    public ZipReader(File fileData) throws IOException {
        this.strPath = fileData.getParent();
        this.strName = fileData.getName();
        this.zipFile = new ZipFile(fileData);
        System.out.println("Path: " + this.strPath);
        System.out.println("Name: " + this.strName);
        System.out.println("File: " + this.zipFile.getName());
    }

    public Document Read()
            throws IOException, SAXException, ParserConfigurationException {
        Document doc = null;

        for (Enumeration e = this.zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = (ZipEntry) e.nextElement();
            String strName = entryIn.getName();
            if (strName.equalsIgnoreCase(this.strFile2Change)) {
                doc = XMLUtility.newDocumentFromInputStream(this.zipFile.getInputStream(entryIn));
            }
        }

        return doc;
    }

    public void WriteFinish(Document doc)
            throws IOException, TransformerException {
        String strOldFile = this.strPath.concat(File.separator).concat(this.strName);

        String strNewFile = this.strPath.concat(File.separator).concat(this.strName).concat(this.strNewSufij);
        File newFile = new File(strNewFile);

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFile));
        System.out.println("Saving [File]: " + strNewFile);

        for (Enumeration e = this.zipFile.entries(); e.hasMoreElements();) {
            ZipEntry entryIn = new ZipEntry((ZipEntry) e.nextElement());

            if (!entryIn.getName().equalsIgnoreCase(this.strFile2Change)) {

                ZipEntry destEntry = new ZipEntry(entryIn.getName());
                zos.putNextEntry(destEntry);

                InputStream is = this.zipFile.getInputStream(entryIn);
                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
            } else {
                ZipEntry destEntry = new ZipEntry(this.strFile2Change);

                zos.putNextEntry(destEntry);

                InputStream is = XMLUtility.newInputStreamFromDocument(doc);

                byte[] buf = new byte['Ѐ'];
                int len;
                while ((len = is.read(buf)) > 0) {
                    zos.write(buf, 0, len < buf.length ? len : buf.length);
                }
            }
            zos.closeEntry();
        }
        zos.close();

        this.zipFile.close();

        File oldFile = new File(strOldFile);
        oldFile.delete();

        newFile.renameTo(oldFile);
    }
}
