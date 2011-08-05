package ru.iitp.proling.features;

import com.google.common.base.Objects;

public class FeatureValue implements Value {
	Feature f;
	Value v;
	
	
	public FeatureValue(Feature f, Value v) {
		this.f = f;
		this.v = v;
	}
	
	public FeatureValue() {
		
	}
	

	@Override
	public Object get() {
		if(v.isNull())
			f.eval(v);
		return v.get();
	}

	@Override
	public boolean isNull() {
		return v.isNull();
	}

	@Override
	public void set(Object o) {
		v.set(o);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
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
		
		return Objects.equal(other.f, f);
	}

	@Override
	public void clear() {
		v.clear();
	}

	@Override
	public StringBuilder toStringBuilder(StringBuilder sb) {
		return f.toStringBuilder(sb);
	}
	
	public String toString() {
		return String.format("fv:{%s%s}", f.name(), f.args());
	}
	
	public Feature getFeature() {
		return f;
	}
	
	
}
