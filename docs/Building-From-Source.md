# Building From Source

## Pre-Requisites
 * Java JDK-6 or higher must be installed on your system.
 * Apache Ant must be installed on your system.

## Build Steps

### Step 1: Download the GWT SDK
Download the latest GWT SDK and extract it to a location of your choice.  
In the rest of this document, this location will be referred to as {{GWT_HOME}}.

### Step 2: Obtain the Source Code
Check out the source code to a location of your choice (Requires GIT).
Alternatively GitHub provides you the option to download the source code as a zip file.  
You can download it and extract it to a location of your choice.  

In the rest of this document, the location to which you checked out or extracted the source code 
will be referred to as {{PROJECT_HOME}}.

### Step 3: Configure the Project

Configure the following property files to point to {{GWT_HOME}}:
  * {{PROJECT_HOME}}/gwt-portlet-bridge/build.properties
  * {{PROJECT_HOME}}/samples/build.properties

### Step 4: Build the Project

Using your console/terminal, navigate to {{PROJECT_HOME}} and execute the following command:

```
ant
```

This will build both the bridge and the sample project.  
  * The compiled bridge can now be found at {{PROJECT_HOME}}/gwt-portlet-bridge/artifacts
  * The compiled sample project can now be found at {{PROJECT_HOME}}/samples/artifacts