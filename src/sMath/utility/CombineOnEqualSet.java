package sMath.utility;

import java.util.Collection;
import java.util.Map;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import sMath.Expression;
import sMath.VariableSymbol;
public abstract class CombineOnEqualSet extends THashMap<Expression,Expression> implements Expression{
	protected CombineOnEqualSet() {
	}
	protected CombineOnEqualSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	protected CombineOnEqualSet(int initialCapacity) {
		super(initialCapacity);
	}
	protected CombineOnEqualSet(Map<? extends Expression, ? extends Expression> map) {
		super(map);
	}
	protected CombineOnEqualSet(THashMap<? extends Expression, ? extends Expression> map) {
		super(map);
	}
	@Override
	public THashSet<VariableSymbol> getDependantVariables() {
		return keySet().parallelStream().unordered().flatMap(a->this.get(a).getDependantVariables().parallelStream()).distinct().collect(THashSet<VariableSymbol>::new, THashSet::add, (a,b)->a.addAll(b));
	}
	@Override
	public Expression put(Expression key,Expression term) {
		Expression old=get(key);
		if(old!=null) {
			term=combine(term,old);
		}
		return super.put(key,term);
	}
	@Override
	public String toString() {
		Collection<Expression> i=values();
		return String.join("",i.parallelStream().map(Expression::toString).toArray(String[]::new));
	}
	protected abstract <T extends Expression> Expression combine(T e, T old);
}
