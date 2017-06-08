const React = require('react')
const ReactDOM = require('react-dom')
const { Button, Card, Row, Col, Input, Table, Icon } = require('react-materialize')
const axios = require('axios')

class App extends React.Component {
    constructor(props) {
        super(props)

        let script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n"
        let metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}"
        let manifest = "{\"pluginId\":\"Mandelbrot\"}"

        this.state = {bundles: [], script: script, metadata: metadata, manifest: manifest}

        this.reloadAll = this.reloadAll.bind(this)
        this.renderList = this.renderList.bind(this)
        this.renderItem = this.renderItem.bind(this)
        this.handleDelete = this.handleDelete.bind(this)
        this.handleCreate = this.handleCreate.bind(this)
        this.handleSelect = this.handleSelect.bind(this)
        this.handleScriptChanged = this.handleScriptChanged.bind(this)
        this.handleMetadataChanged = this.handleMetadataChanged.bind(this)
        this.componentDidMount = this.componentDidMount.bind(this)
    }

    handleDelete(e) {
        e.preventDefault()
        let component = this

        let config = {
            timeout: 10000
        }

        let promises = this.state.bundles
            .filter((bundle) => {
                return bundle.selected
            }).map((bundle) => {
                return axios.delete('/api/fractals/' + bundle.uuid, config)
            })

        axios.all(promises)
            .then(function (response) {
                component.reloadAll()
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    handleCreate(e) {
        e.preventDefault()
        let component = this

        let config = {
            timeout: 10000,
            headers: {'content-type': 'application/json'}
        }

        let data = {
            script: this.state.script,
            metadata: this.state.metadata,
            manifest: this.state.manifest
        }

        console.log(data);

        axios.post('/api/fractals', data, config)
            .then(function (response) {
                component.reloadAll()
            })
            .catch(function (error) {
                console.log(error)
            })
    }

    handleSelect(e, uuid, selected) {
        e.preventDefault()
        let bundles = this.state.bundles.map((bundle) => { return { uuid: bundle.uuid, selected: (bundle.uuid == uuid ? selected : bundle.selected) }})
        this.setState({bundles: bundles, script: this.state.script, metadata: this.state.metadata, manifest: this.state.manifest})
    }

    handleScriptChanged(e) {
        e.preventDefault()
        let source = e.target
        this.setState({bundles: this.state.bundles, script: source.value, metadata: this.state.metadata, manifest: this.state.manifest})
    }

    handleMetadataChanged(e) {
        e.preventDefault()
        let source = e.target
        this.setState({bundles: this.state.bundles, script: this.state.script, metadata: source.value, manifest: this.state.manifest})
    }

    componentDidMount() {
        this.reloadAll()
    }

    reloadAll() {
        let component = this

        let config = {
            timeout: 10000
        }

        axios.get('/api/fractals', config)
            .then(function (response) {
                console.log(response);
                let bundles = response.data.map((uuid) => { return { uuid: uuid, selected: false }})
                component.setState({bundles: bundles, script: component.state.script, metadata: component.state.metadata, manifest: component.state.manifest})
            })
            .catch(function (error) {
                console.log(error)
                component.setState({bundles: [], script: component.state.script, metadata: component.state.metadata, manifest: component.state.manifest})
            })
    }

    renderItem(bundle) {
        if (bundle.selected) {
            return <tr key={bundle.uuid + "-selected"}><td><Input label=" " defaultChecked="checked" className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.handleSelect(e, bundle.uuid, false)}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
        } else {
            return <tr key={bundle.uuid              }><td><Input label=" "                          className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.handleSelect(e, bundle.uuid, true )}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
        }
    }

    renderList() {
//        console.log(this.state.bundles)

        return this.state.bundles.map(bundle => this.renderItem(bundle))
    }

    render() {
        return <div className="container s12">
            <h1>Configure</h1>
            <Row>
                <Col s={12}>
                    <Card title="List of fractals" className="hoverable">
                        <Table>
                            <thead>
                                <tr><th></th><th>Preview</th><th>UUID</th></tr>
                            </thead>
                            <tbody>
                                {this.renderList()}
                            </tbody>
                        </Table>
                        <Button waves='light' onClick={(e) => this.handleDelete(e)}>Delete</Button>
                    </Card>
                </Col>
            </Row>
            <Row>
                <Col s={12}>
                    <Card title="Create new fractal" className="hoverable">
                        <form>
                            <div className="input-field">
                                <label htmlFor="script"><Icon left>mode_edit</Icon>Script</label>
                                <textarea className="materialize-textarea" rows="20" cols="80" id="script" name="script" value={this.state.script} onChange={(e) => this.handleScriptChanged(e)}></textarea>
                            </div>
                            <div className="input-field">
                                <label htmlFor="metadata"><Icon left>mode_edit</Icon>Metadata</label>
                                <textarea className="materialize-textarea" rows="20" cols="80" id="metadata" name="metadata" value={this.state.metadata} onChange={(e) => this.handleMetadataChanged(e)}></textarea>
                            </div>
                            <Button waves='light' onClick={(e) => this.handleCreate(e)}>Create</Button>
                        </form>
                    </Card>
                </Col>
            </Row>
        </div>
    }
}

ReactDOM.render(<App />, document.getElementById('app-fractals'))
