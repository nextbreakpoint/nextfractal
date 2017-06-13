const antlr4 = require('antlr4/index');

const { MandelbrotLexer } = require('./MandelbrotLexer');
const { MandelbrotParser } = require('./MandelbrotParser');
const { MandelbrotListener } = require('./MandelbrotListener');

function Mandelbrot() {
    return this;
}

Mandelbrot.prototype = Object.create(null);
Mandelbrot.prototype.constructor = Mandelbrot;

Mandelbrot.prototype.parse = function(script) {
    var inputStream = new antlr4.InputStream(script)
    var lexer = new MandelbrotLexer(inputStream)
    var tokenStream = new antlr4.CommonTokenStream(lexer)
    var parser = new MandelbrotParser(tokenStream)
    var fractal = parser.fractal()
    return fractal
};

exports.Mandelbrot = Mandelbrot;

