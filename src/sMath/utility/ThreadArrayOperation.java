package sMath.utility;

import java.util.concurrent.ForkJoinTask;

public abstract class ThreadArrayOperation extends ForkJoinTask<Void> {
	/***/private static final long serialVersionUID = -3986180033427108415L;
		protected int start;
		protected int end;
		protected ThreadArrayOperation() {
		}
		protected ThreadArrayOperation(int stop){
			end=stop;
		}
		protected ThreadArrayOperation(int begin, int stop){
			start=begin;
			end=stop;
		}
		public ThreadArrayOperation setFields(int begin,int stop){
			start=begin;
			end=stop;
			return this;
		}
		public abstract ThreadArrayOperation construct();
		@Override
		protected boolean exec() {
			int length=end-start-1;//end exclusive
			if(length>=512) {
				int section=1<<(java.lang.Integer.highestOneBit(length)-2);//Basically log base 2
				int extra=length%section;
				int sections=length/section+java.lang.Integer.signum(extra);
				int innerEnd=end-length%section;
				ThreadArrayOperation[] subtasks=new ThreadArrayOperation[sections];
				for(int i=0,location=start;i<sections&&location<innerEnd;i++,location+=section)
					subtasks[i]=(ThreadArrayOperation)construct().setFields(location,location+section).fork();
				if(extra!=0)
					subtasks[sections-1]=(ThreadArrayOperation) construct().setFields(innerEnd,end).fork();
				for(int i=sections-1;i>-1;i--)
					subtasks[i].join();
			} else
				operation();
			return true;
		}
		/**
		 * This method should perform the operation from start to the end of the local array.
		 * For example, this should invert the bits from start to end of copy.
		 * for(int i=start;i<end;i++)
		 * 		copy[i]^=0xffffffffL;
		 */
		protected abstract void operation();
		public Void getRawResult() {return null;} protected void setRawResult(Void value) {}//What's this for?
}
