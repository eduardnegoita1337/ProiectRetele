package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client1 {

    public static void main(String[] args) {
        int port = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
        String hostname = ResourceBundle.getBundle("settings").getString("host");
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Te-ai conectat la server");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
            try(Scanner scanner = new Scanner(System.in)) {
                String response;
                while(true) {
                    try {
                        do{
                            response = reader.readLine();
                            if(response.compareTo("")==0){
                                break;
                            }
                            System.out.println("S: " + response);
                        }while (response != null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String comanda = scanner.nextLine();
                    writer.println(comanda);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.exit(0);
        }
    }
}