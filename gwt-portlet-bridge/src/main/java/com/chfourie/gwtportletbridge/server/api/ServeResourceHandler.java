package com.chfourie.gwtportletbridge.server.api;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public interface ServeResourceHandler {
  void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, java.io.IOException;
}
