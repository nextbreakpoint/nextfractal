const React = require('react')

const { Input } = require('react-materialize')

let BundleItem = class BundleItem extends React.Component {
    constructor(props) {
        super(props)

        this.state = props.bundle

        this.onSelect = props.onSelect.bind(this)
    }

    render() {
        let bundle = this.state

        if (bundle.selected) {
            return <tr><td><Input label=" " defaultChecked="checked" className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.onSelect(bundle.uuid, false)}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a target="_blank" href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
        } else {
            return <tr><td><Input label=" "                          className="filled-in" name="uuid" id={"uuid-" + bundle.uuid} type="checkbox" onClick={(e) => this.onSelect(bundle.uuid, true )}/></td><td><img className="z-depth-3" width={128} height={128} src={"/api/fractals/" + bundle.uuid + "/0/0/0/256.png"}/></td><td><a target="_blank" href={"/fractals/" + bundle.uuid}>{bundle.uuid}</a></td></tr>
        }
    }
}

module.exports = BundleItem