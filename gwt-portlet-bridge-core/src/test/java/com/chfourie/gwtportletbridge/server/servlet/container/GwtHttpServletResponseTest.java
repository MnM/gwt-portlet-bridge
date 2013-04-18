package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletResponseImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.ServletOutputStreamImpl;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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


  @Test
  public void testAddCookie() {
    response.addCookie(new Cookie("", ""));
    verifyZeroInteractions(resourceResponse);
  }


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
  public void testSetDateHeader() {
    response.setDateHeader("", 0L);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testAddDateHeader() {
    response.addDateHeader("", 0L);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetHeader() {
    response.setHeader("", "");
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testAddHeader() {
    response.addHeader("", "");
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetIntHeader() {
    response.setIntHeader("", 0);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testAddIntHeader() {
    response.addIntHeader("", 0);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetContentLength() {
    response.setContentLength(0);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetContentType() {
    response.setContentType("");
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetCharacterEncoding() {
    response.setCharacterEncoding("");
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetLocale() {
    response.setLocale(Locale.CANADA);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetStatusWithInt() {
    response.setStatus(0);
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testSetStatusWithIntAndString() {
    response.setStatus(0, "");
    verifyZeroInteractions(resourceResponse);
  }


  @Test
  public void testContainsHeader() {
    assertFalse(response.containsHeader("name"));
    verifyZeroInteractions(resourceResponse);
  }


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


  @Test
  public void testGetCharacterEncoding() {
    when(resourceResponse.getCharacterEncoding()).thenReturn("value");
    assertEquals("value", response.getCharacterEncoding());
  }


  @Test
  public void testGetBufferSize() {
    when(resourceResponse.getBufferSize()).thenReturn(7);
    assertEquals(7, response.getBufferSize());
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
  public void testGetContentType() {
    when(resourceResponse.getContentType()).thenReturn("value");
    assertEquals("value", response.getContentType());
  }


  @Test
  public void testSendRedirect() throws IOException {
    response.sendRedirect("location");
    verifyZeroInteractions(resourceResponse);
  }
}
