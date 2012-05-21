package com.chfourie.samples.rpc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

@SuppressWarnings({"GwtToHtmlReferences"})
public class RpcClient implements EntryPoint {
  private RpcClientServiceAsync rpcService;

  public void onModuleLoad() {
    rpcService = GWT.create(RpcClientService.class);

    final Button button = new Button("Click me");
    final Label label = new Label();
    final FlowPanel panel = new FlowPanel();
    panel.add(button);
    panel.add(label);

    button.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (label.getText().equals("")) {
          greetServer(label);
        } else {
          label.setText("");
        }
      }
    });

    RootPanel.get("rpc-client-root").add(panel);
  }


  private void greetServer(final Label label) {
    rpcService.getMessage("Hello, World!", new AsyncCallback<String>() {

      @Override
      public void onFailure(Throwable caught) {
        label.setText("Failed to receive answer from server!");
      }


      @Override
      public void onSuccess(String result) {
        label.getElement().setInnerHTML(result);
      }
    });
  }
}
