
class UserDataInputs extends DataInputs{
    constructor() {
        super();
        this.element_input_number = document.getElementById('input-number');
        this.element_input_idcard = document.getElementById('input-idcard');
        this.element_input_field = document.getElementById('input-field');
        if(locator.type === 0){
            this.element_input_number.placeholder = '学号';
            this.element_input_field.placeholder = '所在班级';
        } else {
            this.element_input_number.placeholder = '工号';
            this.element_input_field.placeholder = '所在学院';
        }

        this.element_first_date = document.getElementById('first-date');
        this.element_last_date = document.getElementById('last-date');
        this.element_label_adminType = document.getElementById('label-adminType');
        this.element_label_passport = document.getElementById('label-passport');
        this.element_input_adminType = document.getElementById('input-adminType');
        this.element_input_passport = document.getElementById('input-passport');
        if(locator.type === 2){
            this.element_label_adminType.style.display = 'inline';
            this.element_label_passport.style.display = 'inline';
        }

        this.element_first_date.innerHTML = locator.first_date;
        this.element_last_date.value = locator.last_date;
    }

    //TODO 添加错误提示
    on_submit_click() {
        // let id = table_adapter_user.data.reduce((total, item) => {
        //    return (item.key > total ? item.key : total);
        // },0) + 1;
        // let insert_item = {id: id, name: this.element_input_name.value};
        // table_adapter_user.add(insert_item);
        // return true;
        if(locator.type === 0 || locator.type === 1 || locator.type === 2){
            //let adminType = 0;
            let url;
            if(locator.type === 2){
                url = `/api/user?action=add&type=${locator.type}&name=${this.element_input_name.value}&number=${this.element_input_number.value}&idCard=${this.element_input_idcard.value}&field=${this.element_input_field.value}&adminType=${this.element_input_adminType.value}&passport=${this.element_input_passport.value}`;
            } else {
                url =`/api/user?action=add&type=${locator.type}&name=${this.element_input_name.value}&number=${this.element_input_number.value}&idCard=${this.element_input_idcard.value}&field=${this.element_input_field.value}`;
            }

            api_fetch(url, (status, o) => {
                if(status === 200) {
                    //执行成功
                    table_adapter_user.init_data();
                    this.set_add_open(false);
                } else {
                    this.show_msg(`添加用户异常${o.msg}`, "error");
                }
            });
        }

        return false;
    }
    //TODO 添加错误提示
    on_delete_click() {
        if(locator.type === 0 || locator.type === 1 || locator.type === 2){
            api_fetch(`/api/user?action=delete&ids=${table_adapter_user.checked_items().map((item) => {
                return item.id;
            })}`, (status, o) => {
                if(status === 200){
                    //执行成功
                    table_adapter_user.init_data();
                    this.set_delete_state(false);
                }
            });

        }

        return true;
    }

    init() {
        super.init();
        this.element_input_number.addEventListener('keyup', ()=>{
            this.check_input_name();
        });
        this.element_input_field.addEventListener('keyup', ()=>{
            this.check_input_name();
        });
        this.element_input_idcard.addEventListener('keyup', ()=>{
           this.check_input_name();
        });
        this.element_last_date.addEventListener('change', () => {
            locator.last_date = this.element_last_date.value;
            window.location.href = locator.user_url();
        })
    }

    check_input_name() {
        if(this.element_input_name.value === '' ||
        this.element_input_number.value === '' ||
        this.element_input_idcard.value === '' ){
            this.element_button_submit.disabled = 'disabled';
        } else {
            this.element_button_submit.disabled = undefined;
            this.show_msg('');
        }
    }
}


class UserTableAdapter extends TableAdapter{
    constructor() {
        super(document.getElementById('data-tbody'));
        this.init();
    }

    init(){
        //根据类别进行个性化修改
        this.element_table_header_row = document.getElementById('table-user-header-row');
        if(locator.type === 2){
            //删除最后一个元素
            this.element_table_header_row.children[6].remove();
            this.element_table_header_row.children[5].innerHTML = '密码';
        }

    }

    create_element(item) {
        // <tr>
        //     <td><label><input type="checkbox"></label></td>
        //     <td><label><input type="text" value="信息工程学院"></label></td>
        //     <td>45 / No Initialized</td>
        //     <td>20 / No Initialized</td>
        // </tr>
        let elementString = `<tr>
            <td><label><input type="checkbox"></label></td>
            <td><label><input type="text" value="${item.value.name}"/></label></td>
            <td><label><input type="text" value="${item.value.number}"></label></td>
            <td><label><input type="text" value="${item.value.idCard}"></label></td>
            <td class="info-path">${this._get_path(item.value)}<span class="info-change">点击修改</span></td>
            <td class="info-code">${this._get_code_span(item.value)}</td>
            <td>${this._get_summary(item.value)}</td>
        </tr>`;


        let element = parseElement(elementString, 'tbody');
        let checkbox = element.querySelector('input[type=checkbox]');
        if(item.value.adminType === 2){
            checkbox.disabled = 'disabled';
        }
        if(adminType !== 2){
            checkbox.disabled = 'disabled';
        }
        if(item.checked){
            checkbox.checked = 'checked';
            this.check(element, true);
        }

        if((item.value.type === 2 && item.value.adminType === 2) || adminType !== 2){//系统管理员
            let info_path = element.querySelector('.info-path');
            info_path.innerHTML = `<td class="info-path">${this._get_path(item.value)}</td>`
        } else {
            let span_click = element.querySelector('.info-change');
            span_click.addEventListener('click', ()=>{
                //console.log(`onclick${item}`);
                let info_path = element.querySelector('.info-path');
                let replace_input = parseElement(`<input type="text" value="${this.get_item_field_value(item)}">`);
                replace_input.addEventListener('blur', () => {
                    this.submit_update_info(element, item, 3);
                });
                info_path.innerHTML = '';
                info_path.appendChild(replace_input);
                replace_input.focus();
            });
        }

        let input_texts = element.querySelectorAll('input[type=text]');
        if(item.value.adminType === 2){
            input_texts.forEach((item2) => {
                item2.disabled = 'disabled';
            });
        } else {
            if(adminType === 2){
                input_texts.forEach((item2,index) => {
                    item2.addEventListener('blur', () => {
                        this.submit_update_info(item2, item, index);
                    })
                });
            }else{
                input_texts.forEach((item2) =>{
                    item2.disabled = 'disabled';
                })
            }

        }


        if(locator.type === 2){
            let info_code = element.querySelector('.info-code');
            if(item.value.adminType === 2 || adminType !== 2){
                info_code.innerHTML = '不能修改数据';
            } else {
                let input_password = parseElement(`<input type="text" placeholder="输入新的密码">`);
                info_code.innerHTML = '';
                info_code.appendChild(input_password);
                input_password.addEventListener('blur', () => {
                    this.submit_update_info(input_password, item, 4);
                });
            }

        }


        // let input = element.querySelector('input[type=text]');
        // input.addEventListener('blur', () => {
        //     let index = findIndex(this.element_table.children, element);
        //     if(input.value !== '' && input.value !== this.data[index].value.name){
        //         api_fetch(`/api/profession?college=${collegeId}&action=update&id=${this.data[index].value.id}&name=${input.value}`, (status, o)=>{
        //             if(status === 200){
        //                 this.init_data();
        //             }
        //             //TODO 添加错误提示
        //         })
        //     } else {
        //         input.value = this.data[index].value.name;
        //     }
        // });

        return [element, checkbox];
    }

    key_mapper(item) {
        return item.id
    }

    check(element, value){
        console.log('element = ' + element + ',checked = ' + value);
        if(value){
            element.classList.add('table-selected');
        } else {
            element.classList.remove('table-selected');
        }
    }

    check_count_changed() {
        console.log('checked_count:' + this.checked_count());
        data_inputs.set_delete_state(this.checked_count() > 0);
    }

    get_item_field_value(item){
        if(locator.type === 0){
            return item.value.path.xclass;
        } else if(locator.type === 1) {
            return item.value.path.college;
        } else {
            if(item.value.adminType === 2){
                return `系统管理员`
            } else if(item.value.adminType === 1){
                return `学校管理员`
            } else {
                let s = `院级管理员.`;
                if(item.value.path === undefined){
                    s += `(NULL)`
                } else {
                    s += `${item.value.path.college}`
                }
                return s;
            }
        }
    }

    submit_update_info(element, item, index){
        console.log(index);
        console.log(item);
        if (adminType !== 2){
            this.init_data();
            return;
        }
        if(index === 3){
            let info_path = element.querySelector('.info-path');
            info_path.innerHTML = `${this._get_path(item.value)}<span class="info-change">点击修改</span>`;
            let span_click = element.querySelector('.info-change');
            span_click.addEventListener('click', ()=>{
                //console.log(`onclick${item}`);
                let info_path = element.querySelector('.info-path');
                let replace_input = parseElement(`<input type="text" value="${this.get_item_field_value(item)}">`);
                replace_input.addEventListener('blur', () => {
                    this.submit_update_info(replace_input, item, 3);
                });
                info_path.innerHTML = '';
                info_path.appendChild(replace_input);
                replace_input.focus();
            });
        } else if(index === 4){

        }

        let id = item.value.id;
        let number = item.value.number;
        let name = item.value.name;
        let idCard = item.value.idCard;
        let field = this.get_item_field_value(item);
        if(index === 0){
            name = element.value;
        } else if(index === 1) {
            number = element.value;
        } else if(index === 2){
            idCard = element.value;
        } else if(index === 3){
            field = element.value;
        }

        if(locator.type === 0 || locator.type === 1){
            let url = `/api/user?action=update&type=${locator.type}&id=${id}&number=${number}&name=${name}&idCard=${idCard}&field=${field}`;
            api_fetch(url, (status, o) => {
                this.init_data();
            })
        } else {
            if(item.value.type === 2){
                if(item.value.path === undefined || item.value.adminType !== 0){
                    field = null;
                } else {
                    field = item.value.path.college;
                }
            }
            let adminType = item.value.adminType;
            if(index === 3){
                let values = item.value.split('.');
                if(values[0] === '学校管理员'){
                    adminType = 1;
                } else {
                    adminType = 0;
                }
                if(values.length > 1){
                    field = values[2];
                } else {
                    field = null;
                }
            }

            let url = `/api/user?action=update&type=${locator.type}&id=${id}&number=${number}&name=${name}&idCard=${idCard}&`;
            if(field != null){
                url += `field=${field}&`
            }
            url += `adminType=${adminType}&`;
            if(index === 4){
                let passport = element.value;
                if(passport !== ''){
                    url += `passport=${passport}`;
                }
            }
            url = url.substr(0, url.length  -1);
            api_fetch(url, (status, o)=>{
                this.init_data();
            })
        }
    }

    init_data(){
        api_fetch(locator.url('get'), (status, o) => {
            if(status === 200){
                table_adapter_user.replace(o.data);
                //加载分页导航条
                //page_navigator.pageCount = o.data.pageCount;
                //page_navigator.init();
            }
            //TODO 添加错误提示
        })
    }

    _get_path(value){
        if(value.type === 0){
            if(value.path === undefined){
                return `(NULL)`;
            } else if(value.path.error){
                return `[已删除的班级${value.path.xclassId}]`
            } else {
                return `${value.path.college}.${value.path.profession}.${value.path.xclass}`;
            }
        } else if(value.type === 1) {
            if(value.path === undefined){
                return `(NULL)`
            } else if(value.path.error){
                return `[已删除的学院${value.path.collegeId}]`
            } else {
                return `${value.path.college}`;
            }
        } else {
            if(value.adminType === 2){
                return `系统管理员`
            } else if(value.adminType === 1){
                return `学校管理员`
            } else {
                let s = `院级管理员.`;
                if(value.path === undefined){
                    s += `(NULL)`
                } else {
                    s += `${value.path.college}`
                }
                return s;
            }
        }
    }

    _get_code_span(value){
        if(value.result === 0){
            return `<span class="no-true">未申领</span>`;
        } else if(value.result === 1){
            return `<span class="green-true">√</span>`;
        } else if(value.result === 2){
            return `<span class="yellow-true">${value.remainDays}</span>`;
        } else {
            return `<span class="red-true">${value.remainDays}</span>`;
        }
    }

    _get_summary(value){
        let s = '';
        if(value['summary'] !== undefined){
            value['summary'].forEach((item) => {
                s += `<span class="${this._get_span_class(item)}">&nbsp;&nbsp;</span>&nbsp;`
            });
        }
        return s;
    }

    _get_span_class(i){
        if(i === 0){
            return `no-true`;
        } else if(i === 1){
            return `green-true`;
        } else if(i === 2){
            return `yellow-true`;
        } else {
            return `red-true`;
        }
    }
}

let data_inputs = new UserDataInputs();
data_inputs.init();
let table_adapter_user = new UserTableAdapter();
table_adapter_user.init_data();
//let page_navigator = new UserPageNavigator();
//page_navigator.locator = locator;
let page_tab = new PageTab();
page_tab.init();

//table_adapter_user.replace([{id:0,name:'计算机科学与技术学院'}]);