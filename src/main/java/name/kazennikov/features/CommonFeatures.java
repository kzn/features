package name.kazennikov.features;

import java.util.ArrayList;
import java.util.List;

import name.kazennikov.features.Value.Settable;



public class CommonFeatures {
	/**
	 * Simple feature abstract class. Intended as a function that evaluates
	 * arguments passed as args array
	 * @author Anton Kazennikov
	 *
	 */
	public abstract static class Simple implements FeatureFunction {	
//		public Simple() {
//		}
//		
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			String name = name();
//			result = prime * result + ((name == null) ? 0 : name.hashCode());
//			return result;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (!(obj instanceof Simple))
//				return false;
//			Simple other = (Simple) obj;
//			
//			return Objects.equal(name, other.name);
//					
//		}
		
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
			res.set((args.get(0).get(String.class)).toLowerCase());
		}

		@Override
		public String name() {
			return "lo";
		}		
	}
	
	/**
	 * Tuple feature that return a list of all its arguments.
	 * The value is set only if all values of the tuple are set
	 * @author Anton Kazennikov
	 *
	 */
	public static class Tuple implements FeatureFunction {

		@Override
		public void eval(Value.Settable res, List<Value> args) {
			List<Object> o = new ArrayList<Object>();
			
			for(Value v : args) {
				Object r = v.get();
				
				if(!v.isSet()) {
					//res.set(null);
					return;
				}
				
				o.add(r);
			
			}
			
			res.set(o);
		}

		@Override
		public String name() {
			return "tuple";
		}
	}
	

	
	/**
	 * Base class for root injected features like w(1) [w(root, 1)]
	 * @author Anton Kazennikov
	 *
	 * @param <E>
	 */
	public abstract static class Base<E> implements FeatureFunction, FeatureFunction.Injectable {
		private static int INJECTED_INDEX = 0;
		
		@SuppressWarnings("unchecked")
		public E getObject(List<Value> args) {
			return (E)args.get(INJECTED_INDEX).get();
		}

		
	}
	/**
	 * Base class for features like f(i), where i is a numeric index
	 * @author Anton Kazennikov
	 *
	 */
	public abstract static class Indexed<E> extends Base<E> implements FeatureFunction.Injectable {
		private static int INDEX_INDEX = 1;

		/**
		 * Get the index value from the arg list
		 * @param args argument list
		 * @return
		 */
		public int getIndex(List<Value> args) {
			return args.get(INDEX_INDEX).get(Integer.class);
		}
	}
	
	/**
	 * Implements array function, the [] operation.
	 * If there is only one argument array behaves as traditional array,
	 * returning element by index.
	 * If there are several indices, then this feature returns a list of
	 * elements by given indices
	 * @author Anton Kazennikov
	 *
	 */
	public static class Array implements FeatureFunction {

		@Override
		public String name() {
			return "array";
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			List<?> arr = (List<?>)args.get(0).get();
			
			if(args.size() == 2) {
				int index = (Integer)args.get(1).get();
				if(index >= 0 && index < arr.size())
					res.set(arr.get(index));
			} else {
				List<Object> values = new ArrayList<Object>();
				for(int i = 1; i < args.size(); i++) {
					int index = (Integer)args.get(i).get();
					if(index >= 0 && index < arr.size())
						values.add(arr.get(index));
				}
				res.set(values);
			}
		}
	}
	
	/**
	 * Sequence feature function.
	 * Returns a list of all evaluated arguments
	 * @author Anton Kazennikov
	 *
	 */
	public static class Sequence implements FeatureFunction {

		@Override
		public String name() {
			return "seq";
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			List<Object> vals = new ArrayList<Object>();
			
			for(Value arg : args) {
				vals.add(arg.get());
			}
			
			res.set(vals);
		}
	}
	
	public static final Tuple tuple = new Tuple();
	public static final Sequence sequence = new Sequence();
	public static final Array array = new Array();
	

}
