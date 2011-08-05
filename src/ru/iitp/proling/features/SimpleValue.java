package ru.iitp.proling.features;

import com.google.common.base.Objects;

public class SimpleValue implements Value {
	Object arg;
	
	public SimpleValue() {
		
	}
	
	public SimpleValue(Object arg) {
		this.arg = arg;
	}
	
	/* (non-Javadoc)
	 * @see ru.iitp.proling.features.Value#get()
	 */
	@Override
	public Object get() {
		return arg;
	}
	
	public void set(Object arg) {
		this.arg = arg;
	}
	
	/* (non-Javadoc)
	 * @see ru.iitp.proling.features.Value#isNull()
	 */
	@Override
	public boolean isNull() {
		return arg == null;
	}

	@Override
	public void clear() {
		arg = null;
	}
	
	@Override
	public StringBuilder toStringBuilder(StringBuilder sb) {
		return sb.append(arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arg == null) ? 0 : arg.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimpleValue))
			return false;
		SimpleValue other = (SimpleValue) obj;
		return Objects.equal(other.arg, arg);
	}
	
	@Override
	public String toString() {
		return String.format("value{%s}", arg);
	}
	
	
}
