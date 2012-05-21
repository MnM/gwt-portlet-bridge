package com.chfourie.gwtportletbridge.server.tags;

public class LinkTag extends PortletTagSupport {
  private static final String CHARSET = "charset";
  private static final String MEDIA = "media";
  private static final String REL = "rel";
  private static final String REV = "rev";
  private static final String TARGET = "target";
  private static final String TYPE = "type";
  private static final String HREFLANG = "hreflang";
  private static final String HREF = "href";


  public LinkTag() {
    super("link");
  }


  public void setCharset(String value) {
    setAttribute(CHARSET, value);
  }


  public void setMedia(String value) {
    setAttribute(MEDIA, value);
  }


  public void setRel(String value) {
    setAttribute(REL, value);
  }


  public void setRev(String value) {
    setAttribute(REV, value);
  }


  public void setTarget(String value) {
    setAttribute(TARGET, value);
  }


  public void setType(String value) {
    setAttribute(TYPE, value);
  }


  public void setHreflang(String value) {
    setAttribute(HREFLANG, value);
  }


  public void setHref(String value) {
    setAttribute(HREF, getPortletUrl(value));
  }
}
