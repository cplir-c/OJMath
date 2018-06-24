package sMath.numerical.interfaces;

public interface ICustom extends IArithmatic{//ask this implementing class, use these if noncommutative
	public INumber dividedBy(INumber number);
	public default INumber divide(INumber number) {return dividedBy(number);}
}
