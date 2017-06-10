module.exports = {
    context: __dirname + "/",

    entry: {
        fractals: "./fractals",
        fractal: "./fractal"
    },

    output: {
        path: __dirname + "/../src/main/resources/webroot/js",
        filename: "[name].js"
    },

    resolve: {
        extensions: ['.js', '.jsx', '.json']
    },

    node: {
        fs: 'empty'
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/, exclude: /node_modules/, loaders: ["react-hot-loader", "babel-loader"]
            }
        ]
    }
};
