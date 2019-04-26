package threading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import arguments.Options;
import utils.DeConverter;
import utils.LoggingUtil;

public class UDPServer extends Thread {

//	private volatile boolean stopped = false;
	private Options options;
	public Object received;

	public UDPServer(Options optionsContainer) {
		super();
//		this.stopped = false;
		this.options = optionsContainer;
	}

	public synchronized Object getReceivedData() {
		return received;
	}

	@Override
	public void run() {

		try {

			DatagramSocket socket = new DatagramSocket(options.getPort());
			LoggingUtil.printIfVerbose("Server listening on : %s:%d", options.getVerbose(), options.getIP(),
					options.getPort());
//			while (stopped == false) {
			while (isInterrupted() == false) {
				// Auf Anfrage warten
				DatagramPacket packet = new DatagramPacket(new byte[options.getPacketSize()], options.getPacketSize());
				socket.receive(packet);
				LoggingUtil.printIfVerbose("Server received packet with length of %d from : %s", options.getVerbose(),
						packet.getLength(), packet.getSocketAddress());

				// Empfänger auslesen
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				int len = packet.getLength();
				byte[] data = packet.getData();

				readPackageDataAndParseAdditionalPacketsIfNeccecary(socket, packet, address, port, len, data);
			}

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		LoggingUtil.printIfVerbose("Server stopped: %s:%d", options.getVerbose(), options.getIP(), options.getPort());
	}

	/**
	 * If first packet was received waits for remaining packets, receives parses and
	 * unmarshalls them
	 * 
	 * @param socket
	 * @param packet
	 * @param address
	 * @param port
	 * @param len
	 * @param data
	 * @throws IOException
	 */
	private void readPackageDataAndParseAdditionalPacketsIfNeccecary(DatagramSocket socket, DatagramPacket packet,
			InetAddress address, int port, int len, byte[] data) throws IOException {

		LoggingUtil.printIfVerbose("Request with %s from port %d with  length %d:%n%s%n received.",
				options.getVerbose(), address, port, len, new String(data, 0, len));
		int intPerPackage = len / 4;
		int n = DeConverter.getUsedLength(data, options.getByteOrder(), 0);
		int m = DeConverter.getUsedLength(data, options.getByteOrder(), 1);
		int packetSize = ((n * m + 2) * 4 - 1);
		int receivedPackets = 1;

		if (n == 0 || m == 0) {
			return;
		}

		int[][] result = DeConverter.readIntFromByte(data, options.getByteOrder());

		while (packetSize >= (receivedPackets * data.length)) {

			int whereAmI = intPerPackage * receivedPackets - 2;
			socket.receive(packet);
			data = packet.getData();
			result = DeConverter.readIntFromByte(data, options.getByteOrder(), result, (whereAmI % m), (whereAmI / m),

					packetSize);
			receivedPackets++;
		}
		LoggingUtil.printIfVerbose("Done with receiving of Packages", true);
		LoggingUtil.printIfVerbose("Received Matrix: ", result, options.getVerbose());
		received = result;
	}

//	public void stopServer() {
//		this.stopped = true;
//	}

}
