const path = require('path');
const webpack = require('webpack');
const TerserPlugin = require('terser-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const CaseSensitivePathsPlugin = require('case-sensitive-paths-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const PreloadPlugin = require('preload-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
// const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
    mode: 'production', //生产模式
    entry: {
        app: './src/index.js'
    }, //入口文件
    devtool: 'source-map', //是否生成.map文件(和源代码形成映射便于调试)
    // devtool: 'eval',
    output: {
        filename: 'js/[name].[contenthash:8].js', //输出的文件名
        path: path.resolve(__dirname, '../dist'), //输出的文件目录
        publicPath: '/', //服务跟地址
        chunkFilename: 'js/[name].[contenthash:8].js' //分包出口
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
        // new CleanWebpackPlugin(), //清除dist/打包目录下的文件
        new VueLoaderPlugin(), //解析vue文件
        new webpack.DefinePlugin( //定义开发环境得全局变量
            {
                'process.env': {
                    NODE_ENV: '"production"',
                }
            }
        ),
        new CaseSensitivePathsPlugin(), //区分路径大小写(避免osx开发人员路径书写冲突)
        new FriendlyErrorsWebpackPlugin(), //识别webpack报错
        new MiniCssExtractPlugin({ //将css单独打包
            filename: 'css/[name].[contenthash:8].css',
            chunkFilename: 'css/[name].[contenthash:8].css'
        }),
        new OptimizeCssAssetsPlugin({ //css压缩
            assetNameRegExp: /\.css$/g, //一个正则表达式，指示应优化/最小化的资产的名称。提供的正则表达式针对配置中ExtractTextPlugin实例导出的文件的文件名运行，而不是源CSS文件的文件名。默认为/\.css$/g
            cssProcessor: require('cssnano'), //用于优化\最小化CSS的CSS处理器，默认为cssnano
            cssProcessorOptions: {
                preset: [
                    'default',
                    {
                        discardComments: {
                            removeAll: true 
                        } 
                    }
                ]
            },
            canPrint: true //指示插件是否可以将消息打印到控制台
        }),
        new webpack.HashedModuleIdsPlugin({hashDigest: 'hex'}), //该插件会根据模块的相对路径生成一个四位数的hash作为模块id, 建议用于生产环境(使用16进制编码方式)
        new HtmlWebpackPlugin({ //打包index.html
            title: 'PPCH', //页面title
            filename: 'index.html',
            minify: { //缩小生成的HTML
                collapseWhitespace: true,
                removeComments: true,
                removeRedundantAttributes: true,
                removeScriptTypeAttributes: true,
                removeStyleLinkTypeAttributes: true,
                useShortDoctype: true
            },
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
                    name: 'chunk-vendors',
                    test: /[\\/]node_modules[\\/]/,
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
                            esModule: false, //使用CommonJS
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
                                loader: MiniCssExtractPlugin.loader, //单独打包css
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                                loader: MiniCssExtractPlugin.loader,
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
                      emitWarning: false, //关闭警告提示
                      emitError: false, //关闭错误提示
                      eslintPath: path.resolve(__dirname, '../node_modules/eslint'),
                      formatter: undefined
                    }
                  }
                ]
            }
        ]
    },
};