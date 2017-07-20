/**
 * Created by JINGDAYAN962 on 2016-09-26.
 */
//app base 路径
//var baseUrl="http://www.miniconch.cn/xiaohailuo/";
//var baseUrl="http://localhost:63342/xiaohailuo/";
//aliyun//var baseUrl = "http://"+window.location.host + "/xiaohailuo/";
var baseUrl = "http://"+window.location.host + "/";

//签到
var signInUrl = "core/add/record/sign";
//var signInUrl="111.txt"
//点赞
var likePost = baseUrl + "core/add/record/like";
//评论
var commentPost = baseUrl + "core/add/record/comment";
//根据ID查询详情
var queryRecordDetail = baseUrl + "core/query/record/detail";
//头像路径
var baseIconUrl = "";

//根据屏幕范围初始化地图页面录音点
var initScreemMaker = baseUrl + "core/query/record/list/2";
//var initScreemMaker = baseUrl + "initMarker.txt";

var initRecordList=baseUrl + "core/query/record/list";
//var initRecordList = "initRecordList.txt";

//初始化微信参数接口
var initWxConfigPath = baseUrl + "webchat/get/info/signature";

//用户注册接口
var SignIn = baseUrl + "core/user/register";

//登录接口
var login = baseUrl + "core/login";

//新增录音
var addRecord = baseUrl + "core/add/record/position";

//var loginPath="txt/login.text";

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
}

