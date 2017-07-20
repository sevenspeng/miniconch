/**
 * Created by jingdayan on 2016-11-03.
 */

var weixinAudioObj;
var recordData;
var recordId = getQueryStringByName('recordId');
var recordDetail = "recordDetail.txt";
var recordDetailUrl = queryRecordDetail +"/"+ getQueryStringByName('recordId');

window.onload = function () {
    Vue.filter("recordMonth", function (value) {
        var mydate = new Date(value.replace(/-/g,"/"));
        return mydate.getMonth();
    });
    Vue.filter("recordDay", function (value) {
        var mydate = new Date(value.replace(/-/g,"/"));
        return mydate.getDay();
    });
    initRecord();
    weixinAudioObj = $('.weixinAudio').weixinAudio();
// 添加单一播放的逻辑
    $('.weixinAudio').on('click', function (event) {

        var $this = $(this);
        var currentIndex = $this.index();
        var currentId = $this[0].id;
        // 遍历所有对象，找到非当前点击，执行pause()方法;
        $.each(weixinAudioObj, function (i, el) {

            if (i != currentId) {
                el.pause();
            }
        });
    });

}

function initRecord() {
    var obj = $.ajax({
        url: recordDetailUrl,
        dataType: "json",
        async: false
    });
    //转换成json 对象
    recordData = JSON.parse(obj.responseText);
    if (recordData.resultCode == '200') {
        var recordVM = new Vue({
            el: '#record',
            data: recordData.value,
            methods: {
                like: function (id) {
                    recordData.value.likeCount = recordData.value.likeCount + 1;
                    submitLike(id);
                },
                comment: function (id) {
                    alert(id);
                }
            }
        });
        var commentVue = new Vue({
            el: '#recordComment',
            data: recordData.value
        });
    } else {
        console.log("根据id获取记录失败！");
    }
}

//提交点赞数据
function submitLike(id) {
    var postData = JSON.stringify({
        "recordId": "" + id + "",
        "uId": ""
    });
    $.ajax({
        url: likePost,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            console.log(data);
            console.log("点赞成功");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("点赞失败");
        }
    });
}

//提交评论

//评论
function comment() {
    var comTxt = document.getElementById("txtComment").value.trim();

    var userId = localStorage.userId;
    if (userId == undefined || userId == null || userId == "") {
        userId = "2109c7de-8609-11e6-b6d3-782bcb720a83"
    }
    var cDate = new Date();
    var comm = {
        "uid": "" + userId + "",
        "title": "匿名", "icon": "",
        "date": "" + cDate.getFullYear() + "-" + cDate.getMonth() + "-" + cDate.getDay() + ""
    };
    recordData.value = recordData.value.comments.concat(comm);
    var postData = JSON.stringify({
        "rid": "" + recordId + "",
        "uid": "" + userId + "",
        "comment": "" + comTxt + ""
    });

    $.ajax({
        url: commentPost,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            location.reload(true);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

//是否正在录音
var recording = false;
var recorderAudio = $("#recorder")[0];
//点击开始录音
function recorderBegin() {
    if (!recording) {
        $("#mc").show();
        $("#m").css("margin-top", "15px");
        $("#m").css("margin-left", "-140px");
        recording = !recording;
        startRecording();
    } else {
        $("#mc").hide();
        $("#m").css("margin-top", "-150px");
        $("#m").css("margin-left", "-60px");
        recording = !recording;
        stopRecording();
        //playRecording();
    }
}

function startRecording() {
    HZRecorder.get(function (rec) {
        recorder = rec;
        recorder.start();
    });
}
function stopRecording() {
    recorder.stop();
    var media = recorder.getBlob();
    recorderAudio.src = window.URL.createObjectURL(media);
}

function playRecording() {

    recorder.play(recorderAudio);
}

$(document).ready(function () {
    $("#circle").click(function () {
        $("#contents").fadeIn(500);
    });
});

//根据名称取查询值
function getQueryStringByName(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}