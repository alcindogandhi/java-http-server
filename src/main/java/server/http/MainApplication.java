package server.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class MainApplication {

	public static void main(String[] args) {
		int port = 8001;
		System.out.println("Exemplo de um simples servidor HTTP em Java.");
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException ex) {
				System.err.println("Erro! Porta inválida. Usando o valor padrão (" + port + ")");
			}
		}

		try {
			final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
			server.createContext("/test", new TestRequest());
			server.setExecutor(null);
			server.start();
			System.out.println(" Server started on port " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
