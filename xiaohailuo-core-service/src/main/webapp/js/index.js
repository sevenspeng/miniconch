var currentPosition = null;
// 创建地图
var map;
map = new AMap.Map('container', {
    zoom: 17,
    resizeEnable: true
});

map.on("complete", function (e) {
    loadRecordMarkOfScreen();//根据屏幕对角线加载屏幕范围内的录音点
    enableSearch(); //放开搜索
    if (sessionStorage.currentPosition != undefined
        && sessionStorage.currentPosition != null) {
        var cp = sessionStorage.currentPosition;
        map.setZoomAndCenter(14, [cp.split(',')[0], cp.split(',')[1]]);
    }
});

map.on('moveend', function (e) {
    loadRecordMarkOfScreen();//根据屏幕对角线加载屏幕范围内的录音点
    sessionStorage.currentPosition = map.getCenter();
});
map.on('zoomchange', function (e) {
    loadRecordMarkOfScreen();//根据屏幕对角线加载屏幕范围内的录音点
    sessionStorage.currentPosition = map.getCenter();
});

// 給地图添加缩放工具条,默认显示在右下角
var toolBar = new AMap.ToolBar({
    position: "LB",
    ruler: false,
    noIpLocate: true,
    locate: true,
    liteStyle: true
});
//    map.addControl(toolBar);

//起点（用户位置）的marker标记
var startMarker = null;
AMapUI.loadUI(['overlay/SimpleMarker'], function (SimpleMarker) {

    //创建SimpleMarker实例
    startMarker = new SimpleMarker({
        //前景文字
        iconLabel: 'R',
        //背景图标样式
        iconStyle: 'blue',
        map: map,
        position: map.getCenter(),
        offset: new AMap.Pixel(-10, -32)
    });
    startMarker.hide();
    startMarker.on('click', function (e) {
        location.href = "addRecord.html?Lng=" + e.lnglat.getLng() + "&Lat=" + e.lnglat.getLat() + "";
    });
    startMarker.setMap(map);
});

var wrap = document.getElementsByClassName('wrap')[0];
//显示控制，执行后显示地图页面
var showLeftView = function () {
    AMap.event.removeListener(placeSearch.listElementClickHandler);
    AMap.event.removeListener(autoComplete.selectHandler);
    placeSearch.clear();
    wrap.className = 'wrap';
    //保存当前地图中心点
    sessionStorage.currentPosition = map.getCenter();
}
//显示控制，执行后显示搜索页面
var showRightView = function (onShowed) {
    wrap.className = 'wrap rightShow';
}
//点击返回，页面由搜索页面返回显示页面
AMap.event.addDomListener(document.getElementById('back'), 'click', showLeftView);

//播放器
var ap4 = new APlayer({
    element: document.getElementById('player4'),
    narrow: false,
    autoplay: false,
    showlrc: false,
    mutex: false,
    theme: '#ad7a86',
    mode: 'circulation',
    listmaxheight: '95px',
    music: [
        {
            title: '南桥',
            author: '小海螺-静静',
            url: 'http://www.miniconch.cn:8080/resource/audio/南桥.wav',
            pic: 'http://www.miniconch.cn:8080/resource/img/南桥.jpg'
        },
        {
            title: '离堆公园',
            author: '小海螺',
            url: 'http://www.miniconch.cn:8080/resource/audio/离堆公园.wav',
            pic: 'http://www.miniconch.cn:8080/resource/img/离堆公园.jpg'
        },
        {
            title: '伏龙观',
            author: '小编',
            url: 'http://www.miniconch.cn:8080/resource/audio/伏龙观.wav',
            pic: 'http://www.miniconch.cn:8080/resource/img/伏龙观.jpg'
        },
        {
            title: '飞沙堰',
            author: '佚名',
            url: 'http://www.miniconch.cn:8080/resource/audio/飞沙堰.wav',
            pic: 'http://www.miniconch.cn:8080/resource/img/飞沙堰.jpg'
        }, {
            title: '宝瓶口',
            author: '小海螺',
            url: 'http://www.miniconch.cn:8080/resource/audio/%E5%AE%9D%E7%93%B6%E5%8F%A3.wav',
            pic: 'http://www.miniconch.cn:8080/resource/img/%E5%AE%9D%E7%93%B6%E5%8F%A3.jpg'
        }
    ]
});

function showPlayer() {
    console.log('hello ');
    $('#playerContent')[0].style.height = 'auto';
    $('#forwardIcon')[0].setAttribute('src', 'img/down.png');
    $('#showPlayer')[0].onclick = function () {
        $('#playerContent')[0].style.height = '18px';
        $('#forwardIcon')[0].setAttribute('src', 'img/upward.png');
        $('#showPlayer')[0].onclick = function () {
            showPlayer();
        }
    }
}