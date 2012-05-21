package com.chfourie.gwtportletbridge.client.general.impl;

import com.chfourie.gwtportletbridge.client.general.api.NativeGWT;
import com.chfourie.gwtportletbridge.client.general.api.NativeGwtMocker;
import com.chfourie.gwtportletbridge.client.general.api.PortletClientContext;
import com.chfourie.gwtportletbridge.client.general.api.PortletGwtBridge;
import com.chfourie.gwtportletbridge.server.tags.ModuleTag;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.chfourie.gwtportletbridge.client.general.api.ClientParameterConstants.*;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PortletClientContextImplTest {
  private Map<String, String> rawParams;


  @Before
  public void setUp() {
    rawParams = new HashMap<String, String>();
    PortletGwtBridge gwt = spy(new PortletGwtBridgeStub());
    NativeGwtMocker.mockWith(gwt);
    when(gwt.getModuleName()).thenReturn("ModuleName");
    when(gwt.getInputValues(String.format(ModuleTag.CONTAINER_ID_PATTERN, NativeGWT.getModuleName()))).thenReturn(
        rawParams);
  }


  @After
  public void tearDown() {
    NativeGwtMocker.reset();
  }


  @Test
  public void testGetEncodedParameter() throws Exception {
    rawParams.put(URLEncoder.encode("ParameterName", "UTF-8"), "EValue%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("Value>", ctx.getParameter("ParameterName"));
  }


  @Test
  public void testGetPlainParameter() throws Exception {
    rawParams.put(URLEncoder.encode("ParameterName", "UTF-8"), "PValue%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("Value%3E", ctx.getParameter("ParameterName"));
  }


  @Test
  public void testGetMissingParameter() throws Exception {
    rawParams.put(URLEncoder.encode("ParameterName", "UTF-8"), "EValue%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("", ctx.getParameter("AnotherParameterName"));
  }


  @Test
  public void testHasParameter() throws Exception {
    rawParams.put(URLEncoder.encode("ParameterName", "UTF-8"), "EValue%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertTrue(ctx.hasParameter("ParameterName"));
    assertFalse(ctx.hasParameter("AnotherParameterName"));
  }


  @Test
  public void testGetPortletName() throws Exception {
    rawParams.put(URLEncoder.encode(PORTLET_NAME_PARAM, "UTF-8"), "EPortlet Name%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("Portlet Name>", ctx.getPortletName());
  }


  @Test
  public void testGetPortletMode() throws Exception {
    rawParams.put(URLEncoder.encode(PORTLET_MODE_PARAM, "UTF-8"), "EPortlet Mode%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("Portlet Mode>", ctx.getPortletMode());
  }


  @Test
  public void testHasActionUrl() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "SomeAction", "UTF-8"), "PSomeActionUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertTrue(ctx.hasAction("SomeAction"));
    assertFalse(ctx.hasAction("AnotherAction"));
  }


  @Test
  public void testGetActionUrl() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "SomeAction", "UTF-8"), "PSomeActionUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("SomeActionUrl%3E", ctx.getActionUrl("SomeAction"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetMissingActionUrl() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "SomeAction", "UTF-8"), "PSomeActionUrl");

    PortletClientContext ctx = new PortletClientContextImpl();
    ctx.getActionUrl("AnotherAction");
  }


  @Test
  public void testGetActionBuilder() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "SomeAction", "UTF-8"), "PSomeActionUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("SomeActionUrl%3E", ctx.getActionBuilder("SomeAction").getUrl());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetMissingActionBuilder() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "SomeAction", "UTF-8"), "PSomeActionUrl");

    PortletClientContext ctx = new PortletClientContextImpl();
    ctx.getActionBuilder("AnotherAction");
  }


  @Test
  public void testGetServeResourceHandlerUrlForClass() throws Exception {
    rawParams.put(URLEncoder.encode(SERVE_RESOURCE_PREFIX + RemoteService.class.getCanonicalName(),
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("SomeUrl%3E", ctx.getServeResourceUrl(RemoteService.class));
  }


  @Test
  public void testGetServeResourceHandlerUrlForString() throws Exception {
    rawParams.put(URLEncoder.encode(SERVE_RESOURCE_PREFIX + "SomeName",
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals("SomeUrl%3E", ctx.getServeResourceUrl("SomeName"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetMissingServeResourceHandlerUrlForClass() throws Exception {
    rawParams.put(URLEncoder.encode(RemoteService.class.getCanonicalName(),
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    ctx.getServeResourceUrl(BaseProxy.class);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetMissingServeResourceHandlerUrlForString() throws Exception {
    rawParams.put(URLEncoder.encode("SomeName",
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    ctx.getServeResourceUrl("AnotherName");
  }


  @Test
  public void testHasServeResourceHandlerUrlForClass() throws Exception {
    rawParams.put(URLEncoder.encode(SERVE_RESOURCE_PREFIX + RemoteService.class.getCanonicalName(),
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertTrue(ctx.hasServeResourceUrl(RemoteService.class));
    assertFalse(ctx.hasServeResourceUrl(BaseProxy.class));
  }


  @Test
  public void testHasServeResourceHandlerUrlForString() throws Exception {
    rawParams.put(URLEncoder.encode(SERVE_RESOURCE_PREFIX + "SomeName",
        "UTF-8"), "PSomeUrl%3E");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertTrue(ctx.hasServeResourceUrl("SomeName"));
    assertFalse(ctx.hasServeResourceUrl("AnotherName"));
  }


  @Test
  public void testGetParameterNames() throws Exception {
    rawParams.put(URLEncoder.encode("Name1", "UTF-8"), "Value");
    rawParams.put(URLEncoder.encode("Name2", "UTF-8"), "Value");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals(2, ctx.getParameterNames().size());
    assertTrue(ctx.getParameterNames().contains("Name1"));
  }


  @Test
  public void testActionNames() throws Exception {
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "Name1", "UTF-8"), "Value");
    rawParams.put(URLEncoder.encode(ACTION_PREFIX + "Name2", "UTF-8"), "Value");

    PortletClientContext ctx = new PortletClientContextImpl();
    assertEquals(2, ctx.getActionNames().size());
    assertTrue(ctx.getActionNames().contains("Name1"));
  }
}
