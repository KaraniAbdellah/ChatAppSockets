<img src="/imgs/intro.png">

# Chat Application - Java Socket & Multithreading

A chat application built with Java that uses socket programming and multithreading concepts. This application enables multiple clients to communicate over a network through a server.

This project was developed as part of a study on network programming in Java.

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) 
![Sockets](https://img.shields.io/badge/Socket-00599C?style=for-the-badge&logo=javascript&logoColor=white)
![Threads](https://img.shields.io/badge/Multithreading-6DB33F?style=for-the-badge&logo=java&logoColor=white)
![Nodemon](https://img.shields.io/badge/Nodemon-76D04B?style=for-the-badge&logo=nodemon&logoColor=white)

</div>

### Features:
--> client#1 sends message to client#2.  
--> client can send message to all clients.  
--> client can see all clients that are connected to the app.  
--> **NOTE**: All messages pass by the server (means: client sends to server and then server sends to client)


## Overview Of How it Works:

This image is to understand that each client has its own thread that communicates with the server:

![Client-Server Communication](/imgs/client-server.png)

*Diagram source: [Professor Mohamed Youssfi on YouTube](https://www.youtube.com/watch?v=4NNFgx5DFJo&list=PLTNDvzDK3P0UbORn9y0awJgHu_2XIfe_4&index=3)*



## Project Structure
for the the Project Structure I am using three folders:

```
chatAppJava/
├── src/
│   ├── client/         # Client-side
│   ├── server/         # Server-side
│   └── services/       # some usefull functions
```

## How to Execute
```bash
cd chatAppJava/src
javac client/*.java server/*.java services/*.java # Compile All Java Files
java server/ServerChat.java # Start the Server --> YOU GET: Server Started
```

**NOTE: Better to separate terminal windows**

## Images of Application
![Client#1](/imgs/screenShot.png)


One challenge during development is the need to manually stop and restart the server after each code change. To fix this problem, I am using nodemon, a tool that automatically detects changes and restarts the server.

#### Installation
```bash
npm install nodemon # make sure to have npm downloaded
nodemon --version
nodemon --exec java Main.java # try with Main.java
```

