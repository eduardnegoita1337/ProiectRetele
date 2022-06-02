package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler implements Runnable {

	private static Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ClientHandler(Socket socket) throws IOException {
		Server.listaSocketuri.add(socket);
		Server.nrSocketuri++;
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(socket.getOutputStream(),true);
	}

	@Override
	public void run() {
		while (!socket.isClosed()) {
			try {
				writer.println("Introduceti numele masinii ");
				writer.println("");
				String input;
				String numeClient="";
				try {
					input=reader.readLine();
					if (input != null && input != "\n") {
						numeClient = input;
						Server.notificaFiecareClient(numeClient, input);
					}
					Server.afiseazaClientii();
					writer.println("Introduceti numele clientului: ");
					input = reader.readLine();
					writer.println("Ati selectat clientul " + input);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				writer.println(e.getMessage());
			}
		}
	}

}