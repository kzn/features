package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FeatureExtractor {
	List<Value> f = new ArrayList<Value>();
	Values.Simple root = new Values.Simple();
	Set<FeatureValue> toClear = new HashSet<FeatureValue>();
	Map<Value, Value> funSet = new HashMap<Value, Value>();
	List<FeatureRewriter> rewriters = new ArrayList<FeatureRewriter>();
	
	public FeatureExtractor() {
		rewriters.add(new FeatureRewriter.RootInjector(this));
		rewriters.add(new FeatureRewriter.IndexedTuple(this));
	}
	
	
	public void clear() {
		for(FeatureValue v : toClear)
			v.clear();
	}
	
	
	public List<Object> values(Object o) {
		root.set(o);
		clear();

		List<Object> feats = new ArrayList<Object>();
		for(int i = 0; i != f.size(); i++)
			feats.add(f.get(i).get());
		
		return feats;
	}
	
	public List<String> eval(Object o) {
		root.set(o);
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
	}
	
	public Value get(Value v) {
		if(v instanceof FeatureValue) {
			if(funSet.containsKey(v))
				return funSet.get(v);
			
			funSet.put(v, v);
			
		} 
		
		return v;
	}
	
	public FeatureValue rewrite(FeatureValue f) {
		for(FeatureRewriter fr : rewriters) {
			f = fr.rewrite(f);
		}
		
		return f;
	}
	
	public void addFeature(FeatureValue f) {
		this.f.add(get(f));
	}

	public Values.Simple getRoot() {
		return root;
	}
	
	
	
	
	
	
}
