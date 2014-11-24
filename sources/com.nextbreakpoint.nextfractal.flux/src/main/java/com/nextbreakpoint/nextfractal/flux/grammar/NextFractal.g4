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
	t=TRAP n=USER_VARIABLE '[' c=complex ']' {
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
	realexp '=' realexp
	|
	realexp '>' realexp
	|
	realexp '<' realexp
	|
	realexp '>=' realexp
	|
	realexp '<=' realexp
	|
	realexp '<>' realexp
	|
	'%' USER_VARIABLE 
	)
	(
	'&' conditionexp	
	|
	'|' conditionexp
	|	
	'^' conditionexp	
	)?
	;
	
realexp returns [ASTRealExpression result]
	:
	(
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
	m='|' ce=complexexp '|' {
		$result = new ASTComplexMod($m, $ce.result);	
	}
	|
	a='[' ce=complexexp ']' {
		$result = new ASTComplexAng($a, $ce.result);	
	}
	|
	s='-' re=realexp {
		$result = new ASTRealOp($s, "-", $re.result);
	}
	|
	s='+' re=realexp {
		$result = $re.result;	
	}
	|
	re3=realexp2 {
		$result = $re3.result;	
	}
	)
	(
	'+' re2=realexp {
		$result = new ASTRealOp($result.getLocation(), "+", $result, $re2.result);		
	}
	|
	'-' re2=realexp {
		$result = new ASTRealOp($result.getLocation(), "-", $result, $re2.result);		
	}
	)?
	;
	
realexp2 returns [ASTRealExpression result]
	:
	(
	r=real {
		$result = $r.result;
	}
	|
	f=realfunction {
		$result = $f.result;
	}
	|
	t='(' re2=realexp2 ')' {
		$result = new ASTRealParen($t, $re2.result);
	}
	)
	(
	'*' re2=realexp2 {
		$result = new ASTRealOp($result.getLocation(), "*", $result, $re2.result);		
	}
	|
	'/' re2=realexp2 {
		$result = new ASTRealOp($result.getLocation(), "/", $result, $re2.result);		
	}
	|
	'^' re2=realexp2 {
		$result = new ASTRealOp($result.getLocation(), "^", $result, $re2.result);		
	}
	)?
	;
	
complexexp returns [ASTComplexExpression result]
	:
	(
	v=variable {
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
	s='+' ce=complexexp {
		$result = $ce.result;
	}
	|
	re=realexp {
		$result = $re.result;
	}
	|
	ce3=complexexp2 {
		$result = $ce3.result;
	}
	)
	(
	'+' ce2=complexexp {
		$result = new ASTComplexOp($result.getLocation(), "+", $result, $ce2.result);
	}
	|
	'-' ce2=complexexp {
		$result = new ASTComplexOp($result.getLocation(), "-", $result, $ce2.result);
	}
	)? 
	;
	
complexexp2 returns [ASTComplexExpression result]
	:
	(
	v=variable {
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
	t='(' ce2=complexexp2 ')' {
		$result = new ASTComplexParen($t, $ce2.result);
	}
	)
	(
	'*' ce2=complexexp2 {
		$result = new ASTComplexOp($result.getLocation(), "*", $result, $ce2.result);
	}
	|
	'^' re2=realexp2 {
		$result = new ASTComplexOp($result.getLocation(), "^", $result, $re2.result);
	}
	)? 
	;
	
realfunction returns [ASTRealFunction result]
	:
	f='mod' '(' ce=complexexp ')' {
		$result = new ASTModFunction($f, $ce.result);		
	}
	|
	f='ang' '(' ce=complexexp ')' {
		$result = new ASTAngFunction($f, $ce.result);		
	}
	|
	f=USER_REAL_FUNCTION_1ARGS '(' a=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result });		
	}
	|
	f=USER_REAL_FUNCTION_2ARGS '(' a=realexp ',' b=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result, $b.result });		
	}
	;
			
complexfunction returns [ASTComplexFunction result]
	:
	f=USER_COMPLEX_FUNCTION_1ARGS '(' a=complexexp ')' {
		$result = new ASTComplexFunction($f, $f.text, new ASTComplexExpression[] { $a.result });
	}
	;
	
variable returns [ASTVariable result]
	:
	v=USER_VARIABLE {
		$result = new ASTVariable($v, $v.text);
	}
	;
	
real returns [ASTReal result] 
	:
	r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTReal($r, $r.text);
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTReal($r, "-" + $r.text);
	}
	; 
	
complex returns [ASTComplex result]
	:
	r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, $r.text, "+" + $i.text);
	}
	|
	r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, $r.text, "-" + $i.text);
	}
	|
	i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($i, "0", $i.text);
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, "-" + $r.text, "+" + $i.text);
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, "-" + $r.text, "-" + $i.text);
	}
	|
	'-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($i, "0", "-" + $i.text);
	}
	; 

palette 
	:
	PALETTE USER_VARIABLE '[' USER_INTEGER ']' '{' paletteexp '}'
	;
		
paletteexp 
	:
	'[' USER_INTEGER ',' colorargb ']' '>' '[' USER_INTEGER ',' colorargb ']' ':' '[' realexp ']' ';'  
	;
		
colorrule 
	:
	RULE '(' ruleexp ')' '[' USER_INTEGER '%' ']' '{' colorexp '}'
	;
		
ruleexp 
	:
	(
	realexp '=' realexp
	|
	realexp '>' realexp
	|
	realexp '<' realexp
	|
	realexp '>=' realexp
	|
	realexp '<=' realexp
	|
	realexp '<>' realexp
	)
	(
	'&' ruleexp
	|	
	'|' ruleexp	
	|	
	'^' ruleexp	
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
	'%' USER_VARIABLE '(' USER_INTEGER ')'
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
	'cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan' | 'exp'
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
	('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9')* 
	;

COMMENT
	: 
	('//' ~('\n'|'\r')* '\r'? '\n' {} | '/*' (.)*? '*/' {}) -> skip 
	;

WHITESPACE  
	: 
	( ' ' | '\t' | '\r' | '\n' ) -> skip 
	;
	