package eng.eSystem.eXml;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class XElement {
  private final String name;
  private final IList<XElement> children = new EList<>();
  private final IMap<String, String> attributes = new EMap<>();
  private String content;

  public static XElement fromElement(Element el) {
    XElement ret = new XElement(el.getTagName());

    // attributes
    for (int i = 0; i < el.getAttributes().getLength(); i++) {
      Node n = el.getAttributes().item(i);
      String name = n.getNodeName();
      String value = n.getNodeValue();
      ret.getAttributes().set(name, value);
    }

    // children
    for (int i = 0; i < el.getChildNodes().getLength(); i++) {
      Node n = el.getChildNodes().item(i);
      if (n.getNodeType() != Node.ELEMENT_NODE) continue;
      XElement tmp = XElement.fromElement((Element) n);
      ret.getChildren().add(tmp);
    }

    // direct content
    if (ret.getChildren().isEmpty()){
      String value = el.getTextContent();
      ret.setContent(value);
    } else {
      ret.setContent("");
    }

    return ret;
  }

  public XElement(String name) {
    this.name = name;
  }

  public XElement(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public String getName() {
    return name;
  }

  public IList<XElement> getChildren() {
    return children;
  }

  public IMap<String, String> getAttributes() {
    return attributes;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return sf("<%s>", this.name);
  }

  public Element toElement(Document doc) {
    Element ret = doc.createElement(this.name);

    for (String key : this.attributes.getKeys()) {
      String val = this.attributes.get(key);
      ret.setAttribute(key, val);
    }

    if (this.children.isEmpty())
      ret.setTextContent(this.getContent());
     else {
      for (XElement child : this.children) {
        Element tmp = child.toElement(doc);
        ret.appendChild(tmp);
      }
    }

    return ret;
  }
}
