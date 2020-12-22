layui.use('form', function () {
    var form = layui.form,
        layer = layui.layer;
});

var vue = new Vue({
    el:'#app',
    data:{
        webname:config.name,
        menu:[],
        address:[]
    },
    created:function(){
        this.menu = config.menu;
        this.thisActive();
        this.thisAttr();

        let superadminTemplate = {
            "name": "管理员管理",
            "icon": "&#xe653;",
            "url": "",
            "hidden": true,
            "list": [{
                "name": "管理员列表",
                "url": "admin_index.html"
            },
                {
                    "name": "添加管理员",
                    "url": "admin_add.html"
                }]
        }
        axios({
            method: 'POST',
            url: window.config.baseURL + "/user/manage/info/" + window.localStorage.getItem("adminId"),
            headers: {
                'Content-Type': 'application/json'
            }}).then((response) => {
            let result = response.data
            let code = result.code
            if (code === 0) {
                let user = result.data.user
                if(user.chara === "superadmin") {
                menu.push(superadminTemplate)
                }
            } else {
                alert(result.message)
            }
        }).catch(() => {
            alert("获取用户信息出错")
        })
    },
    methods:{
        //记住收展
        onActive:function (pid,id=false) {
            let data;
            if(id===false){
                data = this.menu[pid];
                if(data.url.length>0){
                    this.menu.forEach((v,k)=>{
                        v.active = false;
                        v.list.forEach((v2,k2)=>{
                            v2.active = false;
                        })
                    })
                    data.active = true;
                }
                data.hidden = !data.hidden;
            }else{
                this.menu.forEach((v,k)=>{
                    v.active = false;
                    v.list.forEach((v2,k2)=>{
                        v2.active = false;
                    })
                })
                data = this.menu[pid].list[id];
            }
            if(data.url.length>0){
                if(data.target){
                    if(data.target=='_blank'){
                        window.open(data.url);
                    }else{
                        window.location.href = data.url;
                    }
                }else{
                    window.location.href = data.url;
                }
            }
        },
        //菜单高亮
        thisActive:function(){
            let pathname = window.location.pathname;
            let host = window.location.host;
            let pid = false;
            let id = false;
            this.menu.forEach((v,k)=>{
                let url = v.url;
                if(url.length>0){
                    if(url[0]!=='/' && url.substr(0,4)!=='http'){
                        url = '/'+url;
                    }
                }
                if(pathname===url){
                    pid = k;
                }
                v.list.forEach((v2,k2)=>{
                    let url = v2.url;

                    if(url.length>0){
                        if(url[0]!=='/' && url.substr(0,4)!=='http'){
                            url = '/'+url;
                        }
                    }
                    if(pathname===url){
                        pid = k;
                        id = k2;
                    }
                })
            })


            if(id!==false){
                this.menu[pid].list[id].active = true;
            }else{
                if(pid!==false){
                    this.menu[pid].active = true;
                }
            }
        },
        //当前位置
        thisAttr:function () {
            //当前位置
            let address = [{
                name:'首页',
                url:'index.html'
            }];
            this.menu.forEach((v,k)=>{
                    v.list.forEach((v2,k2)=>{
                        if(v2.active){
                        address.push({
                            name:v.name,
                            url:'javascript:;'
                        })
                        address.push({
                            name:v2.name,
                            url:v2.url,
                        })
                        this.address = address;
                    }
                })
            })
        }
    }
})





