package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.ServletOutputStreamImpl;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GwtServletOutputStreamTest {
  private OutputStream out;
  ServletOutputStreamImpl gwtStream;

  @Before
  public void setUp() {
    out = mock(OutputStream.class);
    gwtStream = new ServletOutputStreamImpl(out);
  }


  @Test @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfServletOutputStream() {
    assertTrue(gwtStream instanceof ServletOutputStream);
  }


  @Test
  public void testWrite() throws IOException {
    gwtStream.write(7);
    verify(out).write(7);
  }


  @Test(expected = IOException.class)
  public void testWriteThrowsException() throws IOException {
    doThrow(new IOException()).when(out).write(7);
    gwtStream.write(7);
  }


  @Test
  public void testGetOutputStream() {
    assertSame(out, gwtStream.getOutputStream());
  }
}
