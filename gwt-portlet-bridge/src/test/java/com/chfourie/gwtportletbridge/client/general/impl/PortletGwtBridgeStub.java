package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.PortletGwtBridge;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class PortletGwtBridgeStub implements PortletGwtBridge {


  @Override
  public Map<String, String> getInputValues(String formId) {
    return null;
  }


  @Override
  public String getModuleName() {
    return null;
  }


  @Override
  public String decodeQueryString(String value) {
    try {
      return URLDecoder.decode(value, "utf-8");
    } catch (UnsupportedEncodingException ignored) {
      throw new RuntimeException(ignored);
    }
  }


  @Override
  public String encodeQueryString(String value) {
    try {
      return URLDecoder.decode(value, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
