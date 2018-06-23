package sMath.numerical.rational;

import sMath.numerical.Number;
import sMath.numerical.integer.Integer;
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
	public Number negate() {
		return Binimal.valueOf((BigInteger)super.negate(),exponent);
	}
	public static Number valueOf(BigInteger mantissa, int exponent) {
		return valueOf(mantissa.self,mantissa.array,exponent);
	}
	public static Number valueOf(long self, long[] array, int exponent) {
		return null;
	}
	@Override
	public Number add(Number number) {
		if (number instanceof Integer) {
			
		} else if (number instanceof Binimal) {
			
		}
		return number.add(this);
	}
	@Override
	public Number multiply(Number b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number divide(Number b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int compareTo(Number o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Number rightBitShift(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number leftBitShift(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number logicalBitShift(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toBinaryString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseAnd(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseOr(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseNot() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseNor(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseXor(Number n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Number bitwiseNand(Number n) {
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
