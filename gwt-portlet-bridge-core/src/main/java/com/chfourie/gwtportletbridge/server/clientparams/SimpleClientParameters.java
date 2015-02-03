package com.chfourie.gwtportletbridge.server.clientparams;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class SimpleClientParameters extends ClientParameters {
  private final Map<String, String> parameters = new LinkedHashMap<>();


  public SimpleClientParameters() {
  }


  @Override
  public Collection<String> getParameterNames() {
    return Collections.unmodifiableCollection(parameters.keySet());
  }


  @Override
  protected void setParameterValue(String name, String value) {
    parameters.put(name, value);
  }


  @Override
  protected String getParameterValue(String name) {
    return parameters.get(name);
  }
}
