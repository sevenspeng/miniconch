/**
 * Created by JINGDAYAN962 on 2016-12-08.
 */

var loadingImg = document.querySelector("#load2");
loadingImg.addEventListener('click', recorderBegin);
//是否正在录音
var recording = false;
//微信录音ID
var localId = "";
var imgData = null;
var imgSuffix = "";
//点击开始录音
function recorderBegin() {
    if (!recording) {
        $("#load").show();
        recording = !recording;
        startRecording();
    } else {
        $("#load").hide();
        recording = !recording;
        stopRecording();
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
            $(".soundConet").show();
        }
    });
}
//监听到录音结束
wx.onVoiceRecordEnd({
    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
    complete: function (res) {
        localId = res.localId;
        $(".soundConet").show();
    }
});

//播放录音
var playing = false;
var intFlag = 0;
function playRecord() {
    if (playing) {
        playing = !playing;
        wx.stopVoice({
            localId: localId // 需要停止的音频的本地ID，由stopRecord接口获得
        });
        window.clearInterval(intFlag);

    } else {
        playing = !playing;
        wx.playVoice({
            localId: localId // 需要播放的音频的本地ID，由stopRecord接口获得
        });
        intFlag = setInterval("setPlayImg()", 400);
    }
}
//录音播放停止
wx.onVoicePlayEnd({
    success: function (res) {
        localId = res.localId; // 返回音频的本地ID
        window.clearInterval(intFlag);
    }
});
var soundIndex = 3;
function setPlayImg() {
    if (soundIndex < 5) {
        $("#sound3")[0].src = "img/sound" + (soundIndex + 1) + ".png";
        soundIndex++;
    } else {
        $("#sound3")[0].src = "img/sound3.png";
        soundIndex = 3;
    }
}

//图片预览
function PreviewImage(file) {

    var reader = new FileReader();
    var imgName = file.files[0].name;
    imgSuffix = imgName.substring(imgName.lastIndexOf("."), imgName.length);
    reader.readAsDataURL(file.files[0]);
    reader.onload = function (evt) {
        var img = new Image,
            width=640,
            quality = 0.6, //image quality
            canvas = document.createElement("canvas"),
            drawer = canvas.getContext("2d");
        img.src = this.result;
        var img2 = new Image;
        img.onload = function () {
            canvas.width = width;
            canvas.height = width * (img.height / img.width);
            drawer.drawImage(img, 0, 0, canvas.width, canvas.height);
            img2.src = canvas.toDataURL("image/jpeg", quality);
            $("#preview")[0].innerHTML = "<img style='  width: 96%;' src=" + img2.src + ">";
            $("#imgCommet").hide();
            imgData = img2.src.substring(img2.src.toString().indexOf(',') + 1);
        }

    }
}

//微信media_id
var media_id = null;

//提交录音
function submitRecord() {

    //上传录音到微信服务器
    wx.uploadVoice({
        localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
            media_id = res.serverId; // 返回音频的服务器端ID
            processData();
        }
    });
}

function processData() {

    var lat = getUrlParam("Lat");
    var lng = getUrlParam("Lng");
    var description = $(".comments")[0].value;

    var postData = JSON.stringify({
        "lat": "" + lat + "",
        "lng": "" + lng + "",
        "description": "" + description + "",
        "recordId": "" + media_id + "",
        "uid": "",
        "img": [{
            "data": "" + imgData + "",
            "suffix": "" + imgSuffix + ""
        }]
    });
    $.ajax({
        url: addRecord,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            location.href = "success.html";
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
