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
		 
orbitequation 
		:
		e=exp3 {
			driver.setExpression($e.result);
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
			$result = new ASTReal($n, Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal($CF_INFINITY, Float.MAX_VALUE); 
        }
        |
        t='(' x=exp2 ')' { 
			$result = new ASTParen($t, $x.result); 
        }
        | 
        f=expfunc { 
			$result = $f.result; 
        }
        |
        t='-' e=exp { 
			$result = new ASTOperator($t, 'N', $e.result); 
        }
        |
        t='+' e=exp { 
			$result = new ASTOperator($t, 'P', $e.result); 
        }
        ;

exp2 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal($n, Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal($CF_INFINITY, Float.MAX_VALUE); 
        }
        |
        f=exp2func { 
        	$result = $f.result; 
        }
        | 
        t='-' e=exp2 { 
			$result = new ASTOperator($t, 'N', $e.result); 
        }
        |
        t='+' e=exp2 { 
			$result = new ASTOperator($t, 'P', $e.result); 
        }
        |
        t=NOT e=exp2 { 
			$result = new ASTOperator($t, '!', $e.result); 
        }
        |
        t='(' e=exp2 ')' { 
			$result = new ASTParen($t, $e.result); 
        }
        )
        (
        ',' r=exp2 {
        	$result = new ASTCons($result.getLocation(), $result, $r.result);
        }
        |
        '+' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '+', $result, $r.result);
        }
        |
        '-' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '-', $result, $r.result);
        }
        |
        '_' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '_', $result, $r.result);
        }
        |
        '*' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '*', $result, $r.result);
        }
        |
        '/' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '/', $result, $r.result);
        }
        |
        '^' r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '^', $result, $r.result);
        }
        |
        LT r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '<', $result, $r.result);
        }
        |
        GT r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '>', $result, $r.result);
        }
        |
        LE r=exp2 {
        	$result = new ASTOperator($result.getLocation(), 'L', $result, $r.result);
        }
        |
        GE r=exp2 {
        	$result = new ASTOperator($result.getLocation(), 'G', $result, $r.result);
        }
        |
        EQ r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '=', $result, $r.result);
        }
        |
        NEQ r=exp2 {
        	$result = new ASTOperator($result.getLocation(), 'n', $result, $r.result);
        }
        |
        AND r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '&', $result, $r.result);
        }
        |
        OR r=exp2 {
        	$result = new ASTOperator($result.getLocation(), '|', $result, $r.result);
        }
        |
        XOR r=exp2 {
        	$result = new ASTOperator($result.getLocation(), 'X', $result, $r.result);
        }
        )?
        ;

exp3 returns [ASTExpression result]	
        : 
        (
        n=USER_RATIONAL { 
        	$result = new ASTReal($n, Float.parseFloat($n.getText())); 
        }
        |
        CF_INFINITY { 
			$result = new ASTReal($CF_INFINITY, Float.MAX_VALUE); 
        }
        |
        f=exp2func { 
        	$result = $f.result;
        }
        | 
        t='-' e=exp3 { 
			$result = new ASTOperator($t, 'N', $e.result); 
        }
        |
        t='+' e=exp3 { 
			$result = new ASTOperator($t, 'P', $e.result); 
        }
        |
        t=NOT e=exp3 { 
			$result = new ASTOperator($t, '!', $e.result); 
        }
        |
        t='(' x=exp2 ')' { 
			$result = new ASTParen($t, $x.result); 
        }
        )
        (
        '+' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '+', $result, $r.result);
        }
        |
        '-' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '-', $result, $r.result);
        }
        |
        '_' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '_', $result, $r.result);
        }
        |
        '*' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '*', $result, $r.result);
        }
        |
        '/' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '/', $result, $r.result);
        }
        |
        '^' r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '^', $result, $r.result);
        }
        |
        LT r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '<', $result, $r.result);
        }
        |
        GT r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '>', $result, $r.result);
        }
        |
        LE r=exp3 {
        	$result = new ASTOperator($result.getLocation(), 'L', $result, $r.result);
        }
        |
        GE r=exp3 {
        	$result = new ASTOperator($result.getLocation(), 'G', $result, $r.result);
        }
        |
        EQ r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '=', $result, $r.result);
        }
        |
        NEQ r=exp3 {
        	$result = new ASTOperator($result.getLocation(), 'n', $result, $r.result);
        }
        |
        AND r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '&', $result, $r.result);
        }
        |
        OR r=exp3 {
        	$result = new ASTOperator($result.getLocation(), '|', $result, $r.result);
        }
        |
        XOR r=exp3 {
        	$result = new ASTOperator($result.getLocation(), 'X', $result, $r.result);
        }
        )?
        ;

arglist returns [ASTExpression result]
        : 
        e2=explist e1=exp3 {
        	$result = $e2.result.append($e1.result);
        }
        | 
        e=exp3 { 
        	$result = new ASTCons($e.start, new ASTParen($e.start, $e.result));
        }
        ;

expfunc returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction($f, func, null);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction($f, func, args);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction($IF, "if", args);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable($v, var);
        }
        ;

exp2func returns [ASTExpression result]
        : 
        f=USER_STRING '(' ')' { 
        	String func = $f.getText();
        	$result = driver.makeFunction($f, func, null);
        }
        | 
        f=USER_STRING '(' a=arglist ')' { 
        	String func = $f.getText();
        	ASTExpression args = $a.result;
        	$result = driver.makeFunction($f, func, args);
        }
        |
        IF '(' e=exp2 ')' { 
        	ASTExpression args = $e.result;
        	$result = driver.makeFunction($IF, "if", args);
        }
        |
        v=USER_STRING { 
        	String var = $v.getText();
        	$result = driver.makeVariable($v, var);
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

IF
	: 
	'if' 
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
	