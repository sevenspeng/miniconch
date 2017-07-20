//请求屏幕氛围内的录音点
var markers = [];//保存标记点
var map;
//加载地图，调用浏览器定位服务
map = new AMap.Map('container', {
    resizeEnable: true
});

//是否标记新录音点，如果是在标记新录音点，则触发移动地图事件
var recording = false;

//设置地图缩放级别
map.setZoom(15);

//地理编码插件，用于通过坐标获取地址信息
var geocoder = new AMap.Geocoder();
//添加定位组件，用于获取用户当前的精确位置
var geolocation = new AMap.Geolocation({
    showCircle: true, //不显示定位结果的圆
    showMarker: true, //不现实定位结果的标记
    showButton: true, //不现实组件的定位按钮
    timeout: 5000 //浏览器定位超时时间5s
});
map.addControl(geolocation);

geolocation.getCurrentPosition(function(status, result) {

    if (status == 'complete') {
        //onLocateSuccess(result) //定位成功
    } else if (status == 'error') {
        //定位失败
        if (result.message.indexOf('Geolocation permission denied.') !== -1) {
            //Geolocation permission denied.表示用户禁用了浏览器或者APP的定位权限或者关闭了手机的定位服务
            //或者当前页面为非安全页面,Chrome或者IOS10等系统会禁用非安全页面的定位请求
            //如果您的页面还没有支持HTTPS请尽快升级
            //安全页面指的是支持HTTPS的Web站点，而且是通过https协议打开的页面。安全页面也包括本地页面
            showTip('您好，请在系统的隐私设置中打开当前应用的定位权限。');
        } else {
            showTip('无法获取精确位置,将定位您所在的城市。');
        }
     //   onLocateFailed();
    }
})
//定位失败之后进行城市定位
var onLocateFailed = function() {
    geolocation.getCityInfo(function(status, result) {
        map.setZoom(14);
       // showLocation(result.center); //在城市中心点显示起始marker
        //placeSearch.setCity(result.citycode);
        //autoComplete.setCity(result.citycode);
    })
};
//定位成功
var onLocateSuccess = function(result) {
    showTip('定位成功,拖动地图可微调.');
    showLocation(result.position); //在定位结果显示起始marker
    var city = result.addressComponent.city;
    var province = result.addressComponent.province;
    var district = result.addressComponent.district;
    var township = result.addressComponent.township;
    showOriginAddress(result.formattedAddress.replace(province, '').replace(city, '').replace(district, '').replace(township, ''))
    origin.position = result.position;
    placeSearch.setCity(result.addressComponent.citycode);
    autoComplete.setCity(result.addressComponent.citycode);
};
//信息显示
var infoDiv = document.getElementsByClassName('info')[0];
var showTip = function(text) {
    infoDiv.innerHTML = text;
    infoDiv.className = 'info top showOnce'
}

//加载当前地图上的录音点
loadRecordMarkOfScreen();

//输入提示
var autoOptions = {
    input: "tipinput"
};
var auto = new AMap.Autocomplete(autoOptions);

AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发

function select(e) {
    map.remove(markers);
    map.setCenter(e.poi.location);
    map.setZoom(15);
    loadRecordMarkOfScreen();
}

//加载当前页面录音资源
function loadRecordMarkOfScreen() {
    var southwest = map.getBounds().getSouthWest();//获取屏幕西南角坐标
    var northeast = map.getBounds().getNorthEast();//获取屏幕东北角坐标

    var postData = JSON.stringify({
        "northeast": "" + northeast + "",
        "southwest": "" + southwest + ""
    }); 
    $.ajax({
        url: initScreemMaker,
        type: "POST",
        async: false,
        data: postData,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        success: function (data) {
            if (data.resultCode == 200) {
                for (var i = 0; i < data.value.length; i++) {

                    var lng = data.value[i].lng;
                    var lat = data.value[i].lat;
                    var marker1 = new AMap.Marker({
                        icon: "img/water_blue.png",
                        position: [lng, lat]
                    });
                    marker1.setMap(map);
                    marker1.on('click', function () {
                        window.location.href = "detail.html?makerLng=" + lng + "&makerLat=" + lat;
                    });
                    markers.push(marker1);

//                    drawMarker(data.value[i]);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}

//地图上标记录音点
function drawMarker(markerData) {
    var lng = map.getCenter().getLng();
    var lat = map.getCenter().getLat();

    var marker1 = new AMap.Marker({
        icon: "img/water_blue.png",
        position: [markerData.lng, markerData.lat]
    });
    marker1.setMap(map);
    marker1.on('click', function () {
        window.location.href = "detail.html?markerLng=" + markerData.lng + "&markerLat=" + markerData.lat;
    });
    markers.push(marker1);
}

//播放最近录音
var playStation = "stop";//播放状态
var playPosition;//当前播放点
var playRecord;//当前播放录音
function initNearestPosition(currentP) {
    var minDistinceMarker;
    var minDistince;
    for (var i = 0; i < markers.length; i++) {
        var distince = distinceOf2Point(currentP, markers[i].getPosition());
        if (distince <= 100) {
            if (minDistince == null) {
                minDistince = distince;
                minDistinceMarker = markers[i];
            } else {
                if (minDistince > distince) {
                    minDistince = distince;
                    minDistinceMarker = markers[i];
                }
            }
        }
    }
    if (minDistinceMarker != null) {
        playPosition = minDistinceMarker;
        playRecord = initNearestRecord(playPosition);
        document.getElementById("playRecordBtn").disabled = false;
    } else {
        document.getElementById("playRecordBtn").disabled = true;
    }
}
function initNearestRecord(playPosition) {
    //根据点坐标获取官方录音
}
function playNearestRecord() {
    if (playRecord == null || playRecord == "undefined") {
        alert("return");
    }
    if (playStation == "stop") {
        //播放录音
        playStation = "play";
        document.getElementById("playRecordBtn").value = "暂停";
    } else {
        //暂停录音
        playStation = "stop";
        document.getElementById("playRecordBtn").value = "播放";
    }
}
//计算两点距离
function distinceOf2Point(pointFrom, pointTo) {
    return pointFrom.distance([pointTo.getLng(), pointTo.getLat()]); //返回number
}

var newMark = null;
//标记地图中心点
var markCenterPoint = function () {
    newMark = new AMap.Marker({
        icon: "img/Marker.png",
        position: map.getCenter(),
        draggable: true
    });
    newMark.setMap(map);
    newMark.on('click', function (e) {
//        debugger;
        location.href = "addRecord.html?Lng=" + e.lnglat.getLng() + "&Lat=" + e.lnglat.getLat() + "";
        document.getElementById("markDownBtn").disabled = false;
    });
}

//移动标记点
var moveMarker = function (e){
    newMark.moveTo( map.getCenter(),5000,function(k){return k});
}

//地图上新增标记点
function markDown() {
    markCenterPoint();
    map.on('mapmove', moveMarker);
    recording = true;
    document.getElementById("markDownBtn").disabled = true;
}

var loadingImg = document.querySelector("#load2");

loadingImg.addEventListener('click', recorderBegin);
