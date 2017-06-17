# Undertow example with WebSockets (JSR 356) and Jersey

[![Build Status](https://travis-ci.org/cassiomolin/undertow-websockets-jersey.svg?branch=master)](https://travis-ci.org/cassiomolin/undertow-websockets-jersey)
[![MIT Licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/cassiomolin/undertow-websockets-jersey/master/LICENSE.txt)

More details about the application: Coming soon

## How to build and run this application?

To build and run this application, follow these steps:

1. Open a command line window or terminal.
1. Navigate to the root directory of the project, where the `pom.xml` resides.
1. Compile the project: `mvn clean compile`.
1. Package the application: `mvn package`.
1. Change into the `target` directory: `cd target`
1. You should see a file with the following or a similar name: `undertow-websockets-jersey-1.0.jar`.
1. Execute the JAR: `java -jar undertow-websockets-jersey-1.0.jar`.
1. The following endpoints will be available:
    - `http://localhost:8080/api/messages`: REST endpoint over HTTP to create messages
    - `ws://localhost:8080/push`: WebSocket endpoint to receive messages from the server
