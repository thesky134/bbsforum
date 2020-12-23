// 获取帖子分类列表
function getCategories(){
    axios({
        method: 'POST',
        url: baseURL+'/category/all',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if(code === 0){
            let categoriesList = document.getElementById("selectTopicCategories");
            let categories = result.data.categorys;
            for(let i=0; i<categories.length;i++){
                let id = categories[i].id;
                let name = categories[i].name;
                let category = document.createElement("option");
                let categoryName = document.createTextNode(name);
                category.appendChild(categoryName);
                category.setAttribute("name", "category");
                category.value = id;
                categoriesList.appendChild(category);
            }
            showTopic();
        }else {
            alert(message);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 如果有，则展示帖子内容
function showTopic(){
    let rid = localStorage.rid;
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+rid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let topic = result.data.post;
        let title = topic.title;
        let category = topic.category;
        let content = topic.content
        let reward = topic.reward;
        let isHidden = topic.hidden;
        let categories = document.getElementsByName("category");
        let hidden = document.getElementById("hidden");
        for (let i=0;i<categories.length;i++){
            console.log(categories[i])
            console.log(categories[i].innerText)
            if (category === categories[i].innerText){
                console.log(category)
                document.getElementById("selectTopicCategories").selectedIndex = i;
            }
        }
        if (category === "积分悬赏"){
            showReward();
            let rewardScore = document.getElementById("inputTopicReward");
            rewardScore.value = reward;
        }
        if (isHidden){
            hidden.selectedIndex = 1;
        }else {
            hidden.selectedIndex = 0;
        }
        document.getElementById("inputTopicTitle").value = title;
        document.getElementById("inputTopicContent").value = content;
    }).catch(()=>{
        console.log("error");
    })
}
// 发送帖子数据给接口
function createTopic(){
    console.log("createTopic()函数被调用了")
    let selectedCategoryIndex = document.getElementById("selectTopicCategories").selectedIndex;
    let selectedHiddenIndex = document.getElementById("hidden").selectedIndex;
    let isHidden = document.getElementById("hidden")[selectedHiddenIndex].value;
    let title = document.getElementById("inputTopicTitle").value;
    let category = document.getElementById("selectTopicCategories").options[selectedCategoryIndex].text;
    let content = document.getElementById("inputTopicContent").value;
    let hidden = false;
    let reward = 0;
    if(isHidden === "1"){
        hidden = true;
    }
    if(category === "积分悬赏"){
        reward = document.getElementById("inputTopicReward").value;
    }
    console.log("createTopic()函数被调用了")
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/add',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            title: title,
            category: category,
            content: content,
            hidden: hidden,
            reward: reward
        }
    }).then((response)=>{
        console.log(response.data);
        location.href = "index.html";
    }).catch(()=>{
        console.log("error");
    })
}
// 修改帖子
function reviseTopic(){
    let rid = localStorage.rid;
    let selectedCategoryIndex = document.getElementById("selectTopicCategories").selectedIndex;
    let selectedHiddenIndex = document.getElementById("hidden").selectedIndex;
    let isHidden = document.getElementById("hidden")[selectedHiddenIndex].value;
    let title = document.getElementById("inputTopicTitle").value;
    let category = document.getElementById("selectTopicCategories").options[selectedCategoryIndex].text;
    let content = document.getElementById("inputTopicContent").value;
    let hidden = false;
    let reward = 0;
    if(isHidden === "1"){
        hidden = true;
    }
    if(category === "积分悬赏"){
        reward = document.getElementById("inputTopicReward").value;
    }
    axios({
        method: 'POST',
        url: baseURL + '/post/manage/revise',
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            title: title,
            category: category,
            content: content,
            hidden: hidden,
            reward: reward,
            id: rid
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        let message = result.message;
        if (code === 0){
            alert(message);
        }else {
            alert(message);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 显示积分悬赏
function showReward(){
    console.log("showReward()函数被调用了")
    let container = document.getElementById("createTopic");
    let selectedCategoryIndex = document.getElementById("selectTopicCategories").selectedIndex;
    let category = document.getElementById("selectTopicCategories").options[selectedCategoryIndex].text;
    let existReward = document.getElementById("inputTopicReward");
    if(category === "积分悬赏"){
        if(existReward === null){
            let showreward = document.createElement("div");
            showreward.innerHTML = `<div class="row" id="showReward">
											<div class="col-md-4">
												<div class="form-group">
													<label for="inputTopicReward">悬赏积分设置</label>
													<input type="text" name="name" class="form-control" id="inputTopicReward" placeholder="输入你悬赏的积分">
												</div>
											</div>
										</div>`;
            container.appendChild(showreward);
        }
    }else{
        if(existReward !== null){
            let showReward = document.getElementById("showReward");
            let father = showReward.parentNode;
            let grandfather = father.parentNode;
            grandfather.removeChild(father);
        }
    }
}

// 检验帖子标题
function checkTopicTitle() {
    let title = document.getElementById("inputTopicTitle").value;
    let showTitleMessage = document.getElementById("showTitleMessage");
    // 检查正则
    let re_title = /^[^\s]{3,20}$/;
    if (re_title.test(title)) {
        cleanTitleMessage();
    } else {
        showTitleMessage.style.display = "";
    }
}
// 检验帖子正文
function checkTopicContent() {
    let content = document.getElementById("inputTopicContent").value;
    let showContentMessage = document.getElementById("showContentMessage");
    // 检查正则
    let re_content = /^[^\s]{1,3000}$/;
    if (re_content.test(content)) {
        cleanContentMessage();
    } else {
        showContentMessage.style.display = "";
    }
}
// 检验积分
function checkReward(){
    let reward = document.getElementById("inputTopicReward").value;
    let showRewardMessage = document.getElementById("showRewardMessage");
    // 检查正则
    let re_reward = /^[0-9]{1,20}$/;
    if (re_reward.test(reward)) {
        cleanRewardMessage();
    } else {
        showRewardMessage.style.display = "";
    }
}
// 清空提示
function cleanTitleMessage(){
    let showTitleMessage = document.getElementById("showTitleMessage");
    showTitleMessage.style.display = "none";
}
function cleanContentMessage(){
    let showContentMessage = document.getElementById("showContentMessage");
    showContentMessage.style.display = "none";
}
function cleanRewardMessage(){
    let showRewardMessage = document.getElementById("showRewardMessage");
    showRewardMessage.style.display = "none";
}