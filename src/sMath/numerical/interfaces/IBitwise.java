package sMath.numerical.interfaces;

import sMath.numerical.Number;

public interface IBitwise extends ILongBits{
	public Number rightBitShift(Number n);//>>
	public Number leftBitShift(Number n);//<<
	public Number logicalBitShift(Number n);//>>>
	public String toBinaryString();
	public String toHexString();
	public Number bitwiseAnd(Number n);//&
	public Number bitwiseOr(Number n);//|
	public Number bitwiseNot();//~
	public default Number bitwiseNor(Number n) {
		return bitwiseOr(n).bitwiseNot();
	}//~(|)
	public Number bitwiseXor(Number n);//^
	public default Number bitwiseNand(Number n) {
		return bitwiseAnd(n).bitwiseNot();
	}//~(&)
}
