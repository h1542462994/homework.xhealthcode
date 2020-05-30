
class UserDateInputs extends DataInputs{
    on_submit_click() {
        let id = table_adapter_user.data.reduce((total, item) => {
           return (item.key > total ? item.key : total);
        },0) + 1;
        let insert_item = {id: id, name: this.element_input_name.value};
        table_adapter_user.add(insert_item);
        return true;
    }
    on_delete_click() {
        table_adapter_user.remove(...table_adapter_user.checked_items());
        return true;
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
        })
    }
}

let data_inputs = new UserDateInputs();
data_inputs.init();
let table_adapter_user = new UserTableAdapter();
table_adapter_user.init_data();

//table_adapter_user.replace([{id:0,name:'计算机科学与技术学院'}]);