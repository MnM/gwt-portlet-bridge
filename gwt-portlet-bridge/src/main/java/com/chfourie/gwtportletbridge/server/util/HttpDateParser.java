package com.chfourie.gwtportletbridge.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public abstract class HttpDateParser {
  private static int CACHE_SIZE = 1000;
  private static final ConcurrentHashMap<String, Long> CACHE = new ConcurrentHashMap<String, Long>(CACHE_SIZE);
  private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
  private static final DateFormat[] FORMATS;

  static {
    FORMATS = new DateFormat[]{
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };

    for (DateFormat format : FORMATS) format.setTimeZone(GMT);
  }

  public static long parse(String value) {
    if (value == null) return -1L;
    Long cached = getCachedValue(value);
    if (cached != null) return cached;

    synchronized (CACHE) {
      long result = performParse(value);
      addToCache(value, result);
      return result;
    }
  }

  private static Long getCachedValue(String value) {
    return CACHE.get(value);
  }

  private static void addToCache(String value, long result) {
    if (CACHE.size() > CACHE_SIZE) CACHE.clear();
    CACHE.put(value, result);
  }

  private static long performParse(String value) {
    for (DateFormat format : FORMATS) {
      try {
        Date d = format.parse(value);
        if (d != null) return d.getTime();
      } catch (ParseException ignored) {
      }
    }

    return -1L;
  }
}
