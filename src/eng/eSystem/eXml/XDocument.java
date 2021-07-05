package eng.eSystem.eXml;

import eng.eSystem.validation.EAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;

public class XDocument {

  public static XDocument load(Path path) throws EXmlException {
    EAssert.Argument.isNotNull(path, "path");
    return XDocument.load(path.toAbsolutePath().toString());
  }

  public static XDocument load(File file) throws EXmlException {
    EAssert.Argument.isNotNull(file, "file");
    return XDocument.load(file.getAbsolutePath());
  }

  public static XDocument load(String xmlFileName) throws EXmlException {
    EAssert.Argument.isNotNull(xmlFileName, "xmlFileName");
    XDocument ret;
    try (InputStream is = openFileForReading(xmlFileName)) {
      ret = loadFromInputStream(is, xmlFileName);
    } catch (Exception ex) {
      throw new EXmlException("Failed read X-Document from " + xmlFileName, ex);
    }

    return ret;
  }

  public static XDocument load(InputStream inputStream) throws EXmlException {
    EAssert.Argument.isNotNull(inputStream, "inputStream");
    XDocument ret;
    try {
      ret = loadFromInputStream(inputStream, null);
    } catch (Exception e) {
      throw new EXmlException("Failed to load X-Document from stream.", e);
    }
    return ret;
  }

  private static XDocument loadFromInputStream(InputStream stream, String sourceName) throws EXmlException {
    org.w3c.dom.Document doc = null;
    XDocument ret;
    try {
      doc = DocumentReader.readDocument(sourceName, stream);
    } catch (ParserConfigurationException | SAXException | TransformerException ex) {
      throw new EXmlException("Failed to load XML file from input stream.", ex);
    }

    try{
      org.w3c.dom.Element rootElement = doc.getDocumentElement();
      XElement xel = XElement.fromElement(rootElement);
      XDocumentProperties prp = XDocumentProperties.create(doc);
      ret = new XDocument(prp, xel);
    }catch (Exception ex){
      throw new EXmlException("Failed to convert w3c.Document to XDocument.", ex);
    }

    return ret;
  }

  private static InputStream openFileForReading(String fileName) {
    InputStream is;
    try {
      is = new FileInputStream(fileName);
    } catch (FileNotFoundException ex) {
      throw new RuntimeException("Failed to open file " + fileName + ". " + ex.getMessage(), ex);
    }
    return is;
  }

  private static OutputStream openFileForWriting(String fileName) {
    OutputStream is;
    try {
      is = new FileOutputStream(fileName);
    } catch (FileNotFoundException ex) {
      throw new RuntimeException("Failed to open file " + fileName + ". " + ex.getMessage(), ex);
    }
    return is;
  }

  // TODO obsolete, delete
//  private static org.w3c.dom.Document readXmlDocument(InputStream inputStream, String relativeFileName) throws EXmlException {
//    org.w3c.dom.Document doc = null;
////    try {
////      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
////      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
////      doc = dBuilder.parse(inputStream);
////
////      //optional, but recommended
////      //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
////      doc.getDocumentElement().normalize();
////    } catch (ParserConfigurationException | SAXException | IOException ex) {
////      throw new EXmlException("Failed to load XML file from stream.", ex);
////    }
//
//    try {
//      doc = DocumentReader.readDocument(relativeFileName, inputStream);
//    } catch (ParserConfigurationException | SAXException | TransformerException ex) {
//      throw new EXmlException("Failed to load XML file from input stream.", ex);
//    }
//
//    return doc;
//  }

  private final XDocumentProperties properties;
  private final XElement root;

  public XDocument(XElement root) {
    this.root = root;
    XDocumentProperties prp = new XDocumentProperties();
    prp.setEncoding("UTF-8");
    prp.setStandalone(true);
    prp.setVersion("1.0");
    this.properties = prp;
  }

  public XDocument(XDocumentProperties properties, XElement root) {
    this.properties = properties;
    this.root = root;
  }

  public XDocumentProperties getProperties() {
    return properties;
  }

  public XElement getRoot() {
    return root;
  }

  public void save(Path path) throws EXmlException {
    if (path == null) {
      throw new IllegalArgumentException("Value of {path} cannot not be null.");
    }
    this.save(path.toAbsolutePath().toString());
  }

  public void save(File file) throws EXmlException {
    if (file == null) {
      throw new IllegalArgumentException("Value of {file} cannot not be null.");
    }
    this.save(file.getAbsolutePath());
  }

  public void save(OutputStream outputStream) throws EXmlException {
    Document doc;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Element el;
    try {
      dbFactory = DocumentBuilderFactory.newInstance();
      dBuilder = dbFactory.newDocumentBuilder();
      doc = dBuilder.newDocument();

      el = this.root.toElement(doc);
      doc.appendChild(el);

      doc.setXmlStandalone(this.properties.isStandalone());
      doc.setXmlVersion(this.properties.getVersion());

      saveXmlDocument(outputStream, doc);

    } catch (ParserConfigurationException ex) {
      throw new EXmlException("Failed to create w3c document. Internal error.", ex);
    }
  }

  public void save(String xmlFileName) throws EXmlException {
    try (OutputStream os = openFileForWriting(xmlFileName)) {
      this.save(os);
    } catch (Exception ex) {
      throw new EXmlException("Failed to save XDocument to file " + xmlFileName + ".", ex);
    }
  }

  private void saveXmlDocument(OutputStream os, Document doc) throws EXmlException {
    try {

      TransformerFactory tFactory =
          TransformerFactory.newInstance();
      Transformer transformer =
          tFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(os);
      transformer.transform(source, result);

    } catch (javax.xml.transform.TransformerException ex) {
      throw new EXmlException("Failed to write XML content.", ex);
    }
  }
}
