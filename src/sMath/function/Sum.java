package sMath.function;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

import gnu.trove.set.hash.THashSet;
import sMath.Expression;
import sMath.VariableSymbol;
import sMath.function.Product;
import sMath.function.interfaces.ICommutative;
import sMath.function.interfaces.IMultivariate;
import sMath.utility.CombineOnEqualSet;
import sMath.numerical.integer.Integer;
import sMath.numerical.integer.Zero;
import sMath.numerical.interfaces.INumber;

public class Sum extends CombineOnEqualSet implements IMultivariate,ICommutative {
	private Sum(){}//for externalization
	protected Sum(Expression ... args){
		super(args.length);
		for(Expression arg:args)
			computeIfAbsent(arg, Sum::stripConstant);
	}
	public static Expression sum(Expression... args) {
		return sum(Arrays.asList(args));
	}
	protected static Expression stripConstant(Expression e) {
		return (e instanceof Product&&((Product)e).containsKey(Zero.ZERO))?Product.product(((Product)e).values().parallelStream().unordered().filter(a -> !(a instanceof INumber) ).toArray(Expression[]::new)):e;
	}
	public static Expression sum(Collection<Expression> args) {
		Predicate<Object> p=Sum.class::isInstance;
		if(args.parallelStream().anyMatch(p)) {
			Expression[] s=args.parallelStream().filter(p).flatMap(a->((Sum)a).arguments().parallelStream()).toArray(Expression[]::new);
			args.removeIf(p);
			args.addAll(Arrays.asList(s));
		}
		if(args.size()<2)//1 or 0
			if(args.isEmpty())//0
				return Zero.ZERO;
			else
				return args.iterator().next();
		return new Sum(args.toArray(new Expression[args.size()]));
	}
	@Override
	public Collection<Expression> arguments() {
		return Collections.unmodifiableCollection(values());
	}
	@Override
	protected <T extends Expression> Expression combine(T orig, T old) {
		if(orig instanceof Product) {
			Product first=(Product) orig;
			Product second=(Product) old;
			INumber coefficientOne=(INumber) first.get(Integer.valueOf(1));
			INumber coefficientTwo=(INumber) second.get(Integer.valueOf(1));
			if(coefficientOne==null)
				if(coefficientTwo==null)
					coefficientOne=Integer.valueOf(2);
				else
					coefficientOne=coefficientTwo.add(Integer.valueOf(1));
			else
				if(coefficientTwo==null)
					coefficientOne=coefficientOne.add(Integer.valueOf(1));
				else
					coefficientOne=coefficientOne.add(coefficientTwo);
		} else if(orig instanceof INumber) {
			return ((INumber)orig).add((INumber)old);
		} else if(orig.equals(old)) {
			return Product.product(Integer.valueOf(2),orig);
		}
		throw new IllegalArgumentException();
	}
}
