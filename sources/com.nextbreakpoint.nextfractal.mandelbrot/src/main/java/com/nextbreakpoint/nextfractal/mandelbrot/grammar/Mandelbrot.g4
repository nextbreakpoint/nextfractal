grammar Mandelbrot;

options
{
} 

@lexer::header { 
}

@parser::header {
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
	} '[' v=variablelist ']' '{' trap* begin? loop end? '}'
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
	l=LOOP '[' lb=USER_INTEGER ',' le=USER_INTEGER ']' '(' e=conditionexp ')'{
		builder.setOrbitLoop(new ASTOrbitLoop($l, builder.parseInt($lb.text), builder.parseInt($le.text), $e.result));
	} '{' loopstatements* '}'
	;
		
end
	:
	e=END {
		builder.setOrbitEnd(new ASTOrbitEnd($e));		
	} '{' endstatements* '}'
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
	v=USER_VARIABLE '=' e=expression ';' {
		$result = new ASTStatement($v, $v.text, $e.result);
		builder.registerVariable($v.text, $e.result.isReal(), $v);
	}
	;
		
variablelist 
	:
	v=USER_VARIABLE {
		builder.registerStateVariable($v.text, $v);
	}
	| 
	vl=variablelist ',' v=USER_VARIABLE {
		builder.registerStateVariable($v.text, $v);
	} 
	;
		
conditionexp returns [ASTConditionExpression result]
	:
	e1=expression o=('=' | '<' | '>' | '<=' | '>=' | '<>') e2=expression {
		$result = new ASTConditionCompareOp($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	v=USER_VARIABLE '?' e=expression {
		$result = new ASTConditionTrap($v, $v.text, $e.result, true);
	}
	|
	v=USER_VARIABLE '~?' e=expression {
		$result = new ASTConditionTrap($v, $v.text, $e.result, false);
	}
	| 
	c1=conditionexp l=('&' | '|' | '^') c2=conditionexp {
		$result = new ASTConditionLogicOp($c1.result.getLocation(), $l.text, $c1.result, $c2.result);
	}	
	;
	
expression returns [ASTExpression result]
	:
	v=variable {
		$result = $v.result;
	}
	|
	c=complex {
		$result = $c.result;
	}
	|
	f=function {
		$result = $f.result;
	}
	|
	t='(' e=expression ')' {
		$result = new ASTParen($t, $e.result);
	}
	|
	m='|' e=expression '|' {
		$result = new ASTFunction($m, "mod", $e.result);	
	}
	|
	a='<' e=expression '>' {
		$result = new ASTFunction($a, "pha", $e.result);	
	}
	|
	s='-' e=expression {
		$result = new ASTOperator($s, "-", $e.result);
	}
	|
	s='+' e=expression {
		$result = new ASTOperator($s, "+", $e.result);
	}
	|
	e1=expression s='+' e2=expression {
		$result = new ASTOperator($s, "+", $e1.result, $e2.result);		
	}
	|
	e1=expression s='-' e2=expression {
		$result = new ASTOperator($s, "-", $e1.result, $e2.result);		
	}
	|
	e3=expression2 {
		$result = $e3.result;	
	}
	;

expression2 returns [ASTExpression result]
	:
	v=variable {
		$result = $v.result;
	}
	|
	r=real {
		$result = $r.result;
	}
	|
	f=function {
		$result = $f.result;
	}
	|
	t='(' e=expression ')' {
		$result = new ASTParen($t, $e.result);
	}
	|
	m='|' e=expression '|' {
		$result = new ASTFunction($m, "mod", $e.result);	
	}
	|
	a='<' e=expression '>' {
		$result = new ASTFunction($a, "pha", $e.result);	
	}
	|
	e1=expression2 s='*' e2=expression2 {
		$result = new ASTOperator($s, "*", $e1.result, $e2.result);
	}
	|
	i='i' e2=expression2 {
		$result = new ASTOperator($i, "*", new ASTNumber($i, 0.0, 1.0), $e2.result);
	}
	|
	e2=expression2 i='i' {
		$result = new ASTOperator($i, "*", new ASTNumber($i, 0.0, 1.0), $e2.result);
	}
	|
	e3=expression3 {
		$result = $e3.result;
	}
	;

expression3 returns [ASTExpression result]
	:
	v=variable {
		$result = $v.result;
	}
	|
	r=real {
		$result = $r.result;
	}
	|
	f=function {
		$result = $f.result;
	}
	|
	t='(' e=expression ')' {
		$result = new ASTParen($t, $e.result);
	}
	|
	m='|' e=expression '|' {
		$result = new ASTFunction($m, "mod", $e.result);	
	}
	|
	a='<' e=expression '>' {
		$result = new ASTFunction($a, "pha", $e.result);	
	}
	|
	e1=expression3 s='/' e2=expression3 {
		$result = new ASTOperator($s, "/", $e1.result, $e2.result);
	}
	|
	e3=expression4 {
		$result = $e3.result;
	}
	;

expression4 returns [ASTExpression result]
	:
	v=variable {
		$result = $v.result;
	}
	|
	r=real {
		$result = $r.result;
	}
	|
	f=function {
		$result = $f.result;
	}
	|
	t='(' e=expression ')' {
		$result = new ASTParen($t, $e.result);
	}
	|
	m='|' e=expression '|' {
		$result = new ASTFunction($m, "mod", $e.result);	
	}
	|
	a='<' e=expression '>' {
		$result = new ASTFunction($a, "pha", $e.result);	
	}
	|
	e1=expression4 s='^' e2=expression4 {
		$result = new ASTOperator($s, "^", $e1.result, $e2.result);
	}
	;

function returns [ASTFunction result]
	:
	f=('mod' | 'pha' | 're' | 'im') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, $e.result);		
	}
	|
	f=('cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e.result });		
	}
	|
	f=('log' | 'exp' | 'sqrt') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e.result });		
	}
	|
	f=('pow' | 'atan2' | 'hypot') '(' e1=expression ',' e2=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e1.result, $e2.result });		
	}
	;
			
variable returns [ASTVariable result]
	:
	v=USER_VARIABLE {
		$result = new ASTVariable($v, builder.getVariable($v.text, $v));
	}
	;
	
real returns [ASTNumber result] 
	:
	'+'? r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTNumber($r, builder.parseDouble($r.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) {
		$result = new ASTNumber($r, builder.parseDouble("-" + $r.text));
	}
	; 
	
complex returns [ASTNumber result]
	:
	'<' '+'? r=(USER_RATIONAL | USER_INTEGER) ',' '+'? i=(USER_RATIONAL | USER_INTEGER) '>' {
		$result = new ASTNumber($r, builder.parseDouble($r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'<' '+'? r=(USER_RATIONAL | USER_INTEGER) ',' '-' i=(USER_RATIONAL | USER_INTEGER) '>' {
		$result = new ASTNumber($r, builder.parseDouble($r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'<' '-' r=(USER_RATIONAL | USER_INTEGER) ',' '+'? i=(USER_RATIONAL | USER_INTEGER) '>' {
		$result = new ASTNumber($r, builder.parseDouble("-" + $r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'<' '-' r=(USER_RATIONAL | USER_INTEGER) ',' '-' i=(USER_RATIONAL | USER_INTEGER) '>' {
		$result = new ASTNumber($r, builder.parseDouble("-" + $r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'+'? r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($r, builder.parseDouble($r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'+'? r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($r, builder.parseDouble($r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'+'? i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($i, 0.0, builder.parseDouble($i.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '+' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($r, builder.parseDouble("-" + $r.text), builder.parseDouble("+" + $i.text));
	}
	|
	'-' r=(USER_RATIONAL | USER_INTEGER) '-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($r, builder.parseDouble("-" + $r.text), builder.parseDouble("-" + $i.text));
	}
	|
	'-' i=(USER_RATIONAL | USER_INTEGER) 'i' {
		$result = new ASTNumber($i, 0.0, builder.parseDouble("-" + $i.text));
	}
	|
	rn=real {
		$result = $rn.result;
	}
	; 

palette 
	:
	p=PALETTE v=USER_VARIABLE {
		builder.addPalette(new ASTPalette($p, $v.text)); 
	} '{' paletteelement+ '}'
	;
		
paletteelement 
	:
	t='[' bc=colorargb '>' ec=colorargb ',' s=USER_INTEGER ',' e=expression ']' ';' {
		builder.addPaletteElement(new ASTPaletteElement($t, $bc.result, $ec.result, builder.parseInt($s.text), $e.result));
	}
	|
	t='[' bc=colorargb '>' ec=colorargb ',' s=USER_INTEGER ']' ';' {
		builder.addPaletteElement(new ASTPaletteElement($t, $bc.result, $ec.result, builder.parseInt($s.text), null));
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
	e1=expression o=('=' | '>' | '<' | '>=' | '<=' | '<>') e2=expression {
		$result = new ASTRuleCompareOpExpression($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	r1=ruleexp o=('&' | '|' | '^') r2=ruleexp {
		$result = new ASTRuleLogicOpExpression($r1.result.getLocation(), $o.text, $r1.result, $r2.result);
	}
	;
		
colorexp returns [ASTColorExpression result]
	:
	e1=expression {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result);
	}
	|
	e1=expression ',' e2=expression ',' e3=expression {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result, $e2.result, $e3.result);
	}
	|
	e1=expression ',' e2=expression ',' e3=expression ',' e4=expression {
		$result = new ASTColorComponent($e1.result.getLocation(), $e1.result, $e2.result, $e3.result, $e4.result);
	}
	|
	v=USER_VARIABLE '[' e=expression ']' {
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
		$result = new ASTColorARGB((int)(0xFFFFFFFF & builder.parseLong($argb.text, 16)));
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
	'MOVETOREL'
	| 
	'LINETO'
	| 
	'LINETOREL'
	| 
	'ARCTO'
	| 
	'ARCTOREL'
	;

USER_PATHOP_2POINTS
	: 
	'CURVETO'
	| 
	'CURVETOREL'
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
	