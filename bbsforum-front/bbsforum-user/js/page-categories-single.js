// 获取分区的id
function getCategoryId(){
    let cid = localStorage.cid;
    return cid;
}
// 获取下一页位置
function getPosition(){
    console.log("getPosition()函数被调用了")
    let position = parseInt(document.getElementById("nextPage").value);
    position ++;
    document.getElementById("nextPage").value = position;
    return position;
}
// 获取一页帖子 更改分区名称和描述
function getTopics(){
    console.log("getTopics()函数被调用了")
    let positon = getPosition();
    let cid = getCategoryId();
    let url = baseURL+'/post/category/list'
    axios({
        method: 'POST',
        url: url,
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            pageSize: 15,
            position: positon,
            categoryId: cid
        }
    }).then((response)=>{
        let result = response.data;
        let category = result.data.category;
        let topics = result.data.posts;
        // 更改分区名字与介绍
        let categoryHead = document.getElementById("categoryHead");
        let categoryTitle = document.createElement("div");
        categoryTitle.className = "tt-catSingle-title";
        categoryTitle.innerHTML = `<div class="tt-innerwrapper tt-row">
                    <div class="tt-col-left">
                        <ul class="tt-list-badge">
                            <li><a href="#"><span class="tt-color01 tt-badge">`+category.name+`</span></a></li>
                        </ul>
                    </div>
                    <div class="ml-left tt-col-right">
                        <div class="tt-col-item">
                            <h2 class="tt-value">帖子 - `+category.sum+`</h2>
                        </div>
                    </div>
                </div>
                <div class="tt-innerwrapper">`+category.introduction+`</div>`
        categoryHead.appendChild(categoryTitle);
        // 展示帖子
        let topicList = document.getElementById("topicList");
        for(let i=0; i<topics.length; i++){
            let topic = document.createElement("div");
            topic.id = topics[i].id;
            topic.onclick = function (){
                getTopicId(this.id);
            };
            let picture = topics[i].picture;
            let headPic = baseURL+'/image/'+picture;
            let createTime = topics[i].createTime;
            let showTime = createTime.slice(0, createTime.indexOf('T'));
            let uid = topics[i].userId;
            if(topics[i].top){
                if(topics[i].excellent){
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
                                                <div class="headPicContainer">
                                                    <img class="headPic" src="`+headPic+`"/>
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
                                                <a href="page-single-topic.html">`+topics[i].title+`</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">`+topics[i].category+`</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">`+topics[i].commentSum+`</div>
                                        <div class="tt-col-value hide-mobile">`+topics[i].visitSum+`</div>
                                        <div class="tt-col-category hide-mobile">`+showTime+`</div>`;
                }else{
                    topic.className = "tt-item tt-itemselect";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
                                            <div class="headPicContainer">
                                                    <img class="headPic" src="`+headPic+`"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-pinned"></use>
                                                </svg>
                                                <a href="page-single-topic.html">`+topics[i].title+`</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">`+topics[i].category+`</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">`+topics[i].commentSum+`</div>
                                        <div class="tt-col-value hide-mobile">`+topics[i].visitSum+`</div>
                                        <div class="tt-col-category hide-mobile">`+showTime+`</div>`;
                }

            }else{
                if(topics[i].excellent){
                    topic.className = "tt-item";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
                                           <div class="headPicContainer">
                                                    <img class="headPic" src="`+headPic+`"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <svg class="tt-icon">
                                                    <use xlink:href="#icon-locked"></use>
                                                </svg>
                                                <a href="page-single-topic.html">`+topics[i].title+`</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">`+topics[i].category+`</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">`+topics[i].commentSum+`</div>
                                        <div class="tt-col-value hide-mobile">`+topics[i].visitSum+`</div>
                                        <div class="tt-col-category hide-mobile">`+showTime+`</div>`;
                }else {
                    topic.className = "tt-item";
                    topic.innerHTML = `<div id="`+uid+`" class="tt-col-avatar" onclick="toOtherSingleCenter(this.id)">
                                            <div class="headPicContainer">
                                                    <img class="headPic" src="`+headPic+`"/>
                                                </div>
                                        </div>
                                        <div class="tt-col-description">
                                            <h6 class="tt-title">
                                                <a href="page-single-topic.html">`+topics[i].title+`</a>
                                            </h6>
                                        </div>
                                        <div class="tt-col-category"><span class="tt-color04 tt-badge">`+topics[i].category+`</span></div>
                                        <div class="tt-col-value tt-color-select hide-mobile">`+topics[i].commentSum+`</div>
                                        <div class="tt-col-value hide-mobile">`+topics[i].visitSum+`</div>
                                        <div class="tt-col-category hide-mobile">`+showTime+`</div>`;
                }
            }
            topicList.appendChild(topic);
        }
    }).catch(()=>{
        console.log("error");
    })
}