package com.account.monitor.domain.metadata;

/**
 * Yes, immutable, once created, it not allow to change any field of the instance.
 * All the setXXX method will throw exception.
 * You can decide use it or not, all base on you.
 * @author pu
 *
 */
public final class ImmutableEventResult extends EventResult {
		
		public ImmutableEventResult(){
			
		}
		
		public ImmutableEventResult(EventStatusType type, String resultMessage){
			super(type, resultMessage);
		}
		@Deprecated
		/**
		 * The method is not support in MutableEventResult class. It will just throw a IllegalArgumentException
		 * @throw IllegalArgumentException
		 */
		public final void setResultMessage(String result){
			throw new IllegalArgumentException("Not support this method in MutableOKEventResult");
		}
		@Deprecated
		public final void setStatus(EventStatusType status) {
			throw new IllegalArgumentException("Not support this method in MutableOKEventResult");
		}
		@Deprecated
		public final void fail(){
			throw new IllegalArgumentException("Not support this method in MutableOKEventResult");
		}
		@Deprecated
		public final void fail(String failMsg){
			throw new IllegalArgumentException("Not support this method in MutableOKEventResult");
		}
}
