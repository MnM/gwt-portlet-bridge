package com.chfourie.gwtportletbridge.client.requestfactory;

import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.chfourie.gwtportletbridge.client.general.impl.ServeResourceRequestBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.BaseProxy;

public class PortletRequestTransport extends DefaultRequestTransport {

  public PortletRequestTransport() {
    PortletClientContext ctx = GWT.create(PortletClientContext.class);
    setRequestUrl(ctx.getServeResourceUrl(BaseProxy.class.getName()));
  }


  @Override
  protected RequestBuilder createRequestBuilder() {
    return new ServeResourceRequestBuilder(RequestBuilder.POST, getRequestUrl());
  }
}
