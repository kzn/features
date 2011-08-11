package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.List;

import ru.iitp.proling.features.Value.Settable;

import com.google.common.base.Objects;


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
			res.set(((String)args.get(0).get()).toLowerCase());
		}

		@Override
		public String name() {
			return "lo";
		}		
	}
	
	/**
	 * Tuple feature that return a list of all its arguments
	 * @author Anton Kazennikov
	 *
	 */
	public static class Tuple implements FeatureFunction {

		@Override
		public void eval(Value.Settable res, List<Value> args) {
			List<Object> o = new ArrayList<Object>();
			
			for(Value v : args) {
				o.add(v.get());
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
		
		@SuppressWarnings("unchecked")
		public E getObject(List<Value> args) {
			return (E)args.get(0).get();
		}

		
	}
	/**
	 * Base class for features like f(i), where i is a numeric index
	 * @author Anton Kazennikov
	 *
	 */
	public abstract static class Indexed<E> extends Base<E> implements FeatureFunction.Injectable {

		public int getIndex(List<Value> args) {
			return (Integer)args.get(1).get();
		}
	}
	
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
	

}
