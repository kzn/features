package ru.iitp.proling.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Feature register that builds a feature from its name and arguments.
 * 
 * To use a {@link Feature} class as a function one must register it in the builder
 * through the <code>register()</code> function.
 * 
 * <p>
 * Contract: 
 * A suitable <@link Feature} class must have a constructor that accepts 
 * array of {@link Value} objects (a <code>Value[]</code> signature)
 *
 * @author Anton Kazennikov
 *
 */
public class FeatureRegister {
	Map<String, FeatureDefinition> builders = new HashMap<String, FeatureDefinition>();
	
	public Feature build(String name, List<Value> values) {
		FeatureDefinition b = builders.get(name);
		if(b != null)
			return b.build(values);
		
		return null;
	}
	
	public void register(String name, FeatureDefinition fb) {
		builders.put(name, fb);
	}
	
	public void register(String name, Class<? extends Feature> cls) {
		register(name, builderFor(cls));
	}
	
	public boolean hasInjectedArg(String name) {
		FeatureDefinition b = builders.get(name);
		return b.hasInjectedArg();
	}
	
	public static FeatureDefinition builderFor(final Class<? extends Feature> cls) {
		return new FeatureDefinition.Class(cls);
	}
	
	

}
