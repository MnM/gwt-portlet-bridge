package com.chfourie.gwtportletbridge.server.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tag {
  private String name;
  private Map<String, String> attributes;
  private List<Tag> children;


  public Tag(String name) {
    this.name = name;
    attributes = new HashMap<String, String>();
    children = new ArrayList<Tag>();
  }


  public Tag setAttribute(String name, String value) {
    attributes.put(name, value);
    return this;
  }


  public String getAttribute(String name) {
    return attributes.get(name);
  }


  public void add(Tag child) {
    children.add(child);
  }


  public Tag write(Writer writer) {
    try {
      writer.write('<');
      writer.write(name);

      for (String name : attributes.keySet()) {
        writer.write(' ');
        writer.write(name);
        writer.write('=');
        writer.write('\'');
        writer.write(attributes.get(name));
        writer.write('\'');
      }


      writer.write('>');
      for (Tag child : children) child.write(writer);
      writer.write('<');
      writer.write('/');
      writer.write(name);
      writer.write('>');

      return this;
    } catch (IOException e) {
      throw new RuntimeException("Could not write tag.", e);
    }
  }
}
