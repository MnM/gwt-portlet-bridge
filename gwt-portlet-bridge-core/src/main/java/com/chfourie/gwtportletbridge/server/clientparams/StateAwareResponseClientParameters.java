package com.chfourie.gwtportletbridge.server.clientparams;

import javax.portlet.StateAwareResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class StateAwareResponseClientParameters extends ClientParameters {
  static final String NAME_PREFIX = ClientParameters.class.getName() + '.';

  private final Map<String, String> parameters;
  private StateAwareResponse response;


  public StateAwareResponseClientParameters(StateAwareResponse response) {
    this.response = response;
    parameters = new HashMap<String, String>();
  }


  @Override
  public Collection<String> getParameterNames() {
    return Collections.unmodifiableCollection(parameters.keySet());
  }


  public StateAwareResponse getResponseObject() {
    return response;
  }


  @Override
  protected String getParameterValue(String name) {
    return parameters.get(name);
  }


  @Override
  protected void setParameterValue(String name, String value) {
    response.setProperty(NAME_PREFIX + name, value);
    parameters.put(name, value);
  }
}
