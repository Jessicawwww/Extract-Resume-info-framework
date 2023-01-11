
const context = require.context("./plugins", false, /\.tsx$/);
const plugins: any = {};
context.keys().forEach(function (key) {
    const plugin = (require('./plugins/' + key.substring(2)).default)()
    plugins[plugin.getName()] = plugin;
});

export default plugins;