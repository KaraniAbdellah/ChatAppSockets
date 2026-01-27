<img src="/imgs/intro.png">
# Chat Application - Java Socket & Multithreading

A network-based chat application built with Java that demonstrates socket programming and multithreading concepts. This application enables multiple clients to communicate over a network through a central server.

## Project Description

This project was developed as part of a study on sockets and multithreading in Java. It implements a chat application using both concepts to facilitate real-time communication between clients over a network. The server can broadcast messages to all connected clients or route messages to specific recipients.

## Features

- **Client-Server Architecture**: Centralized server managing multiple client connections
- **Multithreading**: Concurrent handling of multiple client connections
- **Socket Communication**: Real-time message exchange over network
- **Message Broadcasting**: Send messages to all connected clients
- **Direct Messaging**: Target specific clients for private communication

## Architecture Overview

The application follows a classic client-server model:

![Client-Server Communication](/imgs/client-server.png)

*Diagram source: [Professor Mohamed Youssfi on YouTube](https://www.youtube.com/watch?v=4NNFgx5DFJo&list=PLTNDvzDK3P0UbORn9y0awJgHu_2XIfe_4&index=3)*

## Project Structure

```
chatAppJava/
├── src/
│   ├── client/         # Client-side implementation
│   ├── server/         # Server-side implementation
│   └── services/       # Shared services and utilities
└── imgs/              # Project images and diagrams
```

## How to Execute

### Step 1: Navigate to Source Directory
```bash
cd chatAppJava/src
```

### Step 2: Compile All Java Files
```bash
javac client/*.java server/*.java services/*.java
```

### Step 3: Start the Server
```bash
java server/ServerChat.java
```

You should see the following output:
```
Server Started
```

### Step 4: Run Client Applications
In separate terminal windows, run the client application to connect to the server.

## Development Tools

### Auto-Restart with Nodemon

One challenge during development is the need to manually stop and restart the server after each code change. To automate this process, you can use **nodemon**, a tool that monitors file changes and automatically restarts your application.

#### Installation
```bash
npm install nodemon
```

#### Verify Installation
```bash
nodemon --version
```

#### Usage
Nodemon can monitor and run various file types (Java, JavaScript, etc.):
```bash
nodemon Main.java
```

This automatically restarts the server whenever you save changes to `ServerChat.java` or other monitored files.

## Technologies Used

- **Java**: Core programming language
- **Java Sockets**: Network communication
- **Java Multithreading**: Concurrent client handling
- **Nodemon**: Development automation (optional)

## Learning Objectives

This project demonstrates:
- Socket programming in Java
- Multithreaded server architecture
- Network communication protocols
- Client-server design patterns
- Real-time message broadcasting

## Acknowledgments

Special thanks to [Professor Mohamed Youssfi](https://www.youtube.com/watch?v=4NNFgx5DFJo&list=PLTNDvzDK3P0UbORn9y0awJgHu_2XIfe_4&index=3) for the educational content on socket programming and multithreading.

---

**Note**: Ensure the server is running before attempting to connect clients. The server must be started first to accept incoming client connections.

