package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.RequestDispatcherImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletRequestImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletResponseImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class GwtRequestDispatcherTest {
  private PortletRequestDispatcher portletDispatcher;
  private HttpServletRequestImpl gwtRequest;
  private HttpServletResponseImpl gwtResponse;
  private ResourceRequest resourceRequest;
  private ResourceResponse resourceResponse;
  private RequestDispatcherImpl dispatcher;

  @Before
  public void setUp() {
    gwtRequest = mock(HttpServletRequestImpl.class);
    gwtResponse = mock(HttpServletResponseImpl.class);
    resourceRequest = mock(ResourceRequest.class);
    resourceResponse = mock(ResourceResponse.class);
    when(gwtRequest.getResourceRequest()).thenReturn(resourceRequest);
    when(gwtResponse.getResourceResponse()).thenReturn(resourceResponse);
    portletDispatcher = mock(PortletRequestDispatcher.class);
    dispatcher = new RequestDispatcherImpl(portletDispatcher);
  }


  @Test
  public void testForward() throws IOException, ServletException, PortletException {
    dispatcher.forward(gwtRequest, gwtResponse);
    verify(portletDispatcher).forward(resourceRequest, resourceResponse);
  }


  @Test(expected = IOException.class)
  public void testForwardThrowsIOException() throws IOException, ServletException, PortletException {
    doThrow(new IOException()).when(portletDispatcher).forward(resourceRequest, resourceResponse);
    dispatcher.forward(gwtRequest, gwtResponse);
  }


  @Test(expected = ServletException.class)
  public void testForwardThrowsPortletException() throws PortletException, IOException, ServletException {
    doThrow(new PortletException()).when(portletDispatcher).forward(resourceRequest, resourceResponse);
    dispatcher.forward(gwtRequest, gwtResponse);
  }


  @Test(expected = ClassCastException.class)
  public void testForwardWithIllegalRequestType() throws IOException, ServletException {
    ServletRequest request = mock(ServletRequest.class);
    dispatcher.forward(request, gwtResponse);
  }


  @Test(expected = ClassCastException.class)
  public void testForwardWithIllegalResponseType() throws IOException, ServletException {
    ServletResponse response = mock(ServletResponse.class);
    dispatcher.forward(gwtRequest, response);
  }


  @Test
  public void testInclude() throws IOException, ServletException, PortletException {
    dispatcher.include(gwtRequest, gwtResponse);
    verify(portletDispatcher).include(resourceRequest, resourceResponse);
  }


  @Test(expected = IOException.class)
  public void testIncludeThrowsIOException() throws IOException, ServletException, PortletException {
    doThrow(new IOException()).when(portletDispatcher).include(resourceRequest, resourceResponse);
    dispatcher.include(gwtRequest, gwtResponse);
  }


  @Test(expected = ServletException.class)
  public void testIncludeThrowsPortletExeption() throws PortletException, IOException, ServletException {
    doThrow(new PortletException()).when(portletDispatcher).include(resourceRequest, resourceResponse);
    dispatcher.include(gwtRequest, gwtResponse);
  }


  @Test(expected = ClassCastException.class)
  public void testIncludeWithIllegalRequestType() throws IOException, ServletException {
    ServletRequest request = mock(ServletRequest.class);
    dispatcher.include(request, gwtResponse);
  }


  @Test(expected = ClassCastException.class)
  public void testIncludeWithIllegalResponseType() throws IOException, ServletException {
    ServletResponse response = mock(ServletResponse.class);
    dispatcher.include(gwtRequest, response);
  }


  @Test
  public void testGetPortletDispatcher() {
    Assert.assertSame(portletDispatcher, dispatcher.getPortletRequestDispatcher());
  }
}
