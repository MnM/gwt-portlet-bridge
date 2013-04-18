package com.chfourie.samples.rpc.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcClientServiceAsync {
  void getMessage(String msg, AsyncCallback<String> async);
}
