package com.chfourie.gwtportletbridge.server.tags;

import com.chfourie.gwtportletbridge.server.portlet.RenderContext;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PortletTagSupport extends TagSupport {
  private final Tag tag;


  public PortletTagSupport(String name) {
    this.tag = new Tag(name);
  }


  @Override
  public int doStartTag() throws JspException {
    tag.write(pageContext.getOut());
    return SKIP_BODY;
  }


  protected void setAttribute(String name, String value) {
    tag.setAttribute(name, value);
  }


  protected String getAttribute(String name) {
    return tag.getAttribute(name);
  }


  protected void add(Tag child) {
    tag.add(child);
  }


  protected String getPortletUrl(String url) {
    return getResponse().encodeURL(getRequest().getContextPath() + url);
  }


  protected RenderRequest getRequest() {
    return RenderContext.instance().getRenderRequest();
  }


  protected RenderResponse getResponse() {
    return RenderContext.instance().getRenderResponse();
  }
}
