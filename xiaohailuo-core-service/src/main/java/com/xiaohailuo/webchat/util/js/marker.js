/**
 * Created by JINGDAYAN962 on 2016-12-20.
 */
//请求屏幕氛围内的录音点
var markers = [];//保存标记点

//显示起始marker，并开启拖拽调整起始位置的功能
var showLocation = function (position) {
    console.log(position);
    if (markFlag) {
        startMarker.setPosition(position);
        startMarker.show();
        startMarker.setMap(map);
    }
    map.setCenter(position);
    startAdjustOrigin(); //开启拖拽地图调整定位点

}

var markFlag = false;
function markDown(obj) {
    if (obj.innerHTML == "标记") {
        markFlag = true;
        obj.innerHTML = "取消标记";
        showLocation(map.getCenter());
    } else {
        markFlag = false;
        obj.innerHTML = "标记";
        clearLocation();
    }
}

var clearLocation = function () {
    startMarker.hide();
}

function loadRecordMarkOfScreen() {
    var southwest = map.getBounds().getSouthWest();//获取屏幕西南角坐标
    var northeast = map.getBounds().getNorthEast();//获取屏幕东北角坐标

    var postData = JSON.stringify({
        "northeast": {"lng": northeast.lng, "lat": northeast.lat},
        "southwest": {"lng": southwest.lng, "lat": southwest.lat}

    });

    console.log(postData);
    console.log(initScreemMaker);

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
                        window.location.href = "list.html?makerLng=" + this.getPosition().getLng() + "&makerLat=" + this.getPosition().getLat();
                    });
                    markers.push(marker1);

                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}
