
class UserDateInputs extends DataInputs{
    //TODO 添加错误提示
    on_submit_click() {
        // let id = table_adapter_user.data.reduce((total, item) => {
        //    return (item.key > total ? item.key : total);
        // },0) + 1;
        // let insert_item = {id: id, name: this.element_input_name.value};
        // table_adapter_user.add(insert_item);
        // return true;
        api_fetch(`/api/college?action=add&name=${this.element_input_name.value}`, (status, o) => {
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
        api_fetch(`/api/college?action=delete&ids=${table_adapter_user.checked_items().map((item) => {
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
            <td><label><a href="/admin/college?page=profession&college=${item.value.id}">查看</a></label></td>
            <td><label><input type="text" value="${item.value.name}"></label></td>
            <td>45 / No Initialized</td>
            <td>20 / No Initialized</td>
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
                api_fetch(`/api/college?action=update&id=${this.data[index].value.id}&name=${input.value}`, (status, o)=>{
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
        api_fetch('/api/college?action=get', (status, o) => {
            if(status === 200){
                table_adapter_user.replace(o.data);
            }
            //TODO 添加错误提示
        })
    }
}

let data_inputs = new UserDateInputs();
data_inputs.init();
let table_adapter_user = new UserTableAdapter();
table_adapter_user.init_data();

//table_adapter_user.replace([{id:0,name:'计算机科学与技术学院'}]);