package com.chfourie.gwtportletbridge.client.general.api;

import com.chfourie.gwtportletbridge.client.general.impl.NativePortletGwtBridge;

import java.util.Map;

public abstract class NativeGWT {
  private static PortletGwtBridge bridge;


  public static Map<String, String> getInputValues(String formId) {
    return getBridge().getInputValues(formId);
  }


  public static String getModuleName() {
    return getBridge().getModuleName();
  }


  private NativeGWT() {
  }


  private static PortletGwtBridge getBridge() {
    if (bridge == null) bridge = new NativePortletGwtBridge();
    return bridge;
  }


  static void mockWith(PortletGwtBridge mock) {
    bridge = mock;
  }


  static void reset() {
    bridge = null;
  }


  public static String decodeQueryString(String value) {
    return value == null ? null : getBridge().decodeQueryString(value);
  }


  public static String encodeQueryString(String value) {
    return value == null ? null : getBridge().encodeQueryString(value);
  }
}
