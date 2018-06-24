package sMath.numerical.interfaces;

public interface IRealNumber extends INumber, IRounding, Comparable<IRealNumber>, IBitAble{
	int compareTo(IRealNumber o);
	byte signum();
}
