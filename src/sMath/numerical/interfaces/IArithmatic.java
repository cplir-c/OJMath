package sMath.numerical.interfaces;

import sMath.numerical.integer.Zero;

public interface IArithmatic{
	public static final Zero ADDITIVE_IDENTITY=Zero.ZERO;
	public static final Integer MULTIPLICATIVE_IDENTITY=Integer.valueOf(1);
	public INumber negate();
	public default INumber subtract(INumber b){return add(b.negate());}
	public INumber add(INumber b);
	public INumber multiply(INumber b);
	public INumber divide(INumber b);
}
