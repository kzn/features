package ru.iitp.proling.features;

import java.util.List;

import com.spinn3r.log5j.Logger;

/**
 * Feature builder interface.
 * @author Anton Kazennikov
 *
 */
public interface FeatureBuilder {
	
	public Feature build(List<Value> args);
	public static class Class implements FeatureBuilder {
		private final static Logger logger = Logger.getLogger();
		java.lang.Class<? extends Feature> cls;
		
		public Class(java.lang.Class<? extends Feature> cls) {
			this.cls = cls;
		}
		
		@Override
		public Feature build(List<Value> args) {
			if(cls == null)
				return null;
			
			try {
				return cls.newInstance();
			} catch(Exception e) {
				logger.error(e);
				return null;
			}
		}

	}

}
