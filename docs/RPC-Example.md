RPC Example
===========

In this example I will take a simple RPC project and integrate the gwt-portlet-bridge into it.  The finished example can be found under the 'samples' folder in the source tree.


The Initial Application  
-------------------------------

We will start of with the following stock standard GWT application...

### RpcClient.gwt.xml
```xml
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.0//EN"
    "http://google-web-toolkit.googlecode.com/svn/releases/2.0/distro-source/core/src/gwt-module.dtd">
<module rename-to="RpcClient">
  <inherits name='com.google.gwt.user.User'/>
  
  <source path="client"/>

  <entry-point class='com.chfourie.samples.rpc.client.RpcClient'/>
</module>
```


### client/RpcClient.java
```java
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
```


### client/RpcClientService.java
```java
package com.chfourie.samples.rpc.client;

import com.google.gwt.user.client.rpc.RemoteService;

@RemoteServiceRelativePath("clientService")
public interface RpcClientService extends RemoteService {
  String getMessage(String msg);
}
```


### client/RpcClientServiceAsync.java
```java
package com.chfourie.samples.rpc.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcClientServiceAsync {
  void getMessage(String msg, AsyncCallback<String> async);
}
```


### server/RpcClientServiceServlet.java
```java
package com.chfourie.samples.rpc.server;

import com.chfourie.samples.rpc.client.RpcClientService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RpcClientServiceServlet extends RemoteServiceServlet implements RpcClientService {
  public String getMessage(String msg) {
    return "Client said: '" + msg + "'<br>Server answered: 'Hello, Client!'";
  }
}
```


### WEB-INF/web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"  
     "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
  <servlet>
    <servlet-name>rpcClientServiceServlet</servlet-name>
    <servlet-class>com.chfourie.samples.rpc.server.RpcClientServiceServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>rpcClientServiceServlet</servlet-name>
    <url-pattern>/RpcClient/rpcClientServiceServlet</url-pattern>
  </servlet-mapping>
</web-app>
```


### HTML Page
```html
<html>
  ...
</html>
```

Converting the GWT Application to a Portlet
-----------------------------------------

### Step 1: Include gwt-portlet-bridge-x.x.x.jar in your project
In including the gwt-portlet-bridge-x.x.x.jar file in your project, please ensure that the following requirements are met:
  * The file must be available to your application at runtime.  This typically means that it must end up in the WEB-INF/lib folder of your artifact.
  * The file must be included in the class-path for compilation of the java files.
  * The file must be included in the class-path for the gwt compilation process.
  * The file must be included in the class-path when running your GWT application in dev-mode.


### Step 2: Update RpcClient.gwt.xml
Update RpcClient.gwt.xml to inherit _com.chfourie.gwtportletbridge.GwtPortletBridge_.  
Please note that the inherit clause for the _GwtPortletBridge_ module should be positioned __after__ any other modules that might inherit from _com.google.gwt.core.Core_.


```xml
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.0//EN"
    "http://google-web-toolkit.googlecode.com/svn/releases/2.0/distro-source/core/src/gwt-module.dtd">
<module rename-to="RpcClient">

  <inherits name='com.google.gwt.user.User'/>
  <inherits name='com.chfourie.gwtportletbridge.GwtPortletBridge'/>
  
  <source path="client"/>

  <entry-point class='com.chfourie.samples.rpc.client.RpcClient'/>

</module>
```


### Step 3: Update RpcClientService.java
Remove the @RemoteServiceRelativePath annotation from the service interface .

```java
package com.chfourie.samples.rpc.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RpcClientService extends RemoteService {
  String getMessage(String msg);
}

```

### Step 4: Update your web.xml file.
Make the following changes to web.xml:
  * Remove the servlet definition.
  * Include the gwt-portlet-bridge tag library

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
  <taglib>
    <taglib-uri>http://chfourie.com/gwt-portlet-bridge</taglib-uri>
    <taglib-location>WEB-INF/lib/gwt-portlet-bridge-0.1.0.jar</taglib-location>
  </taglib>
</web-app>
```


### Step 5: Implement server/RpcPortlet.java

```java
package com.chfourie.samples.rpc.server;

import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class RpcPortlet extends GwtPortlet {

  @Override
  public void init() throws PortletException {
    // Note that since we are creating the servlet ourselves, we can use an IOC framework like
    // Spring or Google Guice to create the servlet, enabling us to easily inject other
    // services into the servlet.
    addServeResourceHandler(new RpcClientServiceServlet());
  }


  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    forward(request, response, "/WEB-INF/jsp/rpc.jsp");
  }
}
```

### Step 6: Implement WEB-INF/jsp/rpc.jsp

```jsp
<%@taglib prefix="gwt" uri="http://chfourie.com/gwt-portlet-bridge" %>

<gwt:module name="RpcClient"/>

<div id="rpc-client-root"></div>
```

### Step 7: Implement WEB-INF/portlet.xml

```xml
<portlet-app xmlns="http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             id="EAppPortlet" version="2.0">

  <portlet>
    <portlet-name>RpcPortlet</portlet-name>
    <display-name>RpcPortlet</display-name>

    <portlet-class>
      com.chfourie.samples.rpc.server.RpcPortlet
    </portlet-class>

    <supports>
      <mime-type>text/html</mime-type>
      <portlet-mode>view</portlet-mode>
    </supports>

    <supported-locale>en</supported-locale>

    <portlet-info>
      <title>RpcPortlet</title>
    </portlet-info>
  </portlet>

</portlet-app>
```

### Step 8: Delete the original HTML
The original HTML file for the GWT application can be deleted since it is no longer required.