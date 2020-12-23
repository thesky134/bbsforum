// 获取一页帖子
function getTopics(){
    let position = getPosition();
    axios({
        method: 'POST',
        url: baseURL+'/post/all/list',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            pageSize: 15,
            position: position
        }
    }).then((response)=>{
        let result = response.data;
        let topics = result.data.posts;
        if (topics.length <= 10){
            document.getElementById("bottom").style.display = "";
        }
        let topicList = document.getElementById("topicList");
        for(let i=0; i<topics.length; i++) {
            let topic = document.createElement("div");
            topic.id = topics[i].id;
            topic.onclick = function (){
                getTopicId(this.id);
            };
            let picture = topics[i].picture;
            let headPic = baseURL+'/image/' + picture;
            let createTime = topics[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let isHidden = topics[i].hidden;
            let uid = topics[i].userId; // userId
            let pid = topics[i].id  // postId
            if (!isHidden){
                topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
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
                                        <div class="tt-col-category">
                                            <span class="tt-color04 tt-badge" id="category`+pid+`">` + topics[i].category + `</span>
                                        </div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile" id="showTimeDiv`+pid+`">` + showTime + `</div>`;
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
                // 是否修改过，显示修改时间
                if (topics[i].modifyTime !== null){
                    let modifyTime = topics[i].modifyTime;
                    let reShowTime = modifyTime.slice(0,modifyTime.indexOf('T'));
                    document.getElementById("showTimeDiv"+pid.toString()).innerHTML = reShowTime;
                }
            }
        }
        checkState();
    }).catch(()=>{
        console.log("error");
    })
}