# Example with Undertow, Weld, Jersey and WebSockets

[![Build Status](https://travis-ci.org/cassiomolin/example-undertow-jersey-websockets.svg?branch=master)](https://travis-ci.org/cassiomolin/example-undertow-jersey-websockets)
[![MIT Licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/cassiomolin/example-undertow-jersey-websockets/master/LICENSE.txt)

Example application using:

- **Undertow:** Servlet container
- **Weld:** CDI reference implementation
- **Jersey:** JAX-RS reference implementation for creating RESTful web services in Java
- **WebSockets:** Using the JSR 356 implementation provided by Undertow

In this example, a message created from the REST API is broadcasted to all WebSocket clients. CDI events are used to send data from the REST API to the WebSocket endpoint.

## Building and running this application?

Follow these steps to build and run this application:

1. Open a command line window or terminal.
1. Navigate to the root directory of the project, where the `pom.xml` resides.
1. Compile the project: `mvn clean compile`.
1. Package the application: `mvn package`.
1. Change into the `target` directory: `cd target`
1. You should see a file with the following or a similar name: `undertow-jersey-websockets-1.0.jar`.
1. Execute the JAR: `java -jar undertow-jersey-websockets-1.0.jar`.
1. A page to test the application will be available at `http://localhost:8080/index.html`:

<img src="src/main/doc/test page.png" width="650">

The following endpoints will be available:

- `http://localhost:8080/api/messages`: REST endpoint over HTTP to broadcast a message to WebSocket client

- `ws://localhost:8080/push`: WebSocket endpoint to get messages pushed by the server
