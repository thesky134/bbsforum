function login(){
    console.log("login()方法被调用了");
    let username = document.getElementById("loginUserName").value;
    let passwd = document.getElementById("loginUserPassword").value;
    axios({
        method: 'POST',
        url: baseURL+'/user/manage/login',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            username: username,
            passwd: passwd
        }
    }).then((response)=>{
        console.log(response.data);
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if (code === 0) {
            let token = result.data["Oauth-Token"];
            localStorage.token = token;
            console.log(localStorage.token);
            location.href="index.html";
        } else if(code === 1) {
            alert("异常错误！");
        }else {
            alert(message);
        }
    }).catch(()=>{
        console.log("error")
    })

}