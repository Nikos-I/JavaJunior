package ru.gb.lesson5.hw;

import ru.gb.lesson5.hw.server.Server;

import java.net.ServerSocket;

public class ServerRun {
    public static void main(String[] args) {
        Server server=new Server();
        ServerSocket serverSocket=server.createServer();
        server.connectClient(serverSocket);
        server.serverClosing(serverSocket);
    }
}
