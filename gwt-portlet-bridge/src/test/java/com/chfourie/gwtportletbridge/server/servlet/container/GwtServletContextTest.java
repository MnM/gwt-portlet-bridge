package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.RequestDispatcherImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.ServletContextImpl;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.servlet.ServletException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GwtServletContextTest {
  private PortletContext portletContext;
  private ServletContextImpl context;

  @Before
  public void setUp() {
    portletContext = mock(PortletContext.class);
    context = new ServletContextImpl(portletContext);
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetContext() {
    context.getContext("/somepath");
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetContextPath() {
    context.getContextPath();
  }


  @Test
  public void testGetMajorVersion() {
    assertEquals(2, context.getMajorVersion());
  }


  @Test
  public void testGetMinorVersion() {
    assertEquals(4, context.getMinorVersion());
  }


  @Test
  public void testGetMimeType() {
    when(portletContext.getMimeType("file")).thenReturn("value");
    assertEquals("value", context.getMimeType("file"));
  }


  @Test @SuppressWarnings({"unchecked"})
  public void testGetResourcePaths() {
    Set result = mock(Set.class);
    when(portletContext.getResourcePaths("/path")).thenReturn(result);
    assertSame(result, context.getResourcePaths("/path"));
  }


  @Test
  public void testGetResource() throws MalformedURLException {
    URL result = new URL("http://www.google.com");
    when(portletContext.getResource("/path")).thenReturn(result);
    assertSame(result, context.getResource("/path"));
  }


  @Test(expected = MalformedURLException.class)
  public void testGetResourceThrowsException() throws MalformedURLException {
    when(portletContext.getResource("/path")).thenThrow(new MalformedURLException());
    context.getResource("/path");
  }


  @Test
  public void testGetResourceAsString() {
    InputStream result = mock(InputStream.class);
    when(portletContext.getResourceAsStream("/path")).thenReturn(result);
    assertSame(result, context.getResourceAsStream("/path"));
  }


  @Test
  public void testGetRequestDispatcher() {
    PortletRequestDispatcher dispatcher = mock(PortletRequestDispatcher.class);
    when(portletContext.getRequestDispatcher("/name")).thenReturn(dispatcher);
    RequestDispatcherImpl gwtDispatcher = (RequestDispatcherImpl) context.getRequestDispatcher("/name");
    assertSame(dispatcher, gwtDispatcher.getPortletRequestDispatcher());
  }


  @Test
  public void testGetRequestDispatcherNull() {
    when(portletContext.getRequestDispatcher("/name")).thenReturn(null);
    assertNull(context.getRequestDispatcher("/name"));
  }


  @Test
  public void testGetNamedDispatcher() {
    PortletRequestDispatcher dispatcher = mock(PortletRequestDispatcher.class);
    when(portletContext.getNamedDispatcher("name")).thenReturn(dispatcher);
    RequestDispatcherImpl gwtDispatcher = (RequestDispatcherImpl) context.getNamedDispatcher("name");
    assertSame(dispatcher, gwtDispatcher.getPortletRequestDispatcher());
  }


  @Test
  public void testGetNamedDispatcherNull() {
    when(portletContext.getNamedDispatcher("name")).thenReturn(null);
    assertNull(context.getNamedDispatcher("name"));
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetServlet() throws ServletException {
    context.getServlet("name");
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetServlets() {
    context.getServlets();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetServletNames() {
    context.getServletNames();
  }


  @Test
  public void testLogMessage() {
    context.log("message");
    verify(portletContext).log("message");
  }


  @Test
  public void testLogExceptionAndMessage() {
    Exception exception = mock(Exception.class);
    context.log(exception, "message");
    verify(portletContext).log("message", exception);
  }


  @Test
  public void testLogMessageAndThrowable() {
    Throwable throwable = mock(Throwable.class);
    context.log("message", throwable);
    verify(portletContext).log("message", throwable);
  }


  @Test
  public void testGetRealPath() {
    when(portletContext.getRealPath("name")).thenReturn("value");
    assertEquals("value", context.getRealPath("name"));
  }


  @Test
  public void testGetServerInfo() {
    when(portletContext.getServerInfo()).thenReturn("result");
    assertEquals("result", context.getServerInfo());
  }


  @Test
  public void testGetInitParameter() {
    when(portletContext.getInitParameter("name")).thenReturn("value");
    assertEquals("value", context.getInitParameter("name"));
  }


  @Test @SuppressWarnings({"unchecked"})
  public void testGetInitParameterNames() {
    Enumeration result = mock(Enumeration.class);
    when(portletContext.getInitParameterNames()).thenReturn(result);
    assertSame(result, context.getInitParameterNames());
  }


  @Test
  public void testGetAttribute() {
    Object result = new Object();
    when(portletContext.getAttribute("name")).thenReturn(result);
    assertSame(result, context.getAttribute("name"));
  }


  @Test @SuppressWarnings({"unchecked"})
  public void testGetAttributeNames() {
    Enumeration result = mock(Enumeration.class);
    when(portletContext.getAttributeNames()).thenReturn(result);
    assertSame(result, context.getAttributeNames());
  }


  @Test
  public void testSetAttribute() {
    Object value = new Object();
    context.setAttribute("name", value);
    verify(portletContext).setAttribute("name", value);
  }


  @Test
  public void testRemoveAttribute() {
    context.removeAttribute("name");
    verify(portletContext).removeAttribute("name");
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetServletContextName() {
    context.getServletContextName();
  }
}
