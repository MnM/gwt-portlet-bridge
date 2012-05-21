package com.chfourie.gwtportletbridge.server.portlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class ServeResourceRequestContext {
  private static final ThreadLocal<ServeResourceRequestContext> instances = new ThreadLocal<ServeResourceRequestContext>();

  private final GwtPortlet portlet;
  private final ResourceRequest request;
  private final ResourceResponse response;


  public static void attach(GwtPortlet portlet, ResourceRequest request, ResourceResponse response) {
    instances.set(new ServeResourceRequestContext(portlet, request, response));
  }


  public static void release() {
    instances.remove();
  }


  public static ServeResourceRequestContext instance() {
    return instances.get();
  }


  private ServeResourceRequestContext(GwtPortlet portlet, ResourceRequest request, ResourceResponse response) {
    this.portlet = portlet;
    this.request = request;
    this.response = response;
  }


  public GwtPortlet getPortlet() {
    return portlet;
  }


  public PortletConfig getPortletConfig() {
    return getPortlet().getPortletConfig();
  }


  public PortletContext getPortletContext() {
    return getPortlet().getPortletContext();
  }


  public ResourceRequest getResourceRequest() {
    return request;
  }


  public ResourceResponse getResourceResponse() {
    return response;
  }
}
