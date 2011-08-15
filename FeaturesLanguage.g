grammar FeaturesLanguage;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
    TUPLE;
}


@header {
  package ru.iitp.proling.features;
}

@lexer::header {
  package ru.iitp.proling.features;
}

features: feature+ -> ^(SIMPLE["seq"] feature+);
arg: (feat | STRING);



args: '(' arg (',' arg)* ')' -> arg+;

feat_simple: SIMPLE (
		     '(' arg (',' arg)* ')' -> ^(SIMPLE arg+)
		     | -> ^(SIMPLE));

array: '[' SIMPLE (',' SIMPLE)* ']' -> SIMPLE+;

simple_or_array: feat_simple (
			 array -> ^(SIMPLE["array"] feat_simple array)
			 | -> feat_simple);

feat: simple_or_array
	      ('{' feature+ '}' -> ^(TUPLE simple_or_array feature+)
	      | -> ^(simple_or_array)
	      ) ;


angle_pair: '<' arg (',' arg)+ '>' -> ^(SIMPLE["tuple"] arg+); 
feature: feats |  angle_pair;
feats: feat ( 
	       (',' feat)+ -> ^(SIMPLE["tuple"] feat+)
	       | -> ^(feat));

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
SIMPLE: ~('(' | ')' | ' ' | ',' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' |
		'[' | ']' )+;

