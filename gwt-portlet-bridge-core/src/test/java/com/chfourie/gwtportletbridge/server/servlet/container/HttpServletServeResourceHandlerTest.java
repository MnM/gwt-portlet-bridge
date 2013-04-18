package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletRequestImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletResponseImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.ServeResourceServletHandler;
import com.chfourie.gwtportletbridge.server.servlet.container.ServletContextImpl;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HttpServletServeResourceHandlerTest {
  private static final String SERVLET_NAME = "servlet name";
  private static final String METHOD_GET = "GET";

  private PortletContext portletContext;
  private HttpServletStub servlet;
  private ServeResourceServletHandler handler;


  @Before
  public void setUp() throws PortletException {
    portletContext = mock(PortletContext.class);
    servlet = new HttpServletStub();
    handler = new ServeResourceServletHandler(servlet, portletContext, SERVLET_NAME);
  }


  @Test
  public void testGetServletName() {
    assertEquals(SERVLET_NAME, servlet.getServletName());
  }


  @Test
  public void testGeneratedNamesUnique() throws PortletException {
    HttpServletStub servlet1 = new HttpServletStub();
    HttpServletStub servlet2 = new HttpServletStub();
    new ServeResourceServletHandler(servlet1, portletContext);
    new ServeResourceServletHandler(servlet2, portletContext);
    assertFalse(servlet1.getServletName().equals(servlet2.getServletName()));
  }


  @Test
  public void testServeResource() throws PortletException, IOException, ServletException {
    ResourceRequest request = mock(ResourceRequest.class);
    ResourceResponse response = mock(ResourceResponse.class);
    handler.serveResource(request, response);

    HttpServletRequestImpl servletRequest = (HttpServletRequestImpl) servlet.getServiceRequest();
    HttpServletResponseImpl servletResponse = (HttpServletResponseImpl)servlet.getServiceResponse();
    ServletContextImpl requestContext = (ServletContextImpl) servletRequest.getServletContext();


    assertSame(request, servletRequest.getResourceRequest());
    assertSame(response, servletResponse.getResourceResponse());
    assertSame(portletContext, requestContext.getPortletContext());
  }


  static class HttpServletStub extends HttpServlet {
    private ServletRequest serviceRequest;
    private ServletResponse serviceResponse;


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
      this.serviceRequest = req;
      this.serviceResponse = res;
    }


    public ServletRequest getServiceRequest() {
      return serviceRequest;
    }


    public ServletResponse getServiceResponse() {
      return serviceResponse;
    }
  }
}
