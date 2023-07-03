const { createProxyMiddleware } = require('http-proxy-middleware')
module.exports = function (app) {
	app.use(
		createProxyMiddleware('/we22', {
			target: 'http://localhost:8080',
			changeOrigin: true,
			pathRewrite: { '^/we22': '' }
		}),/*  */
		createProxyMiddleware('/baidu', {
			target: 'https://api.map.baidu.com',
			changeOrigin: true,
			pathRewrite: { '^/baidu': '' }
		})
	)
}