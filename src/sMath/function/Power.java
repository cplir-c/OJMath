package sMath.function;

import java.util.Arrays;
import java.util.Collection;

import gnu.trove.set.hash.THashSet;
import sMath.Expression;
import sMath.VariableSymbol;
import sMath.function.interfaces.IMultivariate;

public class Power implements IMultivariate {
	protected Expression base, exponent;
	protected Power(Expression base, Expression exponent) {
		this.base=base;
		this.exponent=exponent;
	}
	@Override
	public Collection<Expression> arguments() {
		return Arrays.asList(base,exponent);
	}
	@Override
	public THashSet<VariableSymbol> getDependantVariables() {
		THashSet<VariableSymbol> s=base.getDependantVariables();
		THashSet<VariableSymbol> l=exponent.getDependantVariables();
		if(s.size()<l.size()) {
			l.addAll(s);return l;
		} else {
			s.addAll(l);return s;
		}
	}

}
