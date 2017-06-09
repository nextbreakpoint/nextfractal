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
//        if (bundle.selected) {
//            return <tr key={bundle.uuid + "-selected"}><td><Input label=" " defaultChecked="checked" className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.onSelect(bundle.uuid, false)}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
//        } else {
//            return <tr key={bundle.uuid              }><td><Input label=" "                          className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.onSelect(bundle.uuid, true )}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
//        }

        return <BundleItem key={bundle.uuid} bundle={bundle} onSelect={this.onSelect}/>
    }

    renderList() {
        return this.props.bundles.map(bundle => this.renderItem(bundle))
    }

    render() {
//        console.log(this.props.bundles)

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

//export default Bundles
module.exports = Bundles