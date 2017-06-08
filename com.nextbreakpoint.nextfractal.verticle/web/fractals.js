var React = require('react');
var ReactDOM = require('react-dom');

class App extends React.Component {
    render() {
        return <div class="container s12 m10 l8">
            <h1>Configure fractals</h1>
            <section class="card row hoverable">
                <div class="card-content col s12 m10 l8">
                    <p class="card-title">List of fractals</p>
                    <form id="formDelete">
                        <table id="list">
                            <tr><th></th><th>Preview</th><th>UUID</th></tr>
                        </table>
                        <input class="waves-effect waves-light btn" id="delete" type="submit" value="delete"/>
                    </form>
                </div>
            </section>
            <section class="card row hoverable">
                <div class="card-content col s12 m10 l8">
                    <p class="card-title">Create new fractal</p>
                    <form id="formCreate">
                        <div class="input-field">
                            <i class="material-icons prefix">mode_edit</i>
                            <label for="script">Script</label>
                            <textarea class="materialize-textarea" rows="20" cols="80" id="script" name="script"></textarea>
                        </div>
                        <div class="input-field">
                            <i class="material-icons prefix">mode_edit</i>
                            <label for="metadata">Metadata</label>
                            <textarea class="materialize-textarea" rows="20" cols="80" id="metadata" name="metadata"></textarea>
                        </div>
                        <input class="waves-effect waves-light btn" id="create" type="submit" value="create"/>
                    </form>
                </div>
            </section>
        </div>
    }
}

ReactDOM.render(<App />, document.getElementById('app-fractals'));
