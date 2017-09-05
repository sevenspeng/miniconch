/**
 * Created by JINGDAYAN962 on 2016-11-09.
 */

var appId = "wxc48e52be91c76a40";
var appSecret = "4694b3d66c5733db3eb50e0f776ac7f3";
var url = "http://xiaohailuoapp.applinzi.com/wx.html";
var accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret + "";
var jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//"?access_token=ACCESS_TOKEN&type=jsapi";

wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: 'wxc48e52be91c76a40', // 必填，公众号的唯一标识
    timestamp: getTimestamp(), // 必填，生成签名的时间戳
    nonceStr: getNonceStr(), // 必填，生成签名的随机串
    signature: getSignature(url),// 必填，签名，见附录1
    jsApiList: ['onMenuShareTimeline',
        'onMenuShareAppMessage',
        'onMenuShareQQ',
        'onMenuShareWeibo',
        'onMenuShareQZone',
        'startRecord',
        'stopRecord',
        'onVoiceRecordEnd',
        'playVoice',
        'pauseVoice',
        'stopVoice',
        'onVoicePlayEnd',
        'uploadVoice',
        'downloadVoice',
        'chooseImage',
        'previewImage',
        'uploadImage',
        'downloadImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});



//获取signature
function getSignature(strUrl) {
    var signaStr = "jsapi_ticket=" + getJsAPI() + "&noncestr=" + getNonceStr() + "&timestamp=" + getTimestamp() + "&url=" + strUrl + "";
    return hex_sha1(signaStr);
}

//获取jsapi_ticket
function getJsAPI() {
    debugger;
    var access_token = getAccess_token();
    var currentTimeStamp = (Date.parse(new Date())) / 1000;
    if (localStorage.jsapi_ticket == undefined || (localStorage.timestamp - currentTimeStamp) > 2 * 60 * 60) {
        var obj = $.ajax({
            url: jsapiTicketUrl + "?access_token=" + access_token + "&type=jsapi",
            dataType: "json",
            async: false
        });
        var result = JSON.parse(obj.responseText); 
        if (result.errcode == 0) {
            localStorage.jsapi_ticket = result.ticket;
        } else {
            alert(result.errcode + ":" + result.errmsg);
        }
    }

    return localStorage.jsapi_ticket;
}

//获取access_token
function getAccess_token() {
    debugger;
    var currentTimeStamp = (Date.parse(new Date())) / 1000;
    //如果当前缓存access_token为空||已经超时2小时，重新生成access_token
    if (localStorage.access_token == undefined || (localStorage.timestamp - currentTimeStamp) > 2 * 60 * 60) {
        var obj = $.ajax({
            url: accessTokenUrl,
            dataType: "json",
            async: false
        });
        //转换成json 对象
        var result = JSON.parse(obj.responseText);
        if (result.access_token != "" & result.access_token != undefined) {
            localStorage.access_token = result.access_token;
        } else {
            alert(result.errcode + ":" + result.errmsg);
        }
    }
    return localStorage.access_token;
}
//获取时间戳
function getTimestamp() {

    var currentTimeStamp = (Date.parse(new Date())) / 1000;
    if (localStorage.timestamp == undefined
        || (localStorage.timestamp - currentTimeStamp) > 2 * 60 * 60) {
        localStorage.timestamp = currentTimeStamp;
    }
    return localStorage.timestamp;
}

//获取签名随机串
function getNonceStr() {
    debugger;
    var currentTimeStamp = (Date.parse(new Date())) / 1000;

    //如果当前缓存uuid为空||已经超时2小时，重新生成uuid
    if (localStorage.uuid == undefined
        || (localStorage.timestamp - currentTimeStamp) > 2 * 60 * 60) {
        localStorage.uuid = generateUuid();
    }
    return localStorage.uuid;
}

//生成签名的随机串
function generateUuid() {
    var len = 32;//32长度
    var radix = 16;//16进制
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        for (i = 0; i < len; i++)uuid[i] = chars[0 | Math.random() * radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}