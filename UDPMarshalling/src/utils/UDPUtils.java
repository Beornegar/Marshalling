package utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteOrder;
import java.util.Arrays;

public class UDPUtils {

    /**
     * 
     * Add matrix n then m to array
     * 
     * @param array
     * @param n
     * @param m
     * @param order
     * @return
     */
    private static byte[] addArrayMetadataInfo(byte[] array, int n, int m, ByteOrder order) {

        byte[] erg = ArrayUtils.concatArrays(Converter.intToByteArray(n, order), Converter.intToByteArray(m, order));
        erg = ArrayUtils.concatArrays(erg, array);

        return erg;
    }

    /**
     * 
     * Add Length of Array to array
     * 
     * @param array
     * @param order
     * @return
     */
    private static byte[] addSizeOfArray(byte[] array, ByteOrder order) {

        byte[] length = Converter.intToByteArray(array.length + 4, order);
        byte[] erg = ArrayUtils.concatArrays(length, array);
        return erg;
    }

    /**
     * Add ArrayMetaData(n,m) and Size(Used Bytes) <br>
     * as Info to the start of the byte Array <br>
     * Order : Size, n, m, data
     * 
     * @param array
     * @param n
     * @param m
     * @param order
     * @return
     */
    public static byte[] addMetaData(byte[] array, int n, int m, ByteOrder order) {
        byte[] erg = addArrayMetadataInfo(array, n, m, order);
        // erg = addSizeOfArray(erg, order);
        return erg;
    }

    /**
     * Split array into certain amount of UDP Packages <br>
     * dependant on the given packageSize
     * 
     * @param array
     * @param packageSize
     * @param ia
     * @param port
     * @return
     */
    public static DatagramPacket[] getPackets(byte[] array, int packageSize, InetAddress ia, int port) {

        int length = array.length;
        int numberOfPackages = (int) Math.ceil(((double) length) / packageSize);

        DatagramPacket[] erg = new DatagramPacket[numberOfPackages];
        for (int i = 0; i < numberOfPackages; i++) {
            byte[] part = Arrays.copyOfRange(array, i * packageSize, (i + 1) * packageSize);
            erg[i] = new DatagramPacket(part, packageSize, ia, port);
        }

        return erg;
    }

}
