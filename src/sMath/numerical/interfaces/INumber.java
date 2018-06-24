package sMath.numerical.interfaces;

import sMath.numerical.interfaces.IBitwise;
import sMath.numerical.interfaces.IInteger;
import sMath.numerical.interfaces.IArithmatic;

import sMath.numerical.integer.BigInteger;

import sMath.IConstant;
import sMath.numerical.integer.Integer;
public interface INumber extends IArithmatic, IConstant, IBitwise{
	public static IInteger valueOf(long integer) {
		return Integer.valueOf(integer);
	}
	public static INumber valueOf(long start,long[] k) {
		return BigInteger.valueOf(start,k);
	}
	public INumber reciprocal();
}
