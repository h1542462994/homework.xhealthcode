class PageNavigator {
    constructor() {
        this.element_page_navigator = document.getElementById('page-navigator');
        this.element_navigator_second = document.querySelector('.navigator-second');
    }

    element_page_navigator;
    element_navigator_second;
    locator;
    pageCount;

    init(){
        this.element_page_navigator.innerHTML = ''; //先清空所有的东西
        let index = locator.pageIndex;
        // 第一行
        if(index === 0){
            this.element_page_navigator.appendChild(parseElement(`<span class='disabled'>前一页</span>`))
        } else {
            this.element_page_navigator.appendChild(parseElement(`<span><a href='${this.get_url(index - 1)}'>前一页</a></span>`))
        }
        // 中间的元素
        let start_index = index - 2 < 0 ? 0: index - 2;
        if(index - 2 > 0){
            this.element_page_navigator.appendChild(parseElement(`<span>...</span>`))
        }
        let end_index = start_index + 5 > this.pageCount? this.pageCount: start_index + 5;
        for(let i = start_index; i < end_index; ++i){
            if(i === index){//当前行
                this.element_page_navigator.appendChild(parseElement(`<span><a class='selected' href='${this.get_url(i)}'>${i + 1}</a></span>`));
            } else {
                this.element_page_navigator.appendChild(parseElement(`<span><a href='${this.get_url(i)}'>${i + 1}</a></span>`));
            }
        }
        if(start_index + 5 < this.pageCount){
            this.element_page_navigator.appendChild(parseElement(`<span>...</span>`))
        }
        if(index === this.pageCount - 1){
            this.element_page_navigator.appendChild(parseElement(`<span class='disabled'>后一页</span>`))
        }
    }


    get_url(pageIndex){
        return get_url(`/admin/user?type=${locator.type}&pageIndex=${pageIndex}&scope=${locator.scope}&tag=${locator.type}`);
    }
}