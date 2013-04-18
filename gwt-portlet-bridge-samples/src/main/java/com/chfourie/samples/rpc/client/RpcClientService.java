package com.chfourie.samples.rpc.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RpcClientService extends RemoteService {
  String getMessage(String msg);
}
