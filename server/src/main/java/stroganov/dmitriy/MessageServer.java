package stroganov.dmitriy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Сервер запущен");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Получено сообщение от клиента: " + message);
                        if ("exit".trim().equalsIgnoreCase(message)) {
                            System.out.println("Клиент отключился");
                            break;
                        }
                        out.println("Сообщение получено");
                    }

                } catch (IOException e) {
                    System.err.println("Ошибка обработки клиента: " + e.getMessage());
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        System.err.println("Не удалось закрыть сокет клиента: " + e.getMessage());
                    }
                }
            }).start();
        }
    }
}