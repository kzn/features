package ru.iitp.proling.features;

import java.util.List;

import ru.iitp.proling.features.Value.Settable;

public class FeatureFunctions {
	/**
	 * Constant function
	 * @author Anton Kazennikov
	 *
	 */
	public static class Const implements FeatureFunction {
		Object v;
		String name;
		
		public Const(Object v, String name) {
			this.v = v;
			this.name = name;
		}
		
		public Const(Object v) {
			this(v, null);
		}

		@Override
		public String name() {
			return name != null? name : v.toString();
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			res.set(v);
		}
	}
	
	/**
	 * Null feature function
	 * @author Anton Kazennikov
	 *
	 */
	public static class Null extends Const {

		public Null() {
			super(null);
		}

		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.test.TestCommonFeatures.ConstFeatureFunction#name()
		 */
		@Override
		public String name() {
			return "null";
		}
	}
	
	public final static FeatureFunction NULL = new Null();

}
