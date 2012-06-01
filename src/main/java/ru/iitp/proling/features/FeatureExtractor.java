package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Feature extractor object extracts and returns list of extracted features values
 * @author ant
 *
 */
public class FeatureExtractor {
	List<Feature> f = new ArrayList<Feature>();
	
	Values.Var root = new Values.Var();
	Set<Feature> toClear = new HashSet<Feature>();
	Map<Feature, Feature> funSet = new HashMap<Feature, Feature>();
	List<FeatureRewriter> rewriters = new ArrayList<FeatureRewriter>();
	
	
	public FeatureExtractor() {
		rewriters.add(new FeatureRewriter.PolyRewriter());
		rewriters.add(new FeatureRewriter.SpecialTupleSequenceRewriter());
		rewriters.add(new FeatureRewriter.SpecialTupleRewriter());
		rewriters.add(new FeatureRewriter.RootInjector(this));
		rewriters.add(new FeatureRewriter.IndexedTuple(this));
		rewriters.add(new FeatureRewriter.SequenceRewriter());
		rewriters.add(new FeatureRewriter.TupleSimplifier());
		rewriters.add(new FeatureRewriter.MinimizeRewriter(this));
	}
	
	
	/**
	 * Clear cached values for re-evaluation of the features
	 */
	public void clear() {
		for(Feature v : toClear)
			v.clear();
	}
	
	
	/**
	 * Get raw list of extracted features from source object
	 * @param o feature extraction source
	 * @return list of features
	 */
	public List<Object> values(Object o) {
		root.set(o);
		clear();

		List<Object> feats = new ArrayList<Object>();
		for(int i = 0; i != f.size(); i++) {
			Object v = f.get(i).get();
			if(v != null)
				feats.add(v);
		}
		
		return feats;
	}
	
	/**
	 * Get formatted list of extracted features from source object
	 * @param o feature extraction source
	 * @return list of string features
	 */
	public List<String> eval(Object o) {
		root.set(o);
		clear();
		
		List<String> feats = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i != f.size(); i++) {
			Object v = f.get(i).get();
			
			if(v != null) {
				sb.setLength(0);
				sb.append(i + 1).append('=');
				sb.append(v.toString());
				feats.add(sb.toString());
			}
		}
		
		return feats;
	}
		
	/**
	 * Get feature value function from list of already seen
	 * @param v
	 * @return
	 */
	public Feature get(Feature v) {
		if(v instanceof Feature) {
			if(funSet.containsKey(v))
				return funSet.get(v);
			toClear.add((Feature)v);
			funSet.put(v, v);
		} 
		
		return v;
	}
	
	/**
	 * Rewrite feature values using defined feature rewriters
	 * @param f
	 * @return
	 */
	public Feature rewrite(Feature f) {
		for(FeatureRewriter fr : rewriters) {
			f = fr.rewrite(f);
		}
		
		return f;
	}
	
	/**
	 * Add feature to the list for extraction
	 * @param f
	 */
	public void addFeature(Feature f) {
		this.f.add(get(f));
	}

	/**
	 * Get root value object
	 * @return
	 */
	public Values.Var getRoot() {
		return root;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for(int i = 0; i != f.size(); i++) {
			if(i != 0)
				sb.append(", ");
			sb.append(i+1).append('=').append(f.get(i).toString());
		}
		
		sb.append(']');
		
		return sb.toString();
	}
	/**
	 * Get list of features used by the extractor
	 * @return
	 */
	public List<Feature> get() {
		return f;
	}
}
