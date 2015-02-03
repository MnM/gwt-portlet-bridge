package com.chfourie.gwtportletbridge.server.servlet.container;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

public class ServletContextImpl implements ServletContext {
  private PortletContext portletContext;


  public ServletContextImpl(PortletContext portletContext) {
    this.portletContext = portletContext;
  }


  @Override
  public ServletContext getContext(String uripath) {
    throw new UnsupportedOperationException();
  }


  @Override
  public String getContextPath() {
    throw new UnsupportedOperationException();
  }


  @Override
  public int getMajorVersion() {
    return 2;
  }


  @Override
  public int getMinorVersion() {
    return 4;
  }


  @Override
  public String getMimeType(String file) {
    return portletContext.getMimeType(file);
  }


  @Override
  public Set<String> getResourcePaths(String path) {
    return portletContext.getResourcePaths(path);
  }


  @Override
  public URL getResource(String path) throws MalformedURLException {
    return portletContext.getResource(path);
  }


  @Override
  public InputStream getResourceAsStream(String path) {
    return portletContext.getResourceAsStream(path);
  }


  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    PortletRequestDispatcher portletDispatcher = portletContext.getRequestDispatcher(path);
    return portletDispatcher == null ? null : new RequestDispatcherImpl(portletDispatcher);
  }


  @Override
  public RequestDispatcher getNamedDispatcher(String name) {
    PortletRequestDispatcher portletDispatcher = portletContext.getNamedDispatcher(name);
    return portletDispatcher == null ? null : new RequestDispatcherImpl(portletDispatcher);
  }


  @Override
  public Servlet getServlet(String name) throws ServletException {
    throw new UnsupportedOperationException();
  }


  @Override
  public Enumeration<Servlet> getServlets() {
    throw new UnsupportedOperationException();
  }


  @Override
  public Enumeration<String> getServletNames() {
    throw new UnsupportedOperationException();
  }


  @Override
  public void log(String msg) {
    portletContext.log(msg);
  }


  @Override
  public void log(Exception exception, String msg) {
    portletContext.log(msg, exception);
  }


  @Override
  public void log(String message, Throwable throwable) {
    portletContext.log(message, throwable);
  }


  @Override
  public String getRealPath(String path) {
    return portletContext.getRealPath(path);
  }


  @Override
  public String getServerInfo() {
    return portletContext.getServerInfo();
  }


  @Override
  public String getInitParameter(String name) {
    return portletContext.getInitParameter(name);
  }


  @Override
  public Enumeration<String> getInitParameterNames() {
    return portletContext.getInitParameterNames();
  }


  @Override
  public Object getAttribute(String name) {
    return portletContext.getAttribute(name);
  }


  @Override
  public Enumeration<String> getAttributeNames() {
    return portletContext.getAttributeNames();
  }


  @Override
  public void setAttribute(String name, Object object) {
    portletContext.setAttribute(name, object);
  }


  @Override
  public void removeAttribute(String name) {
    portletContext.removeAttribute(name);
  }


  @Override
  public String getServletContextName() {
    throw new UnsupportedOperationException();
  }


  public PortletContext getPortletContext() {
    return portletContext;
  }

  @Override
  public int getEffectiveMajorVersion() {
    return getMajorVersion();
  }

  @Override
  public int getEffectiveMinorVersion() {
    return getMinorVersion();
  }

  @Override
  public boolean setInitParameter(String s, String s1) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ServletRegistration.Dynamic addServlet(String s, String s1) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Servlet> T createServlet(Class<T> aClass) throws ServletException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ServletRegistration getServletRegistration(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, ? extends ServletRegistration> getServletRegistrations() {
    throw new UnsupportedOperationException();
  }

  @Override
  public FilterRegistration.Dynamic addFilter(String s, String s1) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
    throw new UnsupportedOperationException();
  }

  @Override
  public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Filter> T createFilter(Class<T> aClass) throws ServletException {
    throw new UnsupportedOperationException();
  }

  @Override
  public FilterRegistration getFilterRegistration(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SessionCookieConfig getSessionCookieConfig() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addListener(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends EventListener> void addListener(T t) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addListener(Class<? extends EventListener> aClass) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends EventListener> T createListener(Class<T> aClass) throws ServletException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JspConfigDescriptor getJspConfigDescriptor() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClassLoader getClassLoader() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void declareRoles(String... strings) {
    throw new UnsupportedOperationException();
  }
}
