
class UserDataInputs extends DataInputs{
    //TODO 添加错误提示
    on_submit_click() {
        // let id = table_adapter_user.data.reduce((total, item) => {
        //    return (item.key > total ? item.key : total);
        // },0) + 1;
        // let insert_item = {id: id, name: this.element_input_name.value};
        // table_adapter_user.add(insert_item);
        // return true;
        api_fetch(`/api/profession?college=${collegeId}&action=add&name=${this.element_input_name.value}`, (status, o) => {
            if(status === 200) {
                //执行成功
                table_adapter_user.init_data();
                this.set_add_open(false);
            }
        });
        return false;
    }
    //TODO 添加错误提示
    on_delete_click() {
        api_fetch(`/api/profession?college=${collegeId}&action=delete&ids=${table_adapter_user.checked_items().map((item) => {
            return item.id;
        })}`, (status, o) => {
            if(status === 200){
                //执行成功
                table_adapter_user.init_data();
                this.set_delete_state(false);
            }
        });

        return false;
    }
}

class UserPageNavigator extends PageNavigator{

}

class UserTableAdapter extends TableAdapter{
    constructor() {
        super(document.getElementById('data-tbody'));
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
            <td>${item.value.name}</td>
            <td>${item.value.number}</td>
            <td>${item.value.idCard}</td>
            <td>${this._get_path(item.value)}</td>
            <td>${this._get_code_span(item.value)}</td>
            <td>${this._get_summary(item.value)}</td>
        </tr>`;

        let element = parseElement(elementString, 'tbody');
        let checkbox = element.querySelector('input[type=checkbox]');

        if(item.checked){
            checkbox.checked = 'checked';
            this.check(element, true);
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

    init_data(){
        api_fetch(locator.url('get'), (status, o) => {
            if(status === 200){
                table_adapter_user.replace(o.data.data);
                //加载分页导航条
                page_navigator.pageCount = o.data.pageCount;
                page_navigator.init();
            }
            //TODO 添加错误提示
        })
    }

    _get_path(value){
        if(value.type === 0){
            if(value.path === undefined){
                return `(NULL)`;
            } else {
                return `${value.path.college}.${value.path.profession}.${value.path.xclass}`;
            }
        } else if(value.type === 1) {
            if(value.path === undefined){
                return `(NULL)`
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
let page_navigator = new UserPageNavigator();
page_navigator.locator = locator;
let page_tab = new PageTab();
page_tab.init();

//table_adapter_user.replace([{id:0,name:'计算机科学与技术学院'}]);