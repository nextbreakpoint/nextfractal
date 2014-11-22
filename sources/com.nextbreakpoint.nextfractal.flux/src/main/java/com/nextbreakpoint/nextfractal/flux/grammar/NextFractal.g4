grammar NextFractal;

options
{
} 

@lexer::header { 
}

@parser::header {
	import java.util.Map;
}

@members {
	ASTBuilder driver = new ASTBuilder();
}

root
	:
	orbit color eof
	;
		
orbit
	:
	ORBIT '[' complex ',' complex ']' '{' trap* projection? begin? loop condition end? '}'
	;
		
color
	:
	COLOR '[' argb ']' '{' palette* colorrule* '}' 
	;
		
begin
	:
	BEGIN '{' statement* '}'
	;
		
loop
	:
	LOOP '[' integer ',' integer ']' '{' statement* '}'
	;
		
condition
	:
	CONDITION '{' conditionexp '}'
	;
			
projection
	:
	PROJECTION '{' projectionexp '}'
	;
	
trap
	:
	TRAP USER_TRAP '[' complex ']' '{' pathop* '}'
	;
		
end
	:
	END '{' statement* '}'
	;
		
pathop
	:
	USER_PATHOP_1POINTS '(' complex ')' ';'
	|
	USER_PATHOP_2POINTS '(' complex ',' complex ')' ';'
	;
		
statement
	:
	variable '=' complexexp ';'
	;
		
conditionexp
	:
	(
	realexp USER_COMPARE_OPERATOR realexp
	|
	USER_TRAP
	)
	(
	USER_LOGIC_OPERATOR conditionexp	
	)?
	;
	
projectionexp
	:
	complexexp
	;
	
variable
	:
	USER_VARIABLE
	;

realexp
	:
	(
	real 
	|
	realfunction
	|
	'(' realexp ')'
	|
	'-' realexp
	|
	'+' realexp
	|
	'|' realexp '|'
	|
	'<' realexp '>'
	)
	(
	USER_MATH_OPERATOR realexp
	)?
	;
		
complexexp
	:
	(
	realexp
	|
	complex 
	|
	complexfunction
	|
	'(' complexexp ')'
	|
	'-' complexexp
	|
	'+' complexexp
	|
	'|' complexexp '|'
	|
	'<' complexexp '>'
	)
	(
	USER_MATH_OPERATOR complexexp
	)?
	;
				
realfunction 
	:
	USER_REAL_FUNCTION_1ARGS '(' realexp ')'
	|
	USER_REAL_FUNCTION_2ARGS '(' realexp ',' realexp ')'
	;
			
complexfunction 
	:
	USER_COMPLEX_FUNCTION_1ARGS '(' complexexp ')'
	;
	
integer
	:
	USER_INTEGER
	; 
	
real
	:
	('+'|'-')? USER_RATIONAL
	; 
	
complex
	:
	('+'|'-')? USER_RATIONAL '+' USER_RATIONAL 'i'
	|
	('+'|'-')? USER_RATIONAL '-' USER_RATIONAL 'i'
	|
	('+'|'-')? USER_RATIONAL 'i'
	|
	real
	; 

palette 
	:
	PALETTE USER_PALETTE '[' integer ']' '{' paletteexp '}'
	;
		
paletteexp 
	:
	'[' integer ',' argb ']' '>' '[' integer ',' argb ']' ':' '[' realexp ']' ';'  
	;
		
colorrule 
	:
	RULE '(' ruleexp ')' '[' integer '%' ']' '{' colorexp '}'
	;
		
ruleexp 
	:
	(
	realexp USER_COMPARE_OPERATOR realexp
	)
	(
	USER_LOGIC_OPERATOR ruleexp	
	)?
	;
		
colorexp 
	:
	realexp
	|
	realexp ',' realexp ',' realexp
	|
	realexp ',' realexp ',' realexp ',' realexp
	|
	USER_PALETTE '(' integer ')'
	;
		
argb 
	:
	'(' USER_RATIONAL ',' USER_RATIONAL ',' USER_RATIONAL ',' USER_RATIONAL ')'
	|
	'#' USER_ARGB
	;
		
eof 
	:
	EOF
	;
	
ORBIT 
	:
	'orbit'
	;
 
PROJECTION 
	:
	'projection'
	;
 
TRAP 
	:
	'trap'
	;
 
CONDITION 
	:
	'condition'
	;
 
BEGIN 
	:
	'begin'
	;
  
LOOP 
	:
	'lopp'
	;
	
END 
	:
	'end'
	;
	
COLOR 
	:
	'color'
	;
 
PALETTE 
	:
	'palette'
	;
	
RULE 
	:
	'rule'
	;

USER_LOGIC_OPERATOR
	:
	'&' | '|' | '^'
	;

USER_COMPARE_OPERATOR
	:
	'=' | '<>' | '<' | '>' | '<=' | '>='
	;

USER_MATH_OPERATOR
	:
	'*' | '/' | '^' | '+' | '-'
	;

USER_REAL_FUNCTION_1ARGS
	:
	'cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan' | 'log' | 'exp' | 'mod' | 'sqrt'
	;

USER_REAL_FUNCTION_2ARGS
	:
	'pow' | 'atan2' | 'hypot'
	;

USER_COMPLEX_FUNCTION_1ARGS
	:
	'cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan' | 'exp' | 'mod'
	;

USER_ARGB
 	:
 	('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')
 	|
 	('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')
 	;
 	
USER_RATIONAL
	: 
	('0'..'9')+ | ('0'..'9')+ '.' ('0'..'9')* '%'? | '.' ('0'..'9')+ '%'? | '0'..'9'+ '%' '.' '.'? ('0'..'9')+ '.' ('0'..'9')* ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | '.' ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'?
	; 

USER_INTEGER
	: 
	('0'..'9')+
	; 

USER_PATHOP_1POINTS
	: 
	'MOVETO'
	| 
	'LINETO'
	| 
	'ARCTO'
	| 
	'MOVEREL'
	| 
	'LINEREL'
	| 
	'ARCREL'
	;

USER_PATHOP_2POINTS
	: 
	'CURVETO'
	| 
	'CURVEREL'
	;

USER_VARIABLE 
	: 
	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|':'|'0'..'9')* 
	;

USER_TRAP 
	: 
	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|':'|'0'..'9')* 
	;

USER_PALETTE 
	: 
	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|':'|'0'..'9')* 
	;

USER_STRING 
	: 
	('a'..'z'|'A'..'Z'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377') (('a'..'z'|'A'..'Z'|':'|'0'..'9'|'_'|'\u0200'..'\u0301'|'\u0303'..'\u0377')|('\u0302'('\u0200'..'\u0260'|'\u0262'..'\u0377')))* 
	;

USER_QSTRING	
	:	
	'"' USER_STRING '"' 
	;
	
COMMENT
	: 
	('//' ~('\n'|'\r')* '\r'? '\n' {} | '/*' (.)*? '*/' {}) -> skip 
	;

WHITESPACE  
	: 
	( ' ' | '\t' | '\r' | '\n' ) -> skip 
	;
	