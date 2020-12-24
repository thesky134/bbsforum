// 获取下一页位置
function getPosition(){
    console.log("getPosition()函数被调用了")
    let position = parseInt(document.getElementById("nextPage").value);
    position ++;
    document.getElementById("nextPage").value = position;
    console.log(position);
    return position;
}
// 获取一页回复
function getComments(){
    console.log("getComments()函数被调用了")
    let position = getPosition();
    axios({
        method: 'POST',
        url: baseURL+'/user/comment/list/',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            userId: localStorage.myUid,
            pageSize: 15,
            position: position
        }
    }).then((response)=>{
        let result = response.data;
        let comments = result.data.comments;
        if (comments.length < 15){
            document.getElementById("bottom").style.display = "";
        }
        let commentList = document.getElementById("commentList");
        console.log(commentList);
        for (let i=0; i<comments.length;i++){
            let picture = comments[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let createTime = comments[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let comment = document.createElement("div");
            comment.className = "tt-item";
            comment.id = comments[i].postId;
            comment.onclick = function (){
                getTopicId(this.id);
            };
            comment.innerHTML = `<!--头像-->
                                 <div class="tt-col-avatar">
                                     <div class="headPicContainer">
                                         <img class="headPic" src="` + headPic + `"/>
                                     </div>
                                 </div>
                                 <div class="tt-col-description">
                                     <!--帖子标题-->
                                     <h6 class="tt-title"><a href="page-single-topic.html">`+comments[i].postTitle+`</a></h6>
                                     <!--我的回复-->
                                     <div class="tt-content">`+comments[i].content+`</div>
                                 </div>
                                 <div class="tt-col-category"></div>
                                 <!--发布日期-->
                                 <div class="tt-col-category hide-mobile">`+showTime+`</div>`;
            commentList.appendChild(comment);
        }
        console.log(comments);
    }).catch(()=>{
        console.log("error");
    })
}
// 跳转到个人帖子页
function toSingleThreads(){
    location.href = "page-single_threads.html";
}
// 跳转到详细帖子页
function toSingleTopic(tid){
    localStorage.tid = tid;
}