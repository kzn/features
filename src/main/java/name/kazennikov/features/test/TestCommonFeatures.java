package name.kazennikov.features.test;

import junit.framework.TestCase;

import name.kazennikov.features.FeatureExtractor;
import name.kazennikov.features.FeatureFunctions;
import name.kazennikov.features.FeatureParser;
import name.kazennikov.features.FeatureRegister;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;


public class TestCommonFeatures extends TestCase {

	FeatureParser fp;


	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		FeatureRegister fr = new FeatureRegister();
		fr.register(new FeatureFunctions.Null());
		fr.register(new FeatureFunctions.Const("foo"));
		fp = new FeatureParser(fr);
	}
	
	@Test
	public void testNullTuple() {
		try {
			FeatureExtractor fe = fp.parse("null,foo foo,foo");
			
			assertEquals(1, fe.eval("bar").size());
		} catch(RecognitionException e) {
		}

	}
	
	
	

}
