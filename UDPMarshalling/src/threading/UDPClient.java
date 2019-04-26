package threading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import arguments.Options;
import utils.FileUtils;
import utils.LoggingUtil;
import utils.UDPUtils;

public class UDPClient extends Thread {

	private Options options;
//	private volatile boolean stopped = false;

	public UDPClient(Options options) {
		this.options = options;
//		this.stopped = false;
	}

	@Override
	public void run() {

		LoggingUtil.printIfVerbose("Client started", options.getVerbose());
		sentMatrix();

//		while (stopped == false) {
		while (isInterrupted() == false) {
		}
		LoggingUtil.printIfVerbose("Client stopped: %s:%d", options.getVerbose(), options.getIP(), options.getPort());
	}

	public void sentMatrix() {
		try {
			InetAddress ia = InetAddress.getByName(options.getIP());

			int[][] matrix = FileUtils.getRandomIntMatrix(options.getN(), options.getM());

			LoggingUtil.printIfVerbose("Input Matrix", matrix, options.getVerbose());
			byte[] bytes = FileUtils.getMatrix(matrix, options.getByteOrder());
			bytes = UDPUtils.addMetaData(bytes, options.getN(), options.getM(), options.getByteOrder());

			LoggingUtil.printIfVerbose("Data preperation done with result: %s", options.getVerbose(),
					new String(bytes, 0, bytes.length));

			DatagramPacket[] packets = UDPUtils.getPackets(bytes, options.getPacketSize(), ia, options.getPort());

			DatagramSocket dSocket = new DatagramSocket();

			LoggingUtil.printIfVerbose("Client starts now sending data", options.getVerbose());
			for (int i = 0; i < packets.length; i++) {
				dSocket.send(packets[i]);
				LoggingUtil.printIfVerbose("Client has now sent packet %d from %d packets", options.getVerbose(), i + 1,
						packets.length);
			}
			dSocket.close();
			LoggingUtil.printIfVerbose("All packages sent.", options.getVerbose());

		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void stopClient() {
//		this.stopped = true;
//	}

}
