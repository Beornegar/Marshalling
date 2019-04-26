package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class FileUtils {
    
    public static Random random = new Random();

	/**
	 * Creates a byte array of integer values of fixed size <br>
	 * in certain byte order (little/big endian)
	 * 
	 * @param rows = n
	 * @param columns = m
	 * @param order
	 * @return
	 */
	public static byte[] getMatrix(int[][] matrix, ByteOrder order) {

		 

		byte[][] array = Converter.convert(matrix, order);
		byte[] flatArray = Converter.flatten(array, true);
		return flatArray;
	}

	// ------- Create Matrix from File -------
	public static int[][] getRandomIntMatrix() {
		int rows = ((int) (Math.random() * 100)) + 1;
		int columns = ((int) (Math.random() * 100)) + 1;

		return getRandomIntMatrix(rows, columns);
	}

	public static int[][] getRandomIntMatrix(int rows, int columns) {

		int[][] erg = new int[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				erg[i][j] = ((int) random.nextInt(10000)) + 1;
			}
		}
		return erg;
	}

	public static byte[][] readByteMatrixFromFile(String filepath, String delimiter, ByteOrder order) {
		try {
			return Converter.convert(readIntMatrixFromFile(filepath, delimiter), order);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EmptyFileException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int[][] readIntMatrixFromFile(String filepath, String delimiter)
			throws FileNotFoundException, IOException, EmptyFileException {
		List<List<String>> records = readStringValuesFromFile(filepath, delimiter);
		return convertToIntArray(records);
	}

	public static double[][] readDoubleMatrixFromFile(String filepath, String delimiter)
			throws FileNotFoundException, IOException, EmptyFileException {
		List<List<String>> records = readStringValuesFromFile(filepath, delimiter);
		return convertToDoubleArray(records);
	}

	public static String[][] readStringMatrixFromFile(String filepath, String delimiter)
			throws FileNotFoundException, IOException, EmptyFileException {
		List<List<String>> records = readStringValuesFromFile(filepath, delimiter);
		return convertToStringArray(records);
	}

	private static double[][] convertToDoubleArray(List<List<String>> records) {
		notNull(records);

		double[][] erg = new double[records.size()][];
		for (int row = 0; row < records.size(); row++) {

			double[] column = records.get(row).stream().filter(Objects::nonNull).map(s -> (s.length() == 0 ? "0" : s))
					.map(s -> Double.parseDouble(s)).mapToDouble(d -> (d == null ? 0d : d)).toArray();

			erg[row] = column;
		}

		return erg;
	}

	private static int[][] convertToIntArray(List<List<String>> records) {
		notNull(records);

		int[][] erg = new int[records.size()][];
		for (int row = 0; row < records.size(); row++) {

			int[] column = records.get(row).stream().map(s -> Integer.parseInt(s)).mapToInt(i -> (i == null ? 0 : i))
					.toArray();

			erg[row] = column;
		}
		return erg;
	}

	private static String[][] convertToStringArray(List<List<String>> records) {
		notNull(records);

		String[][] erg = new String[records.size()][];
		for (int row = 0; row < records.size(); row++) {

			if (records.get(row) == null) {
				records.set(row, new ArrayList<String>());
			}

			String[] column = records.get(row).stream().toArray(String[]::new);

			erg[row] = column;
		}
		return erg;
	}

	private static void notNull(List<List<String>> records) {
		if (records == null) {
			System.err.println("convertToStringArray : Input [records] is null!");
			records = new ArrayList<>();
		}
	}

	/***
	 * 
	 * Verwendung des Scanners, da dieser auch Float-Values kann Siehe:
	 * https://www.baeldung.com/java-csv-file-array
	 * 
	 * @return variable list of integer values
	 */
	private static List<List<String>> readStringValuesFromFile(String filepath, String delimiter) {
		List<List<String>> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filepath));) {
			while (scanner.hasNextLine()) {
				records.add(getRecordFromLine(scanner.nextLine(), delimiter));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return records;
	}

	/***
	 * 
	 * Read single line of seperated values into a list
	 * 
	 * @param line:
	 *            input line
	 * @param delimiter:
	 *            seperation string of single elements
	 * @return
	 */
	private static List<String> getRecordFromLine(String line, String delimiter) {

		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line);) {
			rowScanner.useDelimiter(delimiter);
			while (rowScanner.hasNext()) {
				String stringValue = rowScanner.next();
				values.add(stringValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return values;
	}

	// ------- Save Matrix to File --------

	public static void saveMatrix(String filepath, String delimiter, int[][] matrix) {
		writeToFile(filepath, getCSVString(delimiter, matrix));
	}

	public static void saveMatrix(String filepath, String delimiter, double[][] matrix) {
		writeToFile(filepath, getCSVString(delimiter, matrix));
	}

	public static void saveMatrix(String filepath, String delimiter, String[][] matrix) {
		writeToFile(filepath, getCSVString(delimiter, matrix));
	}

	private static void writeToFile(String filepath, String text) {
		try (BufferedWriter br = new BufferedWriter(new FileWriter(filepath))) {
			br.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ------- Display Matrix in console --------

	public static void toConsole(String[][] matrix, String delimiter) {
		System.out.println(getCSVString(delimiter, matrix));
	}

	public static void toConsole(int[][] matrix, String delimiter) {
		System.out.println(getCSVString(delimiter, matrix));
	}

	public static void toConsole(double[][] matrix, String delimiter) {
		System.out.println(getCSVString(delimiter, matrix));
	}

	// ------- Convert Matrix to CSV-String --------

	private static String getCSVString(String delimiter, String[][] matrix) {
		StringBuilder sb = new StringBuilder();

		for (String[] row : matrix) {
			for (String element : row) {
				sb.append(element);
				sb.append(delimiter);
			}
			int lastDelimiter = sb.lastIndexOf(delimiter);
			int lengthOfDelimiter = delimiter.length();
			sb.replace(lastDelimiter, lastDelimiter + lengthOfDelimiter, System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private static String getCSVString(String delimiter, int[][] matrix) {
		StringBuilder sb = new StringBuilder();

		for (int[] row : matrix) {
			for (int element : row) {
				sb.append(element + "");
				sb.append(delimiter);
			}
			int lastDelimiter = sb.lastIndexOf(delimiter);
			int lengthOfDelimiter = delimiter.length();
			sb.replace(lastDelimiter, lastDelimiter + lengthOfDelimiter, System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private static String getCSVString(String delimiter, double[][] matrix) {
		StringBuilder sb = new StringBuilder();

		for (double[] row : matrix) {
			for (double element : row) {
				sb.append(element + "");
				sb.append(delimiter);
			}
			int lastDelimiter = sb.lastIndexOf(delimiter);
			int lengthOfDelimiter = delimiter.length();
			sb.replace(lastDelimiter, lastDelimiter + lengthOfDelimiter, System.getProperty("line.separator"));
		}
		return sb.toString();
	}

}
