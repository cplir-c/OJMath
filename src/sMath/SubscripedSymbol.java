package sMath;

import sMath.numerical.integer.Integer;
public class SubscripedSymbol<T extends Expression> extends VariableSymbol {
	final T subscript;
	public SubscripedSymbol(char letter,T l) {
		super(letter);
		subscript=l;
	}
}
