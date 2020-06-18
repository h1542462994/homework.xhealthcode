class PageTab {
    constructor() {
        this.element_navigator_second = document.querySelector('.navigator-second');
        this.element_navigator_first = document.querySelector('.navigator-first');
    }

    /**
     * with dependency ::locator
     */
    check_url(){
        if(locator.get_scope() === 'all' && locator.type !== 2){ //排除管理员级别
            return `/api/college?action=get`;
        }
        if(locator.type === 1 || locator.type === 2){
            return undefined;
        } else {
            if(locator.get_scope() === 'college'){
                return `/api/profession?action=get&college=${locator.collegeId}`;
            } else if(locator.get_scope() === 'profession' && locator.type === 0){
                return `/api/xclass?action=get&profession=${locator.professionId}`;
            }
            return undefined;
        }
    }

    init(){
        //if(locator.get_scope() === 'all'){
        let url = this.check_url();
        if(url === undefined){
            this.create(undefined)
        } else {
            api_fetch(url, (status, o)=>{
                if(status === 200){
                    this.create(o.data);
                }
            })
        }
        //}
    }

    create(data){
        this.element_navigator_second.innerHTML = '';
        //用于生成右上角的导航切换按钮
        //if(locator.get_scope() === 'all'){ //表示当前属于all的定位域
        //右侧
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

        //左侧
        if (adminType !== 0){
            this.element_navigator_first.appendChild(parseElement(locator.get_all_url()));
        }

        if(locator.get_college_url() !== undefined){
            if (adminType !== 0){
                this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            }

            this.element_navigator_first.appendChild(parseElement(locator.get_college_url()));
        } else if(locator.get_scope() === 'all' && data !== undefined){ //说明有二级目录的出现
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(`<label>
    <select class="select-one">
        <option value="0">请选择</option>
        ${data.reduce((total, item) => {
    return total + `<option value=${item.id}>${item.name}</option>`
}, '')}
    </select>
</label>`));
            let checkbox = this.element_navigator_first.querySelector('.select-one');
            checkbox.addEventListener('change', (event)=>{
                if(checkbox.value !== 0){
                    locator.collegeId = checkbox.value;
                    window.location.href = locator.get_college_scoped();
                }
                //console.log(checkbox.value);
            });
        }


        if(locator.get_profession_url() !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(locator.get_profession_url()));
        } else if(locator.get_scope() === 'college' && data !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(`<label>
    <select class="select-one">
        <option value="0">请选择</option>
        ${data.reduce((total, item) => {
                return total + `<option value=${item.id}>${item.name}</option>`
            }, '')}
    </select>
</label>`));
            let checkbox = this.element_navigator_first.querySelector('.select-one');
            checkbox.addEventListener('change', (event)=>{
                if(checkbox.value !== 0){
                    locator.professionId = checkbox.value;
                    window.location.href = locator.get_profession_scoped();
                }
                //console.log(checkbox.value);
            });
        }


        if(locator.get_xclass_url() !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(locator.get_xclass_url()));
        }else if(locator.get_scope() === 'profession' && data !== undefined){
            this.element_navigator_first.appendChild(parseElement(`<span>/</span>`));
            this.element_navigator_first.appendChild(parseElement(`<label>
    <select class="select-one">
        <option value="0">请选择</option>
        ${data.reduce((total, item) => {
                return total + `<option value=${item.id}>${item.name}</option>`
            }, '')}
    </select>
</label>`));
            let checkbox = this.element_navigator_first.querySelector('.select-one');
            checkbox.addEventListener('change', (event)=>{
                if(checkbox.value !== 0){
                    locator.xclassId = checkbox.value;
                    window.location.href = locator.get_xclass_scoped();
                }
                //console.log(checkbox.value);
            });
        }
    }
}