package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.Serializer;

public abstract class PortletRemoteServiceProxy extends RemoteServiceProxy {
  protected PortletRemoteServiceProxy(String moduleBaseURL, String remoteServiceRelativePath, String serializationPolicyName, Serializer serializer) {
    super(moduleBaseURL, remoteServiceRelativePath, serializationPolicyName, serializer);
    setRpcRequestBuilder(new PortletRpcRequestBuilder());
    configureServiceEntryPoint(remoteServiceRelativePath);
  }


  protected void configureServiceEntryPoint(String remoteServiceRelativePath) {
    PortletClientContext ctx = GWT.create(PortletClientContext.class);

    if (remoteServiceRelativePath != null && ctx.hasServeResourceUrl(remoteServiceRelativePath)) {
      setServiceEntryPoint(ctx.getServeResourceUrl(remoteServiceRelativePath));
    } else if (ctx.hasServeResourceUrl(getServiceInterfaceName())) {
      setServiceEntryPoint(ctx.getServeResourceUrl(getServiceInterfaceName()));
    } else if (ctx.hasServeResourceUrl(RemoteService.class.getName())) {
      setServiceEntryPoint(ctx.getServeResourceUrl(RemoteService.class.getName()));
    }
  }


  protected abstract String getServiceInterfaceName();
}
