package com.chfourie.gwtportletbridge.server.tags;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class TagLifeCycle {
  private final TagSupport tag;
  private final PageContext pageContext;
  private final JspWriter jspWriter;
  private final StringBuilder resultBuffer;


  public static TagLifeCycle start(TagSupport tag) {
    try {
      final PageContext pageContext = mock(PageContext.class);
      final JspWriter jspWriter = mock(JspWriter.class);
      final StringBuilder resultBuffer = new StringBuilder();

      when(pageContext.getOut()).thenReturn(jspWriter);

      doAnswer(new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock inv) throws Throwable {
          resultBuffer.append(inv.getArguments()[0]);
          return null;
        }
      }).when(jspWriter).write(anyString());

      doAnswer(new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock inv) throws Throwable {
          resultBuffer.append((char) ((Integer) inv.getArguments()[0]).intValue());
          return null;
        }
      }).when(jspWriter).write(anyChar());

      tag.setPageContext(pageContext);
      return new TagLifeCycle(tag, pageContext, jspWriter, resultBuffer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private TagLifeCycle(TagSupport tag, PageContext pageContext, JspWriter jspWriter, StringBuilder resultBuffer) {
    this.tag = tag;
    this.pageContext = pageContext;
    this.jspWriter = jspWriter;
    this.resultBuffer = resultBuffer;
  }


  public XmlResult finish() throws Exception {
    return finish(null);
  }


  public XmlResult finish(String root) throws Exception {
    if (root != null) resultBuffer.append('<').append(root).append('>');
    assertEquals(SKIP_BODY, tag.doStartTag());
    assertEquals(EVAL_PAGE, tag.doEndTag());
    tag.release();
    if (root != null) resultBuffer.append('<').append('/').append(root).append('>');
    System.out.println("TAG RESULT: " + resultBuffer.toString());
    return new XmlResult(resultBuffer.toString());
  }
}
