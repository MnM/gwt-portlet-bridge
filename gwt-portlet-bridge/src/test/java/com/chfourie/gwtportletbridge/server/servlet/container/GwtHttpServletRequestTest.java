package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletRequestImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.HttpSessionImpl;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Locale.CANADA;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GwtHttpServletRequestTest {
  private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
  private ServletContext servletContext;
  private ResourceRequest resourceRequest;
  private HttpServletRequestImpl request;


  @Before
  public void before() {
    servletContext = mock(ServletContext.class);
    resourceRequest = mock(ResourceRequest.class);
    request = new HttpServletRequestImpl(resourceRequest, servletContext);
  }


  @Test
  @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfHttpServletRequest() {
    assertTrue(request instanceof HttpServletRequest);
  }


  @Test
  public void testGetAuthType() {
    when(resourceRequest.getAuthType()).thenReturn("expected");
    assertEquals("expected", request.getAuthType());
  }


  @Test
  public void testGetCookies() {
    Cookie[] expected = {mock(Cookie.class)};
    when(resourceRequest.getCookies()).thenReturn(expected);
    assertSame(expected, request.getCookies());
  }


  @Test
  public void testGetDateHeaderWithUnspecifiedHeader() {
    when(resourceRequest.getProperty("name")).thenReturn(null);
    assertEquals(-1L, request.getDateHeader("name"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetDateHeaderWithIllegalValue() {
    when(resourceRequest.getProperty("name")).thenReturn("Not a date");
    request.getDateHeader("name");
  }


  @Test
  public void testGetDateHeaderWithValidHeader() throws ParseException {
    String[] validFormats = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEEEE, dd-MMM-yy HH:mm:ss zzz",
        "EEE MMMM d HH:mm:ss yyyy"};
    Calendar cal = Calendar.getInstance(GMT, Locale.US);
    Date d = cal.getTime();

    for (String formatString : validFormats) {
      DateFormat format = new SimpleDateFormat(formatString);
      format.setCalendar(cal);
      long expected = format.parse(format.format(d)).getTime();
      when(resourceRequest.getProperty("name")).thenReturn(format.format(d));
      assertEquals(expected, request.getDateHeader("name"));
    }
  }


  @Test
  public void testGetHeader() {
    when(resourceRequest.getProperty("name")).thenReturn("value");
    assertEquals("value", request.getHeader("name"));
  }


  @Test
  @SuppressWarnings({"unchecked"})
  public void testGetHeaders() {
    Enumeration<String> expected = mock(Enumeration.class);
    when(resourceRequest.getProperties("name")).thenReturn(expected);
    assertSame(expected, request.getHeaders("name"));
  }


  @SuppressWarnings({"unchecked"})
  @Test
  public void testGetHeaderNames() {
    Enumeration expected = mock(Enumeration.class);
    when(resourceRequest.getPropertyNames()).thenReturn(expected);
    assertSame(expected, request.getHeaderNames());
  }


  @Test
  public void testGetValidIntHeader() {
    when(resourceRequest.getProperty("name")).thenReturn("7");
    assertEquals(7, request.getIntHeader("name"));
  }


  @Test(expected = NumberFormatException.class)
  public void testGetInvalidIntHeader() {
    when(resourceRequest.getProperty("name")).thenReturn("x");
    request.getIntHeader("name");
  }


  @Test
  public void testGetMissingIntHeader() {
    assertEquals(-1, request.getIntHeader("name"));
  }


  @Test
  public void testGetMethod() {
    when(resourceRequest.getMethod()).thenReturn("expected");
    assertEquals("expected", request.getMethod());
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetPathInfo() {
    request.getPathInfo();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetPathTranslated() {   
    request.getPathTranslated();
  }


  @Test
  public void testGetContextPath() {
    when(resourceRequest.getContextPath()).thenReturn("expected");
    assertEquals("expected", request.getContextPath());
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetQueryString() {     
    request.getQueryString();
  }


  @Test
  public void testGetRemoteUser() {
    when(resourceRequest.getRemoteUser()).thenReturn("expected");
    assertEquals("expected", request.getRemoteUser());
  }


  @Test
  public void testIsUserInRoleWhenTrue() {
    when(resourceRequest.isUserInRole("role")).thenReturn(true);
    assertTrue(request.isUserInRole("role"));
  }


  @Test
  public void testIsUserInRoleWhenFalse() {
    when(resourceRequest.isUserInRole("role")).thenReturn(false);
    assertFalse(request.isUserInRole("role"));
  }


  @Test
  public void testGetUserPrincipal() {
    Principal expected = mock(Principal.class);
    when(resourceRequest.getUserPrincipal()).thenReturn(expected);
    assertSame(expected, request.getUserPrincipal());
  }


  @Test
  public void testGetRequestedSessionId() {
    when(resourceRequest.getRequestedSessionId()).thenReturn("expected");
    assertEquals("expected", request.getRequestedSessionId());
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetRequestURI() {                
    request.getRequestURI();
  }


  @Test
  public void testGetRequestURL() {
    assertNull(request.getRequestURL());
    verifyZeroInteractions(resourceRequest);
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetServletPath() {
    request.getServletPath();
  }


  @Test
  public void testGetSession() {
    PortletSession portletSession = mock(PortletSession.class);
    when(resourceRequest.getPortletSession()).thenReturn(portletSession);
    HttpSessionImpl session = (HttpSessionImpl) request.getSession();
    assertSame(servletContext, session.getServletContext());
    assertSame(portletSession, session.getPortletSession());
  }


  @Test
  public void testGetSessionWithCreateTrue() {
    PortletSession portletSession = mock(PortletSession.class);
    when(resourceRequest.getPortletSession(true)).thenReturn(portletSession);
    HttpSessionImpl session = (HttpSessionImpl) request.getSession(true);
    assertSame(servletContext, session.getServletContext());
    assertSame(portletSession, session.getPortletSession());
  }


  @Test
  public void testIsRequestedSessionIdValid() {
    when(resourceRequest.isRequestedSessionIdValid()).thenReturn(true);
    assertTrue(request.isRequestedSessionIdValid());
    verify(resourceRequest).isRequestedSessionIdValid();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testIsRequestedSessionIdFromCookie() {
    request.isRequestedSessionIdFromCookie();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testIsRequestedSessionIdFromURL() {
    request.isRequestedSessionIdFromURL();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testIsRequestedSessionIdFromUrl() {
    request.isRequestedSessionIdFromUrl();
  }


  @Test
  public void testGetAttribute() {
    when(resourceRequest.getAttribute("name")).thenReturn("expected");
    assertEquals("expected", request.getAttribute("name"));
  }


  @Test
  @SuppressWarnings({"unchecked"})
  public void testGetAttributeNames() {
    Enumeration<String> expected = mock(Enumeration.class);
    when(resourceRequest.getAttributeNames()).thenReturn(expected);
    assertSame(expected, request.getAttributeNames());
  }


  @Test
  public void testGetCharacterEncoding() {
    when(resourceRequest.getCharacterEncoding()).thenReturn("expected");
    assertEquals("expected", request.getCharacterEncoding());
  }


  @Test
  public void testSetCharacterEncoding() throws UnsupportedEncodingException {
    request.setCharacterEncoding("expected");
    verify(resourceRequest).setCharacterEncoding("expected");
  }


  @Test
  public void testGetContentLength() {
    when(resourceRequest.getContentLength()).thenReturn(5);
    assertEquals(5, request.getContentLength());
  }


  @Test
  public void testGetContentType() {
    when(resourceRequest.getContentType()).thenReturn("expected");
    assertEquals("expected", request.getContentType());
  }


  @Test
  public void testGetInputStream() throws IOException {
    InputStream expected = mock(InputStream.class);
    when(resourceRequest.getPortletInputStream()).thenReturn(expected);
    request.getInputStream().mark(10);
    verify(expected).mark(10);
  }


  @Test
  public void testGetParameter() {
    when(resourceRequest.getParameter("name")).thenReturn("value");
    assertEquals("value", request.getParameter("name"));
  }


  @Test
  @SuppressWarnings({"unchecked"})
  public void testGetParameterNames() {
    Enumeration<String> expected = mock(Enumeration.class);
    when(resourceRequest.getParameterNames()).thenReturn(expected);
    assertSame(expected, request.getParameterNames());
  }


  @Test
  public void testGetParameterValues() {
    String[] expected = new String[]{"expected"};
    when(resourceRequest.getParameterValues("name")).thenReturn(expected);
    assertSame(expected, request.getParameterValues("name"));
  }


  @SuppressWarnings({"unchecked"})
  @Test
  public void testGetParameterMap() {
    Map expected = mock(Map.class);
    when(resourceRequest.getParameterMap()).thenReturn(expected);
    assertSame(expected, request.getParameterMap());
  }


  @Test
  public void testGetProtocol() {
    assertEquals("HTTP/1.1", request.getProtocol());
  }


  @Test
  public void testGetScheme() {
    when(resourceRequest.getScheme()).thenReturn("expected");
    assertEquals("expected", request.getScheme());
  }


  @Test
  public void testGetServerName() {
    when(resourceRequest.getServerName()).thenReturn("expected");
    assertEquals("expected", request.getServerName());
  }


  @Test
  public void testGetServerPort() {
    when(resourceRequest.getServerPort()).thenReturn(4);
    assertEquals(4, request.getServerPort());
  }


  @Test
  public void testGetReader() throws IOException {
    BufferedReader expected = mock(BufferedReader.class);
    when(resourceRequest.getReader()).thenReturn(expected);
    assertSame(expected, request.getReader());
  }


  @Test
  public void testGetRemoteAddr() {
    assertNull(request.getRemoteAddr());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetRemoteHost() {
    assertNull(request.getRemoteHost());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testSetAttribute() {
    request.setAttribute("name", "value");
    verify(resourceRequest).setAttribute("name", "value");
  }


  @Test
  public void testRemoveAttribute() {
    request.removeAttribute("name");
    verify(resourceRequest).removeAttribute("name");
  }


  @Test
  public void testGetLocale() {
    when(resourceRequest.getLocale()).thenReturn(CANADA);
    assertSame(CANADA, request.getLocale());
  }


  @SuppressWarnings({"unchecked"})
  @Test
  public void testGetLocales() {
    Enumeration expected = mock(Enumeration.class);
    when(resourceRequest.getLocales()).thenReturn(expected);
    assertSame(expected, request.getLocales());
  }


  @Test
  public void testIsSecure() {
    when(resourceRequest.isSecure()).thenReturn(true);
    assertTrue(request.isSecure());
    verify(resourceRequest).isSecure();
  }


  @Test(expected = UnsupportedOperationException.class)
  public void testGetRequestDispatcher() {
    request.getRequestDispatcher("value");
  }


  @Test
  public void testGetRealPath() {
    assertNull(request.getRealPath("some path"));
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetRemotePort() {
    assertEquals(0, request.getRemotePort());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetLocalName() {
    assertNull(request.getLocalName());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetLocalAddr() {
    assertNull(request.getLocalAddr());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetLocalPort() {
    assertEquals(0, request.getLocalPort());
    verifyZeroInteractions(resourceRequest);
  }


  @Test
  public void testGetResourceRequest() {
    assertSame(resourceRequest, request.getResourceRequest());
  }
}
