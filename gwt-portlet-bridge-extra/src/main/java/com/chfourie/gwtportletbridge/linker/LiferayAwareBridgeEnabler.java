package com.chfourie.gwtportletbridge.linker;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.PropertyProviderGenerator;
import com.google.gwt.user.rebind.StringSourceWriter;

import java.util.SortedSet;

/**
 * <property-provider name="gpb.runningAs">
 *   <![CDATA[
 *     return typeof(window['Liferay']) === 'undefined' || typeof(window['Liferay']['Portal']) === 'undefined' ? 'servlet' : 'portlet';
 *   ]]>
 * </property-provider>
 */
public class LiferayAwareBridgeEnabler implements PropertyProviderGenerator {

  @Override
  public String generate(TreeLogger treeLogger, SortedSet<String> sortedSet,
    String s, SortedSet<ConfigurationProperty> sortedSet1)
  throws UnableToCompleteException {

    StringSourceWriter body = new StringSourceWriter();
    body.println("{");
    body.indent();

    body.println("return typeof(window['Liferay']) === 'undefined'"
      + " || typeof(window['Liferay']['Portal']) === 'undefined' ? 'servlet' : 'portlet';");

    body.outdent();
    body.println("}");

    return body.toString();
  }
}
