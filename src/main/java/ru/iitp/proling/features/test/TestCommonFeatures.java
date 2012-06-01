package ru.iitp.proling.features.test;

import junit.framework.TestCase;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import ru.iitp.proling.features.FeatureExtractor;
import ru.iitp.proling.features.FeatureFunctions;
import ru.iitp.proling.features.FeatureParser;
import ru.iitp.proling.features.FeatureRegister;

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
