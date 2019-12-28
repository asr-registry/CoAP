### Constrained Application Protocol (CoAP) IoT Protocol Library

CoAP is an IoT protocol. CoAP stands for Constrained Application Protocol, and it is defined in RFC 7252. CoAP is a simple protocol with low overhead specifically designed for constrained devices (such as microcontrollers) and constrained networks. This protocol is used in M2M data exchange and is very similar to HTTP, even if there are important differences that we will cover laters.

The main features of CoAP protocols are:

- Web protocol used in M2M with constrained requirements
- Asynchronous message exchange
- Low overhead and very simple to parse
- URI and content-type support
- Proxy and caching capabilities

Some features are very similar to HTTP even if CoAP must not be considered a compressed HTTP protocol because CoAP is specifically designed for IoT and in more details for M2M so it is very optimized for this task.


There are two different layers that make CoAp protocol: Messages and Request/Response. The Messages layer deals with UDP and with asynchronous messages. The Request/Response layer manages request/response interaction based on request/response messages.

CoAP supports four different message types:

- Confirmable
- Non-confirmable
- Acknowledgment
- Reset

#### Common Terminologies

- Endpoint: An entity that participates in the CoAP protocol. Usually, an Endpoint is identified with a host

- Sender: The entity that sends a message

- Recipient: The destination of a message

- Client: The entity that sends a request and the destination of the response

- Server: The entity that receives a request from a client and sends back a response to the client

#### Message Structure

- Ver: It is a 2 bit unsigned integer indicating the version

- T: it is a 2 bit unsigned integer indicating the message type: 0 confirmable, 1 non-confirmable

- TKL: Token Length is the token 4 bit length

- Code: It is the code response (8 bit length)

- Message ID: It is the message ID expressed with 16 bit

- And so on.