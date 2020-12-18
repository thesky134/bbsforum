const menu = [
    // {
    // "name": "首页",
    // "icon": "&#xe68e;",
    // "url": "index.html",
    // "hidden": false,
    // "list": []
    // },
    // {
    //     "name": "基本组件",
    //     "icon": "&#xe653;",
    //     "url": "",
    //     "hidden": false,
    //     "list": [{
    //         "name": "layui基本组件",
    //         "url": "pages_component.html",
    //     }, {
    //         "name": "layui内置模块",
    //         "url": "pages_model.html"
    //     }, {
    //         "name": "提示框",
    //         "url": "pages_msg.html"
    //     }]
    // },
    {
        "name": "用户管理",
        "icon": "&#xe612;",
        "url": "",
        "hidden": false,
        "list": [{
            "name": "用户列表",
            "url": "user_index.html"
        }, {
            "name": "添加用户",
            "url": "user_add.html"
        }]
    },
    {
        "name": "帖子管理",
        "icon": "&#xe609;",
        "url": "",
        "hidden": false,
        "list": [ {
            "name": "帖子管理",
            "url": "article_index.html"
        }]
    },
    {
        "name": "分类管理",
        "icon": "&#xe620;",
        "url": "",
        "hidden": false,
        "list": [{
            "name": "分类管理",
            "url": "type_index.html"
        }]
    },
    // {
    //     "name": "数据库",
    //     "icon": "&#xe857;",
    //     "url": "",
    //     "hidden": false,
    //     "list": [{
    //         "name": "备份数据库",
    //         "url": "db_backup.html"
    //     }, {
    //         "name": "还原数据库",
    //         "url": "db_reduction.html"
    //     }]
    // },
    // {
    //     "name": "退出登录",
    //     "icon": "&#xe65c;",
    //     "url": "out.html",
    //     "list": []
    // },
    // {
    //     "name": "开发者官网",
    //     "icon": "&#xe65f;",
    //     "url": "http://www.qadmin.net/",
    //     "target": "_blank",
    //     "list": []
    // },
    // {
    //     "name": "开发文档",
    //     "icon": "&#xe655;",
    //     "url": "http://docs.qadmin.net/",
    //     "target": "_blank",
    //     "list": []
    // }
    ];

const config = {
    name: "Qadmin",
    menu: menu,
};

// module.exports.name = "Qadmin";
// module.exports.menu = menu;
