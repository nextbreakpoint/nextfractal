const React = require('react')
const ReactDOM = require('react-dom')

const { Map, TileLayer } = require('react-leaflet')

const { Row, Col } = require('react-materialize')

const { Mandelbrot } = require('./Mandelbrot')

const position = [0, 0]

var uuid = "00000000-0000-0000-0000-000000000000"

const regexp = /https?:\/\/.*:.*\/fractals\/(.*)/g
const match = regexp.exec(window.location.href)

if (match != null && match.length == 2) {
    uuid = match[1]
}

const url = '/api/fractals/' + uuid + '/{z}/{x}/{y}/256.png'

class App extends React.Component {
    render() {
        //let script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n"
        //let mandel = new Mandelbrot()
        //console.log(mandel.parse(script));

        return <Row>
            <Col s={12} className="center-align">
                <Map center={position} zoom={2} className="z-depth-3">
                    <TileLayer url={url} attribution='&copy; Andrea Medeghini' minZoom={2} maxZoom={22} tileSize={256}/>
                </Map>
            </Col>
        </Row>
    }
}

ReactDOM.render(<App />, document.getElementById('app-fractal'))
