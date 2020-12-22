// 判断登录状态展示状态栏
function checkLoginState() {
    let username = null;
    let picture = getHeadPic();
    let headPic = baseURL+'/image/' + picture;
    let loginState = false;
    let uid = null;
    let loginHeader = document.getElementById("tt-header-login");
    axios({
        method: 'POST',
        url: baseURL+'/user/manage/checkloginstate',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        loginState = result.data.loginState;
        uid = result.data.userId;
        localStorage.myUid = uid;
        console.log(result);
        if(loginState){
            username = result.data.username;
            let userTab = document.createElement("div");
            userTab.className = "col-auto ml-auto";
            userTab.innerHTML = `<div class="tt-user-info d-flex justify-content-center">
                                     <div class="tt-col-avatar" id="headPicDiv">

                                     </div>
                                    <div class="">
                                        <a href="./page-single_threads.html" class="btn btn-primary" id="myUsername">`+username+`</a>
                                        <a href="./page-login.html" class="btn btn-secondary" onclick="logout()">退出</a>
                                    </div>`;
            loginHeader.appendChild(userTab);
            getHeadPic();
        }else{
            let loginEntrances = document.createElement("div");
            loginEntrances.className = "col-auto ml-auto";
            loginEntrances.innerHTML = `<div class="tt-account-btn">
                        <a href="../bbsforum-admin/login.html" class="btn btn-primary">管理员</a>
                        <a href="./page-login.html" class="btn btn-primary">登录</a>
                        <a href="./page-signup.html" class="btn btn-secondary">注册</a>
                    </div>`;
            loginHeader.appendChild(loginEntrances);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 获取用户个人栏头像
function getHeadPic(){
    axios({
        method: 'POST',
        url: baseURL+'/user/manage/info/'+localStorage.myUid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let picture = result.data.user.picture;
        let headPicSrc = baseURL+'/image/'+picture;
        let headPicDiv = document.getElementById("headPicDiv");
        if (document.getElementById("headPicContainer")){
            headPicDiv.removeChild(document.getElementById("headPicContainer"));
        }
        let headPicContainer = document.createElement("div");
        headPicContainer.className = "headPicContainer";
        headPicContainer.id = "headPicContainer";
        headPicContainer.onclick = function (){
            toSingleCenter();
        }
        headPicContainer.innerHTML = `<img class="headPic" src=`+headPicSrc+`>`;
        headPicDiv.appendChild(headPicContainer);
    }).catch(()=>{
        console.log("error");
    })
}
// 跳转到用户自己的个人中心
function toSingleCenter(){
    location.href = "page-single_threads.html";
}
// 跳转到其他用户的个人中心
function toOtherSingleCenter(uid){
    getUserId(uid);
    location.href = "page-other-single_threads.html";
}
// 获取下一页位置
function getPosition(){
    let position = parseInt(document.getElementById("nextPage").value);
    position ++;
    document.getElementById("nextPage").value = position;
    console.log(position);
    return position;
}
// 退出登录
function logout(){
    localStorage.removeItem("token");
}
// 获取帖子id
function getTopicId(id){
    localStorage.tid = id;
}
// 获取用户id
function getUserId(id){
    let uid = id;
    localStorage.uid = uid;
}