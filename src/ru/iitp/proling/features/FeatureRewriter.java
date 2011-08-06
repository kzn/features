package ru.iitp.proling.features;

public interface FeatureRewriter {
	public FeatureValue rewrite(FeatureValue f);
	
	public static class RootInjector implements FeatureRewriter {
		Evaluator eval;
		
		public RootInjector(Evaluator eval) {
			this.eval = eval;
		}

		@Override
		public FeatureValue rewrite(FeatureValue f) {
			if(Feature.Injectable.class.isAssignableFrom(f.getFeature().getClass())) {
				f.args().add(0, eval.getRoot());
			}
			
			return f;
		}
	}
}
