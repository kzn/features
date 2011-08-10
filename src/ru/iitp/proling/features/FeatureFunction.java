package ru.iitp.proling.features;

import java.util.List;

/**
 * Function to extract feature from an object
 * @author ant
 *
 */
public interface FeatureFunction {
	public interface Provider {
		public FeatureFunction get();
	}
	
	/**
	 * This interface indicates that the first element of the feature
	 * is an injected root element.
	 * 
	 * <p>
	 * Suppose, there is a w(n) feature, that returns word from a sentence at 
	 * position n. So, actually, this feature is a w(root, n) feature, if
	 * sentence is a root object.
	 * This interface permits to write w(n) instead of w(root, n)
	 * @author Anton Kazennikov
	 *
	 */
	public interface Injectable {
	}
	
	/**
	 * Get feature function name
	 * @return
	 */
	public String name();
		
	/**
	 * Evaluate feature and store result in a value object
	 * @param res result value object
	 */
	public void eval(Value.Settable res, List<Value> args);
}
