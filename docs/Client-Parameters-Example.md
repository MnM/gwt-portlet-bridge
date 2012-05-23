Client Parameters Example
====================

String parameters can be embedded in the parent page ad load time, saving you the expense of another RPC call to the server. The example below can be found under the 'samples' folder in the source tree.

### Step 1: Set the parameter value in your view method in the portlet

```java
package com.chfourie.samples.clientparams.server;

import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class ClientParamsPortlet extends GwtPortlet {

  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    ClientParameters.instance().setParameter("message", "Hello World!");
    forward(request, response, "/WEB-INF/jsp/clientparams.jsp");
  }

}
```


### Step 2: Read the parameter value in the client

```java
package com.chfourie.samples.clientparams.client;

import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class ClientParamsClient implements EntryPoint {

  public void onModuleLoad() {
    PortletClientContext ctx = GWT.create(PortletClientContext.class);
    RootPanel.get("clientparams-root").getElement().setInnerHTML("<h1>" + ctx.getParameter("message") + "</h1>");
  }
}
```