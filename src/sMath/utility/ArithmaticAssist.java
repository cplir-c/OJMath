package sMath.utility;

public class ArithmaticAssist {
	private ArithmaticAssist(){}
	
	/**
	 * @return true if l is a power of two*/
	public static boolean isPowerOfTwo(long l) {
		if(l<0) l=-l;//-9223372036854775808 (Long.MIN_VALUE) is represented by only the sign bit true, so it fits the pattern
		return (l&(l-1))==0;
	}
	public static boolean safeToAdd(long self,long other) {
		long r=self+other;
		return (((self ^ r) & (other ^ r)) >= 0);
	}
	//from http://hg.openjdk.java.net/jdk9/jdk9/jdk/file/tip/src/java.base/share/classes/java/lang/Math.java#multiplyHigh and subject to the license referred to on lines two through twenty three
	/**
     * Returns as a {@code long} the most significant 64 bits of the 128-bit
     * product of two 64-bit factors.
     *
     * @param x the first value
     * @param y the second value
     * @return the result
     * @since 9
     */
    public static long multiplyHigh(long x, long y) {
        if (x < 0 || y < 0) {
            // Use technique from section 8-2 of Henry S. Warren, Jr.,
            // Hacker's Delight (2nd ed.) (Addison Wesley, 2013), 173-174.
            long x1 = x >> 32;
            long x2 = x & 0xFFFFFFFFL;
            long y1 = y >> 32;
            long y2 = y & 0xFFFFFFFFL;
            long z2 = x2 * y2;
            long t = x1 * y2 + (z2 >>> 32);
            long z1 = t & 0xFFFFFFFFL;
            long z0 = t >> 32;
            z1 += x2 * y1;
            return x1 * y1 + z0 + (z1 >> 32);
        } else {
            // Use Karatsuba technique with two base 2^32 digits.
            long x1 = x >>> 32;
            long y1 = y >>> 32;
            long x2 = x & 0xFFFFFFFFL;
            long y2 = y & 0xFFFFFFFFL;
            long A = x1 * y1;
            long B = x2 * y2;
            long C = (x1 + x2) * (y1 + y2);
            long K = C - A - B;
            return (((B >>> 32) + K) >>> 32) + A;
        }
    }
}
