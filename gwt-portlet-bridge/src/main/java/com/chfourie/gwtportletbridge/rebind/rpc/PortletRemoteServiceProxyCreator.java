package com.chfourie.gwtportletbridge.rebind.rpc;

import com.chfourie.gwtportletbridge.client.general.impl.PortletRemoteServiceProxy;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;

import java.util.Map;

public class PortletRemoteServiceProxyCreator extends ProxyCreator {

  public PortletRemoteServiceProxyCreator(JClassType serviceIntf) {
    super(serviceIntf);
  }


  @Override
  protected Class<? extends RemoteServiceProxy> getProxySupertype() {
    return PortletRemoteServiceProxy.class;
  }


  @Override
  protected void generateProxyMethods(SourceWriter w, SerializableTypeOracle serializableTypeOracle, TypeOracle typeOracle, Map<JMethod, JMethod> syncMethToAsyncMethMap) {
    w.println();
    w.println("protected String getServiceInterfaceName() {");
    w.indent();
    w.println("return \"" + serviceIntf.getQualifiedSourceName() + "\";");
    w.outdent();
    w.println("}");

    super.generateProxyMethods(w, serializableTypeOracle, typeOracle, syncMethToAsyncMethMap);
  }
}
