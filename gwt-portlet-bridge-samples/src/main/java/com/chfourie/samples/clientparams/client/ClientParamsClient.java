package com.chfourie.samples.clientparams.client;

import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class ClientParamsClient implements EntryPoint {

  public void onModuleLoad() {
    PortletClientContext ctx = GWT.create(PortletClientContext.class);
    RootPanel.get("clientparams-root").getElement().setInnerHTML("<h1>" + ctx.getParameter("message") + "</h1>");
  }
}
