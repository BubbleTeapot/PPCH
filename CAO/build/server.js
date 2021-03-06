const path = require('path');
const webpack = require('webpack');
const webpackDevServer = require('webpack-dev-server');

const config = require('./webpack.dev.config');
const options = {
  open: true,
  // hot: true,
  host: '127.0.0.1',
  historyApiFallback: true,
  contentBase: path.resolve(__dirname, '../dist'),
}
webpackDevServer.addDevServerEntrypoints(config, options);
const compiler = webpack(config);
const server = new webpackDevServer(compiler, options);

server.listen(8001, '127.0.0.1', () => {
  console.log('start: 127.0.0.1:80');
});
