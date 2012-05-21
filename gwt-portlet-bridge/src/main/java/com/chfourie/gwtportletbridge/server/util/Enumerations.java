package com.chfourie.gwtportletbridge.server.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

public abstract class Enumerations {


  public static <T> Enumeration<T> empty (Class<T> cls) {
    return empty();
  }


  public static <T> Enumeration<T> empty() {
    return new Enumeration<T>() {
      @Override
      public boolean hasMoreElements() {
        return false;
      }


      @Override
      public T nextElement() {
        return null;
      }
    };
  }


  public static <T> Enumeration<T> asEnumeration(Class<T> cls, T... values) {
    Vector<T> v = new Vector<T>(values.length);
    Collections.addAll(v, values);
    return v.elements();
  }
}
