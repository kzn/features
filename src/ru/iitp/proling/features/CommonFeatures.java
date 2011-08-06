package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;


public class CommonFeatures {
	/**
	 * Simple feature abstract class. Intended as a function that evaluates
	 * arguments passed as args array
	 * @author Anton Kazennikov
	 *
	 */
	public abstract static class Simple implements Feature {
		final String name;
		
		public Simple(String name) {
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Simple))
				return false;
			Simple other = (Simple) obj;
			
			return Objects.equal(name, other.name);
					
		}
		
	}

	
	/**
	 * Lowercase feature.
	 * <p>
	 * Accepts single argument: a String.<br>
	 * Returns String in the lower case<br>
	 * @author Anton Kazennikov
	 *
	 */
	public static class LowerCase extends Simple {

		public LowerCase() {
			super("lo");
		}

		@Override
		public void eval(Value.Settable res, List<Value> args) {
			res.set(((String)args.get(0).get()).toLowerCase());
		}
		
		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append(name());//.append('(');
			//args.get(0).toStringBuilder(sb);
			//sb.append(')');
			
			return sb;
		}
	}
	
	/**
	 * Tuple feature that return a list of all its arguments
	 * @author Anton Kazennikov
	 *
	 */
	public static class Tuple extends Simple {
		public Tuple() {
			super("tuple");
		}

		@Override
		public void eval(Value.Settable res, List<Value> args) {
			List<Object> o = new ArrayList<Object>();
			
			for(Value v : args) {
				o.add(v.get());
			}
			
			res.set(o);
				
		}

		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append('<');
			/*int i = 0; 
			for(Value arg : args) {
				if(i++ != 0)
					sb.append(',');
				arg.toStringBuilder(sb);
			}*/
			sb.append('>');
			
			return sb;
		}
	}
	

	
	/**
	 * Base class for root injected features like w(1) [w(root, 1)]
	 * @author Anton Kazennikov
	 *
	 * @param <E>
	 */
	public abstract static class Base<E> extends Simple implements Feature.Injectable {

		public Base(String name) {
			super(name);
		}
		
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
	public abstract static class Indexed<E> extends Base<E> implements Feature.Injectable {

		public Indexed(String name) {
			super(name);
		}
		
		public int getIndex(List<Value> args) {
			return (Integer)args.get(1).get();
		}
		
		
		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append(name).append('(');
			//args.get(1).toStringBuilder(sb);
			sb.append(')');
			
			return sb;
		}

	}
	

}
