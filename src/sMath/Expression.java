package sMath;

import gnu.trove.set.hash.THashSet;

public interface Expression{
	public abstract String toString();
	public abstract THashSet<VariableSymbol> getDependantVariables();
}
