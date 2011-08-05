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
	 * Set actual value object
	 * @param o
	 */
	public void set(Object o);
	
	/**
	 * Checks if value object is null
	 * @return
	 */
	public boolean isNull();
	
	/**
	 * Clears value
	 */
	public void clear();
	
	/**
	 * Write string representation of this value to StringBuilder
	 * @param sb string builder
	 * @return sb
	 */
	public StringBuilder toStringBuilder(StringBuilder sb);

}