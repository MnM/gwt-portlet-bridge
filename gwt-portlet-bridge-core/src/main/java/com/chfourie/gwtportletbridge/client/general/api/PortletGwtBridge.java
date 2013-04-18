package com.chfourie.gwtportletbridge.client.general.api;

import java.util.Map;

public interface PortletGwtBridge {

  Map<String, String> getInputValues(String formId);

  String getModuleName();

  String decodeQueryString(String value);

  String encodeQueryString(String value);
}
