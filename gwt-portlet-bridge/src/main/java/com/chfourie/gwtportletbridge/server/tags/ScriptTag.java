package com.chfourie.gwtportletbridge.server.tags;

public class ScriptTag extends PortletTagSupport {
  private static final String SRC = "src";
  private static final String TYPE = "type";
  private static final String LANGUAGE = "language";

  public ScriptTag() {
    super("script");
    setType("text/javascript");
    setLanguage("javascript");
  }


  public void setType(String value) {
    setAttribute(TYPE, value);
  }


  public void setLanguage(String value) {
    setAttribute(LANGUAGE, value);
  }


  public void setSrc(String value) {
    setAttribute(SRC, getPortletUrl(value));
  }
}
