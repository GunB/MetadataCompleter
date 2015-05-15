/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author hangarita
 */
public interface ElementHandler {

    Document Read() throws IOException, SAXException, ParserConfigurationException;

    public void WriteFinish(Document doc) throws IOException, TransformerException;
}
