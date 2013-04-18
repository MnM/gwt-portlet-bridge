package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.HttpSessionImpl;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings({"unchecked"})
public class GwtHttpSessionTest {
  private ServletContext servletContext;
  private PortletSession portletSession;
  private HttpSessionImpl session;


  @Before
  public void before() {
    servletContext = mock(ServletContext.class);
    portletSession = mock(PortletSession.class);
    session = new HttpSessionImpl(portletSession, servletContext);
  }


  @Test
  @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfHttpSession() {
    assertTrue(session instanceof HttpSession);
  }


  @Test
  public void testGetCreationTime() {
    when(portletSession.getCreationTime()).thenReturn(123L);
    assertEquals(123L, session.getCreationTime());
  }


  @Test
  public void testGetId() {
    when(portletSession.getId()).thenReturn("Some ID");
    assertEquals("Some ID", session.getId());
  }


  @Test
  public void testGetLastAccessedTime() {
    when(portletSession.getLastAccessedTime()).thenReturn(123L);
    assertEquals(123L, session.getLastAccessedTime());
  }


  @Test
  public void testGetServletContext() {
    assertSame(servletContext, session.getServletContext());
  }


  @Test
  public void testSetMaxInactiveInterval() {
    portletSession.setMaxInactiveInterval(10); // Shouldn't throw an exception
  }


  @Test
  public void testGetMaxInactiveInterval() {
    when(portletSession.getMaxInactiveInterval()).thenReturn(13);
    assertEquals(13, session.getMaxInactiveInterval());
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetSessionContext() {
    session.getSessionContext();
  }


  @Test
  public void testGetAttribute() {
    Object value = new Object();
    when(portletSession.getAttribute("name")).thenReturn(value);
    assertSame(value, session.getAttribute("name"));
  }


  @Test
  public void testGetValue() {
    Object value = new Object();
    when(portletSession.getAttribute("name")).thenReturn(value);
    assertSame(value, session.getValue("name"));
  }


  @Test
  public void testGetAttributeNames() {
    Enumeration value = mock(Enumeration.class);
    when(portletSession.getAttributeNames()).thenReturn(value);
    assertSame(value, session.getAttributeNames());
  }


  @Test
  public void testGetValueNames() {
    Enumeration values = new Enumeration<String>() {
      String[] values = new String[] {"0", "1", "2"};
      int pos = 0;

      @Override
      public boolean hasMoreElements() {
        return pos < 3;
      }


      @Override
      public String nextElement() {
        return values[pos++];
      }
    };

    when(portletSession.getAttributeNames()).thenReturn(values);
    String[] result = session.getValueNames();
    assertEquals(3, result.length);
  }


  @Test
  public void testSetAttribute() {
    Object value = new Object();
    session.setAttribute("name", value);
    verify(portletSession).setAttribute("name", value);
  }


  @Test
  public void testPutValue() {
    Object value = new Object();
    session.putValue("name", value);
    verify(portletSession).setAttribute("name", value);
  }


  @Test
  public void testRemoveAttribute() {
    session.removeAttribute("name");
    verify(portletSession).removeAttribute("name");
  }


  @Test
  public void testRemoveValue() {
    session.removeValue("name");
    verify(portletSession).removeAttribute("name");
  }


  @Test
  public void testInvalidate() {
    session.invalidate();
    verify(portletSession).invalidate();
  }


  @Test
  public void testIsNewTrue() {
    when(portletSession.isNew()).thenReturn(true);
    assertTrue(session.isNew());
  }


  @Test
  public void testIsNewFalse() {
    when(portletSession.isNew()).thenReturn(false);
    assertFalse(session.isNew());
  }
}
