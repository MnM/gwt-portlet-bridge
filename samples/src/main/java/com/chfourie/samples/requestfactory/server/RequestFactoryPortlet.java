package com.chfourie.samples.requestfactory.server;

import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class RequestFactoryPortlet extends GwtPortlet {

  @Override
  public void init() throws PortletException {
    addServeResourceHandler(new RequestFactoryServlet());
  }


  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    forward(request, response, "/WEB-INF/jsp/requestfactory.jsp");
  }
}
