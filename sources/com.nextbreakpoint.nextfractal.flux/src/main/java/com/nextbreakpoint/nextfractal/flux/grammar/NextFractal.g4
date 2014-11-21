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
	ORBIT '[' complex ',' complex ']' '{' begin loop condition end '}'
	;
		
color
	:
	COLOR '[' argb ']' '{' '}' 
	;
		
begin
	:
	statement*
	;
		
loop
	:
	statement*
	;
		
condition
	:
	;
		
end
	:
	statement*
	;
		
statement
	:
	variable '=' exp
	;
		
variable
	:
	USER_VARIABLE
	;
		
exp
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
	('+'|'-')? USER_RATIONAL
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

LT
	: 
	'<' 
	;

GT
	: 
	'>' 
	;

LE
	: 
	'<='
	;

GE
	: 
	'>=' 
	;

EQ
	: 
	'==' 
	;

NEQ
	: 
	'!='
	;

NOT
	: 
	'!' 
	;

AND
	: 
	'&&' 
	;

OR
	: 
	'||' 
	;

XOR
	: 
	'^^' 
	;

USER_PATHOP
	: 
	'MOVETO'
	| 
	'LINETO'
	| 
	'ARCTO'
	| 
	'CURVETO'
	| 
	'MOVEREL'
	| 
	'LINEREL'
	| 
	'ARCREL'
	| 
	'CURVEREL'
	| 
	'CLOSEPOLY' 
	;

USER_VARIABLE 
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
	