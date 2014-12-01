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
	private ASTBuilder builder = new ASTBuilder();
	
	public ASTBuilder getBuilder() { return builder; }
}

fractal
	:
	f=FRACTAL {
		builder.setFractal(new ASTFractal($f));
	} '{' orbit color '}' eof 
	;
		
orbit
	:
	o=ORBIT '[' ra=complex ',' rb=complex ']' {
		builder.setOrbit(new ASTOrbit($o, new ASTRegion($ra.result, $rb.result)));
	} '{' trap* projection? begin? loop condition end? '}'
	;
		
color
	:
	c=COLOR '[' argb=colorargb ']' { 
		builder.setColor(new ASTColor($c, $argb.result));
	} '{' palette* colorrule* '}'
	;
		
begin
	:
	b=BEGIN { 
		builder.setOrbitBegin(new ASTOrbitBegin($b));
	} '{' beginstatements* '}'
	;
		
loop
	:
	l=LOOP '[' lb=USER_INTEGER ',' le=USER_INTEGER ']' {
		builder.setOrbitLoop(new ASTOrbitLoop($l, builder.parseInt($lb.text), builder.parseInt($le.text)));
	} '{' loopstatements* '}'
	;
		
end
	:
	e=END {
		builder.setOrbitEnd(new ASTOrbitEnd($e));		
	} '{' endstatements* '}'
	;
		
condition
	:
	c=CONDITION '{' e=conditionexp '}' {
		builder.setOrbitCondition(new ASTOrbitCondition($c, $e.result));
	}
	;
			
projection
	:
	p=PROJECTION '{' ce=complexexp '}' {
		builder.setOrbitProjection(new ASTOrbitProjection($p, $ce.result));
	}
	|
	p=PROJECTION '{' re=realexp '}' {
		builder.setOrbitProjection(new ASTOrbitProjection($p, $re.result));
	}
	;
	
trap
	:
	t=TRAP n=USER_VARIABLE '[' c=complex ']' {
		builder.addOrbitTrap(new ASTOrbitTrap($t, $n.text, $c.result));
	} '{' pathop* '}'
	;
		
pathop
	:
	o=USER_PATHOP_1POINTS '(' c=complex ')' ';' {
		builder.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text, $c.result));
	}
	|
	o=USER_PATHOP_2POINTS '(' c1=complex ',' c2=complex ')' ';' {
		builder.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text, $c1.result, $c2.result));
	}
	;
		
beginstatements 
	:
	s=statement {
		builder.addBeginStatement($s.result);
	}
	;
		
loopstatements 
	:
	s=statement {
		builder.addLoopStatement($s.result);
	}
	;
		
endstatements 
	:
	s=statement {
		builder.addEndStatement($s.result);
	}
	;
		
statement returns [ASTStatement result]
	:
	v=USER_VARIABLE '=' e=complexexp ';' {
		$result = new ASTStatement($v, $v.text, $e.result);
	}
	;
		
conditionexp returns [ASTConditionExpression result]
	:
	e1=realexp o=('=' | '<' | '>' | '<=' | '>=' | '<>') e2=realexp {
		$result = new ASTConditionCompareOp($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	v=USER_VARIABLE '[' e=complexexp ']'{
		$result = new ASTConditionTrap($v, $v.text, $e.result);
	}
	| 
	c1=conditionexp l=('&' | '|' | '^') c2=conditionexp {
		$result = new ASTConditionLogicOp($c1.result.getLocation(), $l.text, $c1.result, $c2.result);
	}	
	;
	
realexp returns [ASTRealExpression result]
	:
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
	m='|' ce=complexexp '|' {
		$result = new ASTComplexRealFunction($m, "mod", $ce.result);	
	}
	|
	a='[' ce=complexexp ']' {
		$result = new ASTComplexRealFunction($a, "pha", $ce.result);	
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
	re1=realexp '+' re2=realexp {
		$result = new ASTRealOp($re1.result.getLocation(), "+", $re1.result, $re2.result);		
	}
	|
	re1=realexp '-' re2=realexp {
		$result = new ASTRealOp($re1.result.getLocation(), "-", $re1.result, $re2.result);		
	}
	|
	re3=realexp2 {
		$result = $re3.result;	
	}
	;

realexp2 returns [ASTRealExpression result]
	:
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
	m='|' ce=complexexp '|' {
		$result = new ASTComplexRealFunction($m, "mod", $ce.result);	
	}
	|
	a='[' ce=complexexp ']' {
		$result = new ASTComplexRealFunction($a, "pha", $ce.result);	
	}
	|
	re1=realexp2 '*' re2=realexp2 {
		$result = new ASTRealOp($re1.result.getLocation(), "*", $re1.result, $re2.result);		
	}
	|
	re3=realexp3 {
		$result = $re3.result;	
	}
	;

realexp3 returns [ASTRealExpression result]
	:
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
	m='|' ce=complexexp '|' {
		$result = new ASTComplexRealFunction($m, "mod", $ce.result);	
	}
	|
	a='[' ce=complexexp ']' {
		$result = new ASTComplexRealFunction($a, "pha", $ce.result);	
	}
	|
	re1=realexp3 '/' re2=realexp3 {
		$result = new ASTRealOp($re1.result.getLocation(), "/", $re1.result, $re2.result);		
	}
	|
	re3=realexp4 {
		$result = $re3.result;	
	}
	;

realexp4 returns [ASTRealExpression result]
	:
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
	m='|' ce=complexexp '|' {
		$result = new ASTComplexRealFunction($m, "mod", $ce.result);	
	}
	|
	a='[' ce=complexexp ']' {
		$result = new ASTComplexRealFunction($a, "pha", $ce.result);	
	}
	|
	re1=realexp4 '^' re2=realexp2 {
		$result = new ASTRealOp($re1.result.getLocation(), "^", $re1.result, $re2.result);		
	}
	;
	
complexexp returns [ASTComplexExpression result]
	:
	c=complex {
		$result = $c.result;
	}
	|
	v=complexvariable {
		$result = $v.result;
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
	ce1=complexexp '+' ce2=complexexp {
		$result = new ASTComplexOp($ce1.result.getLocation(), "+", $ce1.result, $ce2.result);
	}
	|
	ce1=complexexp '-' ce2=complexexp {
		$result = new ASTComplexOp($ce1.result.getLocation(), "-", $ce1.result, $ce2.result);
	}
	|
	r=realexp {
		$result = $r.result;
	}
	|
	ce3=complexexp2 {
		$result = $ce3.result;
	}
	;
		
complexexp2 returns [ASTComplexExpression result]
	:
	v=complexvariable {
		$result = $v.result;
	}
	|
	cf=complexfunction {
		$result = $cf.result;
	}
	|
	t='(' ce=complexexp ')' {
		$result = new ASTComplexParen($t, $ce.result);
	}
	|
	ce1=complexexp2 '*' ce2=complexexp2 {
		$result = new ASTComplexOp($ce1.result.getLocation(), "*", $ce1.result, $ce2.result);
	}
	|
	i='i' ce2=complexexp2 {
		$result = new ASTComplexOp($i, "*", new ASTComplex($i, 0.0, 1.0), $ce2.result);
	}
	|
	ce2=complexexp2 i='i' {
		$result = new ASTComplexOp($ce2.result.getLocation(), "*", new ASTComplex($i, 0.0, 1.0), $ce2.result);
	}
	|
	r=realexp2 {
		$result = $r.result;
	}
	|
	ce3=complexexp3 {
		$result = $ce3.result;
	}
	;
	
complexexp3 returns [ASTComplexExpression result]
	:
	v=complexvariable {
		$result = $v.result;
	}
	|
	cf=complexfunction {
		$result = $cf.result;
	}
	|
	t='(' ce=complexexp ')' {
		$result = new ASTComplexParen($t, $ce.result);
	}
	|
	ce1=complexexp3 '^' re2=realexp3 {
		$result = new ASTComplexOp($ce1.result.getLocation(), "^", $ce1.result, $re2.result);
	}
	|
	r=realexp2 {
		$result = $r.result;
	}
	;
	
realfunction returns [ASTRealFunction result]
	:
	f=('mod' | 'pha') '(' ce=complexexp ')' {
		$result = new ASTComplexRealFunction($f, $f.text, $ce.result);		
	}
	|
	f=('cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan' | 'log' | 'exp' | 'mod' | 'sqrt') '(' a=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result });		
	}
	|
	f=('pow' | 'atan2' | 'hypot') '(' a=realexp ',' b=realexp ')' {
		$result = new ASTRealFunction($f, $f.text, new ASTRealExpression[] { $a.result, $b.result });		
	}
	;
			
complexfunction returns [ASTComplexFunction result]
	:
	f=('cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan' | 'exp') '(' a=complexexp ')' {
		$result = new ASTComplexFunction($f, $f.text, new ASTComplexExpression[] { $a.result });
	}
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
	
real returns [ASTReal result] 
	:
	'+'? r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTReal($r, builder.parseDouble($r.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTReal($r, builder.parseDouble("-" + $r.text));
	}
	; 
	
complex returns [ASTComplex result]
	:
	'+'? r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, builder.parseDouble($r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'+'? r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, builder.parseDouble($r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'+'? i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($i, 0.0, builder.parseDouble($i.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, builder.parseDouble("-" + $r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($r, builder.parseDouble("-" + $r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTComplex($i, 0.0, builder.parseDouble("-" + $i.text));
	}
	|
	rn=real {
		$result = new ASTComplex($rn.result);
	}
	; 

palette 
	:
	p=PALETTE v=USER_VARIABLE '[' l=USER_INTEGER ']' {
		builder.addPalette(new ASTPalette($p, $v.text, builder.parseInt($l.text))); 
	} '{' paletteelement+ '}'
	;
		
paletteelement 
	:
	t='[' bi=USER_INTEGER ',' bc=colorargb ']' '>' '[' ei=USER_INTEGER ',' ec=colorargb ']' ':' '[' e=realexp ']' ';' {
		builder.addPaletteElement(new ASTPaletteElement($t, builder.parseInt($bi.text), builder.parseInt($ei.text), $bc.result, $ec.result, $e.result));
	}
	|
	t='[' bi=USER_INTEGER ',' bc=colorargb ']' '>' '[' ei=USER_INTEGER ',' ec=colorargb ']' ';' {
		builder.addPaletteElement(new ASTPaletteElement($t, builder.parseInt($bi.text), builder.parseInt($ei.text), $bc.result, $ec.result, null));
	}  
	;
		
colorrule 
	:
	t=RULE '(' r=ruleexp ')' '[' o=USER_RATIONAL ']' '{' c=colorexp '}' {
		builder.addRule(new ASTRule($t, builder.parseFloat($o.text), $r.result, $c.result));
	} 
	;
		
ruleexp returns [ASTRuleExpression result]
	:
	e1=realexp o=('=' | '>' | '<' | '>=' | '<=' | '<>') e2=realexp {
		$result = new ASTRuleOpExpression($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	r1=ruleexp o=('&' | '|' | '^') r2=ruleexp {
		$result = new ASTRuleLogicOpExpression($r1.result.getLocation(), $o.text, $r1.result, $r2.result);
	}
	;
		
colorexp returns [ASTColorExpression result]
	:
	e1=realexp {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result);
	}
	|
	e1=realexp ',' e2=realexp ',' e3=realexp {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result, $e2.result, $e3.result);
	}
	|
	e1=realexp ',' e2=realexp ',' e3=realexp ',' e4=realexp {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result, $e2.result, $e3.result, $e4.result);
	}
	|
	v=USER_VARIABLE '[' e=realexp ']' {
		$result = new ASTColorPalette($v, $v.text, $e.result);
	}
	;
		
colorargb returns [ASTColorARGB result]
	:
	'(' a=USER_RATIONAL ',' r=USER_RATIONAL ',' g=USER_RATIONAL ',' b=USER_RATIONAL ')' {
		$result = new ASTColorARGB(builder.parseFloat($a.text), builder.parseFloat($r.text), builder.parseFloat($g.text), builder.parseFloat($b.text));
	}
	|
	'#' argb=USER_ARGB {
		$result = new ASTColorARGB(builder.parseInt($argb.text, 16));
	}
	;
		
eof 
	:
	EOF
	;
	
FRACTAL 
	:
	'fractal'
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
	