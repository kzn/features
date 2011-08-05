package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;


public class CommonFeatures {
	
	public abstract static class SimpleFeature implements Feature {
		final String name;
		final Value[] args;
		
		public SimpleFeature(String name, Value[] args) {
			this.name = name;
			this.args = args;
		}

		@Override
		public String name() {
			return name;
		}
		
		public Object getObject() {
			return args[0].get();
		}

		@Override
		public List<Value> args() {
			return Arrays.asList(args);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(args);
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof SimpleFeature))
				return false;
			SimpleFeature other = (SimpleFeature) obj;
			
			return Objects.equal(name, other.name) 
					&& Arrays.equals(args, other.args);//Objects.equal(args, other.args);
					
		}
		
		
	}

	
	public static class LoFeature extends SimpleFeature {

		public LoFeature(Value[] args) {
			super("lo", args);
		}

		@Override
		public void eval(Value res) {
			res.set(((String)args[0].get()).toLowerCase());
		}
		
		@Override
		public StringBuilder toStringBuilder(StringBuilder sb) {
			sb.append(name()).append('(');
			args[0].toStringBuilder(sb);
			sb.append(')');
			
			return sb;
		}
	}
	
	public static class TupleFeature extends SimpleFeature {
		public TupleFeature(Value[] args) {
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
