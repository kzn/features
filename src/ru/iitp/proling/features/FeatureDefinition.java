package ru.iitp.proling.features;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Feature builder interface.
 * @author Anton Kazennikov
 *
 */
public interface FeatureDefinition {
	public Feature build(List<Value> args);
	public boolean hasInjectedArg();
	
	
	public static class Class implements FeatureDefinition {
		java.lang.Class<? extends Feature> cls;
		
		public Class(java.lang.Class<? extends Feature> cls) {
			this.cls = cls;
		}
		
		@Override
		public Feature build(List<Value> args) {
			if(cls == null)
				return null;
			
			try {
				//Constructor<? extends Feature> ctor = cls.getConstructor(List.class);
				//return ctor.newInstance(args);
				return cls.newInstance();
			} catch(Exception e) {
				return null;
			}
		}

		@Override
		public boolean hasInjectedArg() {
			return Feature.Injectable.class.isAssignableFrom(cls);
		}
	}

}
