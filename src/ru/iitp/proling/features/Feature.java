package ru.iitp.proling.features;

import java.util.List;

import ru.iitp.proling.features.Values.Var;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

/**
 * Feature value object. 
 * <p>
 * This Value object returns result of feature evaluation.
 * Assumes that all features are pure functions, so if feature arguments
 * doesn't change, then the feature value doesn't change too. Then the 
 * feature value can be cached
 * 
 * @author Anton Kazennikov
 *
 */
public class Feature implements Value.Clearable {
	FeatureFunction f;
	List<Value> args;
	Values.Var v;
	
	
	public Feature(FeatureFunction f, Values.Var v, List<Value> args) {
		this.f = f;
		this.v = v;
		this.args = args;
	}
	
	public Feature() {
		
	}
	

	@Override
	public Object get() {
		if(v.isNull())
			f.eval(v, args);
		return v.get();
	}

	@Override
	public boolean isNull() {
		return v.isNull();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Feature))
			return false;
		Feature other = (Feature) obj;
		
		return Objects.equal(other.f, f) && Objects.equal(other.args, args);
	}

	@Override
	public void clear() {
		v.clear();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(f.name());
		sb.append('(');
		if(f instanceof FeatureFunction.Injectable) {
			Joiner.on(", ").appendTo(sb, args.subList(1, args.size()));
		} else {
			Joiner.on(", ").appendTo(sb, args);
		}

		sb.append(')');
		
		return sb.toString();
	}
	
	public FeatureFunction getFeature() {
		return f;
	}
	
	public List<Value> args() {
		return args;
	}
	
	public Value arg(int i) {
		return args.get(i);
	}

	public Var getValue() {
		return v;
	}
	
	public void setArgs(List<Value> args) {
		this.args = args;
	}
	
	
}
