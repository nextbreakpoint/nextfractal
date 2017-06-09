const React = require('react')
const ReactDOM = require('react-dom')

const Bundles = require('./Bundles.js')
const NewBundle = require('./NewBundle.js')

const { Row, Col } = require('react-materialize')

const axios = require('axios')

class App extends React.Component {
    constructor(props) {
        super(props)

        this.state = {bundles: []}

        this.reloadAll = this.reloadAll.bind(this)
        this.handleCreate = this.handleCreate.bind(this)
        this.handleDelete = this.handleDelete.bind(this)
        this.handleSelect = this.handleSelect.bind(this)
        this.componentDidMount = this.componentDidMount.bind(this)
    }


    handleCreate(data) {
        let component = this

        let config = {
            timeout: 10000,
            headers: {'content-type': 'application/json'}
        }

        axios.post('/api/fractals', data, config)
            .then(function (response) {
//                console.log(response);

                var bundles = component.state.bundles.slice()

                bundles.push({uuid:response.data.uuid, selected: false})

                component.setState({bundles: bundles})
            })
            .catch(function (error) {
                console.log(error)
            })
    }

    handleDelete() {
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
            .then(function (responses) {
                let deletedUuids = responses
                    .filter((res) => {
                        return res.status == 200
                    })
                    .map((res) => {
                        return res.config.url.substring(res.config.url.lastIndexOf("/") + 1)
                    })

                let bundles = component.state.bundles
                    .filter((bundle) => {
                        return !deletedUuids.includes(bundle.uuid)
                    })
                    .map((bundle) => {
                        return { uuid: bundle.uuid, selected: bundle.selected }
                    })

                component.setState({bundles: bundles})
            })
            .catch(function (error) {
                console.log(error)
            });
    }

    handleSelect(uuid, selected) {
        let bundles = this.state.bundles
            .map((bundle) => { return { uuid: bundle.uuid, selected: (bundle.uuid == uuid ? selected : bundle.selected) }})

        this.setState({bundles: bundles})
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
                let bundles = response.data.map((uuid) => { return { uuid: uuid, selected: false }})

                component.setState({bundles: bundles})
            })
            .catch(function (error) {
                console.log(error)
                component.setState({bundles: []})
            })
    }


    render() {
        return <div className="container s12">
            <h1>Configure</h1>
            <Row>
                <Col s={12}>
                    <Bundles bundles={this.state.bundles} onDelete={this.handleDelete} onSelect={this.handleSelect}/>
                </Col>
            </Row>
            <Row s={12}>
                <Col s={12}>
                    <NewBundle onCreate={this.handleCreate}/>
                </Col>
            </Row>
        </div>
    }
}

ReactDOM.render(<App />, document.getElementById('app-fractals'))
