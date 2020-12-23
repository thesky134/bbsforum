// 展示个人信息
function showInfo() {
    axios({
        method: 'POST',
        url: baseURL + '/user/manage/info/'+localStorage.uid,
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
        showUsername.innerHTML = `<a href="#">`+username+`</a>`;
        showScore.innerHTML = `<a href="#"><span class="tt-color14 tt-badge">积分 : `+score+`</span></a>`;
        showUsernameDiv.appendChild(showUsername);
        showScoreDiv.appendChild(showScore);
        showHeadPicDiv.appendChild(showHeadPic);
    }).catch(() => {
        console.log("error");
    })
}