package com.chfourie.gwtportletbridge.server.servlet.container;

import com.chfourie.gwtportletbridge.server.servlet.container.ServletInputStreamImpl;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GwtServletInputStreamTest {
  private ServletInputStreamImpl wrapper;
  private InputStream wrapped;

  @Before
  public void setUp() {
    wrapped = mock(InputStream.class);
    wrapper = new ServletInputStreamImpl(wrapped);
  }

  @Test @SuppressWarnings({"ConstantConditions"})
  public void testInstanceOfServletInputStream() {
    assertTrue(wrapper instanceof ServletInputStream);
  }

  @Test
  public void testReadMethodWrapped() throws IOException {
    when(wrapped.read()).thenReturn(4);
    assertEquals(4, wrapper.read());
  }

  @Test
  public void testSkipMethodWrapped() throws IOException {
    when(wrapped.skip(6)).thenReturn(4L);
    assertEquals(4L, wrapper.skip(6));
  }

  @Test
  public void testAvailableMethodWrapped() throws IOException {
    when(wrapped.available()).thenReturn(3);
    assertEquals(3, wrapper.available());
  }

  @Test
  public void testCloseMethodWrapped() throws IOException {
    wrapper.close();
    verify(wrapped).close();
  }

  @Test
  public void testMarkMethodWrapped() {
    wrapper.mark(7);
    verify(wrapped).mark(7);
  }

  @Test
  public void testResetMethodWrapped() throws IOException {
    wrapper.reset();
    verify(wrapped).reset();
  }

  @Test
  public void testMarkSupportedMethodWrapped() {
    when(wrapped.markSupported()).thenReturn(true);
    assertTrue(wrapper.markSupported());
    verify(wrapped).markSupported();
  }
}
