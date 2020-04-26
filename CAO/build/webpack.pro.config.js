const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: './src/index.js',
    // devtool: 'source-map',
    devtool: 'eval',
    output: {
        filename: 'js/[name].[contenthash:8].js',
        path: path.resolve(__dirname, '../dist'),
        publicPath: '/',
        chunkFilename: 'js/[name].[contenthash:8].js'
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, '../src'),
            'asset': path.resolve(__dirname, '../public')
        },
        extensions: ['.mjs', '.js', '.jsx', '.vue', '.json'],
        modules: [
            'node_modules',
            path.resolve(__dirname, '../node_modules')
        ]
    },
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
                vendors: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'chunk-vendors',
                    chunks: 'all',
                    minChunks: 2,
                    minSize: 30000,
                    priority: -10
                },
                common: {
                    name: 'chunk-common',
                    minChunks: 2,
                    priority: -20,
                    chunks: 'all',
                    reuseExistingChunk: true
                }
            }
        },
        minimizer
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
                        limit: 8192,
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
};