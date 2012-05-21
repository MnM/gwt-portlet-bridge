package com.chfourie.gwtportletbridge.rebind.requestfactory;

import com.chfourie.gwtportletbridge.client.requestfactory.AbstractPortletRequestFactory;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.web.bindery.requestfactory.gwt.client.impl.AbstractClientRequestFactory;
import com.google.web.bindery.requestfactory.gwt.rebind.RequestFactoryGenerator;

import java.io.StringWriter;

public class PortletRequestFactoryGenerator extends RequestFactoryGenerator
    implements DeferredGeneratorContext.PreCommitHandler {

  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    return super.generate(logger, new DeferredGeneratorContext(context, this), typeName);
  }


  @Override
  public void preCommit(StringWriter writer) {
    StringBuffer buffer = writer.getBuffer();

    String content = buffer.toString()
        .replace(AbstractClientRequestFactory.class.getCanonicalName(),
            AbstractPortletRequestFactory.class.getCanonicalName())
        .replace(AbstractClientRequestFactory.class.getSimpleName(),
            AbstractPortletRequestFactory.class.getSimpleName());

    buffer.delete(0, buffer.length());
    buffer.append(content);
  }
}
