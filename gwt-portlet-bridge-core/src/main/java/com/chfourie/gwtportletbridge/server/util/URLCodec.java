package com.chfourie.gwtportletbridge.server.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public abstract class URLCodec {
  private URLCodec() {
  }


  public static String encode(String value) {
    try {
      return URLEncoder.encode(value, "UTF-8");
    } catch (UnsupportedEncodingException unused) {
      throw new RuntimeException(unused);
    }
  }
  
  
  public static String decode(String value) {
    try {
      return URLDecoder.decode(value, "UTF-8");
    } catch (UnsupportedEncodingException unused) {
      throw new RuntimeException(unused);
    }
  }
}
