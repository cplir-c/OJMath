package sMath.numerical.rational;

import sMath.numerical.Number;
import sMath.numerical.integer.Integer;

public class LongFloat extends Integer {
	final int exponent;
	protected LongFloat(double n){
		//exponent: 0x7ff0000000000000L 52 bit shift
		//mantissa: 0x000fffffffffffffL
		//sign: 0x8000000000000000L
		super((Double.doubleToLongBits(n)&0x000fffffffffffffL)*(byte)Double.compare(n,0));//mantissa
		exponent=(int) ((Double.doubleToLongBits(n)<<1)>>53);//catch the last bit as the sign of the exponent
	}
	public static Number valueOf(long self, long denominator) {
		// TODO Auto-generated method stub
		return null;
	}
}
