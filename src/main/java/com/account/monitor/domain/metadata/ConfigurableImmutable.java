package com.account.monitor.domain.metadata;

/**
 * Indicate one class can be set as immutable object, but before set it to immutable object
 * the instance of the class can be configure
 * @author pu
 *
 */
public interface ConfigurableImmutable {
	/**
	 * Check is this instance now immutable
	 * @return
	 */
	public boolean isImmutable();
	
	/**
	 * Set this instance to immutable, after invoke this method all the methods for this class
	 * which will change data should not allowed and a  UnsupportedOperationException should be raise
	 * directly.
	 */
	public void setToImmutable();
}
