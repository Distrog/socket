package stroganov.dmitriy;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageClient {
    public static void main(String[] args) {

        System.out.println("Клиент запущен");

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String message = scanner.nextLine();

                out.println(message);

                if ("exit".trim().equalsIgnoreCase(message)) {
                    System.out.println("Отключение клиента");
                    break;
                }

                System.out.println("Ответ сервера: " + in.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}