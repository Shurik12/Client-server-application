# Client-server application
Simple client-server application on Java like redis.
## Description: 
It is a simple client-server application with multiclients, wich realises request-resonse relatonship between clients and server. Now there are three methods: get, set and append for clients requesting. It works and testings on local machine with some terminals. Simalteniosly run Server and Client(s). Client sees simple interface for requests.

Type data into db is pares key:valye (String:String). Data store local during execution session and then will de delleting.

* Get - get value on key, return value;
* Set - change value of current key, if key exist;
* Lpop - delete the first key in db with value, if db is not empty;
* Dbsize - return count of notes in db
* Append - insert key:value in db

Client-Server app work locally, has logs, time executon methods and some tests for check execution.

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
