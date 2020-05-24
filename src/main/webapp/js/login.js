window.addEventListener('load', login_initialize);

function login_initialize(){
   let input_is_admin = document.getElementsByName("is_admin");
   input_is_admin[0].addEventListener('click', ()=>{
      change_login_panel(0);
   });
   input_is_admin[1].addEventListener('click',()=>{
      change_login_panel(1);
   });
}

function change_login_panel(type){
   let input_name = document.getElementById("input-name");
    if(type === 0){
       input_name.style.display = 'block';
    } else {
       input_name.style.display = 'none';
    }
}