package utils;

public class LoggingUtil {

    /**
     * Uses a {@link String} (Formatted) the boolean verbose, and at last some uncounted aguments
     * like prinf does to print a formatted string, also adds a newLine to each call
     * @param string
     * @param verbose
     * @param arguments
     */
    public static void printIfVerbose(String string, boolean verbose, Object ...arguments ) {
        if (verbose)
            System.out.printf("\n"+string,arguments);

    }
    
    public static void printIfVerbose(String title,int[][] printArray, boolean verbose) {
        if (verbose) {
            System.out.println("");
            System.out.println(title);
            for(int[] i:printArray) {
                String newLine = "";
                for(int j:i) {
                    newLine+= j+";";
                }
                System.out.println(newLine);
            }
        }
            

    }
    
    public static void printIfVerbose(String title, Object[][] printArray, boolean verbose) {
        if (verbose) {
            System.out.println("");
            System.out.println(title);
            for(Object[] i:printArray) {
                String newLine = "";
                for(Object j:i) {
                    newLine+= j+";";
                }
                System.out.println(newLine);
            }
        }
            

    }


}
