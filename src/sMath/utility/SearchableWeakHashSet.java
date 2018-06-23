package sMath.utility;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gnu.trove.set.hash.THashSet;

public class SearchableWeakHashSet<E> implements Set<E>,Iterable<E>{
	static class SearchableSet<F> extends THashSet<F>{
		@SuppressWarnings("unchecked")
		public F find(Object obj) {
			return (F) _set[super.index(obj)];
		}

		public SearchableSet() {
			super();
		}

		SearchableSet(Collection<? extends F> collection) {
			super(collection);
		}

		SearchableSet(int initialCapacity, float loadFactor) {
			super(initialCapacity, loadFactor);
		}

		SearchableSet(int initialCapacity) {
			super(initialCapacity);
		}
	}
	SearchableSet<WeakHashItem<E>> set;
	protected SearchableWeakHashSet() {
		set=new SearchableSet<>();
	}

	@SuppressWarnings("unchecked")
	public SearchableWeakHashSet(Collection<? extends E> collection) {
		Object[] array=collection.toArray();
		for(int i=0;i<array.length;i++)
			array[i]=new WeakHashItem<E>((E)array[i]);//wrap collection in WeakHashItem using an array
		List<WeakHashItem<E>> list=Arrays.asList((WeakHashItem<E>[])array);
		set=new SearchableSet<>(list);
	}

	public SearchableWeakHashSet(int initialCapacity, float loadFactor) {
		set=new SearchableSet<>(initialCapacity, loadFactor);
	}

	public SearchableWeakHashSet(int initialCapacity) {
		set=new SearchableSet<>(initialCapacity);
	}
	
	public static final class WeakHashItem<T> extends WeakReference<T>{
		public int hashCode;
		public WeakHashItem(T referent) {
			super(referent);
			hashCode=referent.hashCode();
		}
		public WeakHashItem(T referent,int hash) {
			super(referent);
			hashCode=hash;
		}
		@Override
		public int hashCode() {
			return hashCode;
		}
		@Override
		public boolean equals(Object l) {
			if(l instanceof SearchableWeakHashSet.WeakHashItem<?>) {//l is not null
				@SuppressWarnings("unchecked")
				WeakHashItem<T> o=((WeakHashItem<T>)l);
				if(o.hashCode==hashCode) {
					T k=o.get();
					return k.equals(get());
				}
			}
			return false;
		}
	}
	
	@Override
	public boolean add(E obj) {
		return set.add(new WeakHashItem<E>(obj));
	}
	
	public boolean add(E obj,int hash) {
		return set.add(new WeakHashItem<E>(obj,hash));
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<WeakHashItem<E>> setIterator=set.iterator();
		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				return setIterator.hasNext();
			}

			@Override
			public E next() {
				return setIterator.next().get();
			}
			
		};
	}

	@Override
	public Object[] toArray() {
		return set.parallelStream().unordered().map(WeakHashItem<E>::get).toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) set.parallelStream().unordered().map(WeakHashItem<E>::get).toArray();
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean allAdded=true;
		for(E item:c)
			allAdded&=set.add(new WeakHashItem<E> (item));
		return allAdded;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}
	
	public E retrieve(Object obj) {
		WeakHashItem<E> found;
		if((found=set.find(obj))!=null) return found.get();
		return null;
	}
}
