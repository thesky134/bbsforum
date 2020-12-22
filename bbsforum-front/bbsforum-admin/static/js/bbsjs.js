window.config = {
    baseURL: 'http://localhost:8083'
}

axios.interceptors.request.use(
    function (config){
        config.headers['Oauth-Token'] = localStorage.adminToken;
        return config;
    },
    function (error){
        return Promise.reject(error);
    }
)

axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    let result = response.data
    let code = result.code
    if(code === 121 || code === 141) {
        layui.use("layer", function () {
            let layer = layui.layer
            top.location.href= "/bbsforum-admin/login.html"
        })
    }
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});

function getFormatTime(timeStr) {
    if(timeStr === null || timeStr === undefined) {
        return ""
    }
    let tPosition = timeStr.indexOf('T')
    let pointPosition = timeStr.indexOf('.')
    return timeStr.substring(0, tPosition) + " " + timeStr.substring(tPosition + 1, pointPosition)
}

