package com.chfourie.gwtportletbridge.server.tags;

import com.chfourie.gwtportletbridge.server.portlet.RenderContext;
import org.junit.Before;
import org.junit.Test;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScriptTagTest {
  private TagLifeCycle lifeCycle;
  private ScriptTag tag;


  @Before
  public void setUp() throws IOException {
    tag = new ScriptTag();
    lifeCycle = TagLifeCycle.start(tag);
  }


  @Test
  public void testNoAttributesSet() throws Exception {
    XmlResult result = lifeCycle.finish();
    assertEquals(2, result.count("script/@*"));
    assertEquals("text/javascript", result.getTextValue("script/@type"));
    assertEquals("javascript", result.getTextValue("script/@language"));
  }


  @Test
  public void testSetType() throws Exception {
    tag.setType("text/vbscript");
    XmlResult result = lifeCycle.finish();
    assertEquals("text/vbscript", result.getTextValue("script/@type"));
  }


  @Test
  public void testSetLanguage() throws Exception {
    tag.setLanguage("vbscript");
    XmlResult result = lifeCycle.finish();
    assertEquals("vbscript", result.getTextValue("script/@language"));
  }


  @Test
  public void testSetSrc() throws Exception {
    RenderRequest request = mock(RenderRequest.class);
    RenderResponse response = mock(RenderResponse.class);

    when(request.getContextPath()).thenReturn("context/path/");
    when(response.encodeURL("context/path/script/script.js")).thenReturn("encoded/url");
    RenderContext.attach(null, request, response);

    tag.setSrc("script/script.js");
    XmlResult result = lifeCycle.finish();
    assertEquals("encoded/url", result.getTextValue("script/@src"));
  }
}
