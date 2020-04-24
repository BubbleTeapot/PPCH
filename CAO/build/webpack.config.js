const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = env => {
    return {
        mode: 'development',
        entry: {
            app: './src/index.js'
        },
        output: {
            filename: 'js/[name].[hash:8].js',
            path: path.resolve(__dirname, '../dist'),
            publicPath: '/',
        },
        devtool: 'inline-source-map',
        plugins: [
            new CleanWebpackPlugin(),
            new HtmlWebpackPlugin({
                filename: 'index.html',
                template: './public/index.html'
            }),
            new VueLoaderPlugin()
        ],
        optimization: {
            moduleIds: 'hashed',
            runtimeChunk: 'single',
            splitChunks: {
                cacheGroups: {
                    vendor: {
                        test: /[\\/]node_modules[\\/]/,
                        name: 'vendors',
                        chunks: 'all'
                    }
                }
            }
        },
        module: {
            rules: [
                {
                    test: /\.css$/,
                    use: [
                        'style-loader',
                        'css-loader'
                    ]
                },
                {
                    test: /\.js$/,
                    include: path.resolve(__dirname, '../src'),
                    use: [
                        'babel-loader'
                    ]
                },
                {
                    test: /\.(png|svg|jpg|gif)$/,
                    use: [{
                        loader: 'url-loader',
                        options: {
                            esModule: false,
                            limit: 4096,
                            outputPath: 'images',
                            publicPath: 'images/',
                            name: '[name].[hash:8].[ext]'
                        }
                    }]
                },
                {
                    test: /\.(woff|woff2|eot|ttf|otf)$/,
                    use: [
                        'url-loader'
                    ]
                },
                {
                    test: /\.xml$/,
                    use: [
                        'xml-loader'
                    ]
                },
                {
                    test: /\.vue$/,
                    use: [
                        'vue-loader'
                    ]
                }
            ]
        },
    }
};