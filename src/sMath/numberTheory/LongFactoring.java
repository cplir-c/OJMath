package sMath.numberTheory;

import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.lang.Runnable;

public abstract class LongFactoring {
	public static void main(String[] args){
		SynchronousQueue<int[]> queue=new SynchronousQueue<int[]>();
		Runnable runner=combinationsWithReplacement(3,12,queue);
		Thread other=new Thread(runner);
		other.start();
		try{
			System.out.println("in try");
			int[] empty={};
			for(int[] combination=queue.take();!java.util.Arrays.equals(empty,combination);combination=queue.take()){
				System.out.println(java.util.Arrays.toString(combination)+" took");
			}
			System.out.println("Done!");
		}catch (InterruptedException e){
			System.out.println(e);
		} finally {

		}
	}
	static long carmichael(long n){//https://en.wikipedia.org/wiki/Carmichael_function
		java.util.concurrent.ConcurrentSkipListSet<Long> coprimeToN=new java.util.concurrent.ConcurrentSkipListSet<Long>();
		for (long i=1;i<=n;i++)
			if (n%i!=0)//if n is not divisible by i, they're coprime
				if (!coprimeToN.contains(i)){
					coprimeToN.add(i);
					for (long j:coprimeToN.toArray(new Long[coprimeToN.size()]))
						if (j*i<n)
							coprimeToN.add(j*i);
				}
		for (long m=1;true;m++){
			for(long a:coprimeToN){
				long i=a;
				for (long j=1;j<m;j++){
					i*=a;
					//i%=n;
					System.out.println("m,a,i,j"+m+" "+a+" "+i+" "+j);
				}
				if (i%n==1)
					return a; 
			}
		}
	}
		static int[] quadraticPrimeFactorization(long n){
			//max long prime: 2147483629
			//trial division speed: about 4792 primes tested because pi(floor(2^(31/2.)))=4792
			//GNFS is really slow for the max long prime: e^((12884901774^(1/3) log^(2/3)(2147483629))/7^(2/3))~=3.090216234328024406445152277533869366391212968481042... � 10^2150
			final int SMOOTHNESS_BOUND=(int)Math.exp(Math.sqrt(Math.log(n)*Math.log(Math.log(n))));
			//pi(53)=16
			//step two: Use sieving to locate pi(B) + 1 numbers a_i such that b_i=(a_i**2 mod n) is B-smooth (all prime factors are less than B).
				//The quadratic sieve algorithm selects a set of primes called the factor base
			//and attempts to find x such that the least absolute remainder of 
			//y(x) = x**2 mod n factorizes completely over the factor base
			final int[] FACTOR_BASE=sieveOfSundaram(SMOOTHNESS_BOUND);
			int[] a=new int[FACTOR_BASE.length+1];
				StrictMath.min(x*x%n,n-x*x%n);
			return FACTOR_BASE;
		}
	static Runnable combinationsWithReplacement(final int poolLength,final int repeat, SynchronousQueue<int[]> queue){//translated from https://docs.python.org/3/library/itertools.html?highlight=combinations#itertools.combinations
		class Combinate implements Runnable{
			public SynchronousQueue<int[]> queue;
			Combinate(SynchronousQueue<int[]> queue){
				this.queue=queue;
			}
			@Override
			public void run() {
				System.out.println("started thread");//This will be combinations with replacement; the indices from the end to the rightmost non maximal pool index are set to the value of the rightmost non maximal pool index+1
				final int MAX_POOL_INDEX=poolLength-1;
				int[] indices=new int[repeat];
				java.util.function.Predicate<int[]> test=((a)->{for(int i:a)if (i!=MAX_POOL_INDEX)return true;return false;});
				while (test.test(indices)){
					int rightmostMaximalPoolIndex=repeat-1;
					for(;indices[rightmostMaximalPoolIndex]==MAX_POOL_INDEX;rightmostMaximalPoolIndex--);
					indices[rightmostMaximalPoolIndex]++;
					int j=indices[rightmostMaximalPoolIndex];
					for(int i=rightmostMaximalPoolIndex+1;i<repeat;i++){
						indices[i]=j;
					}
					while(true)
						try {
							queue.put(indices.clone());
							break;
						} catch (InterruptedException e) {
							System.err.println("Missed combination "+java.util.Arrays.toString(indices)+" due to interruption exception:"+e);
						}
				}

				while(true)
					try {
						queue.put(new int[0]);
						return;
					} catch (InterruptedException e) {
						System.err.println("Missed combination []/*the ending of the queue*/ due to interruption exception:"+e);
					}
			}
		};
		return new Combinate(queue);
	}
	/**Start with a list of the integers from 1 to n. From this list,
	 *  remove all numbers of the form i + j + 2ij where:
	i,j are elements of the set of natural numbers, 
	1<=i<=j and i + j + 2ij<=n
	The remaining numbers are doubled and incremented by one, giving a list 
	of the odd prime numbers (i.e., all primes except 2) below 2n + 2.*/
	static int[] sieveOfSundaram(int n){
		/* 2n + 2=i //inverting 2n+2 at the end
		 * 2n = i - 2
		 * n = (i - 2)/2
		 * n = i/2 - 1
		 */
		n/=2;
		n-=1;
		java.util.BitSet candidates=new java.util.BitSet(n);
		candidates.set(0,n);
		//candidates[0] represents 1, candidates[1] represents 2 and so on
		for (int j=1;3*j<=n;j++){// 1+j+2ij<=n|i=1 -> 3j<=n
			for (int i=1;i<=j && i+j+2*i*j<=n;i++){
				candidates.clear(i+j+2*i*j);
			}
		}
		int[] primes = new int[candidates.cardinality()];//2 replaces 1
		primes[0]=2;
		int p=1;//next open prime position
		for (int i = candidates.nextSetBit(1); i >= 0; i = candidates.nextSetBit(i+1)){//skip0 which represents 1, cause its technically not prime
			primes[p]=i*2+1;
			p+=1;
			//i represents the primality of (i+1)*2+2
			//so 2*i+4 is prime if (candidates[i])
		}
		System.out.println(java.util.Arrays.toString(primes));
		return primes;
	}
}