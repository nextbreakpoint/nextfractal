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

    module: {
        rules: [
            {
                test: /\.jsx?$/, exclude: /node_modules/, loaders: ["react-hot-loader", "babel-loader"]
            }
        ]
    }
};
