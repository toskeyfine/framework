'use strict'

const path = require('path')
const devEnv = require('./dev.env')


module.exports = {
    dev: {

        // Paths
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        // 代理列表, 是否开启代理通过[./dev.env.js]配置
        proxyTable: devEnv.OPEN_PROXY === false ? {} : {
            '/proxyApi': {
                target: 'http://127.0.0.1',
                changeOrigin: true,
                pathRewrite: {
                    '^/proxyApi': '/'
                }
            }
        },

        host: 'localhost',
        port: 9999,
        autoOpenBrowser: true,
        errorOverlay: true,
        notifyOnErrors: true,
        poll: false,

        useEslint: true,
        showEslintErrorsInOverlay: false,

        devtool: 'eval-source-map',

        cacheBusting: true,

        cssSourceMap: false,
    },

    build: {
        index: path.resolve(__dirname, '../dist/index.html'),

        assetsRoot: path.resolve(__dirname, '../dist'),
        assetsSubDirectory: 'static',
        assetsPublicPath: './',


        productionSourceMap: false,
        devtool: '#source-map',
        productionGzip: false,
        productionGzipExtensions: ['js', 'css'],

        bundleAnalyzerReport: process.env.npm_config_report
    }
}