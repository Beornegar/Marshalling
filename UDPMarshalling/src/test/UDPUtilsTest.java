package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import utils.Converter;
import utils.UDPUtils;

public class UDPUtilsTest {

	
	@Test
	void addMetaDataTest() {
		
		int n = 2;
		int m = 2;
		
		int[] a1 = {1,1,1};
		int[] a2 = {1,1,1};

		ByteOrder order = ByteOrder.BIG_ENDIAN;
		
		byte[] b1 = Converter.convert(a1, order);
		byte[] b2 = Converter.convert(a1, order);
	
		byte[][] bytes = new byte[2][];
		bytes[0] = b1;
		bytes[1] = b2;

		int length = b1.length + b2.length;

		byte[] start = Converter.flatten(bytes, true);

		assertTrue(start.length == length);
		
		start = UDPUtils.addMetaData(start, n, m, order);
		
		byte[] e1  = Arrays.copyOfRange(start, 0, 4);
		byte[] e2  = Arrays.copyOfRange(start, 4, 8);

		// n + m
		int i1 = java.nio.ByteBuffer.wrap(e1).order(order).getInt();
		int i2 = java.nio.ByteBuffer.wrap(e2).order(order).getInt();
		
		assertTrue( i1  == n);
		assertTrue( i2  == m);
		
	}
	
	@Test
	void getPacketsTest() {
		
		int n = 2;
		int m = 2;
		
		int[] a1 = {1,1,1};
		int[] a2 = {1,1,1};

		ByteOrder order = ByteOrder.BIG_ENDIAN;
		
		byte[] b1 = Converter.convert(a1, order);
		byte[] b2 = Converter.convert(a1, order);
	
		byte[][] bytes = new byte[2][];
		bytes[0] = b1;
		bytes[1] = b2;

		int length = b1.length + b2.length;

		byte[] start = Converter.flatten(bytes, true);

		assertTrue(start.length == length);
		
		// start.length = 36
		start = UDPUtils.addMetaData(start, n, m, order);
		
		
		try {
			int packageSize = 20;
			DatagramPacket[] erg = UDPUtils.getPackets(start, packageSize, InetAddress.getByName("localhost"), 10000);
	
			assertTrue(erg.length == 2);
			byte[] data1 = erg[0].getData();
			byte[] data2 = erg[1].getData();
			
			assertTrue(data1.length == 20);
			assertTrue(data2.length == 20);
			
			byte[] d1 = Arrays.copyOfRange(start, 0, 20);
			byte[] d2 = Arrays.copyOfRange(start, 20, 40);
			
			assertArrayEquals(d1, data1);
			assertArrayEquals(d2, data2);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
}
