$(function () {
    $("#loading").ajaxStart(function () {
        $(this).html("<img src='/jqueryStu/images/loading.gif' />");
    });
    $("#loading").ajaxSuccess(function () {
        $(this).html("");
        // $(this).empty(); // 或者直接清除
    });

});

window.onload = function () {
    var aInput = document.getElementsByTagName('input');
    var oUser = aInput[0];
    var oPwd = aInput[1]
    var aI = document.getElementsByTagName('i')[0];
    var aI1 = document.getElementsByTagName('i')[1];
    var submitBtn = $('.submit')[0];

    //用户名检测

    oUser.onfocus = function () {
        aI.innerHTML = '';
        oUser.removeAttribute("placeholder");
    }

    oUser.onkeyup = function () {

    }

    oUser.onblur = function () {
        var tel = /1[3|4|5|7|8][0-9]\d{8}$/;
        if (!tel.test(this.value)) {
            aI.innerHTML = '手机号不正确';
        } else if (this.value == "") {
            aI.innerHTML = '手机号不可为空';
        }
    }

    //密码检测

    oPwd.onfocus = function () {
        aI1.innerHTML = '';
        if (oUser.value == "") {
            aI.innerHTML = '手机号不可为空';
        }
        oPwd.removeAttribute("placeholder");
    }
    oPwd.onblur = function () {
        if (this.value == "") {
            aI1.innerHTML = '密码不可为空';
        } else
            aI1.innerHTML = '';
        oPwd.setAttribute("placeholder", "请输入确认密码");
        oPwd.style.placeholder = '请输入确认密码';
    }
    submitBtn.onclick = function () {

        $("<div id='bg'></div>").appendTo("body").fadeIn(200);
        $("#bg").html("<img src='img/loading/loading3.gif' style='opacity: 0.9;width: 100%;margin-top: 100px;'/>");
        var mobile = $('.mobile')[0].value;
        var pwd = $('.psd')[0].value;
        var tel = /1[3|4|5|7|8][0-9]\d{8}$/;
        if (!tel.test(mobile) || mobile == "") {
            aI.innerHTML = '手机号不正确';
            return;
        } else if (pwd == "") {
            aI1.innerHTML = '密码不可为空';
            return;
        }
        var loginMsg = JSON.stringify({
            "mobile": "" + mobile + "",
            "password": "" + pwd + ""
        });

        $.ajax({
            url: loginURL,
            type: "POST",
            async: false,
            data: loginMsg,
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            cache: false,
            success: function (data) {
                console.log(data);
                if (data.resultCode == 200) {
                    console.log('登录成功');
                    sessionStorage.userId = data.value.uid
                    window.location.href = "index.html";
                } else {
                    $("#bg").remove();
                    $("#loginError").show();
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("登录失败");
                $("#bg").remove();
                $("#loginError").show();
            }
        });
    }

}