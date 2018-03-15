<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
    <style type="text/css">
        body {
    font-family: "Microsoft YaHei UI";
        }
        .center{
            text-align: center;
            width: 100%;
        }
    
    </style>
</head>
<body>
<!--第一页开始-->
<div class="page" >
    <div class="center"><p>${templateName}</p></div>
    <div><p>iText官网:${ITEXTUrl}</p></div>
    <div><p>FreeMarker官网:${freeMarkerUrl}</p></div>
    <div><p>JFreeChart教程:${JFreeChartUrl}</p></div>
    <!--外部链接-->
    <p>静态logo图</p>
    <div>
        <img src="${imageUrl}" alt="美团点评" width="110" height="20.8"/>
    </div>
    
    
    <#if picUrl !="" >
     <!--动态生成的图片-->
    <p>图表数据</p>
    <div>
        <img src="${picUrl}" alt="我的图片" />
    </div>
    </#if>
    
   
</div>
<!--第一页结束-->
<!---分页标记-->
<span style="page-break-after:always;"></span>
<!--第二页开始-->
<div class="page">
    <div>第二页开始了</div>
    <div>列表值:</div>

    

 <table class="tb" style=" border-collapse: collapse;
    border-spacing: 0;">
	<tbody >
	 
		<tr style="line-height: 1.5;border:1px solid  #777;">
		
		 <#list tb.head.items as  headitem>
		    
			<th style="height:${tb.head.height}px;width:${headitem.width}px; border:1px solid  #777;"><b >${headitem.name}</b></th>
			
			</#list>
		</tr>
		

		  <#list tb.rows as row> 
		 <tr >
			  <#list row.items as rowitem> 
			<td  style="height:30px; border:1px solid solid;" >${rowitem.name}</td>
		 	</#list>
			
		</tr>
		</#list>
	
		
	
		
	</tbody>
</table>

    

</div>


<!--第二页结束-->
</body>
</html>