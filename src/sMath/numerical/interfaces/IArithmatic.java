package sMath.numerical.interfaces;

import sMath.numerical.Number;
import sMath.numerical.integer.Zero;

public interface IArithmatic extends Comparable<Number>{
	public static final Zero ADDITIVE_IDENTITY=Zero.ZERO;
	public static final Integer MULTIPLICATIVE_IDENTITY=Integer.valueOf(1);
	public Number negate();
	public default Number subtract(Number b){return add(b.negate());}
	public Number add(Number number);
	public Number multiply(Number b);
	public Number divide(Number b);
	public IInteger floorDivide(Number b);
}
