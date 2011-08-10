package ru.iitp.proling.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Feature register that builds a feature from its name and arguments.
 * 
 * To use a {@link FeatureFunction} class as a function one must register it in the builder
 * through the <code>register()</code> function.
 * 
 * <p>
 * By default, comes with registered {@link CommonFeatures.Tuple} class
 * 
 * <p>
 * Contract: 
 * A suitable <@link Feature} class must have a constructor that accepts 
 * array of {@link Value} objects (a <code>Value[]</code> signature)
 *
 * @author Anton Kazennikov
 *
 */
public class FeatureRegister {
	Map<String, FeatureFunction.Provider> providers = new HashMap<String, FeatureFunction.Provider>();
	
	/**
	 * Identity provider. Provides specified FeatureFunction
	 * @author Anton Kazennikov
	 *
	 */
	public static class IdentityProvider implements FeatureFunction.Provider {
		final FeatureFunction fun;
		
		public IdentityProvider(FeatureFunction fun) {
			this.fun = fun;
		}

		@Override
		public FeatureFunction get() {
			return fun;
		}
	}
	
	/**
	 * Function aliasing provider
	 * @author Anton Kazennikov
	 *
	 */
	public class AliasProvider implements FeatureFunction.Provider {
		String alias;
		
		public AliasProvider(String alias) {
			this.alias = alias;
		}

		@Override
		public FeatureFunction get() {
			return providers.get(alias).get();
		}
		
	}
	
	public FeatureRegister() {
		register("tuple", new CommonFeatures.Tuple());
	}
	
	/**
	 * Get feature function by name.
	 * @param name feature function name
	 * @return FeatureFunction if it exists, or null
	 */
	public FeatureFunction get(String name) {
		FeatureFunction.Provider b = providers.get(name);
		if(b != null)
			return b.get();
		
		return null;
	}
	
	/**
	 * Register feature function with its default name
	 * @param fun
	 */
	public void register(FeatureFunction fun) {
		register(fun.name(), fun);
	}
	
	/**
	 * Register a function alias
	 * @param alias function alias name
	 * @param name real function name
	 */
	public void register(String alias, String name) {
		register(alias, new AliasProvider(name));
	}
	
	/**
	 * Register feature function provider with custom name
	 * @param name feature function name
	 * @param provider feature function provider
	 */
	public void register(String name, FeatureFunction.Provider provider) {
		providers.put(name, provider);
	}
	
	/**
	 * Register feature function with specified name
	 * @param name feature function name
	 * @param fun feature function to register
	 */
	public void register(String name, FeatureFunction fun) {
		providers.put(name, identityFor(fun));
	}
	
	/**
	 * Check if this register contains a function with specified name
	 * @param name
	 * @return
	 */
	public boolean contains(String name) {
		return providers.containsKey(name);
	}
	
	/**
	 * Static method to create identity providers for feature functions
	 * @param fun feature function
	 * @return fresh feature function provider
	 */
	public static FeatureFunction.Provider identityFor(FeatureFunction fun) {
		return new IdentityProvider(fun);
	}
	
	

}
