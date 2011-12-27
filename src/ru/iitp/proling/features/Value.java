package ru.iitp.proling.features;

/**
 * Generic object to store some value
 * @author Anton Kazennikov
 *
 */
public interface Value {
	/**
	 * Get actual value object
	 */
	public Object get();
	public <E> E get(Class<E> cls);
		
	/**
	 * Checks if value object is null. Null usually means that the value is uninitialized
	 * @return
	 */
	public boolean isNull();
	
	public interface Settable extends Value {
		/**
		 * Set actual value object
		 * @param o
		 */
		public void set(Object o);
	}
	
	
	public interface Clearable extends Value {
		/**
		 * Clears value
		 */
		public void clear();
	}
	
	/**
	 * Mutable value. Implements both Settable and Clearable interfaces
	 * @author Anton Kazennikov
	 *
	 */
	public interface Mutable extends Settable, Clearable {
		
	}

}