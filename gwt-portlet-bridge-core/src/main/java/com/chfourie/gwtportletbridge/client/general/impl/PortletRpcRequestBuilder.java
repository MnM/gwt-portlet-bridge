package com.chfourie.gwtportletbridge.client.general.impl;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

public class PortletRpcRequestBuilder extends RpcRequestBuilder {

  @Override
  protected RequestBuilder doCreate(String serviceEntryPoint) {
    return new ServeResourceRequestBuilder(RequestBuilder.POST, serviceEntryPoint);
  }
}
