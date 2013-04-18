package com.chfourie.samples.clientparams.server;

import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class ClientParamsPortlet extends GwtPortlet {

  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    ClientParameters.instance().setParameter("message", "Hello World!");
    forward(request, response, "/WEB-INF/jsp/clientparams.jsp");
  }

}
