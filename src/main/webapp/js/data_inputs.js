class DataInputs {
    element_msg;
    element_button_add;
    element_button_delete;
    element_button_submit;
    element_button_cancel;
    element_div_data_inserted;
    element_input_name;
    add_open = false;

    constructor() {
        this.element_msg = document.getElementById('msg');
        this.element_button_add = document.getElementById('button-add');
        this.element_button_delete = document.getElementById('button-delete');
        this.element_button_submit = document.getElementById('button-submit');
        this.element_button_cancel = document.getElementById('button-cancel');
        this.element_div_data_inserted = document.getElementById('div-data-inserted');
        this.element_input_name = document.getElementById('input-name');

        this.element_button_add.addEventListener('click', () => {
            this.set_add_open(true);
        });
        this.element_button_cancel.addEventListener('click',() => {
            this.set_add_open(false);
        });
        this.element_input_name.addEventListener('keyup',() => {
            this.check_input_name();
        });
        this.element_button_submit.addEventListener('click',() => {
            if(this.on_submit_click()){
                this.set_add_open(false)
            }
        });
        this.element_button_delete.addEventListener('click',() => {
            if (this.on_delete_click()){
                this.set_delete_state(false);
            }
        });

    }

    init(){
        this.check_input_name();
        this.element_button_delete.disabled = 'disabled';
        this.show_msg();
        this.set_add_open(false);
    }

    check_input_name(){
        if(this.element_input_name.value === ''){
            this.element_button_submit.disabled = 'disabled';
        } else {
            this.element_button_submit.disabled = undefined;
        }
    }
    set_add_open(value){
        this.add_open = value;
        if(value){
            this.element_div_data_inserted.style.display = 'block';
            this.element_button_add.disabled = 'disabled';
        } else {
            this.element_div_data_inserted.style.display = 'none';
            this.element_button_add.disabled = undefined;
        }
    }
    get_add_open(){
        return this.add_open;
    }
    show_msg(msg, type){
        if(msg === undefined || msg.empty()){
            this.element_msg.style.display = 'none';
        } else {
            this.element_msg.innerHTML = msg;
            if(type === 'info'){
                this.element_msg.classList.add('msg-info');
                this.element_msg.classList.remove('msg-error');
            } else {
                this.element_msg.classList.remove('msg-info');
                this.element_msg.classList.add('msg-error');
            }
        }
    }
    set_delete_state(state){
        if(state === true){
            this.element_button_delete.disabled = undefined
        } else {
            this.element_button_delete.disabled = 'disabled';
        }
    }

    on_delete_click(){}
    on_submit_click(){}
}