package arguments;

import java.util.Collections;
import java.util.HashMap;

/**
 * OptionsContainer to Parse Arguments from Commandline and get it into an Proper object for easier
 * handling Also provides read methods and a default implementation and allowed parameters.
 * 
 * @author vdinger
 *
 */
public class Options extends OptionsContainer {
    protected HashMap<String, String> optionsHashMap = new HashMap<String, String>();
    private final String[] allowed = new String[] {"-mode", // Server or Client Mode?
            "-ps", // PackageSize
            "-verbose", // Put out all comments?
            "-address", "-port", "-n", "-m", "-byteorder"

    };

    public Options() {
        super();

        Collections.addAll(super.allowedOptions, allowed);

        InitDefaults();
    }


    private ProgramMode makeProgramMode(String s) {
        return ProgramMode.fromString(s);
    }

    /**
     * Parses String into Enum ByteOrder
     * 
     * @param s
     * @return
     */
    private ByteOrder makeByteOrder(String s) {
        return ByteOrder.fromString(s);
    }


    /**
     * Init for Default Values, which will be taken if nothing is there
     */
    private void InitDefaults() {
        if (defaultOptionsHashMap == null) {
            defaultOptionsHashMap = new HashMap<String, String>();
        }

        defaultOptionsHashMap.put("-mode", ProgramMode.SERVER.getValue());
        defaultOptionsHashMap.put("-ps", "1024");
        defaultOptionsHashMap.put("-address", "127.0.0.1");
        defaultOptionsHashMap.put("-port", "626");
        defaultOptionsHashMap.put("-n", "4");
        defaultOptionsHashMap.put("-m", "4");
        defaultOptionsHashMap.put("-byteorder", ByteOrder.LITTLEENDIAN.getValue());


    }

    public int getPort() {
        return this.makeInteger(getParameterValue("-port"));
    }

    public String getIP() {
        return this.getParameterValue("-address");
    }

    public int getPacketSize() {
        return this.makeInteger(this.getParameterValue("-ps"));
    }

    public ProgramMode getProgramMode() {
        return this.makeProgramMode(this.getParameterValue("-mode"));
    }

    public int getN() {
        return this.makeInteger(this.getParameterValue("-n"));
    }

    public int getM() {
        return this.makeInteger(this.getParameterValue("-m"));
    }

    public java.nio.ByteOrder getByteOrder() {
        ByteOrder byteOrder = this.makeByteOrder(this.getParameterValue("-byteorder"));
        if (ByteOrder.LITTLEENDIAN.equals(byteOrder)) {
            return java.nio.ByteOrder.LITTLE_ENDIAN;
        }
        if (ByteOrder.BIGENDIAN.equals(byteOrder)) {
            return java.nio.ByteOrder.BIG_ENDIAN;
        }
        throw new IllegalArgumentException("Parameter " + byteOrder + " is not valid. Typo?");
    }

}
