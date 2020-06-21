package eng.eSystem.eXml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;
import java.lang.annotation.ElementType;

class DocumentReader {

  public static Document readDocument(String sourceId, InputStream inputStream)
      throws TransformerException, ParserConfigurationException, SAXException {
    /*
     * During application startup
     */
    DocumentBuilderFactory documentBuilderFactory
        = DocumentBuilderFactory.newInstance();
    TransformerFactory transformerFactory
        = TransformerFactory.newInstance();
    Transformer nullTransformer = transformerFactory.newTransformer();

    /*
     * Create an empty document to be populated within a DOMResult.
     */
    DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
    Document ret = docBuilder.newDocument();
    DOMResult domResult = new DOMResult(ret);

    /*
     * Create SAX parser/XMLReader that will parse XML. If factory
     * options are not required then this can be short cut by:
     *      xmlReader = XMLReaderFactory.createXMLReader();
     */
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    // saxParserFactory.setNamespaceAware(true);
    // saxParserFactory.setValidating(true);
    SAXParser saxParser = saxParserFactory.newSAXParser();
    XMLReader xmlReader = saxParser.getXMLReader();

    /*
     * Create our filter to wrap the SAX parser, that captures the
     * locations of elements and annotates their nodes as they are
     * inserted into the DOM.
     */
    LocationAnnotator locationAnnotator = new LocationAnnotator(xmlReader, ret);

    /*
     * Create the SAXSource to use the annotator.
     */
    InputSource inputSource = new InputSource(inputStream);
    SAXSource saxSource = new SAXSource(locationAnnotator, inputSource);

    /*
     * Finally read the XML into the DOM.
     */
    nullTransformer.transform(saxSource, domResult);

    return ret;
  }
}
