package name.kazennikov.features;

import java.util.List;

import name.kazennikov.features.Value.Settable;

public class StringFeatures {
	private StringFeatures() {
		
	}
	
	/**
	 * Lowercase feature.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns String in the lower case<br>
	 * @author Anton Kazennikov
	 *
	 */
	public static class LowerCase implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			res.set(args.get(0).get(String.class).toLowerCase());
		}

		@Override
		public String name() {
			return "lo";
		}		
	}

	
	public static class IsLowerCase implements FeatureFunction {

		@Override
		public String name() {
			return "lo-p";
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			String s = args.get(0).get(String.class).toLowerCase();
			res.set(s.toLowerCase().equals(s));
		}
		
	}
	
	public static class IsUpperCase implements FeatureFunction {

		@Override
		public String name() {
			return "up-p";
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			String s = args.get(0).get(String.class).toLowerCase();
			res.set(s.toUpperCase().equals(s));
		}
	}
	
	public static class Length implements FeatureFunction {

		@Override
		public String name() {
			return "strlen";
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			String s = args.get(0).get(String.class).toLowerCase();
			res.set(s.length());
		}
	}


}
