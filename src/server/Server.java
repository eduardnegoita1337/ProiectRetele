package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server implements AutoCloseable {

	private ServerSocket serverSocket;
	public static int nrSocketuri=0;
	public static ArrayList<String> listaClienti= new ArrayList<String>();
	public static ArrayList<Socket> listaSocketuri= new ArrayList<Socket>();

	public Server(int port) throws IOException {
		System.out.println("Serverul porneste...");
		serverSocket = new ServerSocket(port);

		ExecutorService executorService = Executors.newFixedThreadPool(100);
		executorService.execute(() -> {
			while (!serverSocket.isClosed()) {
				try {

					executorService.submit(new ClientHandler(serverSocket.accept()));

				} catch (IOException e) {

				}
			}
		});
	}
	public static void notificaFiecareClient(String numeClient, String input) throws IOException {
		listaClienti.add(numeClient);
		for(int i=0;i<nrSocketuri;i++){
			PrintWriter writer = new PrintWriter(listaSocketuri.get(i).getOutputStream(),true);
			writer.println("Clientul " + numeClient + " s-a conectat.");
			writer.println( "Lista clienti: " + listaClienti);
		}

	}
	public static void afiseazaClientii() throws IOException {
		for(int i=0; i<nrSocketuri ;i ++){
			PrintWriter writer = new PrintWriter(listaSocketuri.get(i).getOutputStream(),true);
			writer.println("Alegeti un client: " + listaClienti);
		}
	}

	@Override
	public void close() throws Exception {
		serverSocket.close();	
	}

}