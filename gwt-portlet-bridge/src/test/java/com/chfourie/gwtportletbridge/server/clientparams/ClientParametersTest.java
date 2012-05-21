package com.chfourie.gwtportletbridge.server.clientparams;

import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.clientparams.StateAwareResponseClientParameters;
import org.junit.Test;

import javax.portlet.RenderRequest;
import javax.portlet.StateAwareResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.chfourie.gwtportletbridge.server.clientparams.ClientParameters.getExistingParameters;
import static com.chfourie.gwtportletbridge.server.clientparams.StateAwareResponseClientParameters.NAME_PREFIX;
import static com.chfourie.gwtportletbridge.server.util.Enumerations.asEnumeration;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientParametersTest {


  @Test
  public void testSameThreadSameInstance() {
    StateAwareResponse response = mock(StateAwareResponse.class);
    ClientParameters.bind(response);
    assertSame(ClientParameters.instance(), ClientParameters.instance());
  }


  @Test
  public void testDifferentThreadDifferentInstance() throws InterruptedException {
    ClientParameters.release();
    final StateAwareResponse response = mock(StateAwareResponse.class);

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        ClientParameters.bind(response);
      }
    });

    t.start();
    t.join();

    assertNull(ClientParameters.instance());
  }


  @Test
  public void testBindWithStateAwareResponse() {
    StateAwareResponse response = mock(StateAwareResponse.class);
    ClientParameters.bind(response);
    StateAwareResponseClientParameters instance = (StateAwareResponseClientParameters) ClientParameters.instance();
    assertSame(response, instance.getResponseObject());
  }


  @Test
  public void testBindWithRenderRequest() {
    RenderRequest request = mock(RenderRequest.class);
    when(request.getParameterNames()).thenReturn(asEnumeration(String.class, NAME_PREFIX + "name1", NAME_PREFIX + "name2"));
    when(request.getParameter(NAME_PREFIX + "name1")).thenReturn("E<value>");
    when(request.getParameter(NAME_PREFIX + "name2")).thenReturn("P<value>");

    ClientParameters.bind(request);

    assertEquals("<value>", ClientParameters.instance().getParameter("name1"));
    assertEquals("<value>", ClientParameters.instance().getParameter("name2"));
    assertTrue(ClientParameters.instance().isEncoded("name1"));
    assertFalse(ClientParameters.instance().isEncoded("name2"));
  }


  @Test
  public void testReleaseAfterBindWithStateAwareResponse() {
    StateAwareResponse response = mock(StateAwareResponse.class);
    ClientParameters.bind(response);
    ClientParameters.release();
    assertNull(ClientParameters.instance());
  }


  @Test
  public void testSetParameterWithUrlEncodeTrue() throws UnsupportedEncodingException {
    final Map<String, String> parameters = new HashMap<String, String>(1);

    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        return null;
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
        parameters.put(name, value);
      }
    };

    subject.setParameter("name", "<value>", true);
    assertEquals("E<value>", parameters.get("name"));
  }


  @Test
  public void testSetParameterWithUrlEncodeFalse() {
    final Map<String, String> parameters = new HashMap<String, String>(1);

    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        return null;
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
        parameters.put(name, value);
      }
    };

    subject.setParameter("name", "<value>", false);
    assertEquals("P<value>", parameters.get("name"));
  }


  @Test
  public void testGetParameterWithUrlEncoded() {
    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        assertEquals("name", name);
        return "E<value>";
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
      }
    };

    assertEquals("<value>", subject.getParameter("name"));
  }


  @Test
  public void testGetParameterWithUrlPlain() {
    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        assertEquals("name", name);
        return "P<value>";
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
      }
    };

    assertEquals("<value>", subject.getParameter("name"));
  }


  @Test
  public void testIsEncodedTrue() {
    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        assertEquals("name", name);
        return "E<value>";
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
      }
    };

    assertTrue(subject.isEncoded("name"));
  }


  @Test
  public void testIsEncodedFalse() {
    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        assertEquals("name", name);
        return "PLAIN/<value>";
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
      }
    };

    assertFalse(subject.isEncoded("name"));
  }


  @Test
  public void testIsEncodedForMissing() {
    ClientParameters subject = new ClientParameters() {
      @Override
      protected String getParameterValue(String name) {
        assertEquals("name", name);
        return null;
      }


      @Override
      public Collection<String> getParameterNames() {
        return null;
      }


      @Override
      protected void setParameterValue(String name, String value) {
      }
    };

    assertFalse(subject.isEncoded("name"));
  }


  @Test
  public void testGetParametersFromRenderRequest() {
    RenderRequest request = mock(RenderRequest.class);
    when(request.getParameterNames()).thenReturn(asEnumeration(String.class, NAME_PREFIX + "name1", NAME_PREFIX + "name2"));
    when(request.getParameter(NAME_PREFIX + "name1")).thenReturn("<value1>");
    when(request.getParameter(NAME_PREFIX + "name2")).thenReturn("<value2>");

    Map<String, String> result = getExistingParameters(request);
    assertEquals("<value1>", result.get("name1"));
    assertEquals("<value2>", result.get("name2"));
  }
}