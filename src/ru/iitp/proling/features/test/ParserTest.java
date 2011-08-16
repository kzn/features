package ru.iitp.proling.features.test;

import java.util.List;

import junit.framework.TestCase;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import com.google.common.base.Joiner;

import ru.iitp.proling.features.CommonFeatures;
import ru.iitp.proling.features.FeatureExtractor;
import ru.iitp.proling.features.FeatureFunction;
import ru.iitp.proling.features.FeatureParser;
import ru.iitp.proling.features.FeatureRegister;
import ru.iitp.proling.features.Value;
import ru.iitp.proling.features.Value.Settable;

public class ParserTest extends TestCase {
	public static class SimpleFeat implements FeatureFunction {
		String name;
		public SimpleFeat(String name) {
			this.name = name;
		}
		@Override
		public String name() {
			return name;
		}
		@Override
		public void eval(Settable res, List<Value> args) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static class SimpleFeatIndexed extends CommonFeatures.Indexed<Object> {
		String name;

		public SimpleFeatIndexed(String name) {
			this.name = name;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public void eval(Settable res, List<Value> args) {
			throw new UnsupportedOperationException();
		}
	}

	
	public static String parse(String def) {
		FeatureRegister fr = new FeatureRegister();
		fr.register(new SimpleFeat("n"));
		fr.register(new SimpleFeat("m"));
		fr.register(new SimpleFeatIndexed("p"));
		
		FeatureParser parser = new FeatureParser(fr);
		try {
			FeatureExtractor fe = parser.parse(def);
			return Joiner.on(' ').join(fe.get());
		} catch(RecognitionException e) {
		}
		return "";
	}

	@Test
	public void testSimple() {
		assertEquals(parse("n").toString(), "n()");
		assertEquals(parse("n(0)").toString(), "n(0)");
		assertEquals(parse("n(0,1)").toString(), "n(0, 1)");
		assertEquals(parse("n(0) m(0)").toString(), "n(0) m(0)");
	}
	
	@Test
	public void testTuple() {
		assertEquals(parse("n(0),m(0)"), "tuple(n(0), m(0))");
		assertEquals(parse("<n(0),m(0)>"), "tuple(n(0), m(0))");
	}
	
	
	
	@Test
	public void testTupleSimplifier() {
		assertEquals(parse("n(0),tuple(m(1), m(2))"), "tuple(n(0), m(1), m(2))");
	}
	
	@Test
	public void testIndexedRewriter() {
		assertEquals(parse("p(1,2)"), "tuple(p(1), p(2))");
	}
	
	@Test 
	public void testSpecialTuple() {
		assertEquals(parse("n(0){m(1) m(2)}"), "tuple(n(0), m(1)) tuple(n(0), m(2))");
		assertEquals(parse("n(0){m(1) m(2) {p(0) p(1)}}"), "tuple(n(0), m(1)) tuple(n(0), m(2), p(0)) tuple(n(0), m(2), p(1))");
	}
	
	@Test
	public void testSpecialRewriterTupleSequence() {
		assertEquals(parse("seq(n(0), n(1)){m(1) m(2)}"), 
				"tuple(n(0), m(1)) tuple(n(0), m(2)) tuple(n(1), m(1)) tuple(n(1), m(2))");
	}
	

}
