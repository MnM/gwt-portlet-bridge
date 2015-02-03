package com.chfourie.gwtportletbridge.server.servlet.container;

import org.junit.Before;
import org.junit.Test;

import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class GwtHttpServletResponseTest {
  private ResourceResponse resourceResponse;
  private HttpServletResponseImpl response;


  @Before
  public void before() {
    resourceResponse = mock(ResourceResponse.class);
    response = new HttpServletResponseImpl(resourceResponse);
  }

  @Test
  @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfHttpServletResponse() {
    assertTrue(response instanceof HttpServletResponse);
  }

  @Test
  public void testGetResourceResponse() {
    assertSame(resourceResponse, response.getResourceResponse());
  }


  /* If the portlet want to set a response status code it should do this via
     setProperty with the key ResourceResponse.HTTP_STATUS_CODE. */
  @Test
  public void testSetStatusWithInt() {
    response.setStatus(0);
    verify(resourceResponse).setProperty(ResourceResponse.HTTP_STATUS_CODE, "" + 0);
  }

  @Test
  public void testSetStatusWithIntAndString() {
    response.setStatus(0, "");
    verifyZeroInteractions(resourceResponse);
  }

  @Test
  public void testGetContentType() {
    when(resourceResponse.getContentType()).thenReturn("value");
    assertEquals("value", response.getContentType());
  }

/*
  The following methods of the HttpServletResponse must return null:
    encodeRedirectURL and encodeRedirectUrl.
*/

  @Test
  public void testEncodeRedirectURL() {
    assertNull(response.encodeRedirectURL("name"));
    verifyZeroInteractions(resourceResponse);
  }

  @Test
  public void testEncodeRedirectUrl() {
    assertNull(response.encodeRedirectUrl("name"));
    verifyZeroInteractions(resourceResponse);
  }


/*
  The following methods of the HttpServletResponse must be equivalent to the
  methods of the ResourceResponse of similar name:
    getCharacterEncoding, setContentType, setBufferSize,flushBuffer,
    resetBuffer, reset, getBufferSize, isCommitted, getOutputStream, getWriter,
    getLocale, encodeURL and encodeUrl.
*/

  @Test
  public void testGetCharacterEncoding() {
    when(resourceResponse.getCharacterEncoding()).thenReturn("value");
    assertEquals("value", response.getCharacterEncoding());
  }

  @Test
  public void testSetContentType() {
    response.setContentType("value");
    verify(resourceResponse).setContentType("value");
  }

  @Test
  public void testSetBufferSize() {
    response.setBufferSize(7);
    verify(resourceResponse).setBufferSize(7);
  }

  @Test
  public void testFlushBuffer() throws IOException {
    response.flushBuffer();
    verify(resourceResponse).flushBuffer();
  }

  @Test
  public void testResetBuffer() {
    response.resetBuffer();
    verify(resourceResponse).resetBuffer();
  }

  @Test
  public void testReset() {
    response.reset();
    verify(resourceResponse).reset();
  }

  @Test
  public void testGetBufferSize() {
    when(resourceResponse.getBufferSize()).thenReturn(7);
    assertEquals(7, response.getBufferSize());
  }

  @Test
  public void testIsCommittedTrue() {
    when(resourceResponse.isCommitted()).thenReturn(true);
    assertTrue(response.isCommitted());
  }

  @Test
  public void testIsCommittedFalse() {
    when(resourceResponse.isCommitted()).thenReturn(false);
    assertFalse(response.isCommitted());
  }

  @Test
  public void testGetOutputStream() throws IOException {
    OutputStream out = mock(OutputStream.class);
    when(resourceResponse.getPortletOutputStream()).thenReturn(out);
    ServletOutputStreamImpl gwtOut = (ServletOutputStreamImpl) response.getOutputStream();
    assertSame(out, gwtOut.getOutputStream());
  }

  @Test
  public void testGetOutputStreamNull() throws IOException {
    when(resourceResponse.getPortletOutputStream()).thenReturn(null);
    assertNull(response.getOutputStream());
  }

  @Test
  public void testGetWriter() throws IOException {
    PrintWriter writer = mock(PrintWriter.class);
    when(resourceResponse.getWriter()).thenReturn(writer);
    assertSame(writer, response.getWriter());
  }

  @Test
  public void testGetLocale() {
    when(resourceResponse.getLocale()).thenReturn(Locale.CHINA);
    assertSame(Locale.CHINA, response.getLocale());
  }

  @Test
  public void testEncodeURL() {
    when(resourceResponse.encodeURL("name")).thenReturn("value");
    assertEquals("value", response.encodeURL("name"));
  }

  @Test
  public void testEncodeUrl() {
    when(resourceResponse.encodeURL("name")).thenReturn("value");
    assertEquals("value", response.encodeUrl("name"));
  }

/*
  The following methods of the HttpServletResponse must be equivalent to
  the method defined in the Servlet Specification for HttpServletResponse:
    setContentLength,setCharacterEncoding, and setLocale.
*/

  @Test
  public void testSetContentLength() {
    response.setContentLength(0);
    verify(resourceResponse).setContentLength(0);
  }

  @Test
  public void testSetCharacterEncoding() {
    response.setCharacterEncoding("");
    verify(resourceResponse).setCharacterEncoding("");
  }

  @Test
  public void testSetLocale() {
    response.setLocale(Locale.CANADA);
    verify(resourceResponse).setLocale(Locale.CANADA);
  }

/*
  The following methods of the HttpServletResponse must perform no operations:
    sendError, sendRedirect.

  The containsHeader method of the HttpServletResponse must return false.
*/

  @Test
  public void testSendErrorWithIntAndString() throws IOException {
    response.sendError(1, "");
    verifyZeroInteractions(resourceResponse);
  }

  @Test
  public void testSendErrorWithInt() throws IOException {
    response.sendError(1);
    verifyZeroInteractions(resourceResponse);
  }

  @Test
  public void testSendRedirect() throws IOException {
    response.sendRedirect("location");
    verifyZeroInteractions(resourceResponse);
  }

  @Test
  public void testContainsHeader() {
    assertFalse(response.containsHeader("name"));
    verifyZeroInteractions(resourceResponse);
  }

/*
  The following methods of the HttpServletRequest must be based on the
  properties provided by the setProperties/addProperties method of the
  ResourceResponse interface:
    addCookie, setDateHeader, addDateHeader, setHeader, addHeader,setIntHeader,
    addIntHeader.
*/

  @Test
  public void testAddCookie() {
    final Cookie c = new Cookie("test", "");
    response.addCookie(c);
    verify(resourceResponse).addProperty(c);
  }

  @Test
  public void testAddHeader() {
    response.addHeader("", "");
    verify(resourceResponse).addProperty("", "");
  }

  @Test
  public void testSetHeader() {
    response.setHeader("", "");
    verify(resourceResponse).setProperty("", "");
  }

  @Test
  public void testSetIntHeader() {
    response.setIntHeader("", 0);
    verify(resourceResponse).setProperty("", "" + 0);
  }

  @Test
  public void testAddIntHeader() {
    response.addIntHeader("", 0);
    verify(resourceResponse).addProperty("", "" + 0);
  }

// Todo: The spec says we should interact with the resourceResponse but I have
// no idea how so I'm leaving these commented out for now!
//
//  @Test
//  public void testSetDateHeader() {
//    response.setDateHeader("", 0L);
//    verifyZeroInteractions(resourceResponse);
//  }
//
//  @Test
//  public void testAddDateHeader() {
//    response.addDateHeader("", 0L);
//    verifyZeroInteractions(resourceResponse);
//  }
}
