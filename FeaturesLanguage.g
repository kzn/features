grammar FeaturesLanguage;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}


@header {
  package ru.iitp.proling.features;
}

@lexer::header {
  package ru.iitp.proling.features;
}

features: feature+;
arg: (feat | STRING);


feat: SIMPLE^ ('('! arg (','! arg)* ')'!)?; 

angle_pair: '<' arg (',' arg)+ '>' -> ^(SIMPLE["tuple"] arg+); 
feature: feats |  angle_pair;
feats: feat ( 
	       (',' feat)+ -> ^(SIMPLE["tuple"] feat+)
	       | -> ^(feat));

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};

header: '['! .* ']'!;
STRING : '"' (~('"' | '\\') | '\\' .)* '"';
SIMPLE: ~('(' | ')' | ' ' | ',' | '<' | '>' | '\t' | '\r' | '\n' )+;

