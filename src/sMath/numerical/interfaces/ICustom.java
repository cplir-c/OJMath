package sMath.numerical.interfaces;

import sMath.numerical.Number;

public interface ICustom extends IArithmatic{//ask this implementing class, use these if noncommutative
	public Number dividedBy(Number number);
	public default Number divide(Number number) {return dividedBy(number);}
}
