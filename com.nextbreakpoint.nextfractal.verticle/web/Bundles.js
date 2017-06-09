const React = require('react')

const { Card, Button, Table, Input } = require('react-materialize')

const BundleItem = require('./BundleItem.js')

let Bundles = class Bundles extends React.Component {
    constructor(props) {
        super(props)

        this.renderList = this.renderList.bind(this)
        this.renderItem = this.renderItem.bind(this)

        this.onDelete = props.onDelete.bind(this)
        this.onSelect = props.onSelect.bind(this)
    }

    renderItem(bundle) {
        return <BundleItem key={bundle.uuid} bundle={bundle} onSelect={this.onSelect}/>
    }

    renderList() {
        return this.props.bundles.map(bundle => this.renderItem(bundle))
    }

    render() {
        return <Card title="List of fractals" className="hoverable">
            <Table>
                <thead>
                    <tr><th></th><th>Preview</th><th>UUID</th></tr>
                </thead>
                <tbody>
                    {this.renderList()}
                </tbody>
            </Table>
            <Button waves='light' onClick={this.onDelete}>Delete</Button>
        </Card>
    }
}

module.exports = Bundles