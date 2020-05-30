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

function api_fetch(url, callback){
    let xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = () => {
        if(xmlHttpRequest.readyState === XMLHttpRequest.DONE){
            callback(xmlHttpRequest.status, JSON.parse(xmlHttpRequest.responseText));
        }
    };
    xmlHttpRequest.open("post",url,true);
    xmlHttpRequest.send();
}