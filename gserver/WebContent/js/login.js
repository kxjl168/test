$(function() {

	//alert(1);
	
	
	
	

	// 添加回车事件
	$("#password").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			checkLogin();
		}
	});
	
	// 添加回车事件
	$("#code").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			checkLogin();
		}
	});

	//alert(top.location + "/" + this.location);

	if (top.location != this.location)
		top.location.href=basePath+"/loginin.do";

	// 全局的ajax访问，处理ajax清求时sesion超时
	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(XMLHttpRequest, textStatus) {
			var sessionstatus = XMLHttpRequest
					.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") {
				// 如果超时就处理 ，指定要跳转的页面
				window.location.href("loginin.do");
			}
		}
	});
	
	//code.js 验证码
	createCode();
	
	$("#loginbtn").focus();
});

function checkLogin() {
	
	window.location.href = basePath + "/page/entry";
	return;
	
	
	if(!validate())
		return;
	
	var userName = $("#username").val();
	var passWord = $("#password").val();
	if (userName.length == 0) {
		$.messager.alert("操作提示", "请输入用户名！", "info");
		$("#username").focus();
		return;
	} else if (passWord.length == 0) {
		$.messager.alert("操作提示", "请输入密码！", "info");
		$("#password").focus();
		return;
	} else {
		$.ajax({
			url : basePath + "/exlogin.action",
			type : "post",
			data : {
				"username" : userName,
				"password" : passWord
			},
			dataType : "json",
			success : function(data) {
				if (data.sucess) {
					window.location.href = basePath + "/main.do";
				} else {
					$.messager.alert("操作提示", data.msg, "info");
				}
			}
		});
	}
}