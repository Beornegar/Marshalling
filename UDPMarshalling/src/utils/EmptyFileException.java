package utils;

public class EmptyFileException extends Exception {

	private static final long serialVersionUID = 1L;

	private String text;

	   public EmptyFileException(String text) {
		   this.text = text;
	   }

	   public String toString() {
	      return "EmptyFileException: " + text;
	   }
	
}
