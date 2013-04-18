package com.chfourie.samples.requestfactory.client;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface MessageRequestFactory extends RequestFactory {
  MessageRequest messageRequest();
}
