package ru.iitp.proling.features;

import java.util.List;

import ru.iitp.proling.features.Values.Simple;

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
public class FeatureValue implements Value.Clearable {
	Feature f;
	List<Value> args;
	Values.Simple v;
	
	
	public FeatureValue(Feature f, Values.Simple v, List<Value> args) {
		this.f = f;
		this.v = v;
		this.args = args;
	}
	
	public FeatureValue() {
		
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

	/*@Override
	public void set(Object o) {
		v.set(o);
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((f == null) ? 0 : args.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FeatureValue))
			return false;
		FeatureValue other = (FeatureValue) obj;
		
		return Objects.equal(other.f, f) && Objects.equal(other.args, args);
	}

	@Override
	public void clear() {
		v.clear();
	}

//	@Override
//	public StringBuilder toStringBuilder(StringBuilder sb) {
//		return f.toStringBuilder(sb);
//	}
	
	public String toString() {
		return String.format("fv:{%s%s}", f.name(), args);
	}
	
	public Feature getFeature() {
		return f;
	}
	
	public List<Value> args() {
		return args;
	}
	
	public Value arg(int i) {
		return args.get(i);
	}

	public Simple getValue() {
		return v;
	}
	
	
}
