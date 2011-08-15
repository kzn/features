package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.Arrays;
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
}
