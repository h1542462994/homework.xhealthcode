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