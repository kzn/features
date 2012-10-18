package name.kazennikov.features;

import java.util.List;

public class StringFeatures {
	private StringFeatures() {
		
	}
	
	/**
	 * Lowercase function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns String in the lower case<br>
	 * @author Anton Kazennikov
	 *
	 */
	public static class LowerCase implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			res.set((args.get(0).get(String.class)).toLowerCase());
		}

		@Override
		public String name() {
			return "lc";
		}		
	}
	
	/**
	 * Uppercase function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns String in the lower case<br>
	 * @author Anton Kazennikov
	 *
	 */
	public static class UpperCase implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			res.set((args.get(0).get(String.class)).toUpperCase());
		}

		@Override
		public String name() {
			return "uc";
		}		
	}
	
	/**
	 * Lowercase function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns true if string is in lowercase<br>
	 * @author Anton Kazennikov
	 *
	 */
	public static class LowerCasePredicate implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s = args.get(0).get(String.class);
			res.set(s.equals(s.toLowerCase()));
		}

		@Override
		public String name() {
			return "lc-pred";
		}		
	}
	
	/**
	 * Uppercase function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns true if string is in uppercase
	 * @author Anton Kazennikov
	 *
	 */
	public static class UpperCasePredicate implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s = args.get(0).get(String.class);
			res.set(s.equals(s.toUpperCase()));
		}

		@Override
		public String name() {
			return "uc-pred";
		}		
	}
	
	/**
	 * Capitalized function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns true if the string is capitalized
	 * @author Anton Kazennikov
	 *
	 */
	public static class CapitalizedPredicate implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s = args.get(0).get(String.class);
			res.set(Character.isUpperCase(s.charAt(0)));
		}

		@Override
		public String name() {
			return "uc-pred";
		}		
	}
	
	/**
	 * String length function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns length of a given string
	 * @author Anton Kazennikov
	 *
	 */
	public static class Length implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s = args.get(0).get(String.class);
			res.set(s.length());
		}

		@Override
		public String name() {
			return "strlen";
		}		
	}
	
	/**
	 * String compare function.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns state of string comparison
	 * @author Anton Kazennikov
	 *
	 */
	public static class Compare implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s1 = args.get(0).get(String.class);
			String s2 = args.get(1).get(String.class);
			res.set(s1.compareTo(s2));
		}

		@Override
		public String name() {
			return "strcmp";
		}		
	}
	
	/**
	 * String compare(ignoring case) function.
	 * <p>
	 * Accepts single argument: a String.<br>
 	 * Returns state of string comparison (
	 * @author Anton Kazennikov
	 *
	 */
	public static class CompareIgnoreCase implements FeatureFunction {


		@Override
		public void eval(Value.Settable res, List<Value> args) {
			String s1 = args.get(0).get(String.class);
			String s2 = args.get(1).get(String.class);
			res.set(s1.compareToIgnoreCase(s2));
		}

		@Override
		public String name() {
			return "stricmp";
		}		
	}



	
	



}

