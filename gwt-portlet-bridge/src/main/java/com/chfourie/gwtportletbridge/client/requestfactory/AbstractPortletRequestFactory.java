package com.chfourie.gwtportletbridge.client.requestfactory;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.gwt.client.impl.AbstractClientRequestFactory;

public abstract class AbstractPortletRequestFactory extends AbstractClientRequestFactory {
  @Override
  public void initialize(EventBus eventBus) {
    initialize(eventBus, new PortletRequestTransport());
  }
}
