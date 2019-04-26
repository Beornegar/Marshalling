package utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class Converter {

	private static final int BYTES_IN_INT = 4;

	private Converter() {
	}

	public static byte[] convert(int[] array, ByteOrder order) {
		if (array == null) {
			System.err.println("Int array was null");
			return new byte[0];
		}
		return writeInts(array, order);
	}

	public static int[] convert(byte[] array) {
		if (array == null) {
			return new int[0];
		}
		return readInts(array);
	}

	public static byte[] convert(String[] array) {
		if (array == null) {
			return new byte[0];
		}
		return readBytesFromString(array);
	}

	private static byte[] readBytesFromString(String[] array) {
		//TODO
		return null;
	}

	public static int[][] convert(byte[][] array) {
		if (array == null) {
			return new int[0][0];
		}
		int[][] erg = new int[array.length][];
		for (int i = 0; i < array.length; i++) {
			erg[i] = readInts(array[i]);

		}
		return erg;
	}

	/**
	 * Converts an Integer-2D-Array to Byte-2D-Array in <br>
	 * given order
	 * 
	 * @param array
	 * @param order
	 * @return
	 */
	public static byte[][] convert(int[][] array, ByteOrder order) {
		if (array == null || order == null) {
			return new byte[0][0];
		}
		byte[][] erg = new byte[array.length][];
		for (int i = 0; i < array.length; i++) {
			erg[i] = writeInts(array[i], order);
		}
		return erg;
	}

	/**
	 * 
	 * Create an byte array out of an int array with <br>
	 * bytes of integer in given order
	 * 
	 * @param array
	 *            Int array to convert
	 * @param order
	 *            Little or Big Endian
	 * @return Byte array of int array
	 */
	private static byte[] writeInts(int[] array, ByteOrder order) {

		if (array == null || order == null) {
			return new byte[0];
		}

		byte[] erg = new byte[0];
		for (int i = 0; i < array.length; i++) {
			byte[] temp = intToByteArray(array[i], order);
			erg = ArrayUtils.concatArrays(erg, temp);
		}
		return erg;
	}

	/**
	 * 
	 * Flatten an 2D-Array to a 1D-Array
	 * 
	 * @param array
	 * @param lowToHigh
	 * @return
	 */
	public static byte[] flatten(byte[][] array, boolean lowToHigh) {

		if (array == null) {
			return new byte[0];
		}

		byte[] erg = new byte[0];
		if (lowToHigh) {
			for (int i = 0; i < array.length; i++) {
				erg = ArrayUtils.concatArrays(erg, array[i]);
			}
		} else {
			for (int i = array.length - 1; i >= 0; i--) {
				erg = ArrayUtils.concatArrays(erg, array[i]);
			}
		}

		return erg;
	}

	/**
	 * 
	 * Converts an integer to an Byte array as little or big endian
	 * 
	 * @param i
	 *            Integer to convert
	 * @param order
	 *            Litte or Big Endian
	 * @return
	 */
	public static byte[] intToByteArray(int i, ByteOrder order) {
		final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
		bb.order(order);
		bb.putInt(i);
		return bb.array();
	}

	private static int[] readInts(byte[] array) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(array);
			DataInputStream dataInputStream = new DataInputStream(bis);
			int size = array.length / BYTES_IN_INT;
			int[] res = new int[size];
			for (int i = 0; i < size; i++) {
				res[i] = dataInputStream.readInt();
			}
			return res;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
