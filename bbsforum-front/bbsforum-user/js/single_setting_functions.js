// 展示个人信息
function showInfo() {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/info/'+localStorage.myUid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        let result = response.data;
        let user = result.data.user;
        let picture = user.picture;
        let headPic = baseURL + '/image/' + picture;
        let score = user.score;
        let username = user.username;
        let email = user.email;
        let phone = user.phone;
        let jobType = user.jobType;
        let jobLocation = user.jobLocation;
        let personalSignal = user.personalSignal;
        let scoreDiv = document.getElementById("scoreDiv");
        let settingsUserScore = document.createElement("settingsUserScore");
        settingsUserScore.for="settingsUserName";
        settingsUserScore.innerText = score;
        document.getElementById("sheadPic").src = headPic;
        scoreDiv.appendChild(settingsUserScore);
        document.getElementById("settingsUserName").value = username;
        document.getElementById("settingsUserEmail").value = email;
        document.getElementById("settingsUserPhone").value = phone;
        document.getElementById("settingsUserJob").value = jobType;
        document.getElementById("settingsUserLocation").value = jobLocation;
        document.getElementById("settingsUserAbout").value = personalSignal;
        // 展示头像+用户名+积分
        let showUsernameDiv = document.getElementById("showUsernameDiv");
        let showScoreDiv = document.getElementById("showScoreDiv");
        let showHeadPicDiv = document.getElementById("showHeadPicDiv");
        let showUsername = document.createElement("div");
        let showScore = document.createElement("li");
        let showHeadPic = document.createElement("div");
        showHeadPic.className = "headPicContainer";
        showHeadPic.id = "showHeadPic";
        showHeadPic.innerHTML = `<img id="AHeadPic" class="headPic" src="`+headPic+`"/>`;
        showUsername.innerHTML = `<a href="#" id="AUsername">`+username+`</a>`;
        showScore.innerHTML = `<a href="#"><span class="tt-color14 tt-badge">积分 : `+score+`</span></a>`;
        showUsernameDiv.appendChild(showUsername);
        showScoreDiv.appendChild(showScore);
        showHeadPicDiv.appendChild(showHeadPic);
    }).catch(() => {
        console.log("error");
    })
}
// 更新头像
function updateHeadPicFather(){
    updateHeadPic();
}
function updateHeadPic(){
    console.log("updateHeadPic()被调用了");
    let picture = document.getElementById("picture").files[0]
    let formData = new FormData()
    formData.append("picture", picture)
    const config = {
        headers: {
            "Content-Type":
                "multipart/form-data;boundary=" + new Date().getTime(),
            'Oauth-Token': localStorage.token
        }
    };
    axios.post(
        baseURL+"/user/manage/update/picture",
        formData,
        config
    ).then((response) => {
        let result = response.data;
        let message = result.message;
        let sheadDiv = document.getElementById("sheadDiv");
        if (document.getElementById("showHeadPicMessage")) {
            sheadDiv.removeChild(document.getElementById("showHeadPicMessage"));
        }
        let showHeadPicMessage = document.createElement("label");
        showHeadPicMessage.id = "showHeadPicMessage";
        showHeadPicMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showHeadPicMessage.innerText = message;
        sheadDiv.appendChild(showHeadPicMessage);
        getSettingHeadPic();
        getHeadPic();
        console.log(response);
    }).catch(() => {
        console.log("error")
    })
}
// 获取用户头像
function getSettingHeadPic(){
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
        console.log(headPicSrc);
        let sheadPicDiv = document.getElementById("sheadPicDiv");
        if (document.getElementById("sheadPicContainer")){
            sheadPicDiv.removeChild(document.getElementById("sheadPicContainer"));
            console.log("删除了");
        }
        let sheadPicContainer = document.createElement("div");
        sheadPicContainer.className = "headPicContainer";
        sheadPicContainer.id = "sheadPicContainer";
        sheadPicContainer.innerHTML = `<img class="headPic" src=`+headPicSrc+`>`;
        sheadPicDiv.appendChild(sheadPicContainer);
        let showHeadPicDiv = document.getElementById("showHeadPicDiv");
        showHeadPicDiv.removeChild(document.getElementById("showHeadPic"));
        let showHeadPic = document.createElement("div");
        showHeadPic.id = "showHeadPic";
        showHeadPic.className = "headPicContainer";
        showHeadPic.innerHTML = `<img id="AHeadPic" class="headPic" src="`+headPicSrc+`">`;
        showHeadPicDiv.appendChild(showHeadPic);
    }).catch(()=>{
        console.log("error");
    })
}

// 更新个人信息
// 更新用户名
function checkUsername() {
    let username = document.getElementById("settingsUserName").value;
    let usernameDiv = document.getElementById("usernameDiv");
    // 检查正则
    let re_username = /^[^\s]{3,20}$/;
    if (re_username.test(username)) {
        updateUsername(username);
    } else {
        if (document.getElementById("showUsernameMessage")) {
            usernameDiv.removeChild(document.getElementById("showUsernameMessage"));
        }
        let showUsernameMessage = document.createElement("label");
        showUsernameMessage.id = "showUsernameMessage";
        showUsernameMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showUsernameMessage.innerText = "用户名不能为空，不能含空格，且长度为3~20位！";
        usernameDiv.appendChild(showUsernameMessage);
    }
}
function updateUsername(username) {
    console.log(username);
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/username',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            username: username
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        // <label v-show="vshow_email" style="font-size: 10px; color: #ff0000">{{email_msg}}</label>
        let usernameDiv = document.getElementById("usernameDiv");
        if (document.getElementById("showUsernameMessage")) {
            usernameDiv.removeChild(document.getElementById("showUsernameMessage"));
        }
        let showUsernameMessage = document.createElement("label");
        showUsernameMessage.id = "showUsernameMessage";
        showUsernameMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showUsernameMessage.innerText = message;
        usernameDiv.appendChild(showUsernameMessage);
        let myUsername = document.getElementById("myUsername");
        myUsername.innerText = username;
        let AUsername = document.getElementById("AUsername");
        AUsername.innerText = username;
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 更新邮箱
function checkEmail() {
    let email = document.getElementById("settingsUserEmail").value;
    let emailDiv = document.getElementById("emailDiv");
    // 检查正则
    let re_email = /^\S+@\S+\.\S+$/;
    if (re_email.test(email)) {
        updateEmail(email);
    } else {
        if (document.getElementById("showEmailMessage")) {
            emailDiv.removeChild(document.getElementById("showEmailMessage"));
        }
        let showEmailMessage = document.createElement("label");
        showEmailMessage.id = "showEmailMessage";
        showEmailMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showEmailMessage.innerText = "邮箱格式有误！";
        emailDiv.appendChild(showEmailMessage);
    }
}
function updateEmail(email) {
    console.log(email);
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/email',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            email: email
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        // <label v-show="vshow_email" style="font-size: 10px; color: #ff0000">{{email_msg}}</label>
        let emailDiv = document.getElementById("emailDiv");
        if (document.getElementById("showEmailMessage")) {
            emailDiv.removeChild(document.getElementById("showEmailMessage"));
        }
        let showEmailMessage = document.createElement("label");
        showEmailMessage.id = "showEmailMessage";
        showEmailMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showEmailMessage.innerText = message;
        emailDiv.appendChild(showEmailMessage);
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 更新密码
function checkOldPasswd() {
    let oldPasswd = document.getElementById("oldUserPassword").value;
    let passwdDiv = document.getElementById("passwdDiv");
    // 检查正则
    let re_oldPasswd = /^[^\s]{6,26}$/;
    if (re_oldPasswd.test(oldPasswd)) {
        console.log(oldPasswd);
        return oldPasswd;
    } else {
        if (document.getElementById("showPasswdMessage")) {
            passwdDiv.removeChild(document.getElementById("showPasswdMessage"));
        }
        let showPasswdMessage = document.createElement("label");
        showPasswdMessage.id = "showPasswdMessage";
        showPasswdMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPasswdMessage.innerText = "密码不能为空，密码长度为6~26位！";
        passwdDiv.appendChild(showPasswdMessage);
    }
}
function checkNewPasswd() {
    checkOldPasswd();
    let oldPasswd = checkOldPasswd();
    let newPasswd = document.getElementById("settingsUserPassword").value;
    let passwdDiv = document.getElementById("passwdDiv");
    // 检查正则
    let re_passwd = /^[^\s]{6,26}$/;
    if (re_passwd.test(newPasswd)) {
        updatePasswd(oldPasswd,newPasswd);
    } else {
        if (document.getElementById("showPasswdMessage")) {
            passwdDiv.removeChild(document.getElementById("showPasswdMessage"));
        }
        let showPasswdMessage = document.createElement("label");
        showPasswdMessage.id = "showPasswdMessage";
        showPasswdMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPasswdMessage.innerText = "密码不能为空，密码长度为6~26位！";
        passwdDiv.appendChild(showPasswdMessage);
    }
}
function updatePasswd(oldPasswd, newPasswd) {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/passwd',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            oldPasswd: oldPasswd,
            newPasswd: newPasswd
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        let token = result.token;
        localStorage.token = token;
        let passwdDiv = document.getElementById("passwdDiv");
        if (document.getElementById("showPasswdMessage")) {
            passwdDiv.removeChild(document.getElementById("showPasswdMessage"));
        }
        let showPasswdMessage = document.createElement("label");
        showPasswdMessage.id = "showPasswdMessage";
        showPasswdMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPasswdMessage.innerText = message;
        passwdDiv.appendChild(showPasswdMessage);
        alert(message);
    }).catch(() => {
        console.log("error");
    })
}

// 更新联系方式
function checkPhone() {
    let phone = document.getElementById("settingsUserPhone").value;
    let phoneDiv = document.getElementById("phoneDiv");
    // 检查正则
    let re_phone = /^[0-9]{3,20}$/;
    if (re_phone.test(phone)) {
        updatePhone(phone);
    } else {
        if (document.getElementById("showPhoneMessage")) {
            phoneDiv.removeChild(document.getElementById("showPhoneMessage"));
        }
        let showPhoneMessage = document.createElement("label");
        showPhoneMessage.id = "showPhoneMessage";
        showPhoneMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPhoneMessage.innerText = "联系方式长度应在3~20位，且都为数字！";
        phoneDiv.appendChild(showPhoneMessage);
    }
}
function updatePhone(phone) {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/phone',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            phone: phone
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        // <label v-show="vshow_email" style="font-size: 10px; color: #ff0000">{{email_msg}}</label>
        let phoneDiv = document.getElementById("phoneDiv");
        if (document.getElementById("showPhoneMessage")) {
            phoneDiv.removeChild(document.getElementById("showPhoneMessage"));
        }
        let showPhoneMessage = document.createElement("label");
        showPhoneMessage.id = "showPhoneMessage";
        showPhoneMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPhoneMessage.innerText = message;
        phoneDiv.appendChild(showPhoneMessage);
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 更新工作性质
function checkJobType() {
    let jobType = document.getElementById("settingsUserJob").value;
    let jobTypeDiv = document.getElementById("jobTypeDiv");
    // 检查长度
    let length = jobType.length;
    if (length >= 2 && length <= 30) {
        updateJobType(jobType);
    } else {
        if (document.getElementById("showJobTypeMessage")) {
            jobTypeDiv.removeChild(document.getElementById("showJobTypeMessage"));
        }
        let showJobTypeMessage = document.createElement("label");
        showJobTypeMessage.id = "showJobTypeMessage";
        showJobTypeMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showJobTypeMessage.innerText = "工作性质长度应在2~30！";
        jobTypeDiv.appendChild(showJobTypeMessage);
    }
}
function updateJobType(jobType) {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/jobtype',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            jobType: jobType
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        let jobTypeDiv = document.getElementById("jobTypeDiv");
        if (document.getElementById("showJobTypeMessage")) {
            jobTypeDiv.removeChild(document.getElementById("showJobTypeMessage"));
        }
        let showJobTypeMessage = document.createElement("label");
        showJobTypeMessage.id = "showJobTypeMessage";
        showJobTypeMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showJobTypeMessage.innerText = message;
        jobTypeDiv.appendChild(showJobTypeMessage);
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 更新工作地点
function checkJobLocation() {
    let jobLocation = document.getElementById("settingsUserLocation").value;
    let jobLocationDiv = document.getElementById("jobLocationDiv");
    // 检查长度
    let length = jobLocation.length;
    if (length >= 2 && length <= 60) {
        updateJobLocation(jobLocation);
    } else {
        if (document.getElementById("showJobLocationMessage")) {
            jobLocationDiv.removeChild(document.getElementById("showJobLocationMessage"));
        }
        let showJobLocationMessage = document.createElement("label");
        showJobLocationMessage.id = "showJobLocationMessage";
        showJobLocationMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showJobLocationMessage.innerText = "工作地址长度应在2~60！";
        jobLocationDiv.appendChild(showJobLocationMessage);
    }
}
function updateJobLocation(jobLocation) {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/joblocation',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            jobLocation: jobLocation
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        let jobLocationDiv = document.getElementById("jobLocationDiv");
        if (document.getElementById("showJobLocationMessage")) {
            jobLocationDiv.removeChild(document.getElementById("showJobLocationMessage"));
        }
        let showJobLocationMessage = document.createElement("label");
        showJobLocationMessage.id = "showJobLocationMessage";
        showJobLocationMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showJobLocationMessage.innerText = message;
        jobLocationDiv.appendChild(showJobLocationMessage);
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 更新个性签名
function checkPersonalSignal() {
    let personalSignal = document.getElementById("settingsUserAbout").value;
    let personalSignalDiv = document.getElementById("personalSignalDiv");
    // 检查长度
    let length = personalSignal.length;
    if (length >= 0 && length <= 100) {
        updatePersonalSignal(personalSignal);
    } else {
        if (document.getElementById("showPersonalSignalDivMessage")) {
            personalSignalDiv.removeChild(document.getElementById("showPersonalSignalDivMessage"));
        }
        let showPersonalSignalDivMessage = document.createElement("label");
        showPersonalSignalDivMessage.id = "showPersonalSignalDivMessage";
        showPersonalSignalDivMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPersonalSignalDivMessage.innerText = "个性签名长度应在0~100！";
        personalSignalDiv.appendChild(showPersonalSignalDivMessage);
    }
}
function updatePersonalSignal(personalSignal) {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/update/personalsignal',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            personalSignal: personalSignal
        }
    }).then((response) => {
        let result = response.data;
        let message = result.message;
        let personalSignalDiv = document.getElementById("personalSignalDiv");
        if (document.getElementById("showPersonalSignalDivMessage")) {
            personalSignalDiv.removeChild(document.getElementById("showPersonalSignalDivMessage"));
        }
        let showPersonalSignalDivMessage = document.createElement("label");
        showPersonalSignalDivMessage.id = "showPersonalSignalDivMessage";
        showPersonalSignalDivMessage.style.cssText = "font-size: 10px; color: #ff0000";
        showPersonalSignalDivMessage.innerText = message;
        personalSignalDiv.appendChild(showPersonalSignalDivMessage);
        console.log(result);
    }).catch(() => {
        console.log("error");
    })
}

// 清空提示
document.onclick = function (event) {
    var e = event || window.event;
    var elem = e.srcElement || e.target;
    while (elem) {
        if (elem.id == "js-popup-settings") {
            return;
        }
        elem = elem.parentNode;
    }
    // 清空提示的方法
    cleanMessage();
}
function cleanMessage() {
    let headDiv = document.getElementById("sheadDiv");
    let usernameDiv = document.getElementById("usernameDiv");
    let emailDiv = document.getElementById("emailDiv");
    let passwdDiv = document.getElementById("passwdDiv");
    let phoneDiv = document.getElementById("phoneDiv");
    let jobTypeDiv = document.getElementById("jobTypeDiv");
    let jobLocationDiv = document.getElementById("jobLocationDiv");
    let personalSignalDiv = document.getElementById("personalSignalDiv");
    if (document.getElementById("showHeadPicMessage")) {
        headDiv.removeChild(document.getElementById("showHeadPicMessage"));
    }
    if (document.getElementById("showUsernameMessage")) {
        usernameDiv.removeChild(document.getElementById("showUsernameMessage"));
    }
    if (document.getElementById("showEmailMessage")) {
        emailDiv.removeChild(document.getElementById("showEmailMessage"));
    }
    if (document.getElementById("showPasswdMessage")) {
        passwdDiv.removeChild(document.getElementById("showPasswdMessage"));
    }
    if (document.getElementById("showPhoneMessage")) {
        phoneDiv.removeChild(document.getElementById("showPhoneMessage"));
    }
    if (document.getElementById("showJobTypeMessage")) {
        jobTypeDiv.removeChild(document.getElementById("showJobTypeMessage"));
    }
    if (document.getElementById("showJobLocationMessage")) {
        jobLocationDiv.removeChild(document.getElementById("showJobLocationMessage"));
    }
    if (document.getElementById("showPersonalSignalDivMessage")) {
        personalSignalDiv.removeChild(document.getElementById("showPersonalSignalDivMessage"));
    }
}
