# Client-server application
Simple client-server application on Java like redis.
## Description: 
It is a simple client-server application with multiclients, wich realises request-resonse relatonship between clients and server. Now there are three methods: get, set and append for clients requesting. It works and testings on local machine with some terminals. Simalteniosly run Server and Client(s). Client sees simple interface for requests.
## Instalation
Need JDK 11 for running application.
```bash
javac Server.java Client.java
```
## Running
At once run Server in one terminal window
```bash
java Server
```
Then in another terminal run client
```bash
java Client
```
