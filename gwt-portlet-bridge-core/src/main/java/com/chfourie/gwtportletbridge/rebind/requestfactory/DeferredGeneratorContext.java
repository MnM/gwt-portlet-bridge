package com.chfourie.gwtportletbridge.rebind.requestfactory;

import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.ResourceOracle;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.IdentityHashMap;

public class DeferredGeneratorContext implements GeneratorContext {

  public static interface PreCommitHandler {
    void preCommit(StringWriter writer);
  }

  private final GeneratorContext original;
  private final IdentityHashMap<PrintWriter, PrintWriter> originalWriters = new IdentityHashMap<PrintWriter, PrintWriter>();
  private final IdentityHashMap<PrintWriter, StringWriter> stringWriters = new IdentityHashMap<PrintWriter, StringWriter>();
  private final PreCommitHandler handler;


  public DeferredGeneratorContext(GeneratorContext original, PreCommitHandler handler) {
    this.original = original;
    this.handler = handler;
  }


  @Override
  public boolean checkRebindRuleAvailable(String sourceTypeName) {
    return original.checkRebindRuleAvailable(sourceTypeName);
  }


  @Override
  public void commit(TreeLogger logger, PrintWriter pw) {
    StringWriter stringWriter = stringWriters.get(pw);

    if (stringWriter == null) {
      original.commit(logger, pw);
    } else {
      handler.preCommit(stringWriter);
      PrintWriter originalWriter = originalWriters.get(pw);
      originalWriter.write(stringWriter.toString());
      original.commit(logger, originalWriter);
    }
  }


  @Override
  public void commitArtifact(TreeLogger logger, Artifact<?> artifact) throws UnableToCompleteException {
    original.commitArtifact(logger, artifact);
  }


  @Override
  public GeneratedResource commitResource(TreeLogger logger, OutputStream os) throws UnableToCompleteException {
    return original.commitResource(logger, os);
  }


  @Override
  public PropertyOracle getPropertyOracle() {
    return original.getPropertyOracle();
  }


  @Override
  public ResourceOracle getResourcesOracle() {
    return original.getResourcesOracle();
  }


  @Override
  public TypeOracle getTypeOracle() {
    return original.getTypeOracle();
  }


  @Override
  public PrintWriter tryCreate(TreeLogger logger, String packageName, String simpleName) {
    PrintWriter originalWriter = original.tryCreate(logger, packageName, simpleName);
    if (originalWriter == null) return null;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter, true);
    originalWriters.put(printWriter, originalWriter);
    stringWriters.put(printWriter, stringWriter);
    return printWriter;
  }


  @Override
  public OutputStream tryCreateResource(TreeLogger logger, String partialPath) throws UnableToCompleteException {
    return original.tryCreateResource(logger, partialPath);
  }

  @Override
  public CachedGeneratorResult getCachedGeneratorResult() {
    return original.getCachedGeneratorResult();
  }

  @Override
  public boolean isGeneratorResultCachingEnabled() {
    return original.isGeneratorResultCachingEnabled();
  }

  @Override
  public boolean isProdMode() {
    return original.isProdMode();
  }

  @Override
  public boolean tryReuseTypeFromCache(String s) {
    return original.tryReuseTypeFromCache(s);
  }
}
