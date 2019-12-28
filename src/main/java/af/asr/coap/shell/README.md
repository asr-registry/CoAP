CoAP is a RESTful web transfer protocol specialized for use with constrained nodes and constrained networks in the Internet of Things (IoT).


this module provides an interactive, command line interface for interacting with CoAP enabled servers. It supports the coap: and coaps: schemas (e.g. UDP and DTLS). It also can manage your IKEA TRÅDFRI smart lights set ;)

It is build on top of the Spring Shell, Californium (Cf) and Scandium (Sc) projects. It is a SpringBoot application, that builds into a single, self-executable jar and runs on any Java8+ environment.

## Features
- Plain coap: and secured coaps: endpoints (e.g. UDP and DTLS transports).

- CoAP GET, PUT, POST and DELETE methods.

- CoAP Resource Observing.

- CoAP Resource Discovery. Filters by href, ct, rt, obs …​

- Synchronous and Asynchronous (--async argument) message exchanges.

- Confirmable and Non-Confirmable message exchange.

- TAB auto-completion for commands and arguments.

- Extensive commands help (type help).

- Plugable key/trust stores and credentials.

- SpringBoot, self-executable jar, running in any Java 8+ environment.

- Basic support for IKEA Tradfri Gateway.

#### Example Commands
`
java -jar application.jar

connect coap://californium.eclipse.org

discover --query href=/*

get /multi-format --accept application/xml

ikea gateway key --ip 127.0.0.1 --identity myIkeaGatewayIdentity --security-code <Gateway Code Label>

connect coaps://192.168.178.151:5684 --identity myIkeaGatewayIdentity --secret X5xyYM41qFS7vN10
available

ikea turn on --instance 65539
ikea turn off --instance 65539
`