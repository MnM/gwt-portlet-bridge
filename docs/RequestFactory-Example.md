RPC Example
===========

In this example I will take a simple RequestFactory project and integrate the gwt-portlet-bridge into it.  The finished example can be found under the 'samples' folder in the source tree.


The Initial Application  
-------------------------------

We will start of with the following stock standard GWT application...

### RequestFactory.gwt.xml
```xml
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.0//EN"
    "http://google-web-toolkit.googlecode.com/svn/releases/2.0/distro-source/core/src/gwt-module.dtd">
<module rename-to="RequestFactory">

  <inherits name='com.google.gwt.user.User'/>
  <inherits name='com.google.web.bindery.requestfactory.RequestFactory' />
  
  <source path="client"/>

  <entry-point class='com.chfourie.samples.requestfactory.client.RequestFactoryClient'/>

</module>
```


### client/RequestFactoryClient.java
```java
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
```


### client/MessageRequest.java
```java
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
```


### client/MessageRequestFactory.java
```java
package com.chfourie.samples.requestfactory.client;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface MessageRequestFactory extends RequestFactory {
  MessageRequest messageRequest();
}
```


### server/MessageService.java
```java
package com.chfourie.samples.requestfactory.server;

public class MessageService {

  public String getMessage(String msg) {
    return "Client said: '" + msg + "'<br>Server answered: 'Hello, Client!'";
  }
}
```


### server/MessageServiceLocator.java
```java
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
```


### WEB-INF/web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
    "http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>
  <servlet>
    <servlet-name>requestFactoryServlet</servlet-name>
    <servlet-class>com.google.web.bindery.requestfactory.server.RequestFactoryServlet</servlet-class>
    <init-param>
      <param-name>symbolMapsDirectory</param-name>
      <param-value>WEB-INF/classes/symbolMaps/</param-value>
    </init-param>
  </servlet>

  <servlet-mapping>
    <servlet-name>requestFactoryServlet</servlet-name>
    <url-pattern>/gwtRequest</url-pattern>
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


### Step 2: Update RequestFactory.gwt.xml
Update RpcClient.gwt.xml to inherit _com.chfourie.gwtportletbridge.RequestFactory_.  
Please note that the inherit clause for the RequestFactory module should be positioned __after__ any other modules that might inherit from 
_com.google.gwt.core.Core_ or _com.google.web.bindery.requestfactory.RequestFactory_.


```xml
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.0//EN"
    "http://google-web-toolkit.googlecode.com/svn/releases/2.0/distro-source/core/src/gwt-module.dtd">
<module rename-to="RequestFactory">

  <inherits name='com.google.gwt.user.User'/>
  <inherits name='com.google.web.bindery.requestfactory.RequestFactory' />
  <inherits name='com.chfourie.gwtportletbridge.RequestFactory'/>
  
  <source path="client"/>

  <entry-point class='com.chfourie.samples.requestfactory.client.RequestFactoryClient'/>

</module>
```

### Step 3: Update your web.xml file.
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


### Step 4: Implement server/RequestFactoryPortlet.java

```java
package com.chfourie.samples.requestfactory.server;

import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

public class RequestFactoryPortlet extends GwtPortlet {

  @Override
  public void init() throws PortletException {
    addServeResourceHandler(new RequestFactoryServlet());
  }


  @Override
  protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    forward(request, response, "/WEB-INF/jsp/requestfactory.jsp");
  }
}
```

### Step 5: Implement WEB-INF/jsp/requestfactory.jsp

```jsp
<%@taglib prefix="gwt" uri="http://chfourie.com/gwt-portlet-bridge" %>

<gwt:module name="RequestFactory"/>

<div id="requestfactory-root"></div>
```

### Step 6: Implement WEB-INF/portlet.xml

```xml
<portlet-app xmlns="http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             id="EAppPortlet" version="2.0">

  <portlet>
    <portlet-name>RequestFactoryPortlet</portlet-name>
    <display-name>RequestFactoryPortlet</display-name>

    <portlet-class>
      com.chfourie.samples.requestfactory.server.RequestFactoryPortlet
    </portlet-class>

    <supports>
      <mime-type>text/html</mime-type>
      <portlet-mode>view</portlet-mode>
    </supports>

    <supported-locale>en</supported-locale>

    <portlet-info>
      <title>RequestFactoryPortlet</title>
    </portlet-info>
  </portlet>

</portlet-app>
```

### Step 7: Delete the original HTML
The original HTML file for the GWT application can be deleted since it is no longer required.