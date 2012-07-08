package name.kazennikov.features.test;

import name.kazennikov.features.FeaturesLanguageLexer;
import name.kazennikov.features.FeaturesLanguageParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

import junit.framework.TestCase;

public class ASTTest extends TestCase {
	public static Tree parse(String def) {
		try {
			CharStream s = new ANTLRStringStream(def);
			FeaturesLanguageLexer lexer = new FeaturesLanguageLexer(s);
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			FeaturesLanguageParser parser = new FeaturesLanguageParser(tokenStream);
			Tree tree = (Tree)parser.features().getTree();
			return tree;
		} catch(RecognitionException e) {
			return null;
		}
	}
	
	public static String ast(String def) {
		Tree tree = parse(def);
		if(tree == null)
			return "";
		return tree.toStringTree();
	}
	
	@Test
	public void testSingleDefinition() {
		assertEquals(ast("p"), "(seq p)");
		assertEquals(ast("p(1)"), "(seq (p 1))");
		assertEquals(ast("p(1) p(2)"), "(seq (p 1) (p 2))");
		assertEquals(ast("p(1,2)"), "(seq (p 1 2))");
	}
	
	@Test
	public void testString() {
		assertEquals(ast("p(1, \"foo\")"), "(seq (p 1 \"foo\"))");
		assertEquals(ast("p(1, \"foo\\\"bar\")"), "(seq (p 1 \"foo\\\"bar\"))");
	}
	
	@Test
	public void testPair() {
		assertEquals(ast("p(1),p(2)"), "(seq (tuple (p 1) (p 2)))");
		assertEquals(ast("<p(1),p(2)>"), "(seq (tuple (p 1) (p 2)))");
	}
	
	@Test
	public void testSpecialTuple() {
		assertEquals(ast("p(0){p(1) p(3)}"), "(seq (TUPLE (p 0) (p 1) (p 3)))");
	}
	
	@Test
	public void testArray() {
		assertEquals(ast("p(0)[5]"), "(seq (array (p 0) 5))");
		assertEquals(ast("p(0)[5,2]"), "(seq (array (p 0) 5 2))");
		assertEquals(ast("p(0)[5,2] { w(1) w(2)}"), "(seq (TUPLE (array (p 0) 5 2) (w 1) (w 2)))");
	}

}
