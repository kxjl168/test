function loadmenu(url) {

	window.location.href = getImUrl() + url;
};

function getRootMenu() {

	// $("#nav").html("");

	$.ajax({
		url : basePath + "/menu/getRootMenus.do",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (null != data && data.length != 0) {
				var menus = eval(data);
				// 初始化菜单单元格
				var tableStr = "<ul>";

				var sysdiv = "<span style=\"padding-top: 0px;\">";
				$.each(menus, function(i, menu) {
					// tableStr += "<td menuId='" + menu.menuId + "' menuUrl =
					// '"
					// + menu.menuUrl
					// + "'><a style='cursor: pointer;'><b/><span>"
					// + menu.menuName + "</span></td>";

					if (menu.menuName != '系统管理') {
						tableStr += "<li  menuId='" + menu.menuId + "' "
								+ " menuICO='" + menu.menuICO + "' "
								+ " menuUrl = '" + menu.menuUrl + "' " + ">"
								+ "<img alt='' src='" + webctx
								+ "/images/icon/Top bar_triangle.png' />"
								+ menu.menuName + "</li>";
					} else {
						sysdiv += "<label" + " menuICO='" + menu.menuICO + "' "
								+ " menuId='" + menu.menuId + "' menuUrl = '"
								+ menu.menuUrl + "'><img   alt='' src='"
								+ webctx
								+ "/images/setting_2.png' /></label></span> ";

					}

				});
				tableStr += "</ul>";
				// 把菜单追加到frameTopMenu div中
				$("#nav").html(tableStr + sysdiv);

				// $("#nav ul
				// li").eq(0).addClass("navliSelect").find("img").get(0).src=
				// webctx + "/images/icon/Top bar_triangle02.png";

				addMenuClickEvent();

				$("#nav ul li").eq(0).click();
			}
		}
	});

}

function initmenu(ul, curmenu) {

	var menus = [ {
		id : 1,
		url : 'page/company',
		menutext : '公司账号管理'

	},

	{
		id : 2,
		url : 'page/pccount',
		menutext : '子账号管理'

	}, {
		id : 3,
		url : 'page/device',
		menutext : '路由器管理'

	}, {
		id : 4,
		url : 'page/translog',
		menutext : '流量管理'

	}, {
		id : 5,
		url : 'page/set',
		menutext : '设置'

	}, ];
	
	var basePath=getImUrl();
	
	$.ajax({
		url : basePath + "menu/getRootMenus.do",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (null != data && data.length != 0) {
				 menus = eval(data);
				
				  if(data!=null&&data.toIndex=="true")
				  {
				  window.location.href = basePath + "page/pccountM";
				  return ;
				  }
				 
				 
				var ulDom = $(ul);

				var html = "";
				for ( var i = 0; i < menus.length; i++) {

					if (curmenu == menus[i].menuUrl)
						html += "<li class=\"active\"><a href=\"#\">" + menus[i].menuName
								+ "</a></li>";
					else
						html += "<li ><a href=\"#\"   onclick=\"javascript:loadmenu('"
								+ menus[i].menuUrl + "')\">" + menus[i].menuName + "</a></li>";
				}
				
				html += "<li ><a href=\"#\"   onclick=\"javascript:logout()\">登出 </a></li>";

				ulDom.html(html);
			}
		},
		error:function(error){
			document.location =getImUrl()+ "login.jsp";
			}
		});


	

}

//退出系统
function logout() {
	if (confirm("确定要退出吗？")) {
		document.location =getImUrl()+ "logout.do";
	}
}