package ru.iitp.proling.features;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;


/**
 * Feature parser from simple definition syntax
 * @author ant
 *
 */
public class FeatureParser {
	protected static class Special extends Feature {
		int type;
		String token;
		public Special(int type, String token, List<Value> args) {
			super(null, null, args);
			this.type = type;
			this.token = token;
		}
		
		public int getType() {
			return type;
		}
		
		public String getToken() {
			return token;
		}
	}
	
	FeatureRegister fb;
	
	public FeatureParser(FeatureRegister fb) {
		this.fb = fb;
	}
	
	
	/**
	 * Parses string definition
	 * @param def actual definition
	 * @return FeatureExtractor
	 * @throws RecognitionException
	 */
	public FeatureExtractor parse(String def) throws RecognitionException {
		CharStream stream = new ANTLRStringStream(def);
		return parse(stream);
	}
	
	/**
	 * Helper function - parses antlr char stream
	 * @param stream stream to parse
	 * @return FeatureExtractor
	 * @throws RecognitionException
	 */
	FeatureExtractor parse(CharStream stream) throws RecognitionException {
		FeaturesLanguageLexer lexer = new FeaturesLanguageLexer(stream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		FeaturesLanguageParser parser = new FeaturesLanguageParser(tokenStream);
		Tree tree = parser.features().tree;
		return parse(tree);
	}
	
	/**
	 * Builds feature extractor from antlr parse tree
	 * @param tree antlr parse tree
	 * @return FeatureExtractor
	 */
	FeatureExtractor parse(Tree tree) {
		FeatureExtractor eval = new FeatureExtractor();
		Feature feats = parseFeature(tree);
		
		//List<Feature> feats = new ArrayList<Feature>();
		
		
		
		for(FeatureRewriter rewriter : eval.rewriters) {
			feats = rewriter.rewrite(feats);
		}
		
		for(Value v : feats.args()) {
			if(v instanceof Feature)
				eval.addFeature((Feature)v);
		}

				
		return eval;
	}

	/**
	 * Parses an antlr tree feature node to FeatureValue
	 * @param tree feature node
	 * @param eval
	 * @return
	 */
	Feature parseFeature(Tree tree) {
		String name = tree.getText();
		List<Value> args = new ArrayList<Value>();
		
		for(int i = 0; i != tree.getChildCount(); i++) {
			args.add(parseArg(tree.getChild(i)));
		}
		Feature feat = null;

		if(tree.getType() == FeaturesLanguageLexer.SIMPLE) {
			FeatureFunction f = fb.get(name);
			feat = new Feature(f, new Values.Var(), args);
		} else {
			feat = new Special(tree.getType(), name, args);
		}
				
		return feat;
	}
	
	/**
	 * Parses a feature argument value
	 */
	Value parseArg(Tree tree) {
		return tree.getChildCount() == 0? parseSimpleArg(tree) : parseFeature(tree);
	}
		
	/**
	 * Parses a simple(terminal) feature argument
	 */
	Value parseSimpleArg(Tree tree) {
		String text = tree.getText();
		if(fb.contains(text))
			return parseFeature(tree);
		
		if(text.isEmpty())
			return new Values.Const("");
		
		if(text.charAt(0) == '"' || text.charAt(0) == '\'')
			return parseString(text);
		try {
			int i = Integer.parseInt(text);
			return new Values.Const(Integer.valueOf(i));
		} catch(NumberFormatException e) {
		}
		
		return new Values.Const(text);
	}



	/**
	 * Parses string object to Value
	 */
	Value parseString(String text) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i != text.length() - 1; i++) {
			if(text.charAt(i) == '\\') {
				sb.append(text.charAt(++i));
			} else {
				sb.append(text.charAt(i));
			}
				
		}

		return new Values.Const(sb.toString());
	}

}
