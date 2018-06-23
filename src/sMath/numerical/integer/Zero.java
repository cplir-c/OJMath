package sMath.numerical.integer;

import java.util.concurrent.ForkJoinPool;

import sMath.numerical.Number;
import sMath.utility.ThreadArrayOperation;

public class Zero implements Number {
	private Zero() {};
	public static final Zero ZERO=new Zero();
	@Override
	public Number negate() {
		return this;
	}
	
	@Override
	public String toString() {
		return "0";
	}

	@Override
	public Number add(Number number) {
		return number;
	}

	@Override
	public Number multiply(Number b) {
		return this;
	}

	@Override
	public Number divide(Number b) {
		return this;
	}

	@Override
	public int compareTo(Number o) {
		return -o.signum();
	}

	@Override
	public Number rightBitShift(Number n) {
		return this;
	}

	@Override
	public Number leftBitShift(Number n) {
		return this;
	}

	@Override
	public Number logicalBitShift(Number n) {
		return this;
	}

	@Override
	public String toBinaryString() {
		return "0b0";
	}

	@Override
	public String toHexString() {
		return "0x0";
	}

	@Override
	public Number bitwiseAnd(Number n) {
		return this;
	}

	@Override
	public Number bitwiseOr(Number n) {
		return n;
	}

	@Override
	public Number bitwiseNot() {
		return Integer.valueOf(-1);
	}

	@Override
	public Number bitwiseNor(Number n) {
		return n.bitwiseNot();
	}

	@Override
	public Number bitwiseXor(Number n) {
		return n;
	}
	@Override
	public Number bitwiseNand(Number n) {
		return Integer.valueOf(-1);
	}
	@Override
	public long getLongBits(int offset) {
		return 0;
	}
	@Override
	public void getLongBits(int offset, long[] container) {
		if(container.length<512)
			java.util.Arrays.fill(container,0);
		else {
			class ZeroArray extends ThreadArrayOperation{

				/***/private static final long serialVersionUID = 4347128162929098160L;

				@Override
				public ThreadArrayOperation construct() {
					return new ZeroArray();
				}

				@Override
				protected void operation() {
					for(int i=start;i<end;i++)
						container[i]=0;
				}
				
			}
			ThreadArrayOperation toNegate=new ZeroArray().setFields(0,container.length);
			ForkJoinPool.commonPool().invoke(toNegate);
		}
	}
	@Override
	public int firstSignificant() {
		return 0;
	}
	@Override
	public int lastSignificant() {
		return 0;
	}
	@Override
	public byte signum() {
		return 0;
	}
	@Override
	public Number floorDivide(Number b) {
		return /*b==this?NaN:*/this;//NaN if 0/0
	}
}
