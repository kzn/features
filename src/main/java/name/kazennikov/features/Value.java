package name.kazennikov.features;

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
     * Get the value, casting it to given class
     * @param cls target class value object
     * @return value of class &lt;E&gt; or null, if the value is null
     * or couldn't be cast to cls
     */
	public <E> E get(Class<E> cls);
		
	/**
	 * Checks if value object is null. Null usually means that the value is uninitialized
	 * @return
	 */
	public boolean isSet();

    /**
     * Settable object value interface
     */
	public interface Settable extends Value {
		/**
		 * Set actual value object
		 * @param o
		 */
		public void set(Object o);
	}


    /**
     * Clearable object value interface
     */
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