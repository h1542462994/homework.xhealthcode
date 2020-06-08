class PageTab {
    constructor() {
        this.element_navigator_second = document.querySelector('.navigator-second');
        this.element_navigator_first = document.querySelector('.navigator-first');
    }

    init(){
        this.element_navigator_second.innerHTML = '';
        //用于生成右上角的导航切换按钮
        //if(locator.get_scope() === 'all'){ //表示当前属于all的定位域
        if(locator.get_domain_url() !== undefined){
            this.element_navigator_second.appendChild(parseElement(locator.get_domain_url()));
        }
        if (locator.get_student_url() !== undefined){
            this.element_navigator_second.appendChild(parseElement(locator.get_student_url()));
        }
        if(locator.get_teacher_url() !== undefined){
            this.element_navigator_second.appendChild(parseElement(locator.get_teacher_url()));
        }
        if(locator.get_admin_url() !== undefined){
            this.element_navigator_second.appendChild(parseElement(locator.get_admin_url()));
        }
        //}

        this.element_navigator_first.innerHTML = '';

        this.element_navigator_first.appendChild(parseElement(locator.get_all_url()));
        if(locator.get_college_url() !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(locator.get_college_url()));
        }
        if(locator.get_profession_url() !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(locator.get_profession_url()));
        }
        if(locator.get_xclass_url() !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(locator.get_xclass_url()));
        }
    }
}