package sMath.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class SynchronizedReadWriteSearchableSet<E> extends SearchableWeakHashSet<E> implements Set<E>{
	ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
	public SynchronizedReadWriteSearchableSet() {
		super();
	}

	public SynchronizedReadWriteSearchableSet(Collection<? extends E> collection) {
		super(collection);
	}

	public SynchronizedReadWriteSearchableSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public SynchronizedReadWriteSearchableSet(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	public int size() {
		lock.readLock().lock();
		try{return super.size();}
		finally {lock.readLock().unlock();}
	}

	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{return super.isEmpty();}
		finally {lock.readLock().unlock();}
	}

	@Override
	public boolean contains(Object o) {
		lock.readLock().lock();
		try{return super.contains(o);}
		finally {lock.readLock().unlock();}
	}

	@Override
	public Iterator<E> iterator() {
		lock.readLock().lock();
		try {
			Iterator<WeakHashItem<E>> setIterator=set.iterator();
			return new Iterator<E>() {

				@Override
				public boolean hasNext() {
					lock.readLock().lock();
					try {return setIterator.hasNext();}
					finally {lock.readLock().unlock();}
				}

				@Override
				public E next() {
					lock.readLock().lock();
					try{return setIterator.next().get();}
					finally{lock.readLock().unlock();}
				}
				
				@Override
				public void remove() {
					lock.writeLock().lock();
					try{setIterator.remove();}
					finally{lock.writeLock().unlock();}
				}
			};
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Object[] toArray() {
		lock.readLock().lock();
		try{return super.toArray();}
		finally {lock.readLock().unlock();}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		lock.readLock().lock();
		try{return super.toArray(a);}
		finally {lock.readLock().unlock();}
	}

	@Override
	public boolean add(E e) {
		lock.writeLock().lock();
		try {return super.add(e);}
		finally {lock.writeLock().unlock();}
	}

	@Override
	public boolean remove(Object o) {
		lock.writeLock().lock();
		try {return super.remove(o);}
		finally {lock.writeLock().unlock();}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		lock.readLock().lock();
		try {return super.containsAll(c);}
		finally {lock.readLock().unlock();}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		lock.writeLock().lock();
		try {return super.addAll(c);}
		finally {lock.writeLock().unlock();}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		lock.writeLock().lock();
		try {return super.retainAll(c);}
		finally {lock.writeLock().unlock();}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		lock.writeLock().lock();
		try {return super.removeAll(c);}
		finally {lock.writeLock().unlock();}
	}

	@Override
	public void clear() {
		lock.writeLock().lock();
		try {super.clear();}
		finally {lock.writeLock().unlock();}
	}
	
	@Override
	public E retrieve(Object obj) {
		lock.readLock().lock();
		try {return super.retrieve(obj);}
		finally {lock.readLock().unlock();}
	}
}
