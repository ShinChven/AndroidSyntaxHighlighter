
// ref: https://umijs.org/config/
export default {
  treeShaking: true,
  routes: [
    {
      path: '/',
      component: '../layouts/index',
      routes: [
        { path: '/', component: '../pages/index' }
      ]
    }
  ],
  plugins: [
    // ref: https://umijs.org/plugin/umi-plugin-react.html
    ['umi-plugin-react', {
      antd: false,
      dva: false,
      dynamicImport: false,
      title: 'syntax-hightlighter',
      dll: false,

      routes: {
        exclude: [
          /components\//,
        ],
      },
    }],
  ],
    manifest: {
      basePath: '/',
    },
    history:'hash',
    hash: true,
    base:'/',
    publicPath:'./',
    cssPublicPath:'./',
}
