function msg(data) {
	// 匿名提示
	noty({
		text : data,
		layout : "center",
		timeout : 1000,
		type : "success"
	});
}

function error(data) {
	// 匿名提示
	noty({
		text : data,
		layout : "center",
		timeout : 1000,
		type : "error"
	});
}

function getImUrl() {
	var http = 'http://127.0.0.1:8080/gserver/';

	return http;
}

/**
 * http 非加密测试 同步
 * 
 * @param url
 * @param sendData
 * @param onsuccess
 * @param onfail
 */
function SZUMWS(url, sendData, onsuccess, onfail, showProgress) {

	console.log("sendData: " + sendData);
	var ecStr = sendData;// window.HMClient.AESEncrypt(sendData);
	// console.log("ecCrypt result: " + ecStr);
	console.log("url: " + url);

	var http_url = url;// url.substring(0, url.lastIndexOf('.')) +
						// '_test.action';
	if (showProgress)
		ajaxLoading();
	$.ajax({
		type : "POST",
		url : http_url,
		// 数据需要Aes加密
		data : {
			data : ecStr
		},
		dataType : "json",
		success : function(data) {

			ajaxLoadEnd();

			console.log("ajax return: " + data);
			// 数据需要Aes解密
			// var decryData =data.ResponseMsg;//
			// window.HMClient.AESDeCrypt(data.ResponseMsg);
			// var json = JSON.parse(decryData);
			onsuccess(data);

		},
		error : function(a, b, c) {

			ajaxLoadEnd();
			console.log("ajax error: " + a + b);
			onfail(a);
			// popupAlert("网络异常!");

		}
	});

}

function ajaxLoading() {
	var berthDocument = window.parent.document;
	// 创建背景层
	var bgDiv = $("<div id='bgDiv' class='ie'></div>");
	// 获取当前文档宽度作为背景层宽度
	var bdWidth = $(berthDocument).width();
	// 获取当前文档高度作为背景层高度
	var bdHeight = $(berthDocument).height();
	// 设置背景层样式
	bgDiv.css({
		'width' : bdWidth,
		'height' : bdHeight,
		'position' : "fixed",
		'top' : 0,
		'left' : 0,
		"z-index" : 100000,
		"background-color" : "#fff",
		"opacity" : "0.85",
		"-moz-opacity" : "0.85",

		'position' : "absolute"

	});

	var maskWidth = 200;
	var maskHeight = 90;

	// var loadingDiv = $('<div id="loadingDiv" style="border:1px;"><img
	// src="'+basePath+'/images/loading.gif" /><br/><a
	// style="font-size:14px;">正在加载数据，请稍候...</a></div>');

	// var url=getImUrl()+"szhmpt/android/images/loading.gif";

	var loadingDiv = $("<div class=\"datagrid-mask-msg ie \"></div>")
			.html(
					"<div class='loadimgdiv'><div src='1'  border='0' alt='' style='margin:10px 80px;' class='loadimg'></div></div><div style='color:#ddd;text-align:center;'>正在处理，请稍候...</div>")
			.css({
				display : "block",

				background : '#333',
				width : maskWidth,
				height : maskHeight,
				left : ($(document.body).outerWidth(true) - maskWidth) / 2,
				top : ($(window).height() - maskHeight) / 2
			});

	loadingDiv.css({

		'position' : "absolute",

		"z-index" : 999,
		"border-radius" : "5px",
		"-moz-border-radius" : "5px",
		"-webkit-border-radius" : "5px",
		"-moz-box-shadow" : "0 1px 2px rgba(0,0,0,0.5)",
		"-webkit-box-shadow" : "0 1px 2px rgba(0,0,0,0.5)",
		"text-shadow" : "0 -1px 1px rgba(0,0,0,0.25)",
		"border-bottom" : "1px solid rgba(0,0,0,0.25)"

	});
	// 将确认框添加到背景层中
	bgDiv.append(loadingDiv);
	// 将背景层添加 到页面中
	$(berthDocument).find("body").eq(0).append(bgDiv);
}
function ajaxLoadEnd() {
	$(window.parent.document).find("#bgDiv").remove();
}
