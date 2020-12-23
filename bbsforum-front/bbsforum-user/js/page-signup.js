function register() {
    let username = document.getElementById("loginUserName").value;
    let email = document.getElementById("loginUserEmail").value;
    let password = document.getElementById("loginUserPassword").value;
    axios({
        method: 'POST',
        url: baseURL+'/user/manage/register',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            username: username,
            email: email,
            passwd: password
        }
    }).then((response) => {
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if (code === 0) {
            location.href="page-login.html";
        } else if (code === 101){
            alert("用户名/邮箱已注册！")
        } else {
            alert(message);
        }
    }).catch(() => {
        console.log("error")
    })

}