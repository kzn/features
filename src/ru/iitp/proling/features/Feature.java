package ru.iitp.proling.features;

import java.util.List;

/**
 * Function to extract feature from an object
 * @author ant
 *
 */
public interface Feature {
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
	
	public String name();
	public List<Value> args();
	public void eval(Value res);
	public StringBuilder toStringBuilder(StringBuilder sb);
}
