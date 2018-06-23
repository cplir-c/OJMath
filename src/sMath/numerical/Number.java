package sMath.numerical;

import sMath.numerical.interfaces.IBitwise;
import sMath.numerical.interfaces.IArithmatic;

import sMath.numerical.integer.BigInteger;

import sMath.IConstant;
import sMath.numerical.integer.Integer;
public interface Number extends IArithmatic, IBitwise, IConstant{
	public static Integer valueOf(long integer) {
		return Integer.valueOf(integer);
	}
	public static Number valueOf(long start,long[] k) {
		return BigInteger.valueOf(start,k);
	}
}
