// 获取帖子详情
function getTopicView(){
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        // 获取数据
        let result = response.data;
        let topic = result.data.post;
        let picture = topic.picture;
        let username = topic.user;
        let category = topic.category;
        let reward = topic.reward;
        let title = topic.title;
        let content = topic.content;
        let createDate = topic.createDate;
        let showTime = createDate.slice(0, createDate.indexOf('T'));
        let headPicSrc = baseURL+'/image/'+picture;
        let uid = topic.userId;

        // 获取父元素节点
        let headPicDiv = document.getElementById("theadPicDiv");
        let showUsernameDiv = document.getElementById("showUsernameDiv");
        let showCategoryDiv = document.getElementById("showCategoryDiv");
        let showTitleDiv = document.getElementById("showTitleDiv");
        let showContentDiv = document.getElementById("showContentDiv");
        let showCreateTimeDiv = document.getElementById("showCreateTimeDiv");
        // 创建元素节点
        let headPicContainer = document.createElement("div");
        let showUsername = document.createElement("a");
        let showCategory = document.createElement("span");
        let showTitle = document.createElement("a");
        let showContent = document.createElement("div");
        let showCreateTime = document.createElement("i");
        // 元素节点中添加数据和HTML
        showUsername.innerHTML = `<a href="page-other-single_threads.html">`+username+`</a>`;
        showUsername.id = uid;
        showUsername.onclick = function (){
            toOtherSingleCenter(this.id);
        }
        if (category === "积分悬赏"){
            showCategory.innerHTML = `<span class="tt-color03 tt-badge">`+category+` : `+reward+` 积分</span>`;
        }else {
            showCategory.innerHTML = `<span class="tt-color03 tt-badge">`+category+`</span>`;
        }
        showTitle.innerHTML = `<a href="#">`+title+`</a>`;
        showContent.innerText = content;
        showCreateTime.innerHTML = `<i class="tt-icon"><svg><use xlink:href="#icon-time"></use></svg></i>`+showTime;
        // 父元素节点添加元素节点
        showUsernameDiv.appendChild(showUsername);
        showCategoryDiv.appendChild(showCategory);
        showTitleDiv.appendChild(showTitle);
        showContentDiv.appendChild(showContent);
        showCreateTimeDiv.appendChild(showCreateTime);
        headPicContainer.className = "headPicContainer";
        headPicContainer.id = uid;
        headPicContainer.innerHTML = `<img class="headPic" src=`+headPicSrc+`>`;
        headPicDiv.appendChild(headPicContainer);
        headPicContainer.onclick = function (){
            toOtherSingleCenter(this.id);
        }
        getAllSum();
        getCommentsHot();
    }).catch(()=>{
        console.log("error");
    })
}

// 获取帖子点赞/点踩/喜欢总数
function getAllSum(){
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let topic = result.data.post;
        // 获取点赞/点踩/喜欢总数
        let goodSum = topic.goodSum;
        let badSum = topic.badSum;
        let likeSum = topic.likeSum;
        // 获取是否显示高亮
        let isGood = topic.good;
        let isBad = topic.bad;
        let isLike = topic.like;
        // 获取点赞/点踩/喜欢显示区域
        let goodDiv = document.getElementById("goodDiv");
        let badDiv = document.getElementById("badDiv");
        let likeDiv = document.getElementById("likeDiv");
        // 获取点赞/点踩/喜欢icon
        let goodIcon = document.getElementById("good");
        let badIcon = document.getElementById("bad");
        let likeIcon = document.getElementById("like");

        // 显示点赞数
        if (document.getElementById("good-text")){
            goodDiv.removeChild(document.getElementById("good-text"));
        }
        let goodText = document.createElement("span");
        goodText.id = "good-text";
        goodText.innerHTML = `<span class="tt-text">`+goodSum+`</span>`;
        goodDiv.appendChild(goodText);
        // 点赞是否显示高亮
        if (isGood){
            goodIcon.style.cssText = "fill: #00a0e9;";
            goodText.style.cssText = "color: #00a0e9;";
        }else if(!isGood){
            goodIcon.style.cssText = "";
            goodText.style.cssText = "";
        }

        // 显示点踩数
        if (document.getElementById("bad-text")){
            badDiv.removeChild(document.getElementById("bad-text"));
        }
        let badText = document.createElement("span");
        badText.id = "bad-text";
        badText.innerHTML = `<span class="tt-text">`+badSum+`</span>`;
        badDiv.appendChild(badText);
        // 点踩是否显示高亮
        if (isBad){
            badIcon.style.cssText = "fill: #00a0e9;";
            badText.style.cssText = "color: #00a0e9;";
        }else if (!isBad){
            badIcon.style.cssText = "";
            badText.style.cssText = "";
        }

        // 显示喜欢数
        if (document.getElementById("like-text")){
            likeDiv.removeChild(document.getElementById("like-text"));
        }
        let likeText = document.createElement("span");
        likeText.id = "like-text"
        likeText.innerHTML = `<span class="tt-text">`+likeSum+`</span>`;
        likeDiv.appendChild(likeText);
        // 喜欢是否显示高亮
        if (isLike){
            likeIcon.style.cssText = "fill: #00a0e9;";
            likeText.style.cssText = "color: #00a0e9;";
        }else{
            likeIcon.style.cssText = "";
            likeText.style.cssText = "";
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 获取帖子热度
function getCommentsHot(){
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let topic = result.data.post;
        let visitSum = topic.visitSum;
        let commentSum = topic.commentSum;
        let goodSum = topic.goodSum;
        let likeSum = topic.likeSum;
        // 获取显示区域
        let hVisitDiv = document.getElementById("hVisitDiv");
        let hCommentDiv = document.getElementById("hCommentDiv");
        let hGoodDiv = document.getElementById("hGoodDiv");
        let hLikeDiv = document.getElementById("hLikeDiv");
        // 创建显示区域
        let hVisitText = document.createElement("span");
        hVisitText.innerHTML = `<span class="tt-text">`+visitSum+`</span>`;
        let hCommentText = document.createElement("span");
        hCommentText.innerHTML = `<span class="tt-text">`+commentSum+`</span>`;
        let hGoodText = document.createElement("span");
        hGoodText.innerHTML = `<span class="tt-text">`+goodSum+`</span>`;
        let hLikeText = document.createElement("span");
        hLikeText.innerHTML = `<span class="tt-text">`+likeSum+`</span>`;
        // 添加数据
        hVisitDiv.appendChild(hVisitText);
        hCommentDiv.appendChild(hCommentText);
        hGoodDiv.appendChild(hGoodText);
        hLikeDiv.appendChild(hLikeText);
    }).catch(()=>{
        console.log("error");
    })
}
// 获取一页评论
function getComments(){
    let position = getPosition();
    axios({
        method: 'POST',
        url: baseURL+'/comment/post/list',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            postId: localStorage.tid,
            pageSize: 10,
            position: position
        }
    }).then((response)=>{
        let result = response.data;
        let comments = result.data.comments;
        if (comments.length < 10){
            document.getElementById("bottom").style.display = "";
        }
        let commentsList = document.getElementById("commentsList");
        for(let i=0; i<comments.length;i++){
            let comment = document.createElement("div");
            let createTime = comments[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let picture = comments[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let cid = comments[i].id;
            let uid = comments[i].userId;
            comment.setAttribute("name", "comment");
            comment.innerHTML = `<div class="tt-item">
                    <div class="tt-single-topic">
                        <div class="tt-item-header pt-noborder">
                            <div class="tt-item-info info-top">
                                <div id="`+uid+`" class="tt-avatar-icon" onclick="toOtherSingleCenter(this.id)">
                                    <div class="headPicContainer">
                                        <img class="headPic" src="` + headPic + `"/>
                                    </div>
                                </div>
                                <div class="tt-avatar-title">
                                    <a href="#">`+comments[i].user+`</a>
                                </div>
                                <!--回复时间-->
                                <a href="#" class="tt-info-time">
                                    <i class="tt-icon"><svg><use xlink:href="#icon-time"></use></svg></i>`+showTime+`
                                </a>
                            </div>
                        </div>
                        <div class="tt-item-description">`+comments[i].content+`</div>
                        <div class="tt-item-info info-bottom">
                            <div class="col-separator"></div>
                            <a href="javascript:;" class="btn btn-secondary" style="display: none" name="rewardButton"
                               id="chosen`+cid+`" onclick="chooseAnswer(`+cid+`,this.id)">采纳</a>
                            </div>
                        </div>
                    </div>`;
            commentsList.appendChild(comment);
        }
        isReward();
    }).catch(()=>{
        console.log("error");
    })
}
// 保存当前展示的评论数
function getCommentsSum(){
    let comments = document.getElementsByName("comment");
    localStorage.commentsSum = comments.length;
}
// 删除原来的第一页评论
function removeFirstComments(){
    let commentsList = document.getElementById("commentsList");
    let comments = document.getElementsByName("comment");
    getCommentsSum();
    for(let i=0; i<localStorage.commentsSum; i++){
        commentsList.removeChild(comments[0]);
    }
}
// 获取第一页评论
function getFirstComments(){
    let position = 1;
    axios({
        method: 'POST',
        url: baseURL+'/comment/post/list',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            postId: localStorage.tid,
            pageSize: 10,
            position: position
        }
    }).then((response)=>{
        let result = response.data;
        let comments = result.data.comments;
        if (comments.length < 10){
            document.getElementById("bottom").style.display = "";
        }
        let commentsList = document.getElementById("commentsList");
        for(let i=0; i<comments.length;i++){
            let comment = document.createElement("div");
            let createTime = comments[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let picture = comments[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let cid = comments[i].id;
            let uid = comments[i].userId;
            comment.setAttribute("name", "comment");
            comment.innerHTML = `<div class="tt-item">
                    <div class="tt-single-topic">
                        <div class="tt-item-header pt-noborder">
                            <div class="tt-item-info info-top">
                                <div id="`+uid+`" class="tt-avatar-icon" onclick="toOtherSingleCenter(this.id)">
                                    <div class="headPicContainer">
                                        <img class="headPic" src="` + headPic + `"/>
                                    </div>
                                </div>
                                <div class="tt-avatar-title">
                                    <a href="#">`+comments[i].user+`</a>
                                </div>
                                <!--回复时间-->
                <a href="#" class="tt-info-time">
                <i class="tt-icon"><svg><use xlink:href="#icon-time"></use></svg></i>`+showTime+`
                </a>
                </div>
                </div>
                <div class="tt-item-description">`+comments[i].content+`</div>
                <div class="tt-item-info info-bottom">
                    <div class="col-separator"></div>
                    <a href="javascript:;" class="btn btn-secondary" style="display: none" name="rewardButton"
                       id="chosen`+cid+`" onclick="chooseAnswer(`+cid+`,this.id)">采纳</a>
                </div>
                </div>
                </div>`;
            commentsList.appendChild(comment);
        }
        isReward();
    }).catch(()=>{
        console.log("error");
    })
}
// 写回复
function sendComment(){
    let content = document.getElementById("comment").value;
    axios({
        method: 'POST',
        url: baseURL+'/comment/manage/add/',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            postId: localStorage.tid,
            content: content
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if (code === 0){
            removeFirstComments();
            getFirstComments();
            document.getElementById("comment").value = "";
            alert(message);
        }else {
            alert(message);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 判断是否为积分悬赏分区，显示采纳按钮
function isReward() {
    axios({
        method: 'POST',
        url: baseURL + '/post/view/' + localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        // 获取数据
        let result = response.data;
        let topic = result.data.post;
        let category = topic.category;
        let userId = topic.userId;
        let comment = topic.comment;
        let rewardButtons = document.getElementsByName("rewardButton");
        if (category === "积分悬赏"){
                if (comment !== null){
                    showAnswer();
                    let commentId = comment.id;
                    let chosenAnswer = document.getElementById("chosen"+commentId.toString());
                    chosenAnswer.style.cssText = "";
                    chosenAnswer.setAttribute("name","chosenAnswer");
                    chosenAnswer.innerHTML = "已采纳";
                    chosenAnswer.className = "btn btn-secondary";
                }else {
                    if ((localStorage.myUid == userId) || (localStorage.chara === "admin")
                        ||(localStorage.chara) === "superadmin"){
                        for (let i = 0;i<rewardButtons.length;i++){
                            rewardButtons[i].style.display = "";
                        }
                    }
                }
        }



    }).catch(()=>{
        console.log("error");
    })
}
// 采纳回答
function chooseAnswer(commentId,chosenId){
    axios({
        method: 'POST',
        url: baseURL+'/post/answer',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            postId: localStorage.tid,
            commentId: commentId
        }
    }).then((response)=>{
        let result = response.data;
        let chosenAnswer = document.getElementById(chosenId);
        chosenAnswer.setAttribute("name","chosenAnswer");
        chosenAnswer.innerHTML = "已采纳";
        chosenAnswer.className = "btn btn-secondary";
        let rewardButtons = document.getElementsByName("rewardButton");
        for (let i = 0;i<rewardButtons.length;i++){
            rewardButtons[i].style.display = "none";
        }
        showAnswer();
    }).catch(()=>{
        console.log("error");
    })
}
// 显示被采纳的回答
function showAnswer(){
    axios({
        method: 'POST',
        url: baseURL + '/post/view/' + localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response) => {
        // 获取数据
        let result = response.data;
        let topic = result.data.post;
        let comment = topic.comment;
        let username = comment.user;
        let content = comment.content;
        let createTime = comment.createTime;
        let showTime = createTime.slice(0, createTime.indexOf('T'));
        let picture = comment.picture;
        let headPic = baseURL+'/image/' + picture;

        let topicContainer = document.getElementById("topicContainer");
        let chosenComment = document.createElement("div");
        chosenComment.innerHTML = `<div class="tt-item">
                <div class="tt-single-topic">
                    <div class="tt-item-header pt-noborder">
                        <div class="tt-item-info info-top">
                            <div class="tt-avatar-icon" onclick="toSingleCenter()">
                                <div class="headPicContainer">
                                    <img class="headPic" src="` + headPic + `"/>
                                </div>
                            </div>
                            <div class="tt-avatar-title">
                                <a href="#">`+username+`</a>
                            </div>
                            <!--回复时间-->
            <a href="#" class="tt-info-time">
            <i class="tt-icon"><svg><use xlink:href="#icon-time"></use></svg></i>`+showTime+`
            </a>
            </div>
            </div>
            <div class="tt-item-description">`+content+`</div>
            <div class="tt-item-info info-bottom">
                <div class="col-separator"></div>
                <a href="javascript:;" class="btn btn-secondary" style="display: ">已采纳</a>
            </div>
            </div>
            </div>`;
        topicContainer.appendChild(chosenComment);


    }).catch(()=>{
        console.log("error");
    })
}
