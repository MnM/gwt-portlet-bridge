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
  * When GWT is loaded in the browser, an initial bootstrap JavaScript file is loaded initially.  This 
    boonstrap script determines various parameters such as the browser type being used and then uses this 
    information to load the relevant version of the application.  The URL that it currently compiles and 
    uses to load the relevant version of the application in compiled as a URL relative to itself.  Since 
    the portlet mechanism for encoding URL's wasn't used it might break certain portlet/WSRP 
    implementations.  I do believe that these implementations will be the exception rather than the rule.
  * Images and other resources referenced within the GWT application are currently referenced relative 
    to the application script.  The same dynamics as the above point therefore applies.  Once again this
    should not pose a problem to most Portal & WSRP implementations.

## Version History

The version history can be found [here](docs/Version-History.md).

## Building from Source

gwt-portlet-bridge is a Maven project, so building it is as simple as
checking out the project via Git, changing into the directory and
executing:

  mvn clean install

A Java 7+ compiler is required, because nobody should use anything older
anymore.

## Recipes

### Converting a GWT Project into a Portlet / Using RPC in a GWT Portlet Project
Follow the steps described in the [RPC Example](docs/RPC-Example.md).

### Using RequestFactory in a GWT Portlet Project
Follow the steps described in the [RequestFactory Example](docs/RequestFactory-Example.md).

### Using both RPC and RequestFactory in the same GWT Portlet Project
Follow the steps described in the [RequestFactory Example](docs/RequestFactory-Example.md).  
You do **NOT** have to inherit _com.chfourie.gwtportletbridge.GwtPortletBridge_ separately.  
Just make sure that your _com.chfourie.gwtportletbridge.RequestFactory_ inherit clause is positioned after any other modules that might inherit from _com.google.gwt.core.Core_.

### Embedding String Parameters in your HTML page
Follow the steps described in the [Client Parameters Example](docs/Client-Parameters-Example.md).

## License
All of the deliverable code in the GWT Portlet Bridge has been dedicated to the public domain.  

Anyone is free to copy, modify, publish, use, compile, sell, or distribute the original GWT Portlet 
Bridge code, either in source code form or as a compiled library, for any purpose, commercial or 
non-commercial, and by any means.  

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
THE USE OR OTHER DEALINGS IN THE SOFTWARE.
