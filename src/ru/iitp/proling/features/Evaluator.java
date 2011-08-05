package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Evaluator {
	List<Value> f = new ArrayList<Value>();
	Value init = new SimpleValue();
	Set<FeatureValue> toClear = new HashSet<FeatureValue>();
	Map<Value, Value> funSet = new HashMap<Value, Value>();
	List<String> featureNames = new ArrayList<String>();
	
	
	public void clear() {
		for(Value v : toClear)
			v.clear();
	}
	
	
	public List<Object> values(Object o) {
		init.set(o);
		clear();

		List<Object> feats = new ArrayList<Object>();
		for(int i = 0; i != f.size(); i++)
			feats.add(f.get(i).get());
		
		return feats;
	}
	
	public List<String> eval(Object o) {
		init.set(o);
		clear();
		
		List<String> feats = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i != f.size(); i++) {
			sb.setLength(0);
			sb.append(i + 1).append('=');
			sb.append(f.get(i).get().toString());
			feats.add(sb.toString());
		}
		
		return feats;
	}
	
	public void setArg(FeatureValue f, Value arg) {
		if(f instanceof FeatureValue)
			toClear.add(f);
			
		//toClear.put(arg, f);
	}
	
	public Value get(Value v) {
		if(v instanceof FeatureValue) {
			if(funSet.containsKey(v))
				return funSet.get(v);
			
			funSet.put(v, v);
			
		} 
		
		return v;
	}
	
	public void addFeature(FeatureValue f) {
		this.f.add(get(f));
		StringBuilder sb = new StringBuilder();
		f.getFeature().toStringBuilder(sb);
		this.featureNames.add(sb.toString());
	}

	public Value getInit() {
		return init;
	}
	
	
	
	
	
	
}
