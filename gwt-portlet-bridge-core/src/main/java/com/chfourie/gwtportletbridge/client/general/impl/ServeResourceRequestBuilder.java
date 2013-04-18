package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.NativeGWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;

import java.util.HashMap;
import java.util.Map;

public class ServeResourceRequestBuilder extends RequestBuilder {
  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  public static final String REQUEST_DATA_PARAM_NAME = "payload";
  private final Map<String, String> fields = new HashMap<String, String>();


  public ServeResourceRequestBuilder(Method httpMethod, String url) {
    super(httpMethod, url);

    // NB: Must be called on super!!!
    super.setHeader(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded; charset=utf-8");
  }


  protected ServeResourceRequestBuilder(String httpMethod, String url) {
    super(httpMethod, url);

    // NB: Must be called on super!!!
    super.setHeader(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded; charset=utf-8");
  }


  @Override
  public String getHeader(String header) {
    return NativeGWT.decodeQueryString(fields.get(header));
  }


  @Override
  public void setHeader(String header, String value) {
    throwIfEmptyOrNull("header", header);
    throwIfEmptyOrNull("value", value);
    fields.put(header, NativeGWT.encodeQueryString(value));
  }


  @Override
  public String getRequestData() {
    return getHeader(REQUEST_DATA_PARAM_NAME);
  }


  @Override
  public void setRequestData(String requestData) {
    setHeader(REQUEST_DATA_PARAM_NAME, requestData);
  }


  @Override
  public Request send() throws RequestException {
    throwIfNull("callback", getCallback());

    // NB: Both the following statements MUST be called on super!!!
    super.setRequestData(compileRequestData());
    return super.send();
  }


  private String compileRequestData() {
    StringBuilder builder = new StringBuilder();

    for (String key : fields.keySet()) {
      if (builder.length() > 0) builder.append('&');
      builder.append(key).append('=').append(fields.get(key));
    }

    return builder.toString();
  }


  private void throwIfEmptyOrNull(String name, String value) {
    assert (name != null);
    assert (name.trim().length() != 0);

    throwIfNull(name, value);

    if (0 == value.trim().length()) {
      throw new IllegalArgumentException(name + " cannot be empty");
    }
  }

  private void throwIfNull(String name, Object value) {
    if (null == value) {
      throw new NullPointerException(name + " cannot be null");
    }
  }
}
