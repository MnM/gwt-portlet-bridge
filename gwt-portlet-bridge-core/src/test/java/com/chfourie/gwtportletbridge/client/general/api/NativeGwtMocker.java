package com.chfourie.gwtportletbridge.client.general.api;

public abstract class NativeGwtMocker {
  public static void mockWith(PortletGwtBridge mock) {
    NativeGWT.mockWith(mock);
  }


  public static void reset() {
    NativeGWT.reset();
  }
}
