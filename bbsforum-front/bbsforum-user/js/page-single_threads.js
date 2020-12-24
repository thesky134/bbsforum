// 获取下一页位置
function getPosition(){
    let position = parseInt(document.getElementById("nextPage").value);
    position ++;
    document.getElementById("nextPage").value = position;
    return position;
}
// 获取一页帖子
function getTopics(){
    let position = getPosition();
    let url = baseURL+'/user/post/list'
    axios({
        method: 'POST',
        url: url,
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
        let topics = result.data.posts;
        if (topics.length < 15){
            document.getElementById("bottom").style.display = "";
        }
        let topicList = document.getElementById("topicList");
        for(let i=0; i<topics.length; i++) {
            let topic = document.createElement("div");
            topic.id = topics[i].id;
            topic.onclick = function (){
                getTopicId(this.id);
            };
            topic.setAttribute("name", "topic");
            let picture = topics[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let createTime = topics[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let uid = topics[i].userId; // userId
            let pid = topics[i].id  // postId
            let isHidden = topics[i].hidden;
            topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toSingleCenter(this.id)">
                                                <div class="headPicContainer">
                                                    <img class="headPic" src="` + headPic + `"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon" style="display: none" id="topIcon`+pid+`">
                                                    <use xlink:href="#icon-pinned"></use>
                                                </svg>
                                                <svg class="tt-icon" style="display: none" id="excellentIcon`+pid+`">
                                                    <use xlink:href="#icon-locked"></use>
                                                </svg>
                                                <a href="page-single-topic.html">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-value hide-mobile" style="display: none;font-size: small" id="publish`+pid+`">已发布</div>
                                        <div class="tt-col-value hide-mobile" style="display: none;font-size: small" id="private`+pid+`">待发布</div>
                                        <div class="tt-col-category">
                                            <span class="tt-color04 tt-badge" id="category`+pid+`">` + topics[i].category + `</span>
                                        </div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile" id="showTimeDiv`+pid+`">` + showTime + `</div>
                                        <div class="tt-col-category">
                                            <a href="javascript:;" class="btn btn-primary" style="width: 100px" onclick="toReviseTopic(`+topic.id+`)">修改</a>
                                        </div>
                                        <div class="tt-col-category ">
                                            <a href="javascript:;" class="btn btn-secondary" style="width: 100px" onclick="deleteTopic(`+topic.id+`)">删除</a>
                                        </div>`;
            topicList.appendChild(topic);
            let topIcon = document.getElementById("topIcon"+pid.toString());
            let excellentIcon = document.getElementById("excellentIcon"+pid.toString());
            let categorySpan = document.getElementById("category"+pid.toString());
            // 是否置顶
            if (topics[i].top){
                topic.className = "tt-item tt-itemselect";
                topIcon.style.display = "";
            }else {
                topic.className = "tt-item";
            }
            // 是否加精
            if (topics[i].excellent){
                excellentIcon.style.display = "";
            }
            // 是否属于积分悬赏
            if (topics[i].category === "积分悬赏"){
                categorySpan.innerHTML = topics[i].category+" : "+topics[i].reward;
            }
            // 是否发布
            if (isHidden === true){
                document.getElementById("private"+pid.toString()).style.display = "";
            }else {
                document.getElementById("publish"+pid.toString()).style.display = "";
            }
            // 是否修改过，显示修改时间
            if (topics[i].modifyTime !== null){
                let modifyTime = topics[i].modifyTime;
                let reShowTime = modifyTime.slice(0,modifyTime.indexOf('T'));
                document.getElementById("showTimeDiv"+pid.toString()).innerHTML = reShowTime;
            }
        }
    }).catch(()=>{

        console.log("error");
    })
}
// 跳转到回复页
function toSingleReplies(){
    location.href = "./page-single_replies.html";
}
// 跳转到修改帖子
function toReviseTopic(rid){
    location.href = "./page-revise-topic.html";
    localStorage.rid = rid;
}
// 保存当前展示的帖子数
function getTopicsSum(){
    let topics = document.getElementsByName("topic");
    localStorage.topicsSum = topics.length;
}
// 删除原来的第一页帖子
function removeFirstTopics(){
    let topicList = document.getElementById("topicList");
    let topics = document.getElementsByName("topic");
    getTopicsSum();
    for(let i=0; i<localStorage.topicsSum; i++){
        topicList.removeChild(topics[0]);
    }
}
// 获取第一页帖子
function getFirstTopics(){
    let url = baseURL+'/user/post/list'
    axios({
        method: 'POST',
        url: url,
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            userId: localStorage.myUid,
            pageSize: 15,
            position: 1
        }
    }).then((response)=>{
        let result = response.data;
        let topics = result.data.posts;
        console.log(topics);
        let topicList = document.getElementById("topicList");
        for(let i=0; i<topics.length; i++) {
            let topic = document.createElement("div");
            topic.id = topics[i].id;
            topic.onclick = function (){
                getTopicId(this.id);
            };
            topic.setAttribute("name", "topic");
            let picture = topics[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let createTime = topics[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            if (topics[i].top) {
                if (topics[i].excellent) {
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div class="tt-col-avatar">
                                                <div class="headPicContainer">
                                                    <img class="headPic" src="` + headPic + `"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-pinned"></use>
                                                </svg>
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-locked"></use>
                                                </svg>
                                                <a href="page-single-topic.html">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">` + topics[i].category + `</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile">` + showTime + `</div>
                                       <div class="tt-col-category">
                                        <a href="javascript:;" class="btn btn-primary" style="width: 100px" onclick="toReviseTopic(`+topic.id+`)">修改</a>
                                    </div>
                                    <div class="tt-col-category ">
                                        <a href="javascript:;" class="btn btn-secondary" style="width: 100px" onclick="deleteTopic(`+topic.id+`)">删除</a>
                                    </div>`;
                } else {
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div class="tt-col-avatar">
                                            <div class="headPicContainer">
                                                    <img class="headPic" src="` + headPic + `"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-pinned"></use>
                                                </svg>
                                                <a href="page-single-topic.html">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">` + topics[i].category + `</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile">` + showTime + `</div>
                                     <div class="tt-col-category">
                                        <a href="javascript:;" class="btn btn-primary" style="width: 100px" onclick="toReviseTopic(`+topic.id+`)">修改</a>
                                    </div>
                                    <div class="tt-col-category ">
                                        <a href="javascript:;" class="btn btn-secondary" style="width: 100px" onclick="deleteTopic(`+topic.id+`)">删除</a>
                                    </div>`;
                }

            } else {
                if (topics[i].excellent) {
                    topic.className = "tt-item";
                    topic.innerHTML = `<div class="tt-col-avatar">
                                           <div class="headPicContainer">
                                                    <img class="headPic" src="` + headPic + `"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-locked"></use>
                                                </svg>
                                                <a href="page-single-topic.html">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">` + topics[i].category + `</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile">` + showTime + `</div>
                                        <div class="tt-col-category">
                                        <a href="javascript:;" class="btn btn-primary" style="width: 100px" onclick="toReviseTopic(`+topic.id+`)">修改</a>
                                    </div>
                                    <div class="tt-col-category ">
                                        <a href="javascript:;" class="btn btn-secondary" style="width: 100px" onclick="deleteTopic(`+topic.id+`)">删除</a>
                                    </div>`;
                } else {
                    topic.className = "tt-item";
                    topic.innerHTML = `<div class="tt-col-avatar">
                                            <div class="headPicContainer">
                                                    <img class="headPic" src="` + headPic + `"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <a href="page-single-topic.html">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">` + topics[i].category + `</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile">` + showTime + `</div>
                                        <div class="tt-col-category">
                                        <a href="javascript:;" class="btn btn-primary" style="width: 100px" onclick="toReviseTopic(`+topic.id+`)">修改</a>
                                    </div>
                                    <div class="tt-col-category ">
                                        <a href="javascript:;" class="btn btn-secondary" style="width: 100px" onclick="deleteTopic(`+topic.id+`)">删除</a>
                                    </div>`;
                }
            }
            topicList.appendChild(topic);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 删除帖子
function deleteTopic(did){
    axios({
        method: 'POST',
        url: baseURL + '/post/manage/delete/'+did,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if (code === 0){
            alert(message);
            removeFirstTopics();
            getFirstTopics();
        }else {
            alert(message);
        }
    }).catch(()=>{
        console.log("error");
    })
}