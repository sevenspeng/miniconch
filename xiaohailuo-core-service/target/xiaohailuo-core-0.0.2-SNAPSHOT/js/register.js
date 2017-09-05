function getLength(str) {
    return str.replace(/[^\x00-xff]/g, "xx").length;  //\x00-xff 此区间是单子节 ，除了此区间都是双字节。
}
function findStr(str, n) {
    var tmp = 0;
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) == n) {
            tmp++;
        }
    }
    return tmp;
}
window.onload = function () {
    var uName = $('.username')[0]
    var oName = $('.yzm')[0];
    var uPwd = $('.pwd')[0];
    var uPwd2 = $('.pwd2')[0];
    var submit = $('.submit')[0];


    var aP = document.getElementsByTagName('p');
    var name_msg = aP[0];
    var pwd_msg = aP[0];
    var pwd2_msg = aP[0];

    //用户名检测
    uName.onfocus = function () {
        name_msg.style.display = 'block';
        uName.style.border = '1px solid #fff';
    }
    uName.onblur = function () {
        if ($.trim(this.value) == '') {
            name_msg.innerHTML = '<i>用户名不能为空</i>';
            uName.style.border = '1px solid red';
        } else {
            name_msg.innerHTML = '';
            uName.style.border = '1px solid #fff';
        }
    }
    oName.onfocus = function () {
        name_msg.style.display = 'block';

        oName.style.border = '1px solid #fff';
    }

    oName.onblur = function () {
        // 含有非法字符 ，不能为空 ，长度少于5个字符 ，长度大于25个字符
        var tel = /^1[3|4|5|7|8][0-9]\d{8}$/;
        if (!tel.test(this.value)) {
            name_msg.innerHTML = '<i>手机号不正确</i>';
            oName.style.border = '1px solid red';
        }
        else {
            name_msg.innerHTML = '';
            oName.style.border = '1px solid #fff';
        }
    }

    //密码检测
    uPwd.onfocus = function () {
        pwd_msg.style.display = 'block';
        uPwd.style.border = '1px solid #fff';
    }
    uPwd.onblur = function () {
        var m = findStr(uPwd.value, uPwd.value[0]);
        var re_n = /[^\d]/g;
        var re_t = /[^a-zA-Z]/g;
        if (this.value == "") {
            pwd_msg.innerHTML = '<i>密码不可为空</i>';
            uPwd.style.border = '1px solid red';
        } else if (m == this.value.length) {
            pwd_msg.innerHTML = '<i>密码不可使用相同的字符</i>';
            uPwd.style.border = '1px solid red';
        } else if (this.value.length < 6 || this.value.length > 16) {
            pwd_msg.innerHTML = '<i>密码长度应为6到16个字符</i>';
            uPwd.style.border = '1px solid red';
        } else if (!re_n.test(this.value)) {
            pwd_msg.innerHTML = '<i>密码不能全为数字</i>';
            uPwd.style.border = '1px solid red';
        } else if (!re_t.test(this.value)) {
            pwd_msg.innerHTML = '<i>密码不能全为字母</i>';
            uPwd.style.border = '1px solid red';
        } else {
            uPwd.style.border = '1px solid #fff';
            pwd_msg.innerHTML = '';
        }
    }

    //确认密码
    uPwd2.onblur= function () {
        if (this.value != uPwd.value) {
            pwd2_msg.innerHTML = '<i></i>两次密码输入到不一致';
            uPwd.style.border = '1px solid red';
        } else if (this.value == "") {
            pwd2_msg.innerHTML = '<i></i>请输入密码';
            uPwd.style.border = '1px solid red';
        } else {
            uPwd2.style.border = '1px solid #fff';
            pwd2_msg.innerHTML = '';
        }

    }

    submit.onclick = function () {
        $("<div id='bg'></div>").appendTo("body").fadeIn(200);
        $("#bg").html("<img src='img/loading/loading3.gif' style='opacity: 0.9;width: 100%;margin-top: 100px;'/>");

        if (pwd2_msg.innerHTML != '') {
            return;
        }
        var nickName = uName.value;
        var mobile = oName.value;
        var pwd = uPwd.value;
        var registerMsg = JSON.stringify({
            "mobile": "" + mobile + "",
            "nickname": "" + nickName + "",
            "profilephoto": "",
            "password": "" + pwd + ""
        });
        $.ajax({
            url: signIn,
            type: "POST",
            async: false,
            data: registerMsg,
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            cache: false,
            success: function (data) {
                console.log(data);
                if (data.resultCode == 200) {
                    console.log('注册失败');
                    sessionStorage.userId = data.value.uid
                    window.location.href = "index.html";
                } else {
                    $("#bg").remove();
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("注册失败");
                $("#bg").remove();
            }
        });
    }

}







































