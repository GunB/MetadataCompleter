/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.ElementHandler;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author hangarita
 */
public class XMLReader implements ElementHandler{
    
    String strPath;
    String strName;
    String strNewSufij = "New" + File.separator;
    String strFile2Change = "metadata.xml";

    //ZipOutputStream zos;
    public XMLReader(String strPath, String strName) throws IOException {
        this.strPath = strPath;
        this.strName = strName;
    }

    @Override
    public Document Read() throws IOException, SAXException, ParserConfigurationException {
        File fXmlFile = new File(strPath.concat(File.separator).concat(strName));
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	return dBuilder.parse(fXmlFile);
    }

    @Override
    public void WriteFinish(Document doc) throws IOException, TransformerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
