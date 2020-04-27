{
    test: /\.less$/,
    oneOf: [
        {
            resourceQuery: /module/,
            use: [
                {
                    loader: 'mini-css-extract-plugin',
                    options: {
                        hmr: false,
                        publicPath: '../'
                    }
                },
                {
                    loader: 'css-loader',
                    options: {
                        sourceMap: false,
                        importLoaders: 2, //启用/禁用或设置在CSS加载程序之前应用的加载程序的数量
                        modules: {
                            localIdentName: '[name]_[local]_[hash:base64:5]'
                        }
                    }
                },
                {
                    loader: 'postcss-loader',
                    options: {
                        sourceMap: false,
                    }
                },
                {
                    loader: 'less-loader',
                    options: {
                      sourceMap: false
                    }
                }
            ]
        },
        {
            resourceQuery: /\?vue/,
            use: [
                {
                    loader: 'mini-css-extract-plugin',
                    options: {
                        hmr: false,
                        publicPath: '../'
                    }
                },
                {
                    loader: 'css-loader',
                    options: {
                        sourceMap: false,
                        importLoaders: 2,
                    }
                },
                {
                    loader: 'postcss-loader',
                    options: {
                        sourceMap: false,
                    }
                },
                {
                    loader: 'less-loader',
                    options: {
                      sourceMap: false
                    }
                }
            ]
        },
        {
            resourceQuery: /\.module\.\w+$/,
            use: [
                {
                    loader: 'mini-css-extract-plugin',
                    options: {
                        hmr: false,
                        publicPath: '../'
                    }
                },
                {
                    loader: 'css-loader',
                    options: {
                        sourceMap: false,
                        importLoaders: 2,
                        modules: {
                            localIdentName: '[name]_[local]_[hash:base64:5]'
                        }
                    }
                },
                {
                    loader: 'postcss-loader',
                    options: {
                        sourceMap: false,
                    }
                },
                {
                    loader: 'less-loader',
                    options: {
                      sourceMap: false
                    }
                }
            ]
        },
        {
            use: [
                {
                    loader: 'mini-css-extract-plugin',
                    options: {
                        hmr: false,
                        publicPath: '../'
                    }
                },
                {
                    loader: 'css-loader',
                    options: {
                        sourceMap: false,
                        importLoaders: 2,
                    }
                },
                {
                    loader: 'postcss-loader',
                    options: {
                        sourceMap: false,
                    }
                },
                {
                    loader: 'less-loader',
                    options: {
                      sourceMap: false
                    }
                }
            ]
        },
    ]
},