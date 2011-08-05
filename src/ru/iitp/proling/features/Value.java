package ru.iitp.proling.features;

public interface Value {
	public Object get();
	public void set(Object o);
	public boolean isNull();
	public void clear();
	public StringBuilder toStringBuilder(StringBuilder sb);

}