package sMath;

import gnu.trove.set.hash.THashSet;

public interface IConstant extends Expression {
	public default IConstant evaluate(IConstant[] arguments){
		return this;
	}
	public default THashSet<VariableSymbol> getDependantVariables(){
		return new THashSet<>(0);
	}
}
