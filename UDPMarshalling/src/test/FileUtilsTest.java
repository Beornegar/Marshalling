package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import arguments.ArgumentParsing;
import arguments.Options;
import arguments.OptionsContainer;


public class FileUtilsTest {

    @Test
    void parseStringsFromArgsTest() {

        Options erg = null;
        String[] args = new String[6];
        args[0] = "-n";
        args[1] = "626";
        args[2] = "-m";
        args[3] = "627";

        args[4] = "-verbose";
        args[5] = "true";
        erg = ArgumentParsing.parseStringsFromArgs(args);

        assertNotNull(erg);
        assertEquals(erg.getM(), 627);
        assertEquals(erg.getN(), 626);
        assertEquals(erg.getVerbose(), true);
    }
	
	   @Test
	    void parseStringsFromArgsWithStupidLowerAndUpperCasesTest() {
	        
	        OptionsContainer erg = null;
	        String[] args = new String[2];
	        
	          args[0] = "-vErBoSe";
	          args[1] = "true";
	        erg = ArgumentParsing.parseStringsFromArgs(args);
	        
	        assertNotNull(erg);
	        assertEquals(erg.getVerbose(), true);
	    }
	   
       @Test
       void parseStringsFromArgsWithBoolean1Test() {
           
           OptionsContainer erg = null;
           String[] args = new String[2];
           
             args[0] = "-vErBoSe";
             args[1] = "0";
           erg = ArgumentParsing.parseStringsFromArgs(args);
           
           assertNotNull(erg);
           assertEquals(erg.getVerbose(), false);
       }
       
       @Test
       void parseStringsFromArgsWithBoolean2Test() {
           
           OptionsContainer erg = null;
           String[] args = new String[2];
           
             args[0] = "-vErBoSe";
             args[1] = "off";
           erg = ArgumentParsing.parseStringsFromArgs(args);
           
           assertNotNull(erg);
           assertEquals(erg.getVerbose(), false);
       }
	
	
}
