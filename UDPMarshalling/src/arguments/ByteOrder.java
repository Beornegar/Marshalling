package arguments;

public enum ByteOrder {
    BIGENDIAN("bigendian"),LITTLEENDIAN("littleendian");

    private final String value;

    ByteOrder(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    /**
     * @return the Enum representation for the given string.
     * @throws IllegalArgumentException if unknown string.
     */
    public static ByteOrder fromString(String s) throws IllegalArgumentException {
        
        for(ByteOrder pM:ByteOrder.values()) {
            if(pM.getValue().equals(s.toLowerCase())) {
                return pM;
            }
        }
        
        throw new IllegalArgumentException("unknown value: " + s+" valid Values are: "+ByteOrder.values());
    }

}
