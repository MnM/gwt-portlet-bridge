package com.chfourie.samples.requestfactory.server;

public class MessageService {

  public String getMessage(String msg) {
    return "Client said: \"" + msg + "\"<br>Server answered: \"Hello, Client!\"";
  }
}
