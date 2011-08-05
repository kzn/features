package ru.iitp.proling.features;

import java.util.List;

/**
 * Function to extract feature from an object
 * @author ant
 *
 */
public interface Feature {
	public interface Injectable {
	}
	
	public String name();
	public List<Value> args();
	public void eval(Value res);
	public StringBuilder toStringBuilder(StringBuilder sb);
}
