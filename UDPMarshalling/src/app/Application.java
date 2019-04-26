package app;

import java.util.Scanner;

import arguments.ArgumentParsing;
import arguments.Options;
import threading.UDPClient;
import threading.UDPServer;
import utils.LoggingUtil;

public class Application {

	public static UDPServer serverThread;
	public static UDPClient clientThread;
	public static boolean stopped = false;

	public static void main(String[] args) {

		Options options = ArgumentParsing.parseStringsFromArgs(args);

		LoggingUtil.printIfVerbose(options.toStringSingleLine(), options.getVerbose());

		switch (options.getProgramMode()) {
		case CLIENT:
			LoggingUtil.printIfVerbose("Starting now client on : %s:%d", options.getVerbose(), options.getIP(),
					options.getPort());
			clientThread = new UDPClient(options);
			clientThread.start();
			break;
		case SERVER:
			LoggingUtil.printIfVerbose("Starting now server on : %s:%d", options.getVerbose(), options.getIP(),
					options.getPort());
			serverThread = new UDPServer(options);
			serverThread.start();

			break;
		case SERVERANDCLIENT:
			
			LoggingUtil.printIfVerbose("Starting now server on : %s:%d", options.getVerbose(), options.getIP(),
					options.getPort());
			serverThread = new UDPServer(options);
			serverThread.start();
			
			LoggingUtil.printIfVerbose("Starting now client on : %s:%d", options.getVerbose(), options.getIP(),
					options.getPort());
			clientThread = new UDPClient(options);
			clientThread.start();

			break;
		default:
			break;
		}

		scan(options);

	}

	private static void scan(Options options) {
		Scanner scanner = new Scanner(System.in);
		while (stopped == false) {
			String consoleInput = scanner.nextLine().toUpperCase();
			switch (consoleInput) {
			case "END":
				
				if (serverThread != null) {
					LoggingUtil.printIfVerbose("Stopping server on : %s:%d", options.getVerbose(), options.getIP(),
							options.getPort());
					serverThread.interrupt();
				}
				
				if (clientThread != null) {
					LoggingUtil.printIfVerbose("Stopping client on : %s:%d", options.getVerbose(), options.getIP(),
							options.getPort());
					clientThread.interrupt();
				}
				
				LoggingUtil.printIfVerbose("Stopping application.",options.getVerbose());
				
				stopped = true;
				break;
			case "SEND_MATRIX":
				if (clientThread != null) {
					clientThread.sentMatrix();
				}
				break;
			}
		}
		scanner.close();
	}

}
