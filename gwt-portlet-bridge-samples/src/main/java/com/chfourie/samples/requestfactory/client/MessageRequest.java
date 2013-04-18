package com.chfourie.samples.requestfactory.client;

import com.chfourie.samples.requestfactory.server.MessageService;
import com.chfourie.samples.requestfactory.server.MessageServiceLocator;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(value = MessageService.class, locator = MessageServiceLocator.class)
public interface MessageRequest extends RequestContext {
  Request<String> getMessage(String msg);
}
