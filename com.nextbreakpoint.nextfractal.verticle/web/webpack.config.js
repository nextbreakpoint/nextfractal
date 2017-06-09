module.exports = {
    context: __dirname + "/",

    entry: {
        fractals: "./fractals",
        fractal: "./fractal"
    },

    output: {
        path: __dirname + "/../src/main/resources/webroot/js",
        filename: "app-[name].js"
    },

    resolve: {
        extensions: ['.js', '.jsx', '.json']
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/, exclude: /node_modules/, loaders: ["react-hot-loader", "babel-loader"]
            }
        ]
    }
};
