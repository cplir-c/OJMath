package sMath.numerical.interfaces;

import java.util.ConcurrentModificationException;

public interface IMutable {
	public int hashCode() throws ConcurrentModificationException;
}
