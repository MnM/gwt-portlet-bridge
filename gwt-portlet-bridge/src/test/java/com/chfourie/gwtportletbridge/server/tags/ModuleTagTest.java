package com.chfourie.gwtportletbridge.server.tags;

import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.portlet.GwtPortlet;
import com.chfourie.gwtportletbridge.server.portlet.RenderContext;
import com.chfourie.gwtportletbridge.server.util.Enumerations;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModuleTagTest {
  private TagLifeCycle lifeCycle;


  @Before
  public void setUp() throws IOException {
    GwtPortlet portlet = mock(GwtPortlet.class);
    RenderRequest request = mock(RenderRequest.class);
    RenderResponse response = mock(RenderResponse.class);
    when(request.getParameterNames()).thenReturn(Enumerations.empty(String.class));
    RenderContext.attach(portlet, request, response);
    ClientParameters.bind(request);

    ModuleTag tag = new ModuleTag();
    lifeCycle = TagLifeCycle.start(tag);

    when(request.getContextPath()).thenReturn("/context/path");
    when(response.encodeURL("/context/path/MyModule/MyModule.nocache.js")).thenReturn("correct_url");
    tag.setName("MyModule");
  }


  @Test
  public void testScriptTag() throws Exception {
    XmlResult result = lifeCycle.finish("result");

    assertEquals(3, result.count("result/script/@*"));
    assertEquals("javascript", result.getTextValue("result/script/@language"));
    assertEquals("text/javascript", result.getTextValue("result/script/@type"));
    assertEquals("correct_url", result.getTextValue("result/script/@src"));
  }


  @Test
  public void testFormTag() throws Exception {
    XmlResult result = lifeCycle.finish("result");

    assertEquals(1, result.count("//form"));
    assertEquals(2, result.count("result/form/@*"));
    assertEquals(String.format(ModuleTag.CONTAINER_ID_PATTERN, "MyModule"), result.getTextValue("result/form/@id"));
    assertEquals("#", result.getTextValue("result/form/@action"));
  }


  @Test
  public void testParameters() throws Exception {
    ClientParameters.instance().setParameter("encoded:parameter", "<encoded_value>", true);
    ClientParameters.instance().setParameter("plain:parameter", "plain_value>", false);
    XmlResult result = lifeCycle.finish("result");

    assertEquals(2, result.count("result/form/input"));

    String encodedInputName = URLEncoder.encode("encoded:parameter", "utf-8");
    assertEquals("E%3Cencoded_value%3E", result.getTextValue("//input[@name='" + encodedInputName + "']/@value"));

    String plainInputName = URLEncoder.encode("plain:parameter", "utf-8");
    assertEquals("Pplain_value>", result.getTextValue("//input[@name='" + plainInputName + "']/@value"));
  }
}
