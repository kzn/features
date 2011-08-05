package ru.iitp.proling.features;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FeatureBuilder {
	Map<String, Class<? extends Feature>> builders = new HashMap<String, Class<? extends Feature>>();
	
	public Feature build(String name, List<Value> values) {
		Class<? extends Feature> c = builders.get(name);

		if(c == null)
			return null;
		
		try {
			Constructor<? extends Feature> ctor = c.getConstructor(Value[].class);
			return ctor.newInstance(new Object[]{values.toArray(new Value[]{})});
		} catch(Exception e) {
			return null;
		}
	}
	
	public void register(String name, Class<? extends Feature> cls) {
		builders.put(name, cls);
	}
	
	public boolean hasInjectedArg(String name) {
		Class<? extends Feature> c = builders.get(name);
		
		if(c == null)
			return false;
		return Feature.Injectable.class.isAssignableFrom(c);
	}
	
	

}
