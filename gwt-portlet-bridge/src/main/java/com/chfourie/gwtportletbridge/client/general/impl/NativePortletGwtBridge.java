package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.PortletGwtBridge;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;

import java.util.HashMap;
import java.util.Map;

public class NativePortletGwtBridge implements PortletGwtBridge {


  @Override
  public Map<String, String> getInputValues(String formId) {
    NodeList<Element> elements = DOM.getElementById(formId).getElementsByTagName("input");
    Map<String, String> result = new HashMap<String, String>(elements.getLength());

    for (int i = 0; i < elements.getLength(); i++) {
      result.put(elements.getItem(i).getAttribute("name"), elements.getItem(i).getAttribute("value"));
    }

    return result;
  }


  @Override
  public String getModuleName() {
    return GWT.getModuleName();
  }


  @Override
  public String decodeQueryString(String value) {
    return URL.decodeQueryString(value);
  }


  @Override
  public String encodeQueryString(String value) {
    return URL.encodeQueryString(value);
  }
}
