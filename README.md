# GWT Portlet Bridge

## Introduction

GWT Portlet Bridge is a bridge for using GWT in JSR-286 portlets.  It was initially developed in a WSRP based environment using IBM Websphere and IBM Portal Server.

The following goals was set for the project:

  * It should be easy to use and well documented.
  * It should seamlessly integrate GWT into JSR-286 portlets.
  * It should make use as much of GWT's own code as possible - this minimalizes the chance compatibility issues with future versions of GWT.
  * WSRP 2.0 should be supported.
  * It should fully conform to the JSR-286 specification.
  * It should fully conform to the WSRP 2.0 specification.
  * There should be no runtime dependencies other than that required by GWT and JSR-286.
  * It should be vendor agnostic.
  * All GWT RPC communication should make use of the Portlet's serveResource method (see JSR-286 specification.)

I trust that most of the above goals have been reached with the exception of the following...

### Documentation
The project is not documented in as much detail as initially intended.  I do trust however that the documentation in combination with the samples found in the source tree is sufficient.

### Relative Paths
  * When GWT is loaded in the browser, an initial bootstrap JavaScript file is loaded initially.  This boonstrap script determines various parameters such as the browser type being used and then uses this information to load the relevant version of the application.  The URL that it currently compiles and uses to load the relevant version of the application in compiled as a URL relative to itself.  Since the portlet mechanism for encoding URL's wasn't used it might break certain portlet/WSRP implementations.  I do believe that these implementations will be the exception rather than the rule.
  * Images and other resources referenced within the GWT application are currently referenced relative to the application script.  The same dynamics as the above point therefore applies.

## Version History

The version history can be found [here](https://github.com/chfourie/gwt-portlet-bridge/blob/master/docs/Version-History.md).

## Downloads

All released artefacts can be downloaded from [here](https://github.com/chfourie/gwt-portlet-bridge/downloads).  

## Building from Source

TODO:...

## Recipes

### Converting a GWT Project into a Portlet
Follow the steps described in the [RPC Example](https://github.com/chfourie/gwt-portlet-bridge/blob/master/docs/RPC-Example.md).

### Using RPC in a GWT Portlet Project
Follow the steps described in the [RPC Example](https://github.com/chfourie/gwt-portlet-bridge/blob/master/docs/RPC-Example.md).

### Using RequestFactory in a GWT Portlet Project
Follow the steps described in the [RequestFactory Example](https://github.com/chfourie/gwt-portlet-bridge/blob/master/docs/RequestFactory-Example.md).

### Using RPC in a GWT Portlet Project Configured for RequestFactory
If your GWT module already inherits _com.chfourie.gwtportletbridge.RequestFactory_, you do **NOT** have to inherit _com.chfourie.gwtportletbridge.GwtPortletBridge_ separately.  Just make sure that your _com.chfourie.gwtportletbridge.RequestFactory_ inherit clause is positioned after any other modules that might inherit from _com.google.gwt.core.Core_.

### Embedding String Parameters in your HTML page
Follow the steps described in the [Client Parameters Example](https://github.com/chfourie/gwt-portlet-bridge/blob/master/docs/Client-Parameters-Example.md).