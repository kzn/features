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
		final List<Value> args;
		
		public Simple(String name, List<Value> args) {
			this.name = name;
			this.args = args;
		}

		@Override
		public String name() {
			return name;
		}
		
		@Override
		public List<Value> args() {
			return args;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + args.hashCode();
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
			
			return Objects.equal(name, other.name) 
					&& Objects.equal(args, other.args);//Objects.equal(args, other.args);
					
		}
		
		public Object getObject() {
			return args.get(0).get();
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

		public LowerCase(List<Value> args) {
			super("lo", args);
		}

		@Override
		public void eval(Value res) {
			res.set(((String)args.get(0).get()).toLowerCase());
		}
		
		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append(name()).append('(');
			args.get(0).toStringBuilder(sb);
			sb.append(')');
			
			return sb;
		}
	}
	
	/**
	 * Tuple feature that return a list of all its arguments
	 * @author Anton Kazennikov
	 *
	 */
	public static class Tuple extends Simple {
		public Tuple(List<Value> args) {
			super("tuple", args);
		}

		@Override
		public void eval(Value res) {
			List<Object> o = new ArrayList<Object>();
			
			for(Value v : args) {
				o.add(v.get());
			}
			
			res.set(o);
				
		}

		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append('<');
			int i = 0; 
			for(Value arg : args) {
				if(i++ != 0)
					sb.append(',');
				arg.toStringBuilder(sb);
			}
			sb.append('>');
			
			return sb;
		}
	}
}
