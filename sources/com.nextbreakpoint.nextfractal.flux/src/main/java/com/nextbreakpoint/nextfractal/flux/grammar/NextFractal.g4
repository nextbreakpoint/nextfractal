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
	o=ORBIT '[' ra=complex ',' rb=complex ']' {
		driver.setOrbit(new ASTOrbit($o, new ASTRegion($ra.result, $rb.result)));
	} '{' trap* projection? begin? loop condition end? '}'
	;
		
color
	:
	c=COLOR '[' argb=colorargb ']' { 
		driver.setColor(new ASTColor($c, $argb.result));
	} '{' palette* colorrule* '}'
	;
		
begin
	:
	b=BEGIN { 
		driver.setOrbitBegin(new ASTOrbitBegin($b));
	} '{' beginstatements? '}'
	;
		
loop
	:
	l=LOOP '[' lb=USER_INTEGER ',' le=USER_INTEGER ']' {
		driver.setOrbitLoop(new ASTOrbitLoop($l, $lb.text, $le.text));
	} '{' loopstatements? '}'
	;
		
end
	:
	e=END {
		driver.setOrbitEnd(new ASTOrbitEnd($e));		
	} '{' endstatements? '}'
	;
		
condition
	:
	c=CONDITION '{' conditionexp '}' {
		driver.setOrbitCondition(new ASTOrbitCondition($c, null));
	}
	;
			
projection
	:
	p=PROJECTION '{' ce=complexexp '}' {
		driver.setOrbitProjection(new ASTOrbitProjection($p, $ce.result));
	}
	|
	p=PROJECTION '{' re=realexp '}' {
		driver.setOrbitProjection(new ASTOrbitProjection($p, $re.result));
	}
	;
	
trap
	:
	t=TRAP n=USER_TRAP '[' c=complex ']' {
		driver.addOrbitTrap(new ASTOrbitTrap($t, $n.text));
	} '{' pathops? '}' 
	;
		
pathops 
	:
	o=pathop+ {
	}
	;
		
pathop
	:
	o=USER_PATHOP_1POINTS '(' c=complex ')' ';'
	|
	o=USER_PATHOP_2POINTS '(' c1=complex ',' c2=complex ')' ';'
	;
		
beginstatements 
	:
	s=statement+ {
		driver.addBeginStatement($s.result);
	}
	;
		
loopstatements 
	:
	s=statement+ {
		driver.addLoopStatement($s.result);
	}
	;
		
endstatements 
	:
	s=statement+ {
		driver.addEndStatement($s.result);
	}
	;
		
statement returns [ASTStatement result]
	:
	v=USER_VARIABLE '=' e=complexexp ';' {
		$result = new ASTStatement($v, $v.text, $e.result);
	}
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
	
realvariable returns [ASTRealVariable result]
	:
	v=USER_VARIABLE {
		$result = new ASTRealVariable($v, $v.text);
	}
	;
	
complexvariable returns [ASTComplexVariable result]
	:
	v=USER_VARIABLE {
		$result = new ASTComplexVariable($v, $v.text);
	}
	;
	
realexp returns [ASTRealExpression result]
	:
	(
	v=realvariable {
		$result = $v.result;
	}
	|
	r=real {
		$result = $r.result;
	}
	|
	f=realfunction {
		$result = $f.result;
	}
	|
	t='(' re=realexp ')' {
		$result = new ASTRealParen($t, $re.result);
	}
	|
	s='-' re=realexp {
		$result = new ASTRealOp($s, "-", $re.result);
	}
	|
	'+' re=realexp {
		$result = $re.result;	
	}
	|
	m='|' ce=complexexp '|' {
		$result = new ASTComplexMod($m, $ce.result);	
	}
	|
	a='<' ce=complexexp '>' {
		$result = new ASTComplexAng($a, $ce.result);	
	}
	)
	(
	o=USER_MATH_OPERATOR re2=realexp {
		$result = new ASTRealOp($o, $o.text, $re.result, $re2.result);		
	}
	)?
	;
		
complexexp returns [ASTComplexExpression result]
	:
	(
	v=complexvariable {
		$result = $v.result;
	}
	|
	c=complex  {
		$result = $c.result;
	}
	|
	f=complexfunction {
		$result = $f.result;
	}
	|
	t='(' ce=complexexp ')' {
		$result = new ASTComplexParen($t, $ce.result);
	}
	|
	s='-' ce=complexexp {
		$result = new ASTComplexOp($s, "-", $ce.result);
	}
	|
	'+' complexexp {
		$result = $ce.result;
	}
	)
	(
	o=USER_MATH_OPERATOR ce2=complexexp {
		$result = new ASTComplexOp($o, $o.text, $ce.result, $ce2.result);
	}
	)? 
	;
				
realfunction returns [ASTRealExpression result]
	:
	f=USER_REAL_FUNCTION_1ARGS '(' a=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result });		
	}
	|
	f=USER_REAL_FUNCTION_2ARGS '(' a=realexp ',' b=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result, $b.result });		
	}
	;
			
complexfunction returns [ASTComplexExpression result]
	:
	f=USER_COMPLEX_FUNCTION_1ARGS '(' a=complexexp ')' {
		$result = new ASTComplexFunction($f, $f.text, new ASTComplexExpression[] { $a.result });
	}
	;
	
integer returns [ASTInteger result]
	:
	i=USER_INTEGER {
		$result = new ASTInteger($i, $i.text);
	}
	; 
	
real returns [ASTReal result] 
	:
	p=('+'|'-')? r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTReal($p, $p != null ? $p.text + $r.text : $r.text);
	}
	; 
	
complex returns [ASTComplex result]
	:
	p=('+'|'-')? r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($p, $p != null ? $p.text + $r.text : $r.text, "+" + $i.text);
	}
	|
	p=('+'|'-')? r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($p, $p != null ? $p.text + $r.text : $r.text, "-" + $i.text);
	}
	|
	p=('+'|'-')? i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($p, "0", $p != null ? $p.text + $i.text : $i.text);
	}
	; 

palette 
	:
	PALETTE USER_PALETTE '[' integer ']' '{' paletteexp '}'
	;
		
paletteexp 
	:
	'[' integer ',' colorargb ']' '>' '[' integer ',' colorargb ']' ':' '[' realexp ']' ';'  
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
		
colorargb returns [ASTColorARGB result]
	:
	'(' a=USER_RATIONAL ',' r=USER_RATIONAL ',' g=USER_RATIONAL ',' b=USER_RATIONAL ')' {
		$result = new ASTColorARGB($a.text, $r.text, $g.text, $b.text);
	}
	|
	'#' argb=USER_ARGB {
		$result = new ASTColorARGB($argb.text);
	}
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
	'loop'
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
	('0'..'9')+ '.' ('0'..'9')* '%'? | '.' ('0'..'9')+ '%'? | '0'..'9'+ '%' '.' '.'? ('0'..'9')+ '.' ('0'..'9')* ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | '.' ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'?
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
	