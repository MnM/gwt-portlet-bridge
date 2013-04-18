package com.chfourie.gwtportletbridge.server.portlet;

import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;
import com.chfourie.gwtportletbridge.server.portlet.ServeResourceRequestContext;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServeResourceRequestContextTest {
  private GwtPortlet portlet;
  private ResourceRequest request;
  private ResourceResponse response;


  @Before
  public void setUp() {
    portlet = mock(GwtPortlet.class);
    request = mock(ResourceRequest.class);
    response = mock(ResourceResponse.class);
    ServeResourceRequestContext.attach(portlet, request, response);
  }


  @Test
  public void testSameThreadSameInstance() {
    assertSame(ServeResourceRequestContext.instance(), ServeResourceRequestContext.instance());
  }


  @Test
  public void testDifferentThreadDifferentInstance() throws InterruptedException {
    ServeResourceRequestContext.release();

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        ServeResourceRequestContext.attach(portlet, request, response);
      }
    });

    t.start();
    t.join();

    assertNull(ServeResourceRequestContext.instance());
  }


  @Test
  public void testGetPortlet() throws Throwable {
    assertSame(portlet, ServeResourceRequestContext.instance().getPortlet());
  }


  @Test
  public void testGetPortletConfig() throws Throwable {
    PortletConfig config = mock(PortletConfig.class);
    when(portlet.getPortletConfig()).thenReturn(config);
    assertSame(config, ServeResourceRequestContext.instance().getPortletConfig());
  }


  @Test
  public void testGetPortletContext() throws Throwable {
    PortletContext portletContext = mock(PortletContext.class);
    when(portlet.getPortletContext()).thenReturn(portletContext);
    assertSame(portletContext, ServeResourceRequestContext.instance().getPortletContext());
  }


  @Test
  public void testGetResourceRequest() throws Throwable {
    assertSame(request, ServeResourceRequestContext.instance().getResourceRequest());
  }


  @Test
  public void testGetResourceResponse() throws Throwable {
    assertSame(response, ServeResourceRequestContext.instance().getResourceResponse());
  }


  @Test
  public void testRelease() throws Throwable {
    ServeResourceRequestContext.release();
    assertNull(ServeResourceRequestContext.instance());
  }
}
