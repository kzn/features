package ru.iitp.proling.features;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public interface FeatureRewriter {
	public Feature rewrite(Feature f);
	
	public static class RootInjector implements FeatureRewriter {
		FeatureExtractor eval;
		
		public RootInjector(FeatureExtractor eval) {
			this.eval = eval;
		}

		@Override
		public Feature rewrite(Feature f) {
			List<Value> args = new ArrayList<Value>();

			if(FeatureFunction.Injectable.class.isAssignableFrom(f.getFeature().getClass())) {
				args.add(eval.getRoot());
			}
			
			for(Value arg : f.args()) {
				if(arg instanceof Feature) {
					args.add(rewrite((Feature)arg));
				} else {
					args.add(arg);
				}
			}
			
			
			return new Feature(f.getFeature(), new Values.Var(), args);
		}
	}
	
	/**
	 * f(i,j,k) -> tuple(f(i), f(j), f(k)) rewriter.
	 * Works on already root injected features
	 * @author Anton Kazennikov
	 *
	 */
	public static class IndexedTuple implements FeatureRewriter {
		FeatureExtractor eval;

		public IndexedTuple(FeatureExtractor eval) {
			this.eval = eval;
		}

		@Override
		public Feature rewrite(Feature f) {
			
			if(CommonFeatures.Indexed.class.isAssignableFrom(f.getFeature().getClass())) {
				if(f.args().size() == 2)
					return f;
				
				List<Value> newArgs = new ArrayList<Value>();
				for(int i = 1; i != f.args().size(); i++) {
					Value arg = null;
					if(f.args().get(i) instanceof Feature) {
						arg = rewrite((Feature)f.args().get(i));
					} else {
						arg = f.args().get(i);
					}
					newArgs.add(eval.get(new Feature(f.getFeature(), new Values.Var(), Arrays.asList(f.args.get(0), arg))));
				}
				
				return (Feature)eval.get(new Feature(new CommonFeatures.Tuple(), f.getValue(), newArgs));
			} else {
				List<Value> args = new ArrayList<Value>();
				for(Value arg : f.args()) {
					if(arg instanceof Feature) {
						arg = rewrite((Feature) arg);
					}
					args.add(arg);
				}
				f.setArgs(args);
			}

			return f;
		}
	}
	
	public static class MinimizeRewriter implements FeatureRewriter {
		FeatureExtractor eval;
		
		public MinimizeRewriter(FeatureExtractor eval) {
			this.eval = eval;
		}

		@Override
		public Feature rewrite(Feature f) {
			List<Value> args = new ArrayList<Value>();
			
			for(Value arg : f.args()) {
				Value v = null;
				if(arg instanceof Feature) {
					v = rewrite((Feature)arg);
				} else {
					v = arg;
				}
				
				args.add(v);
			}
			
			return eval.get(new Feature(f.getFeature(), new Values.Var(), args));
		}
	}
	
	public static class SpecialTupleRewriter implements FeatureRewriter {

		@Override
		public Feature rewrite(Feature f) {
			List<Value> args = new ArrayList<Value>();
			for(Value arg : f.args()) {
				if(arg instanceof Feature) {
					Feature feat = rewrite((Feature)arg);
					if(feat.getFeature() == CommonFeatures.sequence)
						args.addAll(feat.args());
					else 
						args.add(feat);
				} else {
					args.add(arg);
				}
			}
			
			if(f instanceof FeatureParser.Special) {
				List<Value> feats = new ArrayList<Value>();
				Value base = args.get(0);
				
				for(int i = 1; i != args.size(); i++) {
					feats.add(new Feature(CommonFeatures.tuple, new Values.Var(), Arrays.asList(base, args.get(i))));
				}
				
				return new Feature(CommonFeatures.sequence, new Values.Var(), feats);
			} 
			
			f.setArgs(args);
			return f;
		}		
	}
	
	public static class SequenceRewriter implements FeatureRewriter {

		@Override
		public Feature rewrite(Feature f) {
			Deque<Value> q = new ArrayDeque<Value>(f.args());
			List<Value> args = new ArrayList<Value>();
			
			while(!q.isEmpty()) {
				Value v = q.pollFirst();
				if(v instanceof Feature) {
					Feature feat = (Feature)v;
					if(feat.getFeature() == CommonFeatures.sequence) {
						List<Value> seqArgs = new ArrayList<Value>(feat.args());
						Collections.reverse(seqArgs);
						for(Value val : seqArgs) {
							q.addFirst(val);
						}
						continue;
					} else {
						v = rewrite((Feature)v);
					}
				}
				args.add(v);
			}
			
			f.setArgs(args);
			return f;
		}
	}
	
	public static class TupleSimplifier implements FeatureRewriter {

		@Override
		public Feature rewrite(Feature f) {
			Deque<Value> q = new ArrayDeque<Value>(f.args());
			List<Value> args = new ArrayList<Value>();
			
			while(!q.isEmpty()) {
				Value v = q.pollFirst();
				if(v instanceof Feature) {
					Feature feat = (Feature)v;
					if(feat.getFeature() == CommonFeatures.tuple && f.getFeature() == CommonFeatures.tuple) {
						List<Value> seqArgs = new ArrayList<Value>(feat.args());
						Collections.reverse(seqArgs);
						for(Value val : seqArgs) {
							q.addFirst(val);
						}
						continue;
					} else {
						v = rewrite((Feature)v);
					}
				}
				args.add(v);
			}
			
			f.setArgs(args);
			return f;
		}
		
	}
}
