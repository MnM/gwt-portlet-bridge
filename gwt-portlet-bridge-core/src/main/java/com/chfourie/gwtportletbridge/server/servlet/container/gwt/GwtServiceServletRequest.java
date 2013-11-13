package com.chfourie.gwtportletbridge.server.servlet.container.gwt;

import com.chfourie.gwtportletbridge.server.servlet.container.HttpServletRequestImpl;
import com.chfourie.gwtportletbridge.server.servlet.container.ServletInputStreamImpl;

import javax.portlet.ResourceRequest;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static com.chfourie.gwtportletbridge.client.general.impl.ServeResourceRequestBuilder.CONTENT_TYPE_HEADER;
import static com.chfourie.gwtportletbridge.client.general.impl.ServeResourceRequestBuilder.REQUEST_DATA_PARAM_NAME;

public class GwtServiceServletRequest extends HttpServletRequestImpl {
  private static final String CHARSET_UTF8 = "UTF-8";
  private final List<String> parameterNames;


  public GwtServiceServletRequest(ResourceRequest resourceRequest, ServletContext servletContext) {
    super(resourceRequest, servletContext);
    parameterNames = collectParameterNames(resourceRequest);
  }


  /*
   * GWT RPC
   * We post data to the server as 'application/x-www-form-urlencoded'.
   * Information that the standard GWT RPC mechanism send as header values, we
   * send as fields in the message body.  The payload is sent as a field named
   * 'payload'.
   */
  @Override
  public String getHeader(String name) {
    return parameterNames.contains(name)
        ? getResourceRequest().getParameter(name)
        : super.getHeader(name);
  }


  /*
   * GWT RPC
   * We post data to the server as 'application/x-www-form-urlencoded'.
   * Information that the standard GWT RPC mechanism send as header values, we
   * send as fields in the message body. The payload is sent as a field named
   * 'payload'.
   */
  @Override
  public ServletInputStream getInputStream() throws IOException {
    String data = getParameter(REQUEST_DATA_PARAM_NAME);
    return new ServletInputStreamImpl(new ByteArrayInputStream(data.getBytes(CHARSET_UTF8)));
  }


  @Override
  public String getContentType() {
    return getHeader(CONTENT_TYPE_HEADER);
  }


  private List<String> collectParameterNames(ResourceRequest resourceRequest) {
    List<String> result = new ArrayList<String>();
    Enumeration<String> parameterNames = resourceRequest.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String name = parameterNames.nextElement();
      if (!REQUEST_DATA_PARAM_NAME.equals(name)) result.add(name);
    }

    return Collections.unmodifiableList(result);
  }
}
