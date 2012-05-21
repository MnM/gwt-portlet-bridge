package com.chfourie.gwtportletbridge.server.clientparams;

import com.chfourie.gwtportletbridge.client.general.api.ClientParameterConstants;

import javax.portlet.RenderRequest;
import javax.portlet.StateAwareResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.chfourie.gwtportletbridge.server.clientparams.StateAwareResponseClientParameters.NAME_PREFIX;

public abstract class ClientParameters implements ClientParameterConstants {
  private static final ThreadLocal<ClientParameters> instances = new ThreadLocal<ClientParameters>();
  private static final String ENCODED_PREFIX = "E";
  private static final String PLAIN_PREFIX = "P";


  public static ClientParameters instance() {
    return instances.get();
  }


  public static void bind(StateAwareResponse response) {
    instances.set(new StateAwareResponseClientParameters(response));
  }


  public static void bind(RenderRequest request) {
    instances.set(new SimpleClientParameters());
    Map<String, String> existingParams = getExistingParameters(request);

    for (String name : existingParams.keySet()) {
      instance().setParameterValue(name, existingParams.get(name));
    }
  }


  static Map<String, String> getExistingParameters(RenderRequest request) {
    Map<String, String> parameters = new HashMap<String, String>();
    Enumeration<String> names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = names.nextElement();
      if (!name.startsWith(NAME_PREFIX)) continue;
      parameters.put(name.substring(NAME_PREFIX.length()), request.getParameter(name));
    }

    return parameters;
  }


  public static void release() {
    instances.remove();
  }


  public void setParameter(String name, String value, boolean urlEncode) {
    String prefix = urlEncode ? ENCODED_PREFIX : PLAIN_PREFIX;
    setParameterValue(name, prefix + value);
  }


  @SuppressWarnings("UnusedDeclaration")
  public void setParameter(String name, String value) {
    setParameter(name, value, true);
  }


  public String getParameter(String name) {
    String rawValue = getParameterValue(name);
    return (rawValue == null) ? null : rawValue.substring(1);
  }


  public boolean isEncoded(String name) {
    String rawValue = getParameterValue(name);
    return rawValue != null && rawValue.startsWith(ENCODED_PREFIX);
  }


  public abstract Collection<String> getParameterNames();
  protected abstract void setParameterValue(String name, String value);
  protected abstract String getParameterValue(String name);
}


