var searchInput = document.getElementById('search');
//输入提示组件，在searchInput输入文字后，将自动显示相关的地点提示
var autoComplete = new AMap.Autocomplete({
    input: searchInput,
    type: '风景名胜',
    citylimit: false,
    noshowDistrict: true
});
//POI搜索组件，用来根据输入框中的值搜索相关的POI信息
var placeSearch = new AMap.PlaceSearch({
    panel: 'searchResult',
    type: '风景名胜',
    pageSize: 8,
    radius: 10000,
    citylimit: false
});
//点击起点或者终点输入框的时候打开搜索界面，在搜索结果点击选择任一POI的时候执行onSelectCallback回调
var onInputClick = function (initText, onSelectCallback) {
    if (initText !== '请输入景点') {
        searchInput.value = initText;
    } else {
        searchInput.value = '';
    }
    showRightView(); //打开搜索界面
    //当在输入提示结果列表选中一个之后，触发POI搜索的关键字搜索
    autoComplete.selectHandler = AMap.event.addListener(autoComplete, 'select', function (e) {
        placeSearch.setType('风景名胜');
        placeSearch.search(e.poi.name)
    });
    //当在POI搜索结果列表选中一个之后，触发onSelectCallback选中回调
    placeSearch.listElementClickHandler = AMap.event.addListener(placeSearch, 'listElementClick', function (e) {
        onSelectCallback(e.data);
        showLeftView();
    })

};
//点击搜索按钮的时候执行关键字搜索
AMap.event.addDomListener(document.getElementById('searchButton'), 'click', function () {
    placeSearch.setType('风景名胜');
    placeSearch.search(searchInput.value);
});

//从搜索结果点击选择一个POI之后执行，设置起点位置为POI的位置
var onOriginSelected = function (poi) {
    origin.position = poi.entr_location || poi.location;
    origin.innerHTML = poi.name;
    if (markFlag) {
        startMarker.setPosition(origin.position);
        startMarker.show();
    }
    startMarker.setMap(map);
    map.setCenter(origin.position);
    startAdjustOrigin();

    loadRecordMarkOfScreen();//初始化屏幕内录音点
};
/*//从搜索结果点击选择一个POI之后执行，设置终点位置为POI的位置
 var onDestinationSelected = function (poi) {
 destination.position = poi.entr_location || poi.location;
 destination.innerHTML = poi.name;
 endMarker.setMap(map);
 endMarker.setPosition(destination.position);
 if (origin.position && destination.position) {
 driving.search(origin.position, destination.position)
 }
 };*/
//定位结束后执行，启用搜索
var enableSearch = function () {
    //点击起点输入的时候,打开搜索界面
    AMap.event.addDomListener(origin, 'click', function (e) {
        stopAdjustOrigin();
        if (origin.innerHTML !== '请输入景点') {
            //如果已经有起始位置，搜索页面打开默认显示定位位置的周边搜索结果
            placeSearch.searchNearBy('', origin.position);
        }
        onInputClick(origin.innerHTML, onOriginSelected)
    });
    /*//点击终点输入的时候,打开搜索界面
     AMap.event.addDomListener(destination, 'click', function() {
     stopAdjustOrigin();
     if (destination.innerHTML !== '你要去哪儿') {
     //如果已经有目的地，搜索页面打开默认显示目的地的周边搜索结果
     placeSearch.searchNearBy('', destination.position);
     }
     onInputClick(destination.innerHTML, onDestinationSelected)
     });*/
}