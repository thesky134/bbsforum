// 获取一页帖子
function getTopics(){
    console.log("getTopics()函数被调用了")
    let positon = getPosition();
    axios({
        method: 'POST',
        url: baseURL+'/post/all/list',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            pageSize: 15,
            position: positon
        }
    }).then((response)=>{
        let result = response.data;
        let topics = result.data.posts;
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
            let modifyTime = topics[i].modifyTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let uid = topics[i].userId;
            let pid = topics[i].id
            if (topics[i].top&&!topics[i].hidden) {
                if (topics[i].excellent) {
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
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
                                                <a href="#">` + topics[i].title + `</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">` + topics[i].category + `</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">` + topics[i].commentSum + `</div>
                                        <div class="tt-col-value hide-mobile">` + topics[i].visitSum + `</div>
                                        <div class="tt-col-category hide-mobile" id="`+pid+`showTimeDiv">` + showTime + `</div>`;
                } else {
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
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
                                        <div class="tt-col-category hide-mobile" id="`+pid+`showTimeDiv">` + showTime + `</div>`;
                }
            } else {
                if (topics[i].excellent&&!topics[i].hidden) {
                    topic.className = "tt-item";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
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
                                        <div class="tt-col-category hide-mobile" id="`+pid+`showTimeDiv">` + showTime + `</div>`;
                } else {
                    if (!topics[i].hidden){
                        topic.className = "tt-item";
                        topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
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
                                        <div class="tt-col-category hide-mobile" id="`+pid+`showTimeDiv">` + showTime + `</div>`;
                    }
                }
            }
            topicList.appendChild(topic);
        }
    }).catch(()=>{
        console.log("error");
    })
}