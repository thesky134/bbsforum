// 判断点赞的状态
function checkGood(){
    console.log("checkGood()被调用了");
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let topic = result.data.post;
        let isGood = topic.good;
        console.log(isGood);
        if (isGood){  // 如果为true，则为高亮，需取消高亮
            deleteGood();
        }else{  // 否则需要变成高亮
            addGood();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 点赞
function addGood(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/good/add/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        console.log(result);
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 取消点赞
function deleteGood(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/good/delete/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let code = result.code;
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}

// 判断点踩的状态
function checkBad(){
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let topic = result.data.post;
        let isBad = topic.bad;
        if (isBad){  // 如果为true，则为高亮，需取消高亮
            deleteBad();
        }else{  // 否则需要变成高亮
            addBad();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 点踩
function addBad(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/bad/add/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 取消点踩
function deleteBad(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/bad/delete/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let code = result.code;
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}

// 判断喜欢的状态
function checkLike(){
    axios({
        method: 'POST',
        url: baseURL+'/post/view/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let topic = result.data.post;
        let isLike = topic.like;
        if (isLike){  // 如果为true，则为高亮，需取消高亮
            deleteLike();
        }else{  // 否则需要变成高亮
            addLike();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 喜欢
function addLike(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/like/add/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let code = result.code;
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 取消喜欢
function deleteLike(){
    axios({
        method: 'POST',
        url: baseURL+'/post/manage/like/delete/'+localStorage.tid,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=> {
        let result = response.data;
        let code = result.code;
        if(code === 0){
            getAllSum();
        }
    }).catch(()=>{
        console.log("error");
    })
}