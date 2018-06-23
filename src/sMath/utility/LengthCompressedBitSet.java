package sMath.utility;

import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;

public class LengthCompressedBitSet {
	private int length;
	private boolean initial;
	private TreeSet<Integer> boolFlipIndices;/** entries represent the indices at which the bit value changes, ex. 01001110111 would be initial=false with lengths of {1,2,4,7,8} and a length of 11*/
	public LengthCompressedBitSet() {
		initial = false;
		boolFlipIndices = new TreeSet<Integer>();
		length = 0;
	}

	public LengthCompressedBitSet(int arg0) {
		boolFlipIndices = new TreeSet<Integer>();
		length = Math.abs(arg0);
		initial = false;
	}

	static public LengthCompressedBitSet valueOf(byte[] a) {
		BitSet set = BitSet.valueOf(a);
		return valueOf(set);
	}

	static public LengthCompressedBitSet valueOf(long[] a) {
		BitSet set = BitSet.valueOf(a);
		return valueOf(set);
	}

	public int size() {
		return 8 + 64 + boolFlipIndices.size() * 160 + 64 * (int) Math.getExponent(boolFlipIndices.size());
		// boolean initial=8 bits
		// long length=64 bits
		// base iterator list size (64 for a 64 bit pointer to the next value, a
		// 64 bit pointer to the Integer object, and the 32 bit value of the
		// integer object)
		// skip list size (64 bit pointers times the doubles exponent of the
		// size of boolFlipIndices, which is the logarithm base 2 of it, to
		// represent the overhead of the skip list part)
	}

	public boolean get(int i) {
		i=Math.abs(i);
		return (boolFlipIndices.headSet(i, true).size() & 0b1) == (initial ? 1 : 0);
	}

	public void and(LengthCompressedBitSet a) {
		int aSize = a.boolFlipIndices.size();
		int thisSize = boolFlipIndices.size();
		if (thisSize > aSize) {
			Iterator<Integer> iterator = a.boolFlipIndices.iterator();
			Optional<Integer> i;
			if (a.initial) {
				i = Optional.ofNullable(iterator.hasNext()?iterator.next():null);
			} else {
				i = Optional.of(0);
			}
			int j;
			int length = a.length;
			while (i.isPresent()) {
				j =iterator.hasNext()?iterator.next():length;
                clear(i.get().intValue(),j);
				i = Optional.ofNullable(iterator.hasNext()?iterator.next():null);
			}
		} else {
			a.and(this);
		}
	}

	private void clear(int start, int end) {
		//end is exclusive, start is inclusive
		start=Math.abs(start);
		end=Math.abs(end);
		if (end<=start)
			return;// don't do anything if start and end are less than or equal
		final boolean FINAL=get(end);
		boolFlipIndices.subSet(start,end).clear();
		if(get(start))
			boolFlipIndices.add(start);//ensure cleared area is false
		if (get(end)!=FINAL)
			boolFlipIndices.add(end);//ensure the next part matches what used to be there
	}

	private static LengthCompressedBitSet valueOf(java.util.BitSet set) {
		if (set == null)
			return null;
		LengthCompressedBitSet a = new LengthCompressedBitSet(set.length());
		if (set.length() == 0)
			return a;
		a.initial = set.get(0);
		int flipPosition = a.initial ? set.nextClearBit(0) : set.nextSetBit(0);
		int stopPosition = set.length() + 1;
		while (flipPosition < stopPosition&&flipPosition>0) {
			a.boolFlipIndices.add(flipPosition);
			flipPosition = set.get(flipPosition) ? set.nextClearBit(flipPosition) : set.nextSetBit(flipPosition);
		}
		return a;
	}

	public java.util.BitSet toBitSet() {
		java.util.BitSet set = new java.util.BitSet((int) length);
		boolean value = initial;
		int i = 0;
		for (int nextFlip : boolFlipIndices) {
			if (value)
				set.set(i,nextFlip);
			i = nextFlip;
			value = !value;
		}
		return set;
	}

	@Override
	public String toString() {
		return "LengthCompressedBitSet:{" + String.join(", ", Arrays.asList(boolFlipIndices.stream().map(new Function<Integer,String>() {
					volatile boolean value;
					int former = 0;
					Function<Integer,String> setValue(boolean a) {
						value = a;
						return this;
					}
					@Override
					public String apply(Integer arg0) {
						String str = value + "*" + (arg0 - former) + '@' + former;
						former = arg0;
						value = !value;
						return str;
					}
				}.setValue(initial)).toArray(new IntFunction<String[]>(){public String[] apply(int i){return new String[i];}}))) + '}';
	}

	public static LengthCompressedBitSet valueOf(Long[] longs) {
		return LengthCompressedBitSet.valueOf(BitSet.valueOf(java.util.Arrays.asList(longs).parallelStream().mapToLong(a->a.longValue()).toArray()));
	}
	public String toBitString(){
		return String.join(" ",LongStream.of(toBitSet().toLongArray()).mapToObj(a->Long.toBinaryString(a)).toArray(String[]::new));
	}
}
