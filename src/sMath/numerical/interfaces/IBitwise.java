package sMath.numerical.interfaces;

public interface IBitwise{
	public INumber rightBitShift(INumber n);//>>
	public INumber leftBitShift(INumber n);//<<
	public INumber logicalBitShift(INumber n);//>>>
	public String toBinaryString();
	public String toHexString();
	public INumber bitwiseAnd(INumber n);//&
	public INumber bitwiseOr(INumber n);//|
	public INumber bitwiseNot();//~
	public default INumber bitwiseNor(INumber n) {
		return bitwiseOr(n).bitwiseNot();
	}//~(this|n)
	public INumber bitwiseXor(INumber n);//^
	public default INumber bitwiseNand(INumber n) {
		return bitwiseAnd(n).bitwiseNot();
	}//~(this&n)
}
