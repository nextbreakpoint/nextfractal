grammar Mandelbrot;

options
{
} 

@lexer::header { 
}

@parser::header {
} 

@members {
	private ASTBuilder driver = new ASTBuilder();
	
	public ASTBuilder getBuilder() { return driver; }
} 

fractal
	:
	f=FRACTAL {
		driver.setFractal(new ASTFractal($f));
	} '{' orbit color '}' eof 
	;
		
orbit
	:
	o=ORBIT '[' ra=complex ',' rb=complex ']' {
		driver.setOrbit(new ASTOrbit($o, new ASTRegion($ra.result, $rb.result)));
	} '[' statevariablelist ']' '{' trap* begin? loop end? '}'
	;
		
color
	:
	c=COLOR '[' argb=colorargb ']' { 
		driver.setColor(new ASTColor($c, $argb.result));
	} '{' palette* colorinit? colorrule* '}'
	;
		
begin
	:
	b=BEGIN { 
		driver.setOrbitBegin(new ASTOrbitBegin($b));
	} '{' beginstatement '}'
	;
		
loop
	:
	l=LOOP '[' lb=INTEGER ',' le=INTEGER ']' '(' e=conditionexp ')'{
		driver.setOrbitLoop(new ASTOrbitLoop($l, driver.parseInt($lb.text), driver.parseInt($le.text), $e.result));
	} '{' loopstatement '}'
	;
		
end
	:
	e=END {
		driver.setOrbitEnd(new ASTOrbitEnd($e));
	} '{' endstatement '}'
	;
		
trap
	:
	t=TRAP n=VARIABLE '[' c=complex ']' {
		driver.addOrbitTrap(new ASTOrbitTrap($t, $n.text, $c.result));
	} '{' pathop* '}'
	;
		
pathop
	:
	o=PATHOP_0POINTS ';' {
		driver.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text));
	}
	|
	o=PATHOP_1POINTS '(' c=complex ')' ';' {
		driver.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text, $c.result));
	}
	|
	o=PATHOP_2POINTS '(' c1=complex ',' c2=complex ')' ';' {
		driver.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text, $c1.result, $c2.result));
	}
	|
	o=PATHOP_3POINTS '(' c1=complex ',' c2=complex ',' c3=complex ')' ';' {
		driver.addOrbitTrapOp(new ASTOrbitTrapOp($o, $o.text, $c1.result, $c2.result, $c3.result));
	}
	;
	
beginstatement 
	: 
	{
		driver.pushStatementList();
	}
	statement* {
		driver.addBeginStatements(driver.getStatementList());
		driver.popStatementList();
	}
	;
		
loopstatement
	:
	{
		driver.pushScope();
		driver.pushStatementList();
	}
	statement* {
		driver.addLoopStatements(driver.getStatementList());
		driver.popScope();
		driver.popStatementList();
	}
	;
		
endstatement 
	:
	{
		driver.pushScope();
		driver.pushStatementList();
	}
	statement* {
		driver.addEndStatements(driver.getStatementList());
		driver.popScope();
		driver.popStatementList();
	}
	;
		
statement
	:
	v=VARIABLE '=' e=expression ';'? {
		driver.registerVariable($v.text, $e.result.isReal(), $v);
		driver.appendStatement(new ASTAssignStatement($v, $v.text, $e.result));
	}
	|
	f=IF '(' c=conditionexp ')' '{' {
		driver.pushScope();
		driver.pushStatementList();
	} statement* '}' {
		ASTStatementList thenList = driver.getStatementList();
		driver.popScope();
		driver.popStatementList();
	} ELSE '{' {
		driver.pushScope();
		driver.pushStatementList();
	} statement* '}' {
		ASTStatementList elseList = driver.getStatementList();
		driver.popScope();
		driver.popStatementList();
		driver.appendStatement(new ASTConditionalStatement($f, $c.result, thenList, elseList));
	} 
	|
	f=IF '(' c=conditionexp ')' '{' {
		driver.pushScope();
		driver.pushStatementList();
	} statement* '}' {
		ASTStatementList thenList = driver.getStatementList();
		driver.popScope();
		driver.popStatementList();
		driver.appendStatement(new ASTConditionalStatement($f, $c.result, thenList));
	} 
	|
	f=IF '(' c=conditionexp ')' {
		driver.pushScope();
		driver.pushStatementList();
	} statement {
		ASTStatementList thenList = driver.getStatementList();
		driver.popScope();
		driver.popStatementList();
		driver.appendStatement(new ASTConditionalStatement($f, $c.result, thenList));
	} 
	|
	t=STOP ';'? {
		driver.appendStatement(new ASTStopStatement($t));
	}
	;
		
statevariable
	:
	'real' v=VARIABLE {
		driver.registerStateVariable($v.text, true, $v);
	} 
	| 
	'complex' v=VARIABLE {
		driver.registerStateVariable($v.text, false, $v);
	} 
	| 
	v=VARIABLE {
		driver.registerStateVariable($v.text, "n".equals($v.text), $v);
	} 
	;
	
statevariablelist 
	:
	statevariable 
	| 
	statevariablelist ',' statevariable
	;
		 
simpleconditionexp returns [ASTConditionExpression result]
	:
	e1=expression o=('=' | '<' | '>' | '<=' | '>=' | '<>') e2=expression {
		$result = new ASTConditionCompareOp($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	v=VARIABLE '?' e=expression {
		$result = new ASTConditionTrap($v, $v.text, $e.result, true);
	}
	|
	v=VARIABLE '~?' e=expression {
		$result = new ASTConditionTrap($v, $v.text, $e.result, false);
	}
	| 
	t=JULIA {
		$result = new ASTConditionJulia($t);
	}	
	| 
	s='(' c=conditionexp ')' {
		$result = new ASTConditionParen($s, $c.result);
	}	
	;
		
conditionexp returns [ASTConditionExpression result]
	:
	c=simpleconditionexp {
		$result = $c.result;
	}	
	| 
	c2=conditionexp2 {
		$result = $c2.result;
	}	
	| 
	c1=conditionexp l='|' c2=conditionexp2 {
		$result = new ASTConditionLogicOp($c1.result.getLocation(), $l.text, $c1.result, $c2.result);
	}	
	;
	
conditionexp2 returns [ASTConditionExpression result]
	:
	c=simpleconditionexp {
		$result = $c.result;
	}	
	| 
	c2=conditionexp3 {
		$result = $c2.result;
	}	
	| 
	c1=conditionexp2 l='^' c2=conditionexp3 {
		$result = new ASTConditionLogicOp($c1.result.getLocation(), $l.text, $c1.result, $c2.result);
	}	
	;

conditionexp3 returns [ASTConditionExpression result]
	:
	c=simpleconditionexp {
		$result = $c.result;
	}	
	| 
	c2=conditionexp4 {
		$result = $c2.result;
	}	
	| 
	c1=conditionexp3 l='&' c2=conditionexp4 {
		$result = new ASTConditionLogicOp($c1.result.getLocation(), $l.text, $c1.result, $c2.result);
	}	
	;

conditionexp4 returns [ASTConditionExpression result]
	:
	c1=simpleconditionexp {
		$result = $c1.result;
	}	
	| 
	n='~' c2=conditionexp4 {
		$result = new ASTConditionNeg($n, $c2.result);
	}	
	;
	
simpleexpression returns [ASTExpression result]
	:
	p=constant {
		$result = $p.result;
	}
	|
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
	a='<' er=expression ',' ei=expression '>' {
		$result = new ASTOperator($a, "<>", $er.result, $ei.result);	
	}
	;
	
expression returns [ASTExpression result]
	:
	e=simpleexpression {
		$result = $e.result;	
	}
	|
	c=complex {
		$result = $c.result;
	}
	|
	e2=expression2 {
		$result = $e2.result;	
	}
	|
	e1=expression s='+' e2=expression2 {
		$result = new ASTOperator($s, "+", $e1.result, $e2.result);		
	}
	|
	e2=expression2 s='+' e1=expression {
		$result = new ASTOperator($s, "+", $e2.result, $e1.result);		
	}
	|
	e1=expression s='-' e2=expression2 {
		$result = new ASTOperator($s, "-", $e1.result, $e2.result);		
	}
//	|
//	d=conditionexp5 '?' el=expression ':' er=expression {
//		$result = new ASTConditionalExpression($d.result.getLocation(), $d.result, $el.result, $er.result);
//	}
	;
	
expression2 returns [ASTExpression result]
	:
	e=simpleexpression {
		$result = $e.result;	
	}
	|
	e3=expression3 {
		$result = $e3.result;
	}
	|
	e1=expression2 s='*' e2=expression2 {
		$result = new ASTOperator($s, "*", $e1.result, $e2.result);
	}
	|
	s='-' e2=expression2 {
		$result = new ASTOperator($s, "-", $e2.result);
	}
	|
	s='+' e2=expression2 {
		$result = new ASTOperator($s, "+", $e2.result);
	}
	|
	i='i' '*'? e2=expression2 {
		$result = new ASTOperator($i, "*", new ASTNumber($i, 0.0, 1.0), $e2.result);
	}
	|
	e2=expression2 '*'? i='i' {
		$result = new ASTOperator($i, "*", new ASTNumber($i, 0.0, 1.0), $e2.result);
	}
	;

expression3 returns [ASTExpression result]
	:
	e=simpleexpression {
		$result = $e.result;	
	}
	|
	e3=expression4 {
		$result = $e3.result;
	}
	|
	e1=expression3 s='/' e2=expression3 {
		$result = new ASTOperator($s, "/", $e1.result, $e2.result);
	}
	;

expression4 returns [ASTExpression result]
	:
	e=simpleexpression {
		$result = $e.result;	
	}
	|
	e1=expression4 s='^' e2=expression4 {
		$result = new ASTOperator($s, "^", $e1.result, $e2.result);
	}
	;

function returns [ASTFunction result]
	:
	f=('mod' | 'mod2' | 'pha' | 're' | 'im') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, $e.result);		
	}
	|
	f=('cos' | 'sin' | 'tan' | 'acos' | 'asin' | 'atan') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e.result });		
	}
	|
	f=('log' | 'exp' | 'sqrt' | 'abs' | 'ceil' | 'floor') '(' e=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e.result });		
	}
	|
	f=('pow' | 'atan2' | 'hypot' | 'max' | 'min') '(' e1=expression ',' e2=expression ')' {
		$result = new ASTFunction($f, $f.text, new ASTExpression[] { $e1.result, $e2.result });		
	}
	;
			
constant returns [ASTNumber result]
	:
	p='e' {
		$result = new ASTNumber($p, Math.E);
	}
	|
	p='pi' {
		$result = new ASTNumber($p, Math.PI);
	}
	|
	p='2pi' {
		$result = new ASTNumber($p, 2 * Math.PI);
	}
	;
	
variable returns [ASTVariable result]
	:
	v=VARIABLE {
		$result = new ASTVariable($v, driver.getVariable($v.text, $v));
	}
	;
	
real returns [ASTNumber result] 
	:
	'+'? r=(RATIONAL | INTEGER) {
		$result = new ASTNumber($r, driver.parseDouble($r.text));
	}
	|
	'-' r=(RATIONAL | INTEGER) {
		$result = new ASTNumber($r, driver.parseDouble("-" + $r.text));
	}
	; 
	
complex returns [ASTNumber result]
	:
	'<' '+'? r=(RATIONAL | INTEGER) ',' '+'? i=(RATIONAL | INTEGER) '>' {
		$result = new ASTNumber($r, driver.parseDouble($r.text), driver.parseDouble("+" + $i.text));
	}
	|
	'<' '+'? r=(RATIONAL | INTEGER) ',' '-' i=(RATIONAL | INTEGER) '>' {
		$result = new ASTNumber($r, driver.parseDouble($r.text), driver.parseDouble("-" + $i.text));
	}
	|
	'<' '-' r=(RATIONAL | INTEGER) ',' '+'? i=(RATIONAL | INTEGER) '>' {
		$result = new ASTNumber($r, driver.parseDouble("-" + $r.text), driver.parseDouble("+" + $i.text));
	}
	|
	'<' '-' r=(RATIONAL | INTEGER) ',' '-' i=(RATIONAL | INTEGER) '>' {
		$result = new ASTNumber($r, driver.parseDouble("-" + $r.text), driver.parseDouble("-" + $i.text));
	}
	|
	'+'? r=(RATIONAL | INTEGER) '+' i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($r, driver.parseDouble($r.text), driver.parseDouble("+" + $i.text));
	}
	|
	'+'? r=(RATIONAL | INTEGER) '-' i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($r, driver.parseDouble($r.text), driver.parseDouble("-" + $i.text));
	}
	|
	'+'? i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($i, 0.0, driver.parseDouble($i.text));
	}
	|
	'-' r=(RATIONAL | INTEGER) '+' i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($r, driver.parseDouble("-" + $r.text), driver.parseDouble("+" + $i.text));
	}
	|
	'-' r=(RATIONAL | INTEGER) '-' i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($r, driver.parseDouble("-" + $r.text), driver.parseDouble("-" + $i.text));
	}
	|
	'-' i=(RATIONAL | INTEGER) 'i' {
		$result = new ASTNumber($i, 0.0, driver.parseDouble("-" + $i.text));
	}
	|
	rn=real {
		$result = $rn.result;
	}
	; 

palette 
	:
	p=PALETTE v=VARIABLE {
		driver.addPalette(new ASTPalette($p, $v.text));
	} '{' paletteelement+ '}'
	;
		
paletteelement 
	:
	t='[' bc=colorargb '>' ec=colorargb ',' s=INTEGER ',' e=expression ']' ';' {
		driver.addPaletteElement(new ASTPaletteElement($t, $bc.result, $ec.result, driver.parseInt($s.text), $e.result));
	}
	|
	t='[' bc=colorargb '>' ec=colorargb ',' s=INTEGER ']' ';' {
		driver.addPaletteElement(new ASTPaletteElement($t, $bc.result, $ec.result, driver.parseInt($s.text), null));
	}  
	;
				
colorinit 
	:
	i=INIT {
		driver.setColorContext(true);
		driver.setColorInit(new ASTColorInit($i));
	} '{' colorstatement '}' 
	;
		
colorstatement
	:
	{
		driver.pushStatementList();
	}
	statement* {
		driver.addColorStatements(driver.getStatementList());
		driver.popStatementList();
	}
	;
		
colorrule 
	:
	t=RULE '(' r=ruleexp ')' '[' o=(RATIONAL | INTEGER) ']' '{' c=colorexp '}' {
		driver.addRule(new ASTRule($t, driver.parseFloat($o.text), $r.result, $c.result));
	} 
	;
		
ruleexp returns [ASTRuleExpression result]
	:
	e1=expression o=('=' | '>' | '<' | '>=' | '<=' | '<>') e2=expression {
		$result = new ASTRuleCompareOp($e1.result.getLocation(), $o.text, $e1.result, $e2.result);
	}
	|
	r1=ruleexp o=('&' | '|' | '^') r2=ruleexp {
		$result = new ASTRuleLogicOp($r1.result.getLocation(), $o.text, $r1.result, $r2.result);
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
	v=VARIABLE '[' e=expression ']' {
		$result = new ASTColorPalette($v, $v.text, $e.result);
	}
	;
		
colorargb returns [ASTColorARGB result]
	:
	'(' a=(RATIONAL | INTEGER) ',' r=(RATIONAL | INTEGER) ',' g=(RATIONAL | INTEGER) ',' b=(RATIONAL | INTEGER) ')' {
		$result = new ASTColorARGB(driver.parseFloat($a.text), driver.parseFloat($r.text), driver.parseFloat($g.text), driver.parseFloat($b.text));
	}
	|
	argb32=ARGB32 {
		$result = new ASTColorARGB((int)(0xFFFFFFFF & driver.parseLong($argb32.text.substring(1), 16)));
	}
	|
	argb24=ARGB24 {
		$result = new ASTColorARGB((int)(0xFF000000 | (0xFFFFFFFF & driver.parseLong($argb24.text.substring(1), 16))));
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
	  
INIT 
	:
	'init'
	;
	
IF 
	:
	'if'
	;
	
ELSE
	:
	'else'
	;
	
STOP 
	:
	'stop'
	;
	
JULIA 
	:
	'julia'
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

ARGB32
 	:
 	'#' ('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')
 	;
 	
ARGB24
 	:
 	'#' ('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')('0'..'9' | 'a'..'f' | 'A'..'F')
 	;
 	
RATIONAL
	: 
	('0'..'9')+ '.' ('0'..'9')* '%'? | '.' ('0'..'9')+ '%'? | '0'..'9'+ '%' '.' '.'? ('0'..'9')+ '.' ('0'..'9')* ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | '.' ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'?
	; 

INTEGER
	: 
	('0'..'9')+
	; 

PATHOP_0POINTS
	: 
	'CLOSE'
	;

PATHOP_1POINTS
	: 
	'MOVETO'
	| 
	'MOVETOREL'
	| 
	'LINETO'
	| 
	'LINETOREL'
	;

PATHOP_2POINTS
	: 
	'ARCTO'
	| 
	'ARCTOREL'
	|
	'QUADTO'
	| 
	'QUADTOREL'
	;

PATHOP_3POINTS
	: 
	'CURVETO'
	| 
	'CURVETOREL'
	;

VARIABLE 
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
	