package com.chfourie.gwtportletbridge.server.tags;

public class StylesheetTag extends LinkTag {
  public StylesheetTag() {
    setType("text/css");
    setRel("stylesheet");
    setMedia("all");
  }
}
