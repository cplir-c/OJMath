package sMath.numerical.integer;

import java.util.concurrent.ForkJoinPool;
import sMath.numerical.interfaces.IInteger;
import sMath.numerical.interfaces.ILongBits;
import sMath.utility.ThreadArrayOperation;
import sMath.numerical.interfaces.INumber;
import sMath.numerical.interfaces.IRealNumber;
public class Zero implements IInteger {
	private Zero() {};
	public static final Zero ZERO=new Zero();
	@Override
	public INumber negate() {
		return this;
	}
	
	@Override
	public String toString() {
		return "0";
	}

	@Override
	public INumber add(INumber number) {
		return number;
	}

	@Override
	public INumber multiply(INumber b) {
		return this;
	}

	@Override
	public INumber divide(INumber b) {
		return this;
	}

	@Override
	public int compareTo(IRealNumber o) {
		return -o.signum();
	}

	@Override
	public INumber rightBitShift(INumber n) {
		return this;
	}

	@Override
	public INumber leftBitShift(INumber n) {
		return this;
	}

	@Override
	public INumber logicalBitShift(INumber n) {
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
	public INumber bitwiseAnd(INumber n) {
		return this;
	}

	@Override
	public INumber bitwiseOr(INumber n) {
		return n;
	}

	@Override
	public INumber bitwiseNot() {
		return Integer.valueOf(-1);
	}

	@Override
	public INumber bitwiseNor(INumber n) {
		return n.bitwiseNot();
	}

	@Override
	public INumber bitwiseXor(INumber n) {
		return n;
	}
	@Override
	public INumber bitwiseNand(INumber n) {
		return Integer.valueOf(-1);
	}
	@Override
	public long getLongBits(int offset) {
		return 0;
	}
	@Override
	public byte signum() {
		return 0;
	}
	@Override
	public IInteger floorDivide(INumber b) {
		// TODO Auto-generated method stub
		return null;
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
	public ILongBits getLongBits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INumber reciprocal() {
		// TODO Auto-generated method stub
		return null;
	}
}
