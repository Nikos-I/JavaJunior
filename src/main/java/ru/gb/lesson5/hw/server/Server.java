package ru.gb.lesson5.hw.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    private static Map<String, ServerSocketWrapper> clients=new HashMap<>();
    final int PORT=4545;
    Socket client;
    ServerSocketWrapper socketWrapper;

    public ServerSocket createServer() {
        try {
            ServerSocket serverSocket=new ServerSocket(PORT);
            System.out.println("Сервер слушает порт: " + PORT);
            return serverSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectClient(ServerSocket server) {
        while (true) {
            try {
                client=server.accept();
                String userName=new Scanner(client.getInputStream()).nextLine();
                socketWrapper=new ServerSocketWrapper(userName, client);
                clients.put(userName, socketWrapper);
                System.out.println("Подключился новый клиент: " + socketWrapper.getUserName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            runThreadClient();
        }
    }

    private void runThreadClient() {
        new Thread(() -> {
            try (Scanner input=socketWrapper.getInput();
                 PrintWriter output=socketWrapper.getOutput()) {

                String userName=socketWrapper.getUserName();

                output.println("Успешное подключение к серверу, " + userName + "! На сервере клиенты: " + clients);

                while (true) {

                    String clientMessage=input.nextLine();

                    String destinationUser=getUserName(clientMessage);
                    ServerSocketWrapper destination=null;

                    if (clientMessage.charAt(0) == '@') {
                        destination=clients.get(destinationUser.toLowerCase());
                    }

                    if (Objects.equals("q", clientMessage)) {
                        sendMessageToEveryone("Клиент " + userName + " отключился", userName);
                        output.println("Отключение от сервера...");
                        System.out.println("Клиент " + userName + " отключился");
                        clients.remove(userName);
                        break;
                    }

                    if (userName.equalsIgnoreCase("admin")) {
                        if (Objects.equals("exit", clientMessage)) {
                            sendMessageToEveryone("Сервер отключен", userName);
                            output.println("Сервер отключен");
                            System.out.println("Сервер отключен");
                            System.exit(0);
                        }
                        if (Objects.equals("kick", clientMessage.split(" ")[0])) {
                            destination=clients.get(clientMessage.split(" ")[1]);
                            destination.getOutput().println("Соединение прервано администратором");
                            destination.getInput().close();
                            destination.getOutput().close();
                            destination.getSocket().close();
                        }
                    }

                    if (destination != null) {
                        destination.getOutput().println(clientMessage);
                    } else {
                        sendMessageToEveryone(clientMessage, userName);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void sendMessageToEveryone(String message, String userName) {
        for (String name : clients.keySet()) {
            if (!clients.get(name).getUserName().equalsIgnoreCase(userName))
                clients.get(name).getOutput().println(message);
        }
    }

    public void serverClosing(ServerSocket server) {
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserName(String message) {
        String destinationUserName="";
        if (message.charAt(0) == '@') {
            destinationUserName=message.split(" ")[0].substring(1);
        } else {
            destinationUserName=message.split(" ")[0];
        }
        return destinationUserName;
    }
}
