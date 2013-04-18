package com.chfourie.gwtportletbridge.server.tags;


import com.chfourie.gwtportletbridge.server.clientparams.ClientParameters;
import com.chfourie.gwtportletbridge.server.util.URLCodec;

import javax.servlet.jsp.JspException;

public class ModuleTag extends ScriptTag {
  public static final String CONTAINER_ID_PATTERN = "gwt:%1$s:parameters";
  private String name;


  public void setName(String value) {
    this.name = value;
    setSrc('/' + value + '/' + value + ".nocache.js");
  }


  @Override
  public int doStartTag() throws JspException {
    super.doStartTag();

    Tag paramContainer = new Tag("form").setAttribute("id", getClientParamConainerId()).setAttribute("action", "#");

    for (String paramName : ClientParameters.instance().getParameterNames()) {
      String value = "" + ClientParameters.instance().getParameter(paramName);
      String prefix = ClientParameters.instance().isEncoded(paramName) ? "E" : "P";
      String encodedValue = ClientParameters.instance().isEncoded(paramName) ? URLCodec.encode(value) : value;

      paramContainer.add(new Tag("input")
          .setAttribute("type", "hidden")
          .setAttribute("name", URLCodec.encode(paramName))
          .setAttribute("value", prefix + encodedValue));
    }

    paramContainer.write(pageContext.getOut());
    return SKIP_BODY;
  }


  private String getClientParamConainerId() {
    return String.format(CONTAINER_ID_PATTERN, name);
  }
}
