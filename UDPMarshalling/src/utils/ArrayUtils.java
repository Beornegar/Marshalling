package utils;

public class ArrayUtils {

	/**
	 * Concats 2 arrays (not primitive)
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static Class<?>[] concatArrays(Class<?>[] first, Class<?>[] second) {
		
		Class<?>[] erg = new Class<?>[first.length + second.length];
		System.arraycopy(first, 0, erg, 0, first.length);
		System.arraycopy(second, 0, erg, first.length, second.length);
		
		return erg;
	}

	/**
	 * Concats 2 arrays
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	static byte[] concatArrays(byte[] first, byte[] second) {
		
		byte[] erg = new byte[first.length + second.length];
		System.arraycopy(first, 0, erg, 0, first.length);
		System.arraycopy(second, 0, erg, first.length, second.length);
		
		return erg;
	}

}
