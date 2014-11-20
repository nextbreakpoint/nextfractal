grammar OrbitEquation;

options
{
} 

@lexer::header { 
}

@parser::header {
	import java.util.Map;
}

@members {
	Builder driver = new Builder();
}

choose
		:
		ORBITEQUATION orbitequation 
		;
		 
orbitequation [ASTExpression result]
		e=exp3 {
			$result = $e.result;
		}
		;

explist returns [ASTExpression result]
        : 
        e2=explist e1=exp {
        	$result = $e2.result.append($e1.result);
        }
        | 
        e=exp { 
        	$result = $e.result;
        }
        ;

exp returns [ASTExpression result]
        : 
        n=USER_RATIONAL { 
			$result = new ASTReal(Float.parseFloat($n.getText()), $n); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE, $CF_INFINITY); 
        }
        |
        t='(' x=exp2 ')' { 
			$result = new ASTParen($x.result, $t); 
        }
        | 
        f=expfunc { 
			$result = $f.result; 
        }
        |
        t='-' e=exp { 
			$result = new ASTOperator('N', $e.result, $t); 
        }
        |
        t='+' e=exp { 
			$result = new ASTOperator('P', $e.result, $t); 
        }
        ;

exp2 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal(Float.parseFloat($n.getText()), $n); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE, $CF_INFINITY); 
        }
        |
        f=exp2func { 
        	$result = $f.result; 
        }
        | 
        t='-' e=exp2 { 
			$result = new ASTOperator('N', $e.result, $t); 
        }
        |
        t='+' e=exp2 { 
			$result = new ASTOperator('P', $e.result, $t); 
        }
        |
        t=NOT e=exp2 { 
			$result = new ASTOperator('!', $e.result, $t); 
        }
        |
        t='(' e=exp2 ')' { 
			$result = new ASTParen($e.result, $t); 
        }
        )
        (
        ',' r=exp2 {
        	$result = new ASTCons($result.getLocation(), $result, $r.result);
        }
        |
        '+' r=exp2 {
        	$result = new ASTOperator('+', $result, $r.result, $result.getLocation());
        }
        |
        '-' r=exp2 {
        	$result = new ASTOperator('-', $result, $r.result, $result.getLocation());
        }
        |
        '_' r=exp2 {
        	$result = new ASTOperator('_', $result, $r.result, $result.getLocation());
        }
        |
        '*' r=exp2 {
        	$result = new ASTOperator('*', $result, $r.result, $result.getLocation());
        }
        |
        '/' r=exp2 {
        	$result = new ASTOperator('/', $result, $r.result, $result.getLocation());
        }
        |
        '^' r=exp2 {
        	$result = new ASTOperator('^', $result, $r.result, $result.getLocation());
        }
        |
        LT r=exp2 {
        	$result = new ASTOperator('<', $result, $r.result, $result.getLocation());
        }
        |
        GT r=exp2 {
        	$result = new ASTOperator('>', $result, $r.result, $result.getLocation());
        }
        |
        LE r=exp2 {
        	$result = new ASTOperator('L', $result, $r.result, $result.getLocation());
        }
        |
        GE r=exp2 {
        	$result = new ASTOperator('G', $result, $r.result, $result.getLocation());
        }
        |
        EQ r=exp2 {
        	$result = new ASTOperator('=', $result, $r.result, $result.getLocation());
        }
        |
        NEQ r=exp2 {
        	$result = new ASTOperator('n', $result, $r.result, $result.getLocation());
        }
        |
        AND r=exp2 {
        	$result = new ASTOperator('&', $result, $r.result, $result.getLocation());
        }
        |
        OR r=exp2 {
        	$result = new ASTOperator('|', $result, $r.result, $result.getLocation());
        }
        |
        XOR r=exp2 {
        	$result = new ASTOperator('X', $result, $r.result, $result.getLocation());
        }
        )?
        ;

exp3 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal(Float.parseFloat($n.getText()), $n); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal(Float.MAX_VALUE, $CF_INFINITY); 
        }
        |
        f=exp2func { 
        	$result = $f.result;
        }
        | 
        t='-' e=exp3 { 
			$result = new ASTOperator('N', $e.result, $t); 
        }
        |
        t='+' e=exp3 { 
			$result = new ASTOperator('P', $e.result, $t); 
        }
        |
        t=NOT e=exp3 { 
			$result = new ASTOperator('!', $e.result, $t); 
        }
        |
        t='(' x=exp2 ')' { 
			$result = new ASTParen($x.result, $t); 
        }
        )
        (
        '+' r=exp3 {
        	$result = new ASTOperator('+', $result, $r.result, $result.getLocation());
        }
        |
        '-' r=exp3 {
        	$result = new ASTOperator('-', $result, $r.result, $result.getLocation());
        }
        |
        '_' r=exp3 {
        	$result = new ASTOperator('_', $result, $r.result, $result.getLocation());
        }
        |
        '*' r=exp3 {
        	$result = new ASTOperator('*', $result, $r.result, $result.getLocation());
        }
        |
        '/' r=exp3 {
        	$result = new ASTOperator('/', $result, $r.result, $result.getLocation());
        }
        |
        '^' r=exp3 {
        	$result = new ASTOperator('^', $result, $r.result, $result.getLocation());
        }
        |
        LT r=exp3 {
        	$result = new ASTOperator('<', $result, $r.result, $result.getLocation());
        }
        |
        GT r=exp3 {
        	$result = new ASTOperator('>', $result, $r.result, $result.getLocation());
        }
        |
        LE r=exp3 {
        	$result = new ASTOperator('L', $result, $r.result, $result.getLocation());
        }
        |
        GE r=exp3 {
        	$result = new ASTOperator('G', $result, $r.result, $result.getLocation());
        }
        |
        EQ r=exp3 {
        	$result = new ASTOperator('=', $result, $r.result, $result.getLocation());
        }
        |
        NEQ r=exp3 {
        	$result = new ASTOperator('n', $result, $r.result, $result.getLocation());
        }
        |
        AND r=exp3 {
        	$result = new ASTOperator('&', $result, $r.result, $result.getLocation());
        }
        |
        OR r=exp3 {
        	$result = new ASTOperator('|', $result, $r.result, $result.getLocation());
        }
        |
        XOR r=exp3 {
        	$result = new ASTOperator('X', $result, $r.result, $result.getLocation());
        }
        )?
        ;

expfunc returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction(func, null, $f);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction(func, args, $f);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction("if", args, $IF);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable(var, $v);
        }
        ;

exp2func returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction(func, null, $f);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction(func, args, $f);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction("if", args, $IF);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable(var, $v);
        }
        ;
        
ORBITEQUATION
	: 
	'ORBITEQUATION' 
	;
	
USER_RATIONAL
	: 
	('0'..'9')+ '.' ('0'..'9')* '%'? | '.' ('0'..'9')+ '%'? | '0'..'9'+ '%' '.' '.'? ('0'..'9')+ '.' ('0'..'9')* ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | '.' ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'? | ('0'..'9')+ ('e'|'E') ('+|-')? ('0'..'9')+ '%'?
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

CF_INFINITY
	: 
	'CF_INFINITY' 
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
	