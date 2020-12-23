// 获取帖子分类 all
function getCategories(){
    let categoriesList = document.getElementById("categoriesList");
    axios({
        method: 'POST',
        url: baseURL+'/category/all',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then((response)=>{
        let result = response.data;
        let categories = result.data.categorys;
        for(let i=0;i<categories.length;i++){
            let category = document.createElement("div");
            category.className = "col-md-6 col-lg-4";
            category.innerHTML = `<div class="tt-item">
                    <div class="tt-item-header">
                        <ul class="tt-list-badge">
                            <li><a href="page-categories-single.html" id="`+categories[i].id+`" onclick="setCategoryId(this.id)">
                            <span class="tt-color01 tt-badge">`+categories[i].name+`</span></a>
                            </li>
                        </ul>
                        <h6 class="tt-title"><a href="#">帖子 - `+categories[i].sum+`</a></h6>
                    </div>
                    <div class="tt-item-layout">
                        <div class="innerwrapper">`+categories[i].introduction+`</div>
                    </div>
                </div>`
            categoriesList.appendChild(category);
        }
    }).catch(()=>{
        console.log("error");
    })
}
// 根据localStorage.cid访问相应数据
function setCategoryId(id){
    let cid = document.getElementById(id).id;
    localStorage.cid = cid;
    console.log(cid);
}