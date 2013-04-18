package com.chfourie.gwtportletbridge.server.portlet;

import javax.portlet.*;

public class RenderContext {
  private static final ThreadLocal<RenderContext> instances = new ThreadLocal<RenderContext>();

  private final GwtPortlet portlet;
  private final RenderRequest request;
  private final RenderResponse response;


  public static void attach(GwtPortlet portlet, RenderRequest request, RenderResponse response) {
    instances.set(new RenderContext(portlet, request, response));
  }


  public static void release() {
    instances.remove();
  }


  public static RenderContext instance() {
    return instances.get();
  }


  public RenderContext(GwtPortlet portlet, RenderRequest request, RenderResponse response) {
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


  public RenderRequest getRenderRequest() {
    return request;
  }


  public RenderResponse getRenderResponse() {
    return response;
  }


  public PortletSession getPortletSession(boolean create) {
    return request.getPortletSession(create);
  }
}
