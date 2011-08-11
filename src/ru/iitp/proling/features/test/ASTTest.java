package ru.iitp.proling.features.test;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.junit.Test;

import ru.iitp.proling.features.FeaturesLanguageLexer;
import ru.iitp.proling.features.FeaturesLanguageParser;
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
		assertEquals(ast("p"), "p");
		assertEquals(ast("p(1)"), "(p 1)");
		assertEquals(ast("p(1) p(2)"), "(p 1) (p 2)");
		assertEquals(ast("p(1,2)"), "(p 1 2)");
	}
	
	@Test
	public void testString() {
		assertEquals(ast("p(1, \"foo\")"), "(p 1 \"foo\")");
		assertEquals(ast("p(1, \"foo\\\"bar\")"), "(p 1 \"foo\\\"bar\")");
	}
	
	@Test
	public void testPair() {
		assertEquals(ast("p(1),p(2)"), "(tuple (p 1) (p 2))");
		assertEquals(ast("<p(1),p(2)>"), "(tuple (p 1) (p 2))");
	}
	
	@Test
	public void testSpecialTuple() {
		assertEquals(ast("p(0){p(1) p(3)}"), "(#tuple (p 0) (p 1) (p 3))");
	}
	
	@Test
	public void testArray() {
		assertEquals(ast("p(0)[5]"), "(array (p 0) 5)");
		assertEquals(ast("p(0)[5,2]"), "(array (p 0) 5 2)");
		assertEquals(ast("p(0)[5,2] { w(1) w(2)}"), "(#tuple (array (p 0) 5 2) (w 1) (w 2))");
	}

}
