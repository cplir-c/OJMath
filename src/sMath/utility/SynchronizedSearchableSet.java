package sMath.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class SynchronizedSearchableSet<E> extends SearchableWeakHashSet<E> implements Set<E>{
	private Object lock=new Object();
	public SynchronizedSearchableSet() {
		super();
	}

	public SynchronizedSearchableSet(Collection<? extends E> collection) {
		super(collection);
	}

	public SynchronizedSearchableSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public SynchronizedSearchableSet(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public int size() {
		synchronized(lock) {return super.size();}
	}

	@Override
	public boolean isEmpty() {
		synchronized(lock) {return super.isEmpty();}
	}

	@Override
	public boolean contains(Object o) {
		synchronized(lock) {return super.contains(o);}
	}

	@Override
	public Iterator<E> iterator() {
		synchronized(lock) {
			Iterator<WeakHashItem<E>> setIterator=set.iterator();
			return new Iterator<E>() {

				@Override
				public boolean hasNext() {
					synchronized(lock) {return setIterator.hasNext();}
				}

				@Override
				public E next() {
					synchronized(lock){return setIterator.next().get();}
				}
				
				@Override
				public void remove() {
					synchronized(lock){setIterator.remove();}
				}
			};
		}
	}

	@Override
	public Object[] toArray() {
		synchronized(lock) {return super.toArray();}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized(lock) {return super.toArray(a);}
	}

	@Override
	public boolean add(E e) {
		synchronized(lock) {return super.add(e);}
	}

	@Override
	public boolean remove(Object o) {
		synchronized(lock) {return super.remove(o);}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized(lock) {return super.containsAll(c);}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		synchronized(lock) {return super.addAll(c);}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized(lock) {return super.retainAll(c);}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized(lock) {return super.removeAll(c);}
	}

	@Override
	public void clear() {
		synchronized(lock) {super.clear();}
	}
	
	@Override
	public E retrieve(Object obj) {
		synchronized(lock) {return super.retrieve(obj);}
	}
}
