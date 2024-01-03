package ru.gb.lesson5.hw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    final int PORT=4545;
    final String HOST="localhost";
    boolean connection=true;

    public void connectClient() {
        Scanner userInput=new Scanner(System.in);
        System.out.println("введите свое имя: ");
        String userName=userInput.nextLine();

        try {
            Socket client=new Socket(HOST, PORT);
            ClientSocketWrapper socketWrapper=new ClientSocketWrapper(client);
            socketWrapper.getOutput().println(userName);

            new Thread(() -> {
                while (connection) {
                    String outMessage=socketWrapper.getInput().nextLine();
                    System.out.println(outMessage);
                    if (Objects.equals("Сервер отключен", outMessage)) {
                        System.exit(0);
                    }
                    if (Objects.equals("Сеанс отключен администратором", outMessage)) {
                        socketWrapper.getOutput().println("q");
                        System.exit(0);
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    String inputMessage=userInput.nextLine();
                    if (Objects.equals("q", inputMessage)) {
                        socketWrapper.getOutput().println(inputMessage);
                        connection=false;
                        break;
                    }
                    if (Objects.equals("exit", inputMessage) && userName.equalsIgnoreCase("admin")) {
                        socketWrapper.getOutput().println(inputMessage);
                        connection=false;
                        break;
                    }
                    socketWrapper.getOutput().println(inputMessage);
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
