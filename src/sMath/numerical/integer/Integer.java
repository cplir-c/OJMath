package sMath.numerical.integer;

import sMath.numerical.interfaces.INumber;
import sMath.numerical.interfaces.IRealNumber;
import sMath.numerical.interfaces.ICustom;
import sMath.numerical.interfaces.IFraction;
import sMath.numerical.interfaces.IInteger;
import sMath.numerical.interfaces.ILongBits;
import sMath.numerical.interfaces.ISparse;
import sMath.numerical.rational.LongFloat;
import sMath.numerical.rational.MixedNumber;
import sMath.utility.ArithmaticAssist;
import sMath.numerical.rational.LongFraction;

import java.lang.Long;
import java.lang.ref.WeakReference;
import java.util.Arrays;

import gnu.trove.TCollections;
import gnu.trove.map.TIntLongMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
public class Integer implements IInteger{
	public final long self;
	@SuppressWarnings("unchecked")
	protected static final WeakReference<IInteger>[] cache=new WeakReference[255];
	public static final String CLASS_NAME="LsMath.numerical.integer.Integer";
	protected Integer(long integer){
		self=integer;
	}
	public static IInteger valueOf(long l) {
		if(((byte)l)==l) {
			if(l==0)
				return Zero.ZERO;
			WeakReference<IInteger> at=cache[(int) (l+128)];
			if (at==null||at.get()==null){
			    Integer k=new Integer(l);
				at=new WeakReference<>(k);
				cache[(int) (l+128)]=at;
			}
			return at.get();
		}
		return new Integer(l);
	}
	@Override
	protected void finalize() throws Throwable {
		if(((self+128)&0xffffffffffffff00L)==0)
			cache[(short) (self+128)]=null;
		super.finalize();
	}
	@Override
	public String toString() {
		return self<0?'-'+Long.toHexString(-self):Long.toHexString(self);
	}
	public boolean equals(Object other){
		return other.getClass()==Integer.class&&((Integer)other).self==self;
	}
	public int hashCode(){
		return (int) (self^(self>>>32));
	}
	@Override
	public INumber negate(){
		return self==-self?BigInteger.valueOf(1,new long[]{0}):Integer.valueOf(-self);
		//otherwise it might not negate
	}
	@Override
	public int compareTo(IRealNumber o) {
		return (o.getClass()==Integer.class)?Long.compare(self,((Integer)o).self):-o.compareTo(this);
	}
	@Override
	public INumber add(INumber number) {
		if(number.getClass()==Integer.class){
			long other=((Integer)number).self;
			long r=self+other;
			if(((self ^ r) & (other ^ r)) >= 0)
				return valueOf(r);
			else {
				//in order to overflow the arguments must be the same sign
				//just add the rest
				//signum should be the same signed addition overflow result
				return BigInteger.valueOf((long)signum(),new long[]{other+self});
			}
		} else {
			return number.add(this);
		}
	}
	@Override
	public INumber multiply(INumber b) {
		if(b.getClass()==Integer.class) {
			long other=((Integer)b).self;
			long result = self * other;
			if (((Math.abs(self) | Math.abs(other)) >>> 31 != 0)&&//o
					( ((other != 0) && (result / other != self)) ||
							(self == Long.MIN_VALUE && other == -1) )) {
				return valueOf(result);
			} else {
				return BigInteger.valueOf(sMath.utility.ArithmaticAssist.multiplyHigh(self, other),new long[]{result});//o: use the jdk 9 multiplyHigh method in a polyfill
			}
		} else {
			return b.multiply(this);
		}
	}
	@Override
	public INumber divide(INumber b) {
	    if(b.getClass()==Integer.class){
	        long other=((Integer)b).self;
	    	return self%other==0?valueOf(self/other):(self>other?MixedNumber.valueOf(Integer.valueOf(self/other),self%other,other):LongFraction.valueOf(self,other));
	    } else if(b instanceof ILongBits) {//BitInterface time
	        return this.compareTo((IRealNumber) b)>0?MixedNumber.valueOf(floorDivide(b), self, self):Fraction.valueOf(this,b);
	    } else// if(b instanceof IFraction||b instanceof ICustom||b instanceof ISparse) {
			return b.reciprocal().multiply(this);
	}
	@Override
	public INumber rightBitShift(INumber n) {//>>
		if(n.getClass()==Integer.class) {
			Integer o=(Integer) n;
			long other=o.self;
			//test if it fits in a long, otherwise relegate it to a LongFloat
			
			if(other<0){
			    
			} else {
			    
			}
		} else if(n instanceof ILongBits){//BitInterface time
			ILongBits bits=(ILongBits)n;
		    int high=bits.startPosition();
		    int low=bits.endPosition();
		} else if(n instanceof IFraction) {
			return n.reciprocal().multiply(this);
		} else if(n instanceof ISparse) {
			
		}
		return null;
	}
	@Override
	public INumber leftBitShift(INumber n) {//<<
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber logicalBitShift(INumber n) {//>>>
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toBinaryString() {
		return Long.toBinaryString(self);
	}
	@Override
	public INumber bitwiseNot() {
		return valueOf(~self);
	}
	@Override
	public INumber bitwiseAnd(INumber n) {
		if(n.getClass()==Integer.class){
			return valueOf(self&((Integer)n).self);
		} else {
			return n.bitwiseAnd(this);
		}
	}
	@Override
	public INumber bitwiseOr(INumber n) {
		if(n.getClass()==Integer.class){
			return valueOf(self|((Integer)n).self);
		} else {
			return n.bitwiseOr(this);
		}
	}
	@Override
	public INumber bitwiseNor(INumber n) {
		if(n.getClass()==Integer.class){
			return valueOf(~(((Integer)n).self|self));
		} else {
			return n.bitwiseNor(this);
		}
	}
	@Override
	public INumber bitwiseXor(INumber n){
		if(n.getClass()==Integer.class){
			return valueOf(((Integer)n).self^self);
		} else {
			return n.bitwiseXor(this);
		}
	}
	@Override
	public INumber bitwiseNand(INumber n) {
		if(n.getClass()==Integer.class){
			return valueOf(~(((Integer)n).self&self));
		} else {
			return n.bitwiseNand(this);
		}
	}
	@Override
	public String toHexString() {
		return Long.toHexString(self);
	}
	@Override
	public long getLongBits(int offset) {
		return (offset&0xffffffc0)==0?self>>>offset:
			(offset<0&&offset>-64?self<<-offset:0);
	}
	@Override
	public byte signum() {
		return (byte)Long.signum(self);
	}
	@Override
	public int startPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int endPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public IInteger floorDivide(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber reciprocal() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ILongBits getLongBits() {
		return this;
	}
}
