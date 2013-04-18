package com.chfourie.gwtportletbridge.server.servlet.container.gwt;

import com.chfourie.gwtportletbridge.server.servlet.container.ServeResourceServletHandler;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

public class GwtServiceServeResourceHandler extends ServeResourceServletHandler {
  public GwtServiceServeResourceHandler(HttpServlet servlet, PortletContext portletContext) throws PortletException {
    super(servlet, portletContext);
  }


  public GwtServiceServeResourceHandler(HttpServlet servlet, PortletContext portletContext, String servletName) throws PortletException {
    super(servlet, portletContext, servletName);
  }


  @Override
  protected ServletRequest asServletRequest(ResourceRequest request) {
    return new GwtServiceServletRequest(request, getServletContext());
  }
}
