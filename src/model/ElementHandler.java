package model;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract interface ElementHandler
{
  public abstract Document Read()
    throws IOException, SAXException, ParserConfigurationException, NullPointerException;
  
  public abstract void WriteFinish(Document paramDocument)
    throws IOException, TransformerException;
}


/* Location:              C:\Users\hangarita\Desktop\eFixer.jar!\model\ElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */