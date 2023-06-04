$(function () {
    $('#_js_loginBtn').click(function () {
        var val = $('#inputPassword').val();
        if (/^1\d{10}$/g.test(val)) {
            $.get(domainUrl + "/users/checkPhone", {phone:val}, function (data) {
                if(!data){
                    $('#inputPassword').next().text('').hide();
                    $('.login-box').hide();
                    $('.signup-box').show();
                    $("#phone").val(val);
                }else{
                    $('#inputPassword').next().text('手机号码已注册.').show()
                }
            })
        } else {
            $('#inputPassword').next().text('手机号码格式不正确').show()
        }
    });
    //短信发送
    $('.vcode-send').click(function () {
        if ($(this).hasClass('disabled')) {
        } else {
            var self = $(this);
            var count = 10;
            self.addClass('disabled');
            self.text(count + '秒后重新获取');
            var timer = setInterval(function () {
                count--;
                if (count > 0) {
                    self.text(count + '秒后重新获取');
                } else {
                    clearInterval(timer);
                    self.text('重新获取验证码');
                    self.removeClass("disabled");
                }
            }, 1000);

            var phone = $("#phone").val();
            $.get(domainUrl + "/users/sendVerifyCode", {phone:phone}, function (data) {
                console.log(data);
                if(data.code === 200){
                    popup("发送成功")
                }else{
                    popup(data.msg);
                }
            })
        }
    });

    //注册
    $("#editForm").ajaxForm({
        url:domainUrl + "/users/regist",
        type:"POST",
        success:function (data) {
            if(data.code === 200){
                location.href = "/login.html";
            }else{
                popup(data.msg);
            }
        }
    })
});