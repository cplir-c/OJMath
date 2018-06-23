package sMath.function;

import java.util.Collection;
import java.util.Collections;

import gnu.trove.set.hash.THashSet;
import sMath.utility.CombineOnEqualSet;
import sMath.Expression;
import sMath.VariableSymbol;
import sMath.function.interfaces.IMultivariate;
import sMath.numerical.Number;

public class Product extends CombineOnEqualSet implements IMultivariate {
	protected Product(Expression...args) {
		super(args.length);
	}
	@Override
	public Collection<Expression> arguments() {
		return Collections.unmodifiableCollection(values());
	}

	@Override
	public THashSet<VariableSymbol> getDependantVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <T extends Expression> Expression combine(T e, T old) {
		// TODO Auto-generated method stub
		return null;
	}
	public static Expression product(Expression... expressions) {
		// TODO Auto-generated method stub
		return null;
	}

}
