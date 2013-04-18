package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.NativeGWT;
import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.google.gwt.http.client.RequestBuilder;

import java.util.*;

import static com.chfourie.gwtportletbridge.client.general.api.ClientParameterConstants.*;

public class PortletClientContextImpl implements PortletClientContext {
  private String portletName = "";
  private String portletMode = "";
  private Map<String, String> parameters;
  private Map<String, String> actionUrls;
  private Map<String, String> serveResourceUrls;


  public PortletClientContextImpl() {
    parameters = new HashMap<String, String>();
    actionUrls = new HashMap<String, String>();
    serveResourceUrls = new HashMap<String, String>();

    Map<String, String> rawItems = NativeGWT.getInputValues(getContainerId());

    for (String rawName : rawItems.keySet()) {
      String name = NativeGWT.decodeQueryString(rawName);
      String rawValue = rawItems.get(rawName);
      String value = (rawValue.startsWith("E")) ? NativeGWT.decodeQueryString(rawValue.substring(1))
          : rawValue.substring(1);

      if (name.equals(PORTLET_NAME_PARAM)) {
        portletName = value;
      } else if (name.equals(PORTLET_MODE_PARAM)) {
        portletMode = value;
      } else if (name.startsWith(ACTION_PREFIX)) {
        actionUrls.put(name.substring(ACTION_PREFIX.length()), value);
      } else if (name.startsWith(SERVE_RESOURCE_PREFIX)) {
        serveResourceUrls.put(name.substring(SERVE_RESOURCE_PREFIX.length()), value);
      } else {
        parameters.put(name, value);
      }
    }
  }


  @Override
  public RequestBuilder getActionBuilder(String name) {
    return new RequestBuilder(RequestBuilder.POST, getActionUrl(name));
  }


  @Override
  public String getActionUrl(String name) {
    if (!hasAction(name)) throw new IllegalArgumentException("Action URL not declared - " + name);
    return actionUrls.get(name);
  }


  @Override
  public boolean hasAction(String name) {
    return actionUrls.containsKey(name);
  }


  @Override
  public String getPortletName() {
    return portletName;
  }


  @Override
  public String getPortletMode() {
    return portletMode;
  }


  @Override
  public String getParameter(String name) {
    String key = NativeGWT.encodeQueryString(name);
    return parameters.containsKey(key) ? parameters.get(key) : "";
  }


  @Override
  public boolean hasParameter(String name) {
    String key = NativeGWT.encodeQueryString(name);
    return parameters.containsKey(key);
  }


  @Override
  public String getServeResourceUrl(Class cls) {
    return getServeResourceUrl(cls.getName());
  }


  @Override
  public boolean hasServeResourceUrl(Class cls) {
    return hasServeResourceUrl(cls.getName());
  }


  @Override
  public String getServeResourceUrl(String name) {
    if (!hasServeResourceUrl(name))
      throw new IllegalArgumentException("ServeResourceUrl not declared for - " + name);
    return serveResourceUrls.get(name);
  }


  @Override
  public boolean hasServeResourceUrl(String name) {
    return serveResourceUrls.containsKey(name);
  }


  @Override
  public Collection<String> getParameterNames() {
    ArrayList<String> result = new ArrayList<String>(parameters.size());
    
    for (String key : parameters.keySet()) {
      result.add(NativeGWT.decodeQueryString(key));
    }
    
    return result;
  }


  @Override
  public Collection<String> getActionNames() {
    return Collections.unmodifiableCollection(actionUrls.keySet());
  }


  private static String getContainerId() {
    return "gwt:" + NativeGWT.getModuleName() + ":parameters";
  }
}
