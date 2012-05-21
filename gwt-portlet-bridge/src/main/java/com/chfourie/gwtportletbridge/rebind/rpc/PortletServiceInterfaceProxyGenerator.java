package com.chfourie.gwtportletbridge.rebind.rpc;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

public class PortletServiceInterfaceProxyGenerator extends ServiceInterfaceProxyGenerator {
  @Override
  protected ProxyCreator createProxyCreator(JClassType remoteService) {
    return new PortletRemoteServiceProxyCreator(remoteService);
  }
}
