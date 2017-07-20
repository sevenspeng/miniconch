/**
 * Created by JINGDAYAN962 on 2016-11-17.
 */
//当前页面完整地址，微信推荐写法
var currentUrl = location.href.split('#')[0];

//接口list，分别是：开始录音、结束录音、录音结束事件、播放录音、暂停播放、停止播放、播放停止事件、上传录音、下载录音
var apiList = ['startRecord', 'stopRecord', 'onVoiceRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice', 'onVoicePlayEnd', 'uploadVoice', 'downloadVoice'];

//微信配置对象
var wxConfig = {
    jsApiList: "",
    url: "",
    appId: "",
    timestamp: "",
    nonceStr: "",
    signature: ""
};

wxConfig.jsApiList = apiList;
wxConfig.url = currentUrl;

//向后台请求微信相关配置
initWxConfig();

//初始化微信配置
wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: wxConfig.appId, // 必填，公众号的唯一标识
    timestamp: wxConfig.timestamp, // 必填，生成签名的时间戳
    nonceStr: wxConfig.nonceStr, // 必填，生成签名的随机串
    signature: wxConfig.signature,// 必填，签名，见附录1
    jsApiList: wxConfig.jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.onVoiceRecordEnd({
    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
    complete: function (res) {
        var localId = res.localId;
    }
});

//从后台加载微信配置参数
function initWxConfig() {
    var postData = JSON.stringify({
        "url": "" + wxConfig.url + ""
    });

    $.ajax({
        url: initWxConfigPath,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            if (data.resultCode == 200) {
                wxConfig.appId = data.value.appId;
                wxConfig.timestamp = data.value.timestamp;
                wxConfig.nonceStr = data.value.nonceStr;
                wxConfig.signature = data.value.signature;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("请求微信配置参数失败");
        }
    });
}

//是否正在录音
var recording = false;
var localId = "";
var playing = false;

//点击开始录音
function  recorderBegin() {
    if (localId != "" && !playing) {
        playing = true;
        $("#load").show();
        $("#load2").css("margin-top", "5px");
        $("#load2").css("margin-left", "-55px");
        wx.playVoice({
            localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
        });
    } else if (localId != "" && playing) {
        playing = !playing;
        $("#load").hide();
        $("#load2").css("margin-top", "-60px");
        $("#load2").css("margin-left", "-25px");
        wx.stopVoice({
            localId: localId // 需要停止的音频的本地ID，由stopRecord接口获得
        });
    }
    if (!recording) {
        $("#load").show();
        $("#load2").css("margin-top", "5px");
        $("#load2").css("margin-left", "-55px");
        recording = !recording;
        startRecording();
    } else {
        $("#load").hide();
        $("#load2").css("margin-top", "-60px");
        $("#load2").css("margin-left", "-25px");
        recording = !recording;
        stopRecording();
        //playRecording();
    }
}
//开始录音
function startRecording() {
    wx.startRecord();
}
//结束录音
function stopRecording() {

    wx.stopRecord({
        success: function (res) {
            localId = res.localId;
        }
    });

}
