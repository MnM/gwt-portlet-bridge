package com.chfourie.gwtportletbridge.server.tags;

import junit.framework.Assert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.List;

public class XmlResult {
  private String stringResult;
  private Document result;

  public XmlResult(String result) {
    try {
      this.stringResult = result;
      this.result = new SAXReader().read(new ByteArrayInputStream(result.getBytes()));
    } catch (DocumentException e) {
      throw new RuntimeException("Could not parse - " + result, e);
    }
  }

  public void assertPresent(String xpath) {
    List items = result.selectNodes(xpath);
    if (items.isEmpty()) fail("Element not present.", xpath);
    if (items.size() > 1) fail("More than one element found.", xpath);
  }

  public void assertNotPresent(String xpath) {
    List items = result.selectNodes(xpath);
    if (items.size() > 0) fail("Element expected not present but found.", xpath);
  }

  public String getTextValue(String xpath) {
    Node n = result.selectSingleNode(xpath);

    if (n == null) {
      fail("Node not found.", xpath);
      return null;
    }

    return n.getText();
  }

  private void fail(String message, String xpath) {
    Assert.fail(message + " (result: '" + stringResult + "', xpath: '" + xpath + "\')");
  }


  public int count(String xpath) {
    return result.selectNodes(xpath).size();
  }


  @Override
  public String toString() {
    return stringResult;
  }
}
