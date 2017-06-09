const React = require('react')
const ReactDOM = require('react-dom')

const { Map, TileLayer } = require('react-leaflet')

const { Row, Col } = require('react-materialize')

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
        return <Row>
            <Col s={12} className="center-align">
                <Map center={position} zoom={2} className="z-depth-3">
                    <TileLayer
                      url={url}
                      attribution='&copy; Andrea Medeghini'
                      maxZoom={22}
                      minZoom={2}
                      tileSize={256}
                    />
                </Map>
            </Col>
        </Row>
    }
}

ReactDOM.render(<App />, document.getElementById('app-fractal'))
