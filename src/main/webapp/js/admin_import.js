class AdminImport {
    element_input_download_template;
    element_input_file;
    element_input_submit;

    constructor() {
        this.element_input_download_template = document.getElementById('input-download-template');
        this.element_input_file = document.getElementById('input-file');
        this.element_input_submit = document.getElementById('input-submit');

        this.element_input_download_template.addEventListener('click', () => {
            this.download_template();
        });
        this.element_input_submit.disabled = 'disabled';
        this.element_input_file.addEventListener('change', () => {
            if(this.element_input_file.value === undefined || this.element_input_file.value === ''){
                this.element_input_submit.disabled = 'disabled';
            } else {
                this.element_input_submit.disabled = undefined;
            }
        });
        this.element_input_submit.addEventListener('click', () => {
            this.submit_template();
        })
    }

    download_template(){
        const form = parseElement(`<form action="${get_url("/api/import")}" method="get">
    
</form>`);
        document.body.appendChild(form);
        form.submit();
        this.element_input_download_template.disabled = 'disabled';
        document.body.removeChild(form);
    }

    submit_template(){
        let formData = new FormData();
        formData.append('file', this.element_input_file.files[0]);

        let xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.onreadystatechange = () => {
            if(xmlHttpRequest.readyState === XMLHttpRequest.DONE){
                console.log(xmlHttpRequest.responseText);
            }
        };
        xmlHttpRequest.open('post', get_url(`/api/import`), true);
        xmlHttpRequest.send(formData);
    }
}