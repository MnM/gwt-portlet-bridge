package com.chfourie.gwtportletbridge.server.util;

import org.junit.Test;

import java.util.Enumeration;

import static com.chfourie.gwtportletbridge.server.util.Enumerations.asEnumeration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EnumerationsTest {

  @Test
  public void testAsEnumeration() {
    Enumeration<String> enu = asEnumeration(String.class, "value1", "value2");
    assertEquals("value1", enu.nextElement());
    assertEquals("value2", enu.nextElement());
    assertFalse(enu.hasMoreElements());
  }
}
