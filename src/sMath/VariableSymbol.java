package sMath;

import gnu.trove.set.hash.THashSet;
import sMath.function.interfaces.Function;

public class VariableSymbol implements Function {
	protected final char name;
	Expression value;
	public VariableSymbol(char letter) {
		name=letter;
	}
	@Override
	public THashSet<VariableSymbol> getDependantVariables() {
		if(value==null) {
			THashSet<VariableSymbol> a =new THashSet(1);
			a.add(this);
			return a;
		}
		return value.getDependantVariables();
	}
	
	@Override
	public String toString() {
		return value==null?String.valueOf(name):value.toString();
	}
}
