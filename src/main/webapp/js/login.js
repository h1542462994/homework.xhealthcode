class LoginState {
    type_labels;
    input_number_row;
    input_number;
    input_number_label;
    input_name_row;
    input_name;
    input_name_label;
    input_passport_row;
    input_passport;
    input_passport_label;
    msg_error = '';
    msg_error_div;
    type = 0;

    constructor() {
        this.type_labels = document.getElementById('input-type-row').children;
        this.input_number_row = document.getElementById('input-number-row');
        this.input_name_row = document.getElementById('input-name-row');
        this.input_passport_row = document.getElementById('input-passport-row');
        this.input_number_label = this.input_number_row.children[0];
        this.input_number = this.input_number_row.children[1];
        this.input_name_label = this.input_name_row.children[0];
        this.input_name = this.input_name_row.children[1];
        this.input_passport_label = this.input_passport_row.children[0];
        this.input_passport = this.input_passport_row.children[1];
        this.msg_error_div = document.getElementById('msg-error');
        for(let i = 0; i < this.type_labels.length; ++i){
            this.type_labels[i].addEventListener('click', ()=> {
                this.to_type(i);
            })
        }
        this.render();
    }

    render(){
        for(let i = 0; i < this.type_labels.length; ++i){
            if(i === this.type){
                this.type_labels[i].classList.add('selected');
            } else {
                this.type_labels[i].classList.remove('selected');
            }
        }

        if(this.type === 0){
            this.input_number_label.innerHTML = '学号';
            this.input_number.placeholder = '请输入学号';
        } else {
            this.input_number_label.innerHTML = '工号';
            this.input_number.placeholder = '请输入工号';
        }

        if(this.type === 2){
            this.input_name_row.style.display = 'none';
            this.input_passport_label.innerHTML = '密码';
            this.input_passport.placeholder = '密码由系统管理员设置';
        } else {
            this.input_name_row.style.display = 'flex';
            this.input_passport_label.innerHTML = '通行证';
            this.input_passport.placeholder = '通行证为身份证后6位';
        }
    }

    show_msg(msg){
        this.msg_error = msg;
        this.msg_error_div.innerHTML = this.msg_error;
        if(this.msg_error === '' || this.msg_error === undefined){
            this.msg_error_div.hidden = 'hidden';
        } else {
            this.msg_error_div.hidden = undefined;
        }
    }

    to_type(type){
        this.type = type;
        this.render();
    }


}

let loginState = new LoginState();