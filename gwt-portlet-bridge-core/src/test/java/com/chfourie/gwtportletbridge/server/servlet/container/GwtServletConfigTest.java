package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.ServletConfigImpl;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import java.util.Enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GwtServletConfigTest {
  private final String SERVLET_NAME = "My Servlet";
  private ServletContext servletContext;
  private ServletConfigImpl servletConfig;

  @Before
  public void setUp() {
    servletContext = mock(ServletContext.class);
    servletConfig = new ServletConfigImpl(servletContext, SERVLET_NAME);
  }


  @Test @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfServletConfig() {
    assertTrue(servletConfig instanceof ServletConfig);
  }


  @Test
  public void testGetServletName() {
    assertEquals(SERVLET_NAME, servletConfig.getServletName());
  }


  @Test
  public void testGetServletContext() {
    assertSame(servletContext, servletConfig.getServletContext());
  }


  @Test
  public void testGetInitParameter() {
    when(servletContext.getInitParameter("name")).thenReturn("value");
    assertEquals("value", servletConfig.getInitParameter("name"));
  }


  @Test @SuppressWarnings({"unchecked"})
  public void testGetInitParameterNames() {
    Enumeration<String> value = mock(Enumeration.class);
    when(servletContext.getInitParameterNames()).thenReturn(value);
    assertSame(value, servletConfig.getInitParameterNames());
  }
}
