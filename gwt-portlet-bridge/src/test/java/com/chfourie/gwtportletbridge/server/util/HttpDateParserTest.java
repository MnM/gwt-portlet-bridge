package com.chfourie.gwtportletbridge.server.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class HttpDateParserTest {
  private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

  @Test
  public void testParseNullValue() {
    assertEquals(-1L, HttpDateParser.parse(null));
  }

  @Test
  public void testGetDateHeaderWithIllegalValue() {
    assertEquals(-1L, HttpDateParser.parse("Not a date"));
  }

  @Test
  public void testGetDateHeaderWithValidHeader() throws ParseException {
    String[] validFormats = { "EEE, dd MMM yyyy HH:mm:ss zzz", "EEEEEE, dd-MMM-yy HH:mm:ss zzz",
        "EEE MMMM d HH:mm:ss yyyy" };
    Calendar cal = Calendar.getInstance(GMT, Locale.US);
    Date d = cal.getTime();

    for (String formatString : validFormats) {
      DateFormat format = new SimpleDateFormat(formatString);
      format.setCalendar(cal);
      long expected = format.parse(format.format(d)).getTime();
      assertEquals(expected, HttpDateParser.parse(format.format(d)));
    }
  }
}
