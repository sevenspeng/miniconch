/**
 * Created by Dayan on 2016/9/4.
 */


window.onload = function () {
    initUI();
    initRecordsByLatlng();
}

//初始化界面适应屏幕大小
function initUI() {
    var height = window.innerHeight;
    var width = window.innerWidth;

    //初始化窗口大小
    $(".bodyContent")[0].style.height = (height - 20) + ".px";
    $("#officialDescri")[0].style.width = (width - 380) + ".px";
    var commList = $(".itemDescri");
    for (var i = 0; i < commList.length; i++) {
        commList[i].style.width = (width - 320) + ".px";
    }

}

//根据经纬度初始化数据
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
//初始化数据
function initRecords(data) {
    //转换成json 对象
    var result = data;//JSON.parse(data);
    if (result.resultCode == '200') {
        var itemListStr = "";
        var officialStr = "";
        for (var i = 0; i < result.value.length; i++) {
                        var item = result.value[i];
                if (item.isOfficial == 'Y') {//官方录音数据处理

                var officialId = item.id;
                var officialAudio = item.recordFile;

                $(".officialPlay")[0].innerHTML += " <audio id=\"audio_" + officialId + "\" ></audio>";
                $(".officialIcon")[0].style.backgroundImage = "url(" + item.icon + ")";

                $(".officalLikeImg")[0].onclick = function () {
                    like("likeCount_" + officialId);
                };
                $(".officialPlayImg")[0].id = "playImg_" + item.id;
                $(".officialPlayImg")[0].onclick = function () {
                    play(this, officialAudio, officialId);
                };
                $(".officialDescri")[0].innerHTML = item.description;
                if (item.replyCount == null) {
                    item.replyCount = 0;
                }
                if (item.likeCount == null) {
                    item.likeCount = 0;
                }
                $(".officialCommentCount")[0].innerHTML = item.replyCount;
                $(".officialLikeCount")[0].innerHTML = item.likeCount;
                $(".officialLikeCount")[0].id = "likeCount_" + item.id;
                $('#officalDetail')[0].href = "recordDetail.html?recordId="+item.id;
            }
            else {
                itemListStr += "<div class=\"item\">"
                + "<div>"
                + "       <div  style=\"background-image: url('" + item.icon + "')\" class=\"itemIcon\"></div>"
                + "       <div class=\"itemDescri\">"
                + "        <div>" + item.title + "</div>"
                + "        <div class=\"itemComment\">"
                + item.description
                + "                      </div>"
                + "       </div>"
                + "       <div class=\"itemPlayContent\">"
                + "       <img  id=\"playImg_" + item.id + "\" src=\"img/play.png\" class=\"playBtn\" onclick=\"play(this,'" + item.recordFile + "','" + item.id + "');\" />"
                + "       <div id=\"audioLen_" + item.id + "\">"
                + item.timeLength
                + "       </div>"
                + " <audio id=\"audio_" + item.id + "\" ></audio>"
                + "       </div>"
                + "       </div>"
                + "       <div class=\"itemBottom\">"
                + "      <div>" + item.recordDate + "</div>"
                + "        <a href='recordDetail.html?recordId" + item.id + "'>      <img src=\"img/comment.png\"/> </a>"
                + "        <div>" + item.replyCount + "</div>"
                + "        <img onclick=\"like('likeCount_" + item.id + "')\" src=\"img/like.png\"/><div id='likeCount_" + item.id + "'>" + item.likeCount + "</div>"
                + "       </div>"
                + "       </div>";
            }
        }

        $("#listContent")[0].innerHTML = itemListStr;
    } else {  //Ajax 请求数据失败
        console.log("Ajax 请求数据失败");
    }


}

//当前播放记录的id
var currentId = "";
var currentAudio = null;
var cureentAudioLen = 0;
var isPause = false;    //播放是否已经暂停
//播放事件
function play(obj, audioPath, id) {


    //如果是重新播放，重置时长
    if (id != currentId) {
        if (currentId != "") {
            currentAudio.pause();   //播放新的语音之前，先暂停当前播放
        }
        currentId = id;
        currentAudio = document.getElementById("audio_" + id);

        currentAudio.oncanplay = function () {
            cureentAudioLen = currentAudio.duration;
            var minuts = Math.floor(cureentAudioLen / 60);
            var seconds = Math.floor(cureentAudioLen % 60);
            if (minuts < 10)
                minuts = "0" + minuts.toString();
            if (seconds < 10)
                seconds = "0" + seconds.toString();
            if (document.getElementById('audioLen_' + currentId) != undefined)
                document.getElementById('audioLen_' + currentId).innerHTML = minuts + ":" + seconds;
        }
    }

    if (obj.src.indexOf("img/play.png") >= 0) {
        //先重置所有播放按钮显示
        for (var i = 0; i < $(".playBtn").length; i++) {
            $(".playBtn")[i].src = "img/play.png";
        }
        //设置当前按钮为暂停图标
        obj.src = "img/stop.png";
        var current = currentAudio.getAttribute("src");
        if (current == "" || current == null)
            currentAudio.setAttribute("src", audioPath);

        currentAudio.play();
        isPause = false;    //未暂停
        //开始倒计时

    } else {
        currentAudio.pause();
        isPause = true;     //播放暂停
        //停止倒计时
        obj.src = "img/play.png"
    }
}

//播放倒计时
function playCountDown() {
    if (currentId != "" && !isPause && document.getElementById('audioLen_' + currentId) != undefined) {// 当前语音项不为空，且播放未暂停
        var timeStr = document.getElementById('audioLen_' + currentId).innerHTML;
        cureentAudioLen = Math.floor(timeStr.split(":")[0] * 60) + Math.floor(timeStr.split(":")[1]);
        if (cureentAudioLen > 0) {  //播放剩余时间大于0
            --cureentAudioLen;
            if (currentAudio == 0)
                document.getElementById('playImg_' + currentId).src = "img/play.png";
            var minuts = Math.floor(cureentAudioLen / 60);
            var seconds = Math.floor(cureentAudioLen % 60);
            if (minuts < 10)
                minuts = "0" + minuts.toString();
            if (seconds < 10)
                seconds = "0" + seconds.toString();
            if (document.getElementById('audioLen_' + currentId) != undefined)
                document.getElementById('audioLen_' + currentId).innerHTML = minuts + ":" + seconds;
        }
    }
}
timer = setInterval("playCountDown()", 1000);

//点赞
function like(id) {
    document.getElementById(id).innerHTML = Number(document.getElementById(id).innerHTML) + 1;

}
var loadingImg = document.querySelector("#load2");

loadingImg.addEventListener('click', recorderBegin);

//是否正在录音
var recording = false;
var recorderAudio = $("#recorder")[0];
//点击开始录音
function recorderBegin() {
    if (!recording) {
        $("#load").show();
        $("#load2").css("margin-top", "15px");
        $("#load2").css("margin-left", "-140px");
        recording = !recording;
        startRecording();
    } else {
        $("#load").hide();
        $("#load2").css("margin-top", "-150px");
        $("#load2").css("margin-left", "-60px");
        recording = !recording;
        stopRecording();
        //playRecording();
    }
}

var recorder;
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