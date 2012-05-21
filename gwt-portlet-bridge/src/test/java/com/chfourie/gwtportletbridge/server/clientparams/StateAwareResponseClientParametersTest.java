package com.chfourie.gwtportletbridge.server.clientparams;

import com.chfourie.gwtportletbridge.server.clientparams.StateAwareResponseClientParameters;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.StateAwareResponse;

import static com.chfourie.gwtportletbridge.server.clientparams.StateAwareResponseClientParameters.NAME_PREFIX;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StateAwareResponseClientParametersTest {
  private StateAwareResponse response;
  private StateAwareResponseClientParameters params;

  @Before
  public void setUp() {
    response = mock(StateAwareResponse.class);
    params = new StateAwareResponseClientParameters(response);
  }

  @Test
  public void testSetParameter() {
    params.setParameterValue("name", "<value>");
    verify(response).setProperty(NAME_PREFIX + "name", "<value>");
  }

  @Test
  public void testGetParameterValue() {
    params.setParameterValue("name", "<value>");
    assertEquals("<value>", params.getParameterValue("name"));
  }

  @Test
  public void testGetParameterNames() {
    params.setParameterValue("name", "<value>");
    assertEquals(params.getParameterNames().iterator().next(), "name");
  }


  @Test
  public void testGetParameterWithUrlEncoded() {
    params.setParameter("name", "<value>", true);
    assertEquals("<value>", params.getParameter("name"));
  }


  @Test
  public void testGetParameterWithUrlPlain() {
    params.setParameter("name", "<value>", false);
    assertEquals("<value>", params.getParameter("name"));
  }


  @Test
  public void testIsEncodedTrue() {
    params.setParameter("name", "<value>", true);
    assertTrue(params.isEncoded("name"));
  }


  @Test
  public void testIsEncodedFalse() {
    params.setParameter("name", "<value>", false);
    assertFalse(params.isEncoded("name"));
  }


  @Test
  public void testIsEncodedMissing() {
    assertFalse(params.isEncoded("name"));
  }
}
