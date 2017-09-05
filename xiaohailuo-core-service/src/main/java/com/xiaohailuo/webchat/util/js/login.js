/**
 * Created by SONGJIAO960 on 2016-11-01.
 */
function login() {
    var telNo = $("#telNo1")[0].value.replace(/(^\s*)|(\s*$)/g, "");
    var passWord = $("#passWord1")[0].value.replace(/(^\s*)|(\s*$)/g, "");
    if (telNo == "" || passWord == "") {
        alert("信息不全，请重新输入");
    }
    else {
        alert("登录成功");
    }
}

function signIn(){
    alert("注册");
}