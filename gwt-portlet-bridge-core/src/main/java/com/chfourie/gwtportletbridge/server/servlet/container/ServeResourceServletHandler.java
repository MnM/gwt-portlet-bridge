package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.api.ServeResourceHandler;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class ServeResourceServletHandler implements ServeResourceHandler {
  private static long instanceNumber;
  private final ServletContextImpl servletContext;
  private final HttpServlet servlet;


  public ServeResourceServletHandler(HttpServlet servlet, PortletContext portletContext) throws PortletException {
    this(servlet, portletContext, generateUniqueName(servlet));
  }


  public ServeResourceServletHandler(HttpServlet servlet, PortletContext portletContext, String servletName)
      throws PortletException {

    this.servlet = servlet;

    try {
      servletContext = new ServletContextImpl(portletContext);
      ServletConfig cfg = new ServletConfigImpl(servletContext, servletName);
      servlet.init(cfg);
    } catch (ServletException e) {
      portletContext.log("Servlet could not be initialized - " + servlet.getClass().getName(), e);
      throw new PortletException(e);
    }
  }


  @Override
  public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
    try {
      servlet.service(asServletRequest(request), asServletResponse(response));
    } catch (ServletException e) {
      servletContext.log("An exception has been thrown by the servlet.", e);
      throw new PortletException(e);
    }
  }


  private static synchronized String generateUniqueName(HttpServlet servlet) {
    return new StringBuilder(ServeResourceServletHandler.class.getSimpleName())
        .append('@').append(instanceNumber++)
        .append('/').append(servlet.getClass().getName())
        .toString();
  }


  protected ServletResponse asServletResponse(ResourceResponse response) {
    return new HttpServletResponseImpl(response);
  }


  protected ServletRequest asServletRequest(ResourceRequest request) {
    return new HttpServletRequestImpl(request, servletContext);
  }

  protected ServletContextImpl getServletContext() {
    return servletContext;
  }
}
