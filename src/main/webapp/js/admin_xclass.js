
class XclassDataInputs extends DataInputs{
    //TODO 添加错误提示
    on_submit_click() {
        // let id = table_adapter_user.data.reduce((total, item) => {
        //    return (item.key > total ? item.key : total);
        // },0) + 1;
        // let insert_item = {id: id, name: this.element_input_name.value};
        // table_adapter_user.add(insert_item);
        // return true;
        api_fetch(`/api/xclass?profession=${professionId}&action=add&name=${this.element_input_name.value}`, (status, o) => {
            if(status === 200) {
                //执行成功
                table_adapter_xclass.init_data();
                this.set_add_open(false);
            } else {
                this.element_input_name.value = '';
                this.show_msg(`插入异常，${o.msg}`, "error");
                this.check_input_name();
            }
        });
        return false;
    }
    //TODO 添加错误提示
    on_delete_click() {
        api_fetch(`/api/xclass?action=delete&profession=${professionId}&ids=${table_adapter_xclass.checked_items().map((item) => {
            return item.id;
        })}`, (status, o) => {
            if(status === 200){
                //执行成功
                table_adapter_xclass.init_data();
                this.set_delete_state(false);
            }
        });

        return true;
    }
}
class XclassTableAdapter extends TableAdapter{

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
            <td><a href="${get_url(`/admin/user?scope=xclass&tag=${item.value.id}`)}">查看</a></td>
            <td><label><input type="text" value="${item.value.name}"></label></td>
            <td>
                <span class="green-${item.value.studentsSummary.green !== 0}">${item.value.studentsSummary.green}</span>
                <span class="yellow-${item.value.studentsSummary.yellow !== 0}">${item.value.studentsSummary.yellow}</span>
                <span class="red-${item.value.studentsSummary.red !== 0}">${item.value.studentsSummary.red}</span>
                <span class="no-${item.value.studentsSummary.no !== 0}">${item.value.studentsSummary.no}</span>
            </td>
        </tr>`;

        let element = parseElement(elementString, 'tbody');
        let checkbox = element.querySelector('input[type=checkbox]');

        if(item.checked){
            checkbox.checked = 'checked';
            this.check(element, true);
        }

        let input = element.querySelector('input[type=text]');
        input.addEventListener('blur', () => {
            let index = findIndex(this.element_table.children, element);
            if(input.value !== '' && input.value !== this.data[index].value.name){
                api_fetch(`/api/xclass?profession=${professionId}&action=update&id=${this.data[index].value.id}&name=${input.value}`, (status, o)=>{
                    if(status === 200){
                        this.init_data();
                    }
                    //TODO 添加错误提示
                })
            } else {
                input.value = this.data[index].value.name;
            }
        });

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
        api_fetch(`/api/xclass?profession=${professionId}&action=get`, (status, o) => {
            if(status === 200){
                table_adapter_xclass.replace(o.data);
            }
            //TODO 添加错误提示
        })
    }
}

let data_inputs = new XclassDataInputs();
data_inputs.init();
let table_adapter_xclass = new XclassTableAdapter();
table_adapter_xclass.init_data();
let page_tab = new PageTab();
page_tab.init();

//table_adapter_user.replace([{id:0,name:'计算机科学与技术学院'}]);