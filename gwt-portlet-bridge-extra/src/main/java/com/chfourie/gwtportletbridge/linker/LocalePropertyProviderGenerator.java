package com.chfourie.gwtportletbridge.linker;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.PropertyProviderGenerator;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

import java.util.SortedSet;

/**
 * Sets the GWT locale property based on the contents of the HTML lang attribute.
 */
public class LocalePropertyProviderGenerator implements PropertyProviderGenerator {

  @Override
  public String generate(TreeLogger logger, SortedSet<String> possibleValues,
    String fallback, SortedSet<ConfigurationProperty> configProperties)
  throws UnableToCompleteException {

    if (fallback == null) {
      fallback = "default";
    }

    StringSourceWriter body = new StringSourceWriter();
    body.println("{");
    body.indent();

    body.println("try {");
    body.indent();
    body.println("var locale = null;");
    body.println("var lang = document.getElementsByTagName('html').item(0).getAttribute('lang');");
    body.println("locale = lang.replace('-', '_');");
    generateInheritanceLookup(logger, body);
    body.outdent();
    body.println("} catch (e) {");
    body.indent();
    body.println("alert(\"Unexpected exception in locale detection, using "
      + "default: \" + e);\n");
    body.outdent();
    body.println("}");

    body.println("return locale || \"" + fallback + "\";");
    body.outdent();
    body.println("}");

    return body.toString();
  }

  /**
   * Taken verbatim from GWT 2.5.1's LocalePropertyProviderGenerator.
   *
   * Generate JS code that takes the value of the "locale" variable and finds
   * parent locales until the value is a supported locale or the default locale.
   *
   * @param logger logger to use
   * @throws UnableToCompleteException
   */
  protected void generateInheritanceLookup(TreeLogger logger, SourceWriter body)
    throws UnableToCompleteException {
    body.println("while (locale && !__gwt_isKnownPropertyValue(\"locale\", locale)) {");
    body.indent();
    body.println("var lastIndex = locale.lastIndexOf(\"_\");");
    body.println("if (lastIndex < 0) {");
    body.indent();
    body.println("locale = null;");
    body.println("break;");
    body.outdent();
    body.println("}");
    body.println("locale = locale.substring(0, lastIndex);");
    body.outdent();
    body.println("}");
  }
}