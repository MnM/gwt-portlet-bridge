package com.chfourie.gwtportletbridge.server.portlet;

import com.chfourie.gwtportletbridge.server.api.ServeResourceHandler;
import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.servlet.container.ServeResourceServletHandler;
import com.chfourie.gwtportletbridge.server.servlet.container.gwt.GwtServiceServeResourceHandler;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.BaseProxy;

import javax.portlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import static com.chfourie.gwtportletbridge.client.general.api.ClientParameterConstants.*;

@SuppressWarnings({"UnusedDeclaration"})
public abstract class GwtPortlet extends GenericPortlet {
  private transient Map<String, ServeResourceHandler> serveResourceHandlerMap = new HashMap<String, ServeResourceHandler>();
  private transient List<String> actionNames;


  @Override
  public final void init(PortletConfig config) throws PortletException {
    super.init(config);
    collectActionNames();
  }


  @Override
  public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
    try {
      ServeResourceRequestContext.attach(this, request, response);
      if (serveResourceHandlerMap.containsKey(request.getResourceID())) {
        serveResourceHandlerMap.get(request.getResourceID()).serveResource(request, response);
      } else {
        super.serveResource(request, response);
      }
    } finally {
      ServeResourceRequestContext.release();
    }
  }


  @Override
  public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException {
    try {
      RenderContext.attach(this, request, response);
      setupClientParameters(request, response);
      super.render(request, response);
    } finally {
      ClientParameters.release();
      RenderContext.release();
    }
  }


  @Override
  public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
    try {
      ClientParameters.bind(response);
      super.processAction(request, response);
    } finally {
      ClientParameters.release();
    }
  }


  @Override
  public void processEvent(EventRequest request, EventResponse response) throws PortletException, IOException {
    try {
      ClientParameters.bind(response);
      super.processEvent(request, response);
    } finally {
      ClientParameters.release();
    }
  }


  protected final void setupClientParameters(RenderRequest request, RenderResponse response) {
    ClientParameters.bind(request);
    setParameter(PORTLET_NAME_PARAM, getPortletName(), true);
    setParameter(PORTLET_MODE_PARAM, request.getPortletMode().toString(), true);
    setupActionUrlParameters(response);
    setupServeResourceHandlerParameters(response);
  }


  protected final void setParameter(String name, String value, boolean urlEncode) {
    ClientParameters.instance().setParameter(name, value, urlEncode);
  }


  protected final void addServeResourceHandler(RemoteServiceServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());

    for (Class cls : service.getClass().getInterfaces()) {
      if (RemoteService.class.isAssignableFrom(cls)) addServeResourceHandler(cls, handler);
    }
  }


  protected final void addServeResourceHandler(String resourceId, RemoteServiceServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());
    addServeResourceHandler(resourceId, handler);
  }


  protected final void addServeResourceHandler(Class resourceId, RemoteServiceServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());
    addServeResourceHandler(resourceId, handler);
  }


  protected final void addServeResourceHandler(RequestFactoryServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());
    addServeResourceHandler(BaseProxy.class, handler);
  }


  protected final void addServeResourceHandler(String resourceId, RequestFactoryServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());
    addServeResourceHandler(resourceId, handler);
  }


  protected final void addServeResourceHandler(Class resourceId, RequestFactoryServlet service) throws PortletException {
    GwtServiceServeResourceHandler handler = new GwtServiceServeResourceHandler(service, getPortletContext());
    addServeResourceHandler(resourceId, handler);
  }


  protected final void addServeResourceHandler(Class resourceId, HttpServlet servlet) throws PortletException {
    addServeResourceHandler(resourceId.getName(), servlet);
  }


  protected final void addServeResourceHandler(String resourceId, HttpServlet servlet) throws PortletException {
    if (RemoteService.class.isAssignableFrom(servlet.getClass()) || RequestFactoryServlet.class.isAssignableFrom(servlet.getClass())) {
      addServeResourceHandler(resourceId, new GwtServiceServeResourceHandler(servlet, getPortletContext(), resourceId));
    } else {
      addServeResourceHandler(resourceId, new ServeResourceServletHandler(servlet, getPortletContext(), resourceId));
    }
  }


  protected void addServeResourceHandler(String resourceId, ServeResourceHandler handler) {
    serveResourceHandlerMap.put(resourceId, handler);
  }


  protected final void addServeResourceHandler(Class resourceId, ServeResourceHandler handler) {
    addServeResourceHandler(resourceId.getName(), handler);
  }

  protected void include(RenderRequest request, RenderResponse response, String view) throws PortletException, IOException {
    getPortletContext().getRequestDispatcher(view).include(request, response);
  }

  protected void forward(RenderRequest request, RenderResponse response, String view) throws PortletException, IOException {
    getPortletConfig().getPortletContext().getRequestDispatcher(view).forward(request, response);
  }


  private void setupServeResourceHandlerParameters(RenderResponse response) {
    for (String serveResourceHandlerName : serveResourceHandlerMap.keySet()) {
      ResourceURL url = response.createResourceURL();
      url.setResourceID(serveResourceHandlerName);
      setParameter(SERVE_RESOURCE_PREFIX + serveResourceHandlerName, url.toString(), false);
    }
  }


  private void setupActionUrlParameters(RenderResponse response) {
    for (String actionName : actionNames) {
      PortletURL url = response.createActionURL();
      url.setParameter(ActionRequest.ACTION_NAME, actionName);
      setParameter(ACTION_PREFIX + actionName, url.toString(), false);
    }
  }


  private void collectActionNames() {
    List<String> names = new ArrayList<String>();

    for (Method method : this.getClass().getMethods()) {
      ProcessAction action = method.getAnnotation(ProcessAction.class);
      if (action != null) names.add(action.name());
    }

    actionNames = Collections.unmodifiableList(names);
  }
}
