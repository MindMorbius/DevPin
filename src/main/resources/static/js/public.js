var baseUrl=".";

var loginUrl=baseUrl+"/user/login";
var registerUrl=baseUrl+"/user/register";
var logoutUrl=baseUrl+"/user/logout";

var taskGets=baseUrl+"/task/gets";
var taskAdd=baseUrl+"/task/add";
var taskDel=baseUrl+"/task/delete";
var taskUpdate=baseUrl+"/task/update";

var generateId=baseUrl+"/dis/generateId";

var disGets=baseUrl+"/dis/gets";
var disAdd=baseUrl+"/dis/add";
var disDel=baseUrl+"/dis/delete";

// 这是一个打印消息的函数，将传入的参数msg输出到控制台
function mlog(msg){
    console.log(msg);
}

// 这是一个页面跳转函数，将当前页面的URL地址修改为传入的参数url所表示的页面地址
function mNav(url){
    window.location.href=url;
}

// 这是一个本地存储函数，将传入的键值对保存到localStorage中
function setS(key,value){
    localStorage.setItem(key,value);
}

// 这是一个本地存储函数，根据传入的键获取对应的值并返回
function getS(key){
    return localStorage.getItem(key);
}

function jsonToBase64(jsonObj) {
    const jsonString = JSON.stringify(jsonObj);

    // 使用 TextEncoder 将 JSON 字符串转换为字节数组 (Uint8Array)
    const encoder = new TextEncoder();
    const byteArray = encoder.encode(jsonString);

    // 将字节数组转换为 Base64 字符串
    let base64String = '';
    for (let i = 0; i < byteArray.length; i++) {
        base64String += String.fromCharCode(byteArray[i]);
    }
    return btoa(base64String); // 使用 btoa 编码字节为 Base64
}

function base64ToJson(base64String) {
    // 解码 Base64 字符串为普通字符串
    const decodedString = atob(base64String);

    // 将普通字符串转换为字节数组
    const byteArray = new Uint8Array(decodedString.length);
    for (let i = 0; i < decodedString.length; i++) {
        byteArray[i] = decodedString.charCodeAt(i);
    }

    // 使用 TextDecoder 将字节数组转换回 JSON 字符串
    const decoder = new TextDecoder();
    const jsonString = decoder.decode(byteArray);

    return JSON.parse(jsonString); // 解析 JSON 字符串为对象
}




