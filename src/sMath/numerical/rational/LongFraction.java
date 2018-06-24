package sMath.numerical.rational;

import sMath.numerical.integer.Fraction;
import sMath.numerical.interfaces.IInteger;
import sMath.numerical.interfaces.ILongBits;
import sMath.numerical.interfaces.INumber;
import sMath.numerical.interfaces.IRealNumber;
import sMath.utility.ArithmaticAssist;
public class LongFraction implements IRealNumber{
	/***/private static final long serialVersionUID = -2181383189529091656L;/***/
	protected final long numerator;
	protected final long denominator;
	protected LongFraction(long a, long b){
		numerator=a;denominator=b;
	}
	public static INumber valueOf(long a,long b) {
		long denominator=b;
		if(denominator<0) {
			if(a==-9223372036854775808L)
				return Fraction.valueOf(a,b);
			denominator=-b;
			a=-a;
		}
		if(denominator<0)//min value
			return Fraction.valueOf(a,b);
		if(ArithmaticAssist.isPowerOfTwo(denominator))
			return LongFloat.valueOf(a,Long.numberOfLeadingZeros(b));
		long gcd=gcd(a,b);
		return new LongFraction(a/gcd,b/gcd);
	}
	public LongFraction add(LongFraction b){
		final long gcd=gcd(this.denominator,b.denominator);
		return new LongFraction(this.numerator*(b.denominator/gcd)+b.numerator*(this.denominator/gcd),this.numerator*b.numerator/gcd);
	}

	public LongFraction negate(){
		return new LongFraction(-this.numerator,this.denominator);
	}
	public LongFraction subtract(LongFraction b){
		return this.add(b.negate());
	}
	public LongFraction multiply(LongFraction b){
		return new LongFraction(this.numerator*b.numerator,this.denominator*b.denominator);
	}
	public LongFraction multiplicativeInverse(){
		return new LongFraction(this.denominator,this.numerator);
	}
	public LongFraction divide(LongFraction b){
		return this.multiply(b.multiplicativeInverse());
	}
	public static LongFraction sum(LongFraction a, LongFraction b){
		return a.add(b);
	}
	/** From https://en.wikipedia.org/wiki/Binary_GCD_algorithm#Iterative_version_in_C */
	static long gcd(long u,long v){
		boolean negative=u<0&&v<0;
		u=Math.abs(u);
		v=Math.abs(v);
		int shift;

		/* GCD(0,v) == v; GCD(u,0) == u, GCD(0,0) == 0 */
		if (u == 0) return v;
		if (v == 0) return u;

		/* Let shift := lg K, where K is the greatest power of 2
	        dividing both u and v. */
		for (shift = 0; ((u | v) & 1) == 0; ++shift) {
			u >>= 1;
		v >>= 1;
		}

		while ((u & 1) == 0)
			u >>= 1;

			/* From here on, u is always odd. */
			do {
				/* remove all factors of 2 in v -- they are not common */
				/*   note: v is not zero, so while will terminate */
				while ((v & 1) == 0)  /* Loop X */
					v >>= 1;

					/* Now u and v are both odd. Swap if necessary so u <= v,
	          then set v = v - u (which is even). For bignums, the
	          swapping is just pointer movement, and the subtraction
	          can be done in-place. */
					if (u > v) {
						long t = v; v = u; u = t; // Swap u and v.
					}

					v = v - u; // Here v >= u.
			} while (v != 0);

			/* restore common factors of 2 */
			long j=u<<shift;

			//if both u and v were negative, result is negative
			return negative?-j:j;
	}
	public String toString(){
		return this.numerator+"/"+this.denominator;
	}
	public int compareTo(LongFraction b) {
		if (this.numerator==b.numerator&&this.denominator==b.denominator)
			return 0;
		if (this.numerator>=b.numerator&&this.denominator<=b.denominator)
			return 1;
		if (this.numerator<=b.numerator&&this.denominator>=b.denominator)
			return -1;
		long gcd=gcd(this.denominator,b.denominator);
		return Integer.signum((int)(this.numerator*(b.denominator/gcd)-b.numerator*(this.denominator/gcd)));
	}
	@Override
	public String toBinaryString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toHexString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber add(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber multiply(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber divide(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IInteger floorDivide(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int compareTo(IRealNumber o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public INumber rightBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber leftBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber logicalBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseAnd(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseOr(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseNot() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseXor(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public byte signum() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ILongBits getLongBits() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber reciprocal() {
		// TODO Auto-generated method stub
		return null;
	}
}