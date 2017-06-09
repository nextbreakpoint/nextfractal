const React = require('react')

const { Card, Button, Icon } = require('react-materialize')

let NewBundle = class NewBundle extends React.Component {
    constructor(props) {
        super(props)

        let script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n"
        let metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}"
        let manifest = "{\"pluginId\":\"Mandelbrot\"}"

        this.state = {script: script, metadata: metadata, manifest: manifest}

        this.handleCreateBundle = this.handleCreateBundle.bind(this)
        this.handleScriptChanged = this.handleScriptChanged.bind(this)
        this.handleMetadataChanged = this.handleMetadataChanged.bind(this)

        this.onCreate = props.onCreate.bind(this)
    }

    handleCreateBundle(e) {
        e.preventDefault()
        this.props.onCreate(this.state)
    }

    handleScriptChanged(e) {
        e.preventDefault()
        let source = e.target
        this.setState({script: source.value, metadata: this.state.metadata, manifest: this.state.manifest})
    }

    handleMetadataChanged(e) {
        e.preventDefault()
        let source = e.target
        this.setState({script: this.state.script, metadata: source.value, manifest: this.state.manifest})
    }

    render() {
        return <Card title="Create new fractal" className="hoverable">
            <form>
                <div className="input-field">
                    <label htmlFor="script"><Icon left>mode_edit</Icon>Script</label>
                    <textarea className="materialize-textarea" rows="20" cols="80" id="script" name="script" value={this.state.script} onChange={(e) => this.handleScriptChanged(e)}></textarea>
                </div>
                <div className="input-field">
                    <label htmlFor="metadata"><Icon left>mode_edit</Icon>Metadata</label>
                    <textarea className="materialize-textarea" rows="20" cols="80" id="metadata" name="metadata" value={this.state.metadata} onChange={(e) => this.handleMetadataChanged(e)}></textarea>
                </div>
                <Button waves='light' onClick={(e) => this.handleCreateBundle(e)}>Create</Button>
            </form>
        </Card>
    }
}

module.exports = NewBundle