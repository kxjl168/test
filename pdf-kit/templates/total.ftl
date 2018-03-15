<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <title></title>
    <style type="text/css">
        body {
    font-family: "Leelawadee UI";  /*"Microsoft YaHei UI"; */
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
   
    <div style="width:475px;height:65px;padding-left:280px">
        <img src="${freeMarkerUrl}"  style="padding-left:450px" />
    </div>
    
  
   <div style="padding-left:210px;">ສາທາລະນະລັດ ປະຊາທິປະໄຕ ປະຊາຊົນລາວ</div>
     <div style="padding-left:150px;">ສັນຕິພາບ ເອກະລາດ ປະຊາທິປະໄຕ ເອກະພາບ ວັດທະນະຖາວອນ</div>
<br/>	



<div >
<div class="pre">Ministry of Public Security </div> <span class="snum">No. :.....................</span> 
</div>

<div >
<div class="pre2">Cyber Crime Prevention Center</div> <span class="snum">Vientiane Capital, Date :.....................</span> 
</div>

	<br/>						
<br/>	
<br/>	

 <div class="rtitle">${templateName}</div>
<br/>	
	<br/>						
<br/>
<div  class="tb">                               
  <table >
	<tbody >
	 
		<tr style="line-height: 1.1;border:1px solid  #777;">
		
		 <#list tb.head.items as  headitem>
		    
			<th style="background:green;color:white; height:${tb.head.height}px;width:${headitem.width}px; border:1px solid  white;"><b >${headitem.name}</b></th>
			
			</#list>
		</tr>
		

		  <#list tb.rows as row> 
		 <tr >
			  <#list row.items as rowitem> 
			<td  style="height:30px; border:1px solid white;" >${rowitem.name}</td>
		 	</#list>
			
		</tr>
		</#list>
	
		
	
		
	</tbody>
</table>
</div>
    
   
    <#if picUrl??>
     <!--动态生成的图片-->
    	<br/>						
<br/>
    <p></p>
    <div class="chartdiv">
        <img src="${picUrl}" class="chart" />
    </div>
    </#if>
    
   
</div>
<!--第一页结束-->
<!---分页标记-->

<!-- 
<span style="page-break-after:always;"></span>
-->

<!--第二页开始-->

<!-- 
<div class="page">
    <div>第二页开始了</div>
    <div>列表值:</div>

    

  

    

</div>
-->



<!--第二页结束-->
</body>
</html>