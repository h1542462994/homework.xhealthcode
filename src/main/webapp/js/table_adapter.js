class TableAdapter{
    /* 将表格中用到的数据存储起来 */
    /* [checked:false,key:key,value:value] */
    data = [];
    /* 将data->key的函数 */
    element_table;

    constructor(table) {
        this.element_table = table;
    }

    /* 用新的数据替换掉旧的数据，并依次产生多个动作 */
    replace(data){
        let fills = data.map((item)=>{
            return {checked:false,key:this.key_mapper(item),value:item}
        });
        let inserted = [];
        let updated = [];
        fills.forEach((item,index) => {
            let find = this.data.find((item2) => item2.key === item.key);
            if(find === undefined){
                inserted.push(item); //在fills中有而datas中没有。
            } else {
                fills[index].checked = find.checked; // 更新update的信息，保持checked的状态?
                updated.push(item); //两者均有
            }
        });
        let deleted = this.data.filter((item) => {
            return fills.find((item2) => item2.key === item.key) === undefined
            // 在datas中有而fills中没有
        });

        // 对旧数据进行删除，并更新旧数据。
        let index = 0;
        let flow_index = 0;
        let count = this.data.length;

        for(;index < count;++index){
            let updated_element = updated.find((item) => {
                return this.data[index].key === item.key;
            });
            if(updated_element === undefined){ //执行删除操作
                this.ondelete(flow_index);
            } else {
                this.onupdate(flow_index, updated_element);
                flow_index = flow_index + 1;
            }
        }
        this.data = this.data.filter((item) => {
            return deleted.find((item2)=>{
                return item2.key === item.key;
            }) === undefined;
        });
        // // 对旧数据进行顺序上的调正
        // index = 0;
        // count = this.data.length;
        // updated.forEach((item, i)=>{
        //     let j = this.data.findIndex((item2) => item2.key === item.key);
        //     this.onmove(j,i);
        // })
        // 对新数据进行插入操作

        count = this.data.length;

        inserted.forEach((item, i)=>{
            let j = this.data.findIndex((item2) => {
                return item2.key > item.key;
            });
            this.oninsert(j, item);
        });

        this.data = fills;
    }
    /* 加入新的数据 */
    add(...data){
        let fills = data.map((item) => {
            return {checked:false, key:this.key_mapper(item), value:item};
        });
        fills.forEach((item) =>{
            let index = this.data.findIndex((item2) => {
                return item2.key === item.key;
            });
            if(index === -1){
                this.oninsert(-1, item);
                this.data = [].concat(...this.data, item);
            } else {
                this.data[index] = item;
                this.onupdate(index, item);
            }
        });


    }
    /* 删除数据 */
    remove(...data){
        let fills = data.map((item) => {
            return {checked:false, key:this.key_mapper(item), value:item};
        });

        // 对旧数据进行删除，并更新旧数据。
        let index = 0;
        let flow_index = 0;
        let count = this.data.length;

        for(;index < count;++index){
            let data = fills.find((item) => {
                return this.data[index].key === item.key;
            });
            if(data === undefined){
                flow_index = flow_index + 1;
            } else { //执行删除操作
                this.ondelete(flow_index);
            }
        }

        this.data = this.data.filter((item) => {
            return fills.find((item2)=>{
                return item2.key === item.key;
            }) === undefined;
        });

    }

    ondelete(index){
        let element = this.element_table.children[index];
        this.element_table.removeChild(element);
        this.check_count_changed()
    }
    onupdate(index, item){
        let element = this.element_table.children[index];
        this.element_table.replaceChild(this.wrap_element(item), element);
    }
    oninsert(index, item){
        let new_element = this.wrap_element(item);
        if(index === -1){
            this.element_table.appendChild(new_element);
        } else {
            let element = this.element_table.children[index];
            this.element_table.insertBefore(new_element, element);
        }
    }


    wrap_element(item){
        let element = this.create_element(item);
        element[1].addEventListener('click',() => {
            let index = findIndex(this.element_table.children, element[0]);
            this.data[index].checked = !this.data[index].checked;
            this.check(this.element_table.children.item(index), this.data[index].checked);
            this.check_count_changed();
        });
        return element[0];
    }

    /**
     * 范围项的定位符
     * @param item 项
     */
    key_mapper(item){}

    /**
     * 创建tr以及对应的checkbox
     */
    create_element(){}

    /**
     * 执行check操作
     * @param element 触发的td行
     * @param value
     */
    check(element, value){}

    checked_count(){
        return this.data.reduce((total, item) =>{
            return total + (item.checked === true ? 1 : 0)
        }, 0);
    }

    checked_items(){
        return this.data.filter((item) => item.checked).map((item)=>item.value);
    }

    check_count_changed(){}

}