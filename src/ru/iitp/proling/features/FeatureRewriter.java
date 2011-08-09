package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface FeatureRewriter {
	public FeatureValue rewrite(FeatureValue f);
	
	public static class RootInjector implements FeatureRewriter {
		FeatureExtractor eval;
		
		public RootInjector(FeatureExtractor eval) {
			this.eval = eval;
		}

		@Override
		public FeatureValue rewrite(FeatureValue f) {
			if(Feature.Injectable.class.isAssignableFrom(f.getFeature().getClass())) {
				List<Value> args = new ArrayList<Value>();
				args.add(eval.getRoot());
				args.addAll(f.args());
				return eval.get(new FeatureValue(f.getFeature(), new Values.Simple(), args));
			}
			
			return f;
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
		public FeatureValue rewrite(FeatureValue f) {
			if(CommonFeatures.Indexed.class.isAssignableFrom(f.getFeature().getClass())) {
				if(f.args().size() == 2)
					return f;
				
				List<Value> newArgs = new ArrayList<Value>();
				for(int i = 1; i != f.args().size(); i++) {
					newArgs.add(eval.get(new FeatureValue(f.getFeature(), new Values.Simple(), Arrays.asList(f.args.get(0), f.args().get(i)))));
				}
				
				return (FeatureValue)eval.get(new FeatureValue(new CommonFeatures.Tuple(), f.getValue(), newArgs));
			}

			return f;
		}
	}
}
