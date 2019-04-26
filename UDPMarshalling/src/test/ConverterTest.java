package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import utils.Converter;

public class ConverterTest {

	@Test
	void intToByteArrayTest() {
		
		int i = 4;		
		byte[] bytes = Converter.intToByteArray(i, ByteOrder.BIG_ENDIAN);		
		int x = java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		
		assertEquals(x, i);
		
		bytes = Converter.intToByteArray(i, ByteOrder.LITTLE_ENDIAN);		
		x = java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		
		assertEquals(x, i);
	}
	
	
	@Test
	void writeIntsTest() {
		
		int[] numbers = new int[] {3,4,5};
		
		byte[] bytes = Converter.convert(numbers, ByteOrder.BIG_ENDIAN);
		
		byte[] eins  = Arrays.copyOfRange(bytes, 0, 4);
		byte[] zwei  = Arrays.copyOfRange(bytes, 4, 8);
		byte[] drei  = Arrays.copyOfRange(bytes, 8, 12);
		
		int x1 = java.nio.ByteBuffer.wrap(eins).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x2 = java.nio.ByteBuffer.wrap(zwei).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x3 = java.nio.ByteBuffer.wrap(drei).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		
		assertEquals(x1, 3);
		assertEquals(x2, 4);
		assertEquals(x3, 5);
		
		bytes = Converter.convert(numbers, ByteOrder.LITTLE_ENDIAN);
		
		eins  = Arrays.copyOfRange(bytes, 0, 4);
		zwei  = Arrays.copyOfRange(bytes, 4, 8);
		drei  = Arrays.copyOfRange(bytes, 8, 12);
		
		x1 = java.nio.ByteBuffer.wrap(eins).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		x2 = java.nio.ByteBuffer.wrap(zwei).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		x3 = java.nio.ByteBuffer.wrap(drei).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		
		assertEquals(x1, 3);
		assertEquals(x2, 4);
		assertEquals(x3, 5);
	}
	
	@Test
	void flattenTest() {
		int[] n1 = new int[] {1,2,3};
		int[] n2 = new int[] {4,5,6};

		
		byte[] b1 = Converter.convert(n1, ByteOrder.BIG_ENDIAN);
		byte[] b2 = Converter.convert(n2, ByteOrder.BIG_ENDIAN);

		
		byte[][] bytes = new byte[2][1];
		bytes[0] = b1;
		bytes[1] = b2;
		
		byte[] erg = Converter.flatten(bytes, true);
		
		byte[] e1  = Arrays.copyOfRange(erg, 0, 4);
		byte[] e2  = Arrays.copyOfRange(erg, 4, 8);
		byte[] e3  = Arrays.copyOfRange(erg, 8, 12);
		byte[] e4  = Arrays.copyOfRange(erg, 12,16);
		byte[] e5  = Arrays.copyOfRange(erg, 16, 20);
		byte[] e6  = Arrays.copyOfRange(erg, 20, 24);
		
		int x1 = java.nio.ByteBuffer.wrap(e1).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x2 = java.nio.ByteBuffer.wrap(e2).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x3 = java.nio.ByteBuffer.wrap(e3).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x4 = java.nio.ByteBuffer.wrap(e4).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x5 = java.nio.ByteBuffer.wrap(e5).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		int x6 = java.nio.ByteBuffer.wrap(e6).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
		
		assertEquals(x1, 1);
		assertEquals(x2, 2);
		assertEquals(x3, 3);
		assertEquals(x4, 4);
		assertEquals(x5, 5);
		assertEquals(x6, 6);
	}
}
