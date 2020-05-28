function parseElement(htmlString, parentTag) {
    let div = document.createElement(parentTag);
    div.innerHTML = htmlString;
    return div.children[0];
}

function findIndex(htmlCollection, element){
    let index = 0;
    for(;index < htmlCollection.length;index++){
        if(htmlCollection.item(index) === element){
            return index;
        }
    }
    return -1;
}