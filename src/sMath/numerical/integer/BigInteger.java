package sMath.numerical.integer;

import java.lang.ref.WeakReference;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import sMath.numerical.interfaces.INumber;
import sMath.numerical.interfaces.IRealNumber;
import sMath.utility.ArithmaticAssist;
import sMath.utility.SearchableWeakHashSet;
import sMath.utility.SynchronizedSearchableSet;
import sMath.utility.ThreadArrayOperation;
import sMath.utility.SearchableWeakHashSet.WeakHashItem;
//http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/tip/src/share/classes/java/math/BigInteger.java
public class BigInteger extends Integer{
	/**This array is all the bits in the integer after the Integer.self*/
	public final long[] array;
	/**This map is a map from longs to maps of long arrays to weakreferences to BigInteger`s*/
	private static final SearchableWeakHashSet<long[]> cache=new SynchronizedSearchableSet<>();
	public static final String CLASS_NAME="LsMath.numerical.integer.BigInteger";
	protected final int hashCode;
	protected WeakReference<String> toString;
	protected WeakReference<INumber> negate;
	protected WeakReference<String> toBinaryString;
	protected WeakReference<String> toHexString;
	protected BigInteger(long start, long[] i){
		super(start);
		array=i;
		long hash=hash(start,i);
		hashCode=(int) (hash^(hash>>32));
	}
	protected BigInteger(long start, long[] i, long lhash){
		super(start);
		array=i;
		long hash=lhash;
		hashCode=(int) (hash^(hash>>32));
	}
	public static long hash(long start,long[] i){
		return start^hash(i);
	}
	public static long hash(long[] i){
		return StreamSupport.longStream(Arrays.spliterator(i,0,i.length),true)
				.unordered()
				.reduce(0,(a,b)->a^b);
	}
	public static INumber valueOf(long start,long[] i){
		if (i.length==0)
			return valueOf(start);
		if (start==0)
			return valueOf(i[0],Arrays.copyOfRange(i, 1, i.length));
		long lhash=hash(i);
		int hash=(int) (lhash^(lhash>>>32));
		long[] found=cache.retrieve(new Object(){
			@SuppressWarnings("unchecked")
			@Override
			public boolean equals(Object o) {
				return o instanceof WeakHashItem&&((WeakHashItem<long[]>)o).hashCode==hash&&Arrays.equals(((WeakHashItem<long[]>)o).get(),i);
			}
			public int hashCode() {
				return hash;
			}
		});//unify i
		if(found==null) {
			found=i;
			cache.add(i);
		}
		return new BigInteger(start,found,lhash^start);
	}
	@Override
	public boolean equals(Object o) {
		return this==o||(o instanceof BigInteger&&((BigInteger)o).hashCode==hashCode&&((BigInteger)o).self==self&&((BigInteger)o).array==array);
	}
	@Override
	public int hashCode() {
		return hashCode;
	}
	@Override
	public String toString(){
		if (toString!=null&&toString.get()!=null) return toString.get();
		String string= (self<0?"-"+Long.toHexString(-self):Long.toHexString(self))+String.join(" ",LongStream.of(array).mapToObj(Long::toHexString).toArray(String[]::new));
		toString=new WeakReference<String>(string);
		return string;
	}
	@Override
	public INumber negate() {
		if(negate!=null&&negate.get()!=null) return negate.get();
		long[] copy=new long[array.length];
		if(copy.length<512)
			for(int i=0;i<copy.length;i++)
				copy[i]=~array[i];
		else{//instantiate a new ForkJoinTask<Void> that negates pieces of the array up to 512 long in parallel
			//// Indeed. ////
			class NegateArray extends ThreadArrayOperation{

				/***/private static final long serialVersionUID = -6897655701950839407L;

				@Override
				public ThreadArrayOperation construct() {
					return new NegateArray();
				}

				@Override
				protected void operation() {
					for(int i=start;i<end;i++)
						copy[i]=~copy[i];
				}

			}

			ThreadArrayOperation toNegate=new NegateArray().setFields(0,copy.length);
			ForkJoinPool.commonPool().submit(toNegate).join();
		}
		INumber negated=valueOf(-self,copy);
		negate=new WeakReference<>(negated);
		return negated;
	}
	@Override
	public INumber add(INumber number) {
		switch(number.getClass().getName()){
		case(Integer.CLASS_NAME):{
			long other=((Integer) number).self;
			long self=this.self;
			if(other==0)
				return this;
			long[] copy=Arrays.copyOf(array,array.length);
			if(ArithmaticAssist.safeToAdd(other,array[array.length-1])) {
				copy[copy.length-1]+=other;
				return BigInteger.valueOf(self, copy);
			}
			//overflows a long on the end
			long overflow=Long.signum(other);
			copy[copy.length-1]+=other;
			int i;
			for(i=copy.length-2;i>-1&&!ArithmaticAssist.safeToAdd(copy[i], other);i--) {
				copy[i]+=overflow;
			}
			if(i==-1) {//check if the for loop quit cause of safe addition or finishing the array
				if(ArithmaticAssist.safeToAdd(self,overflow)){
					self+=overflow;
					//array length must be 1
					copy[0]+=other;
					return BigInteger.valueOf(self,copy);
				}
				copy=new long[array.length+1];
				System.arraycopy(array, 0, copy, 1, array.length);
				copy[0]=self+overflow;
				copy[1]+=other;
				return BigInteger.valueOf(overflow,copy);
			}
			return BigInteger.valueOf(self, copy);
		}
		case(BigInteger.CLASS_NAME):{
			BigInteger other=(BigInteger) number;
		}
		default:{
			return number.add(this);
		}
		}
	}
	@Override
	public INumber multiply(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber divide(INumber b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int compareTo(IRealNumber o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public INumber rightBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber leftBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber logicalBitShift(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toBinaryString() {
		if(toBinaryString!=null&&toBinaryString.get()!=null) return toBinaryString.get();
		String binary= Long.toBinaryString(self)+String.join("",java.util.Arrays.stream(array).parallel().mapToObj(Long::toBinaryString).toArray(String[]::new));
		toBinaryString=new WeakReference<String>(binary);
		return binary;
	}
	@Override
	public String toHexString() {
		if(toHexString!=null&&toHexString.get()!=null) return toHexString.get();
		String hex= Long.toHexString(self)+String.join(" ",LongStream.of(array).mapToObj(Long::toHexString).toArray(String[]::new));
		toHexString=new WeakReference<String>(hex);
		return hex;
	}
	@Override
	public INumber bitwiseNot() {
		long[] copy=new long[array.length];
		if(array.length<512) {
			for(int i=0;i<array.length;i++)
				copy[i]=~array[i];
		} else {
			class NegateArray extends ThreadArrayOperation{
				
				/***/private static final long serialVersionUID = -6897655701950839407L;
				
				@Override
				public ThreadArrayOperation construct() {
					return new NegateArray();
				}
				
				@Override
				protected void operation() {
					for(int i=start;i<end;i++)
						copy[i]=~array[i];
				}
				
			}
			ForkJoinPool.commonPool().invoke((ForkJoinTask<Void>) new NegateArray().setFields(0,array.length));
		}
		return BigInteger.valueOf(~self, copy);
	}
	@Override
	public INumber bitwiseAnd(INumber n) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public INumber bitwiseOr(INumber n) {
		switch(n.getClass().getName()){
		case("Lnumerical.integer.Integer"):{
			Integer other=((Integer)n);
			long[] copy=Arrays.copyOf(array,array.length);
			copy[copy.length-1]|=other.self;
			return valueOf(self,copy);
		}
		case("Lnumerical.integer.BigInteger"):{
			BigInteger other=((BigInteger)n);
			long[] copy;
			long newSelf;
			if(other.array.length>array.length) {
				newSelf=other.self;
				copy=Arrays.copyOf(other.array,other.array.length);
				for(int i=other.array.length-array.length,j=0;j<array.length;i++,j++) {
					copy[i]|=array[j];
				}
				copy[other.array.length-array.length-1]|=self;
			} else if(other.array.length<array.length){
				newSelf=self;
				copy=Arrays.copyOf(array,array.length);
				for(int i=array.length-other.array.length,j=0;j<other.array.length;i++,j++) {
					copy[i]|=other.array[j];
				}
				copy[array.length-other.array.length-1]|=other.self;
			} else {//equal lengths, so need to compare self as well//
				newSelf=other.self|self;
				copy=Arrays.copyOf(array,array.length);
				for (int i=0;i<other.array.length;i++) {
					copy[i]|=other.array[i];
				}
			}
			return valueOf(newSelf,copy);
		}
		default:return n.bitwiseOr(this);
		}
	}
	@Override
	public INumber bitwiseNor(INumber n) {
		// TODO Auto-generated method stub //
		return null;
	}
	@Override
	public INumber bitwiseNand(INumber n) {
		// TODO Auto-generated method stub //
		return null;
	}
}
