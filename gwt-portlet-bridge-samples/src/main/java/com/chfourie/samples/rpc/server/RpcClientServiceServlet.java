package com.chfourie.samples.rpc.server;

import com.chfourie.samples.rpc.client.RpcClientService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RpcClientServiceServlet extends RemoteServiceServlet implements RpcClientService {
  public String getMessage(String msg) {
    return "Client said: \"" + msg + "\"<br>Server answered: \"Hello, Client!\"";
  }
}