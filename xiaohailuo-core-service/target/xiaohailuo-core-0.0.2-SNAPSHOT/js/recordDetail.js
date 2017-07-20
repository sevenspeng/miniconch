/**
 * Created by Dayan on 2016/9/4.
 */

var recordId = "";
var recordDetail = "recordDetail.txt";
var recordDetailUrl =recordDetail; //queryRecordDetail +"/"+ getQueryStringByName('recordId');
window.onload = function () {
    initRecords();
    initUI();

}

//初始化界面适应屏幕大小
function initUI() {
    var height = window.innerHeight;
    var width = window.innerWidth;

    $("#officialDescri")[0].style.width = width - 400 + ".px";
    var commList = $(".itemDescri");
    for (var i = 0; i < commList.length; i++) {
        commList[i].style.width = width - 320 + ".px";
    }


}

//初始化数据
function initRecords() {

    var obj = $.ajax({
        url: recordDetailUrl,
        dataType: "json",
        async: false
    });
    //转换成json 对象
    var result = JSON.parse(obj.responseText);
    if (result.resultCode == '200') {
        var itemListStr = "";
        var item = result.value;
        // window.name=item.title;
        document.title = item.title;
        recordId = item.id;
        if (item.icon != null && item.icon != undefined && item.icon != "") {
            $(".officialIcon")[0].style.backgroundImage = "url(" + item.icon + ")";
        } else {
            $(".officialIcon")[0].style.backgroundImage = "url(" + baseIconUrl + Math.floor((Math.random() * 26) + 1) + ".jpg)";
        }
        if (item.recordFile != null && item.recordFile != "") {
            $("#hlAudio")[0].setAttribute("src", item.recordFile);
            $(".officialPlayImg")[0].onclick = function () {
                play();
            };
            play();
        } else {
            $(".officialPlay").hide();
        }
        $(".officialDescri")[0].innerHTML = item.description;
        $(".officialCommentCount")[0].innerHTML = item.replyCount;
        $(".officialLikeCount")[0].innerHTML = item.likeCount;
        if (item.isOfficial == 'true') {
            $(".officialFlag").show();
        } else {
            $(".officialFlag").hide();
        }

        for (var i = 0; i < item.comments.length; i++) {
            var replyItem = item.comments[i];
            itemListStr += "  <div class=\"item\">"
            + "   <div>";
            if (localStorage.um == replyItem.nickname) {
                itemListStr += "   <div style=\"background-image: url('" + localStorage.userIcon + "')\" class=\"itemIcon\"></div>";
            }
            else {
                itemListStr += "   <div style=\"background-image: url('" + baseIconUrl + Math.floor((Math.random() * 26) + 1) + ".jpg')\" class=\"itemIcon\"></div>";
            }
            itemListStr += "   <div class=\"itemDescri\">"
            + "   <div style=\"width: 100%; height: 60px\">"
            + "   <div style=\"float: left\"> " + replyItem.nickname + "</div>"
            + "  <div style=\"float: right\">" + replyItem.date.split(" ")[0] + "</div>"
            + "   </div>"
            + "   <div class=\"itemComment\">" + replyItem.comment + "</div>"
            + "    </div>"
            + "    </div>"
            + "   </div>"
            + "   <hr style=\"margin-left: 20px; margin-right: 20px; margin-top: 20px\"/>";
        }


        $("#listContent")[0].innerHTML = $("#listContent")[0].innerHTML + itemListStr;
    }
    else {  //Ajax 请求数据失败
        console.log("Ajax 请求数据失败");
    }


}

//播放事件
function play() {
    var audioObj = $("#hlAudio")[0];
    var obj = $(".playBtn")[0];
    if (obj.src.indexOf("img/play.png") >= 0) {
        //设置当前按钮为暂停图标
        obj.src = "img/stop.png";
        audioObj.play();
    } else {
        audioObj.pause();
        obj.src = "img/play.png"
    }
}

//点赞
function like() {
    document.getElementById("idLikeCount").innerHTML = Number(document.getElementById("idLikeCount").innerHTML) + 1;

    var userId = localStorage.userId;

    var postData = JSON.stringify({
        "recordId": "" + recordId + "", "uId": "" + userId + ""
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
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

//评论
function comment() {
    var comTxt = document.getElementById("txtComment").value.trim();

    var userId = localStorage.userId;
    if (userId == undefined || userId == null || userId == "") {
        userId = "2109c7de-8609-11e6-b6d3-782bcb720a83"
    }
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

//根据名称取查询值
function getQueryStringByName(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}