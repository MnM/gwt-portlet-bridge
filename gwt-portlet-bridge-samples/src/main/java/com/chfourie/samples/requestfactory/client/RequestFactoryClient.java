package com.chfourie.samples.requestfactory.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;

@SuppressWarnings({"GwtToHtmlReferences"})
public class RequestFactoryClient implements EntryPoint {
  private MessageRequestFactory requestFactory;


  public void onModuleLoad() {
    final EventBus eventBus = new SimpleEventBus();
    requestFactory = GWT.create(MessageRequestFactory.class);
    requestFactory.initialize(eventBus);

    final Button button = new Button("Click me");
    final Label label = new Label();
    final FlowPanel panel = new FlowPanel();
    panel.add(button);
    panel.add(label);

    button.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (label.getText().equals("")) {
          getMessageFromServer(label);
        } else {
          label.setText("");
        }
      }
    });

    RootPanel.get("requestfactory-root").add(panel);
  }


  private void getMessageFromServer(final Label label) {
    MessageRequest messageRequest = requestFactory.messageRequest();
    messageRequest.getMessage("Hello, Server!").fire(new Receiver<String>() {
      @Override
      public void onSuccess(String response) {
        label.getElement().setInnerHTML(response);
      }
    });
  }
}
