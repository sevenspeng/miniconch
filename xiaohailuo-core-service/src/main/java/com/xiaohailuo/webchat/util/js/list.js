/**
 * Created by jingdayan on 2016-11-03.
 */
var loadingImg = document.querySelector("#m");
var weixinAudioObj;
var recordListData;
window.onload = function () {
    Vue.filter("recordMonth",function(value){
        var mydate = new Date(value.replace(/-/g,"/"));
        return mydate.getMonth();
    });
    Vue.filter("recordDay",function(value){
        var mydate = new Date(value.replace(/-/g,"/"));
        return mydate.getDay();
    });


    initRecordsByLatlng();
    weixinAudioObj = $('.weixinAudio').weixinAudio();
// 添加单一播放的逻辑
    $('.weixinAudio').on('click', function (event) {

        var $this = $(this);
        var currentIndex = $this.index();
        var currentId = $this[0].id;
        // 遍历所有对象，找到非当前点击，执行pause()方法;
        $.each(weixinAudioObj, function (i, el) {

            if (i !=currentId) {
                el.pause();
            }
        });
    });

}


function initRecordsByLatlng() {
    var lng = getUrlParam('makerLng');
    var lat = getUrlParam('makerLat');

    var postData = JSON.stringify({
        "lat": lat,
        "lng": lng
    });
    $.ajax({
        url: initRecordList,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            initRecords(data)
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("获取录音列表失败");
        }
    });
}

function initRecords(data) {

    recordListData = data;//JSON.parse(data);
    if (recordListData.resultCode == '200') {
        var vm = new Vue({
            el: '#record',
            data: recordListData,
            methods:{
                like:function(id){
                     for(var i=0;i<recordListData.value.length;i++){
                         if(recordListData.value[i].id==id){
                             recordListData.value[i].likeCount++;
                         }
                     }
                    submitLike(id);
                },
                comment:function(id){
                    window.location.href="list2.html?recordId="+id;
                }
            }
        })
    } else {
        console.log("Ajax 请求数据失败");
    }
}


//提交点赞数据
function submitLike(id){
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
