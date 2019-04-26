package utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DeConverter {

    private DeConverter() {}

    public static int getUsedLength(byte[] b, ByteOrder order, int index) {
        ByteBuffer bb = wrapByteBufferAndChangeToEndianType(b, order);
        int usedPacketLength = bb.getInt(index * 4);
        return usedPacketLength;
    }



    public static int[][] readIntFromByte(byte[] b, ByteOrder order) {

        int packetSize = b.length;
        ByteBuffer bb = wrapByteBufferAndChangeToEndianType(b, order);

        // int usedPacketLength = bb.getInt();
        int n = bb.getInt();
        int m = bb.getInt();
        int[][] result = new int[n][m];


        int nStart = 0;
        int mStart = 0;
        while (bb.position() < ((n * m + 2) * 4 - 1) && bb.position() < packetSize) {
            result[nStart][mStart] = bb.getInt();
            mStart++;
            if (mStart >= result[0].length) {
                mStart = 0;
                nStart++;
            }
        }

        return result;
    }

    public static int[][] readIntFromByte(byte[] b, ByteOrder order, int[][] result, int mStart, int nStart, int usedPacketLength) {

        int packetSize = b.length;
        ByteBuffer bb = wrapByteBufferAndChangeToEndianType(b, order);

        while (bb.position() < usedPacketLength && bb.position() < packetSize) {

            result[nStart][mStart] = bb.getInt();
            mStart++;
            if (mStart >= result[0].length) {
                mStart = 0;
                nStart++;
            }
            if (nStart >= result.length)
                return result;
        }

        return result;
    }

    private static ByteBuffer wrapByteBufferAndChangeToEndianType(byte[] b, ByteOrder order) {
        ByteBuffer bb = ByteBuffer.wrap(b);
        bb = bb.order(order);
        return bb;
    }


}
