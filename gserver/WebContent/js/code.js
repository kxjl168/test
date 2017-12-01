var code; // 在全局 定义验证码
function createCode() {
	code = "";
	var codeLength = 4;// 验证码的长度
//	var checkCode = document.getElementById("checkCode");
//	checkCode.value = "";
	$("#checkCode").html("");
	var selectChar = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z');

	for (var i = 0; i < codeLength; i++) {
		var charIndex = Math.floor(Math.random() * 60);
		code += selectChar[charIndex];
	}
	if (code.length != codeLength) {
		createCode();
	}
	$("#checkCode").html(code);
//	checkCode.value = code;
}

function validate() {
	var inputCode = document.getElementById("code").value.toUpperCase();
	var codeToUp = code.toUpperCase();
	if (inputCode.length <= 0) {
		$('#errorMsg').html("请输入验证码！");
		$('#errorMsg').css("color","red");
		$('#errorMsg').css("font-size","12px");
		return false;
	} else if (inputCode != codeToUp) {
		$('#errorMsg').html("验证码输入错误！");
		$('#errorMsg').css("color","red");
		$('#errorMsg').css("font-size","12px");
		createCode();
		return false;
	} else {
		return true;
	}
}
