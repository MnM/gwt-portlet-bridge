package com.chfourie.samples.requestfactory.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class MessageServiceLocator implements ServiceLocator {
  @Override
  public Object getInstance(Class<?> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
