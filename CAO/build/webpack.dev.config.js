const path = require('path');
const webpack = require('webpack');
const TerserPlugin = require('terser-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const CaseSensitivePathsPlugin = require('case-sensitive-paths-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const PreloadPlugin = require('preload-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
    mode: 'development', //生产模式
    entry: './src/index.js', //入口文件
    output: {
        filename: 'js/[name].js', //输出的文件名
        path: path.resolve(__dirname, '../dist'), //输出的文件目录
        publicPath: '/', //服务跟地址
        chunkFilename: 'js/[name].js' //分包的命名
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
        new VueLoaderPlugin(), //解析vue文件
        new webpack.DefinePlugin( //定义开发环境得全局变量
            {
                'process.env': {
                    NODE_ENV: '"development"',
                }
            }
        ),
        new CaseSensitivePathsPlugin(), //区分路径大小写(避免osx开发人员路径书写冲突)
        new FriendlyErrorsWebpackPlugin(), //识别webpack报错
        new HtmlWebpackPlugin({ //打包index.html
            title: 'PPCH', //页面title
            favicon: path.resolve(__dirname, '../public/favicon.ico'),
            filename: 'index.html',
            template: './public/index.html'
        }),
        new PreloadPlugin( //不需要预加载的资源
            {
              rel: 'preload',
              include: 'initial',
              fileBlacklist: [ 
                /\.map$/,
                /hot-update\.js$/
              ]
            }
        ),
        new PreloadPlugin( //需要预加载的资源
            {
              rel: 'prefetch',
              include: 'asyncChunks'
            }
        ),
        new CopyPlugin( //复制文件
            [
              {
                from: path.resolve(__dirname, '../public'),
                to: path.resolve(__dirname, '../dist'),
                toType: 'dir',
                ignore: [
                    'images/**/*',
                    'video/**/*',
                    '.DS_Store',
                    {
                        glob: 'index.html',
                        matchBase: false
                    }
                ]
              }
            ]
        )
    ],
    /* 分包处理 */
    optimization: {
        moduleIds: 'hashed',
        runtimeChunk: 'single',
        namedChunks: true, //使用 chunkName 来替换 chunkId，实现固化 chunkId，保持缓存的能力
        splitChunks: {
            cacheGroups: {
                vendors: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'chunk-vendors',
                    chunks: 'all',
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
        /* 对js压缩 */
        minimizer: [
            new TerserPlugin(
                {
                    terserOptions: {
                        /* terserOptions具体参数请参看 https://github.com/terser/terser#minify-options */
                        compress: {
                            /* compress具体参数请参看 https://github.com/terser/terser#compress-options */
                            arrows: false,
                            collapse_vars: false,
                            comparisons: false,
                            computed_props: false,
                            hoist_funs: false,
                            hoist_props: false,
                            hoist_vars: false,
                            inline: false,
                            loops: false,
                            negate_iife: false,
                            properties: false,
                            reduce_funcs: false,
                            reduce_vars: false,
                            switches: false,
                            toplevel: false,
                            typeofs: false,
                            booleans: true,
                            if_return: true,
                            sequences: true,
                            unused: true,
                            conditionals: true,
                            dead_code: true,
                            evaluate: true
                        },
                        mangle: {
                            safari10: true
                        }
                    },
                    sourceMap: true,
                    cache: true, // 开启缓存
                    parallel: true, // 开启多线程
                    extractComments: false // 提取注释
                }
            )
        ]
    },
    /* 插件 */
    module: {
        noParse: /^(vue|vue-router|vuex|vuex-router-sync)$/, //不解析的文件
        rules: [
            {
                test: /\.vue$/,
                use: [
                    {
                        loader: 'cache-loader', //减少二次打包时间(缓存插件)
                    },
                    {
                        loader: 'vue-loader',//vue
                        options: {
                            compilerOptions: { //处理空格
                                whitespace: 'condense'
                            },
                        }
                    }
                ]
            },
            {
                test: /\.(png|jpe?g|gif|webp)(\?.*)?$/,
                use: [
                        {
                        loader: 'url-loader', //图片处理
                        options: {
                            esModule: false,
                            limit: 4096, //图片大小超过4096k用(file-loader)处理
                            fallback: {
                                loader: 'file-loader',
                                options: {
                                    name: 'asset/img/[name].[hash:8].[ext]'
                                }
                            }
                        }
                    }
                ]
            },
            {
                test: /\.(svg)(\?.*)?$/,
                use: [{
                    loader: 'file-loader',
                    options: {
                        esModule: false,
                        name: 'asset/img/[name].[hash:8].[ext]'
                    }
                }]
            },
            {
                test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {
                            esModule: false,
                            limit: 4096,
                            fallback: {
                                loader: 'file-loader',
                                options: {
                                name: 'asset/media/[name].[hash:8].[ext]'
                                }
                            }
                        }
                  }
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: 'asset/font/[name].[hash:8].[ext]'
                        }
                    }
                ]
            },
            {
                test: /\.css$/,
                oneOf: [
                    {
                        resourceQuery: /module/,
                        use: [
                            {
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                loader: 'postcss-loader', //把CSS解析成JavaScript可以操作的抽象语法树结构(Abstract Syntax Tree, AST)，第二个就是调用插件来处理AST并得到结果
                                options: {
                                    sourceMap: false,
                                    plugins: [
                                        require("autoprefixer")
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        resourceQuery: /\?vue/,
                        use: [
                            {
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        resourceQuery: /\.module\.\w+$/,
                        use: [
                            {
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        use: [
                            {
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
                                }
                            }
                        ]
                    },
                ]
            },
            /* 解析.less文件 */
            {
                test: /\.less$/,
                oneOf: [
                    {
                        resourceQuery: /module/,
                        use: [
                            {
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
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
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
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
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
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
                                loader: 'vue-style-loader', //解析vue中css
                                options: {
                                    sourceMap: false,
                                    shadowMode: false
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
                                    plugins: [
                                        require("autoprefixer")
                                    ]
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
            {
                test: /\.m?jsx?$/,
                include: path.resolve(__dirname, '../src'),
                use: [
                    'cache-loader',
                    'thread-loader', //将loader操作放入单独线程运行
                    'babel-loader'
                ]
            },
            {
                enforce: 'pre', //保证在babel转义之前进行代码检查
                test: /\.(vue|(j|t)sx?)$/,
                include: path.resolve(__dirname, '../src'),
                use: [
                  {
                    loader: 'eslint-loader',
                    options: {
                      extensions: [
                        '.js',
                        '.jsx',
                        '.vue'
                      ],
                      cache: true, //此选项启用将整理结果缓存到文件中。
                      emitWarning: true, //关闭警告提示
                      emitError: true, //关闭错误提示
                      eslintPath: path.resolve(__dirname, '../node_modules/eslint'),
                      formatter: undefined
                    }
                  }
                ]
            }
        ]
    },
};