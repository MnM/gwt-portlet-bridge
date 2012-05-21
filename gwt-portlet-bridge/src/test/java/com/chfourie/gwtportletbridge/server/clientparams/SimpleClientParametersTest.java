package com.chfourie.gwtportletbridge.server.clientparams;

import com.chfourie.gwtportletbridge.server.clientparams.SimpleClientParameters;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class SimpleClientParametersTest {
  private SimpleClientParameters params;

  @Before
  public void setUp() {
    params = new SimpleClientParameters();
  }


  @Test
  public void testGetParameterValue() {
    params.setParameterValue("name", "<value>");
    assertEquals("<value>", params.getParameterValue("name"));
  }


  @Test
  public void testGetParameterNames() {
    params.setParameterValue("name1", "<value1>");
    params.setParameterValue("name2", "<value2>");
    Iterator<String> result = params.getParameterNames().iterator();
    assertEquals("name1", result.next());
    assertEquals("name2", result.next());
    assertFalse(result.hasNext());
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
    Assert.assertFalse(params.isEncoded("name"));
  }


  @Test
  public void testIsEncodedMissing() {
    Assert.assertFalse(params.isEncoded("name"));
  }
}
