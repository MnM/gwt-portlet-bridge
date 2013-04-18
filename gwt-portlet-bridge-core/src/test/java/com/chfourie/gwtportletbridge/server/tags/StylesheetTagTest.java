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

public class StylesheetTagTest {
  private TagLifeCycle lifeCycle;
  private StylesheetTag tag;


  @Before
  public void setUp() throws IOException {
    tag = new StylesheetTag();
    lifeCycle = TagLifeCycle.start(tag);
  }


  @Test
  public void testNoAttributesSet() throws Exception {
    XmlResult result = lifeCycle.finish();
    assertEquals(3, result.count("link/@*"));
    assertEquals("text/css", result.getTextValue("link/@type"));
    assertEquals("stylesheet", result.getTextValue("link/@rel"));
    assertEquals("all", result.getTextValue("link/@media"));
  }


  @Test
  public void testSetCharset() throws Exception {
    tag.setCharset("utf-8");
    XmlResult result = lifeCycle.finish();
    assertEquals("utf-8", result.getTextValue("link/@charset"));
  }


  @Test
  public void testSetMedia() throws Exception {
    tag.setMedia("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@media"));
  }


  @Test
  public void testSetRel() throws Exception {
    tag.setRel("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@rel"));
  }


  @Test
  public void testSetRev() throws Exception {
    tag.setRev("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@rev"));
  }


  @Test
  public void testSetTarget() throws Exception {
    tag.setTarget("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@target"));
  }


  @Test
  public void testSetType() throws Exception {
    tag.setType("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@type"));
  }


  @Test
  public void testSetHreflang() throws Exception {
    tag.setHreflang("value");
    XmlResult result = lifeCycle.finish();
    assertEquals("value", result.getTextValue("link/@hreflang"));
  }


  @Test
  public void testSetHref() throws Exception {
    RenderRequest request = mock(RenderRequest.class);
    RenderResponse response = mock(RenderResponse.class);

    when(request.getContextPath()).thenReturn("context/path/");
    when(response.encodeURL("context/path/some/url")).thenReturn("encoded/url");
    RenderContext.attach(null, request, response);

    tag.setHref("some/url");
    XmlResult result = lifeCycle.finish();
    assertEquals("encoded/url", result.getTextValue("link/@href"));
  }
}
