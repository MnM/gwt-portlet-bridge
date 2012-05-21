package com.chfourie.samples.rpc.server;

import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class RpcPortlet extends GwtPortlet {

  @Override
  public void init() throws PortletException {
    // Note that since we are creating the servlet ourselves, we can use an IOC framework like
    // Spring or Google Guice to create the servlet, enabling us to easily inject other
    // services into the servlet.
    addServeResourceHandler(new RpcClientServiceServlet());
  }


  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    forward(request, response, "/WEB-INF/jsp/rpc.jsp");
  }
}
