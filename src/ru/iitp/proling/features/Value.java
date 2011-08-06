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
		
	/**
	 * Checks if value object is null
	 * @return
	 */
	public boolean isNull();
		
	/**
	 * Write string representation of this value to StringBuilder
	 * @param sb string builder
	 * @return sb
	 */
	public StringBuilder toStringBuilder(StringBuilder sb);
	
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
	
	public interface Mutable extends Settable, Clearable {
		
	}

}