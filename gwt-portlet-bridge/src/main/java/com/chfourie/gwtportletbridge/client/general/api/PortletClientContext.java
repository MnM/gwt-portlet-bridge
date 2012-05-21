package com.chfourie.gwtportletbridge.client.general.api;

import com.google.gwt.http.client.RequestBuilder;

import java.util.Collection;

public interface PortletClientContext {
  String getPortletName();
  String getPortletMode();

  String getParameter(String name);
  boolean hasParameter(String name);
  Collection<String> getParameterNames();

  String getServeResourceUrl(Class cls);
  boolean hasServeResourceUrl(Class cls);
  String getServeResourceUrl(String name);
  boolean hasServeResourceUrl(String name);

  RequestBuilder getActionBuilder(String actionName);
  String getActionUrl(String actionName);
  boolean hasAction(String name);
  Collection<String> getActionNames();
}
