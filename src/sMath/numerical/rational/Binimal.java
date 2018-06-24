package sMath.numerical.rational;

import sMath.numerical.integer.Integer;
import sMath.numerical.interfaces.INumber;
import sMath.numerical.interfaces.IRealNumber;
import sMath.numerical.integer.BigInteger;
import gnu.trove.map.hash.TLongObjectHashMap;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.stream.LongStream;
public class Binimal extends BigInteger {
	protected int exponent;
	protected Binimal(){
	    this(1,new long[1],1);
	}
	protected Binimal(long start,long[] significand, int place) {
		super(start,significand);
		exponent=place;
	}
	@Override
	public INumber negate() {
		return Binimal.valueOf((BigInteger)super.negate(),exponent);
	}
	public static INumber valueOf(BigInteger mantissa, int exponent) {
		return valueOf(mantissa.self,mantissa.array,exponent);
	}
	public static INumber valueOf(long self, long[] array, int exponent) {
		return null;
	}
	@Override
	public INumber add(INumber number) {
		if (number instanceof Integer) {
			
		} else if (number instanceof Binimal) {
			
		}
		return number.add(this);
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
	public String toBinaryString() {
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
	public INumber bitwiseNor(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseXor(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseNand(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		if(toString!=null&&toString.get()!=null) return toString.get();
		String result=(self<0?"-"+Long.toHexString(-self):Long.toHexString(self))+' '+String.join(" ",LongStream.of(array).mapToObj(a->String.format("%016x",a)).toArray(String[]::new));
		StringBuilder p=new StringBuilder(result.length()+1);
		p.append(result);
		if(exponent/4<0)
			p.insert(result.length()-exponent/4,".");
		else {
			char[] zeros=new char[-exponent/4];
			Arrays.fill(zeros,'0');
			p.append(zeros);
			p.append('.');
		}
		result=p.toString();
		toString=new WeakReference<String>(result);
		return result;
	}
}
