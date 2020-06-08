/**
 * 资源定位器
 */
class Locator{
    type;
    pageIndex;
    scope;
    tag;
    college;
    collegeId;
    profession;
    professionId;
    xclass;
    xclassId;

    constructor(type, pageIndex, scope, tag) {
        this.type = type;
        this.pageIndex = pageIndex;
        this.scope = scope;
        this.tag = tag;
    }

    url(action){
        if(action === 'get'){
            let s = `/api/user?action=get`;
            if(locator.type !== 0){
                s += `&type=${locator.type}`
            }
            if(locator.pageIndex !== 0){
                s += `&pageIndex=${locator.pageIndex}`
            }
            if(locator.scope !== 'all'){
                s += `&scope=${locator.scope}`
            }
            if(locator.tag !== 0){
                s += `&tag=${locator.tag}`
            }
            return s;
        }
    }

    is_teacher(){
        return this.type === 1 && this.scope === 'all'
    }
    is_student(){
        return this.type === 0 && this.scope === 'all'
    }
    is_admin(){
        return this.type === 2;
    }
    get_scope(){
        if(this.is_admin() || this.scope === 'all'){
            return 'all';
        } else {
            return this.scope;
        }
    }
    get_domain_url(){
        let c= this.type === -1? 'selected': undefined;
        if(this.get_scope() === 'all'){
            return `<a class="${c}" href="${get_url(`/admin/college`)}">架构</a>`;
        } else if(this.get_scope() === 'college'){
            return `<a class="${c}" href="${get_url(`/admin/college?page=profession&college=${this.tag}`)}">架构</a>`
        } else if(this.get_scope() === 'profession'){
            return `<a class="${c}" href="${get_url(`/admin/college?page=xclass&profession=${this.tag}`)}">架构</a>`
        }
    }
    get_student_url(){
        let c = this.type === 0? 'selected': undefined;
        if(this.get_scope() === 'all'){
            return `<a class="${c}" href="${get_url(`/admin/user"`)}>学生</a>`
        } else {
            return `<a class="${c}" href="${get_url(`/admin/user?scope=${this.scope}&tag=${this.tag}`)}">学生</a>`
        }
    }
    get_teacher_url(){
        let c = this.type === 1? 'selected': undefined;
        if(this.get_scope() === 'all'){
            return `<a class="${c}" href="${get_url(`/admin/user?type=1`)}">教师</a>`
        } else if(this.get_scope() === 'college') {
            return `<a class="${c}" href="${get_url(`/admin/user?type=1&scope=${this.scope}&tag=${this.tag}`)}">教师</a>`
        } else {
            return undefined;
        }
    }
    get_admin_url(){
        let c = this.type === 2? 'selected': undefined;
        if(this.get_scope() === 'all'){
            return `<a class="${c}" href="${get_url(`/admin/user?type=2`)}" >管理员</a>`
        } else {
            return undefined;
        }
    }

    get_all_url(){
        if(this.type === -1){
            return `<a href="${get_url(`/admin/college`)}">学校</a>`
        } else if(this.type === 0){
            return `<a href="${get_url(`/admin/user`)}">学生</a>`
        } else if(this.type === 1){
            return `<a href="${get_url(`/admin/user?type=1`)}">教师</a>`
        } else if(this.type === 2){
            return `<a href="${get_url(`/admin/user?type=2`)}">管理员</a>`
        }
    }

    get_college_url(){
        if(this.scope === 'all')
            return undefined;
        if(this.type === -1){
            return `<a href="${get_url(`/admin/college?page=profession&college=${this.collegeId}`)}">${this.college}</a>`
        } else if(this.type === 0){
            return `<a href="${get_url(`/admin/user?scope=college&tag=${this.collegeId}`)}">${this.college}</a>`
        } else if(this.type === 1){
            return `<a href="${get_url(`/admin/user?type=1&scope=college&tag=${this.collegeId}`)}">${this.college}</a>`
        } else {
            return undefined;
        }
    }

    get_profession_url(){
        if(this.scope === 'all' || this.scope === 'college')
            return undefined;
        if(this.type === -1){
            return `<a href="${get_url(`/admin/college?page=xclass&profession=${this.professionId}`)}">${this.profession}</a>`
        } else if(this.type === 0){
            return `<a href="${get_url(`/admin/user?scope=profession&tag=${this.professionId}`)}">${this.profession}</a>`
        } else {
            return undefined;
        }
    }

    get_xclass_url(){
        if(this.scope === 'all' || this.scope === 'college' || this.scope === 'profession')
            return undefined;
        if(this.type === 0){
            return `<a href="${get_url(`/admin/user?scope=xclass&tag=${this.xclassId}`)}">${this.xclass}</a>`
        } else {
            return undefined;
        }
    }
}