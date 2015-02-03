package com.chfourie.gwtportletbridge.server.servlet.container;

import javax.portlet.ResourceResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

/**
 * Response from within the serveResource method, as specified by
 * PORTLETSPEC_20 PLT.19.4.5.
 */
public class HttpServletResponseImpl implements HttpServletResponse {
  private ResourceResponse response;


  public HttpServletResponseImpl(ResourceResponse response) {
    this.response = response;
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void addCookie(Cookie cookie) {
    response.addProperty(cookie);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * false
   */
  @Override
  public boolean containsHeader(String name) {
    return false;
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public String encodeURL(String url) {
    return response.encodeURL(url);
  }



  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * null
   */
  @Override
  public String encodeRedirectURL(String url) {
    return null;
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public String encodeUrl(String url) {
    return response.encodeURL(url);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * null
   */
  @Override
  public String encodeRedirectUrl(String url) {
    return null;
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * no-op
   */
  @Override
  public void sendError(int sc, String msg) throws IOException {
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * no-op
   */
  @Override
  public void sendError(int sc) throws IOException {
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   * no-op
   */
  @Override
  public void sendRedirect(String location) throws IOException {
  }


  /*
   * PORTLETSPEC_20 PLT.19.3.5
   * The following methods of the HttpServletResponse must perform no operations: ..., setDateHeader,...
   */
  @Override
  public void setDateHeader(String name, long date) {
  }


  /*
   * PORTLETSPEC_20 PLT.19.3.5
   * The following methods of the HttpServletResponse must perform no operations: ..., addDateHeader,...
   */
  @Override
  public void addDateHeader(String name, long date) {
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setHeader(String name, String value) {
    response.setProperty(name, value);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void addHeader(String name, String value) {
    response.addProperty(name, value);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setIntHeader(String name, int value) {
    response.setProperty(name, Integer.toString(value));
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void addIntHeader(String name, int value) {
    response.addProperty(name, Integer.toString(value));
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setStatus(int sc) {
    response.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(sc));
  }


  /*
   * PORTLETSPEC_20 PLT.19.3.5
   * The following methods of the HttpServletResponse must perform no operations: ..., setStatus,...
   */
  @Override
  public void setStatus(int sc, String sm) {
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public String getCharacterEncoding() {
    return response.getCharacterEncoding();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */

  @Override
  public String getContentType() {
    return response.getContentType();
  }


  /*
   * PORTLETSPEC_20 PLT.19.3.5
   * The following methods of the HttpServletResponse must be equivalent to the methods of the ResourceResponse of
   * similar name: ..., getOutputStream, ...
   */
  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    OutputStream out = response.getPortletOutputStream();
    return (out == null) ? null : new ServletOutputStreamImpl(out);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public PrintWriter getWriter() throws IOException {
    return response.getWriter();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setCharacterEncoding(String charset) {
    response.setCharacterEncoding(charset);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setContentLength(int len) {
    response.setContentLength(len);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setContentType(String type) {
      response.setContentType(type);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setBufferSize(int size) {
    response.setBufferSize(size);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public int getBufferSize() {
    return response.getBufferSize();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void flushBuffer() throws IOException {
    response.flushBuffer();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void resetBuffer() {
    response.resetBuffer();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public boolean isCommitted() {
    return response.isCommitted();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void reset() {
    response.reset();
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public void setLocale(Locale loc) {
      response.setLocale(loc);
  }


  /*
   * PORTLETSPEC_20 PLT.19.4.6
   */
  @Override
  public Locale getLocale() {
    return response.getLocale();
  }


  public ResourceResponse getResourceResponse() {
    return response;
  }

  @Override
  public int getStatus() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getHeader(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<String> getHeaders(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<String> getHeaderNames() {
    throw new UnsupportedOperationException();
  }
}
