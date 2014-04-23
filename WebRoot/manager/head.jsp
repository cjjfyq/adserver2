<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Pragma", "No-cache");//HTTP 1.1 
	response.setHeader("Cache-Control", "no-cache");//HTTP 1.0 
	response.setHeader("Expires", "0");//防止被proxy
%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"></META>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"></META>
<META HTTP-EQUIV="Expires" CONTENT="0"></META>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
	<title>**平台</title>
	<link href="${pageContext.request.contextPath}/manager/css/index.css"
		rel="stylesheet" type="text/css" />
	<style type="text/css">
INPUT {
	border-color: #ABDBF1;
	border-width: 1px;
	border-top-color: red;
}
</style>
<script type="text/javascript">
document.write("<scr"+"ipt src=\"${pageContext.request.contextPath}/manager/js/jquery-1.6.4.min.js\" language=\"JavaScript\" charset=\"utf-8\"></sc"+"ript>")
</script>

</head>


<div id="header" style="display: inline;">
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td width="12" align="left" valign="top">
				<img src="images/ht_04.gif" width="12" height="33" />
			</td>
			<td width="81%" align="left" valign="middle"
				background="images/ht_05.gif">
				<b>${sessionScope['Manager'].username}${sessionScope['Custom'].customname}${sessionScope['Bdlink'].bdname}</b>
				您好！欢迎登陆
			</td>
			<td width="18.5%" align="right" valign="middle"
				background="images/ht_05.gif">
				<a href="${pageContext.request.contextPath}/logout.action">退出系统</a>
			</td>
			<td width="12" align="left" valign="top"
				style="color: #FFF; line-height: 32px;">
				<img src="images/ht_05-03.gif" width="12" height="33" />
			</td>
		</tr>
	</table>
</div>

<div id="nav">
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="72" align="left" valign="middle"
				background="${pageContext.request.contextPath}/manager/images/ht04.gif">
				<img src="images/ht05.png" height="72" />
			</td>
		</tr>
	</table>
</div>

<div id="mainbody" align="center">
	<div class="left-hand" align="center">
		<table width="194" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="38" align="left" valign="middle"
					background="images/htl_18.png" style="color: #FFF;">
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**平台</strong>
				</td>
			</tr>
			<tr>
				<td height="180" align="left" valign="top"
					background="images/htl_22.png">

					<div id="main">

						<c:if var="fl"
							test="${sessionScope['Manager']==sessionScope['loginManager']}">
							<c:set var="parent" value="${sessionScope['permission']}">
							</c:set>
							<c:forEach items="${parent}" var="pm" varStatus="status">
								<c:set var="key" value="${pm.key}" />
								<c:if test="${pm.key eq 0}">
									<c:forEach items="${parent[pm.key] }" var="m"
										varStatus="status">
										<div id="t${status.index+1}" class="ltag">
											<a href="###" style="display: block;"
												onclick="menuControl(${m.id},'${m.name}','${pageContext.request.contextPath}/${m.url}')"
												id="${m.id}_1">${m.name } ▷</a>
										</div>
										<div id="${m.id}" class="hidden" style="display: none;">
											<c:forEach var="sub"
												items="${sessionScope['permission'][m.id]}">
												<ul>
													<li class="li">
														<input type="button"
															onclick="ChildMenu(${m.id},'${pageContext.request.contextPath}/${sub.url }','${m.name}','${sub.name}');"
															value="${sub.name}"
															style="border: 0; cursor: pointer; background-color: transparent" />
													</li>
												</ul>
											</c:forEach>
										</div>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td align="left" valign="top">
					<img src="images/ht_28.png" width="194" height="6" />
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	//刷新缓存
	//$.post("${pageContext.request.contextPath}/manager/menuAction!refreshPermission.action", { version:Math.random()});
	var rootPath='${pageContext.request.contextPath}';
	var loginManager ='${sessionScope.Manager}';
	if(loginManager==''){  //会话过期
		window.location.href=rootPath+"/login.jsp?version="+Math.random();
	}
	
var rootPath='${pageContext.request.contextPath}';
var showMenuId="${sessionScope['showMenuId']}"; //'<%=request.getParameter("showMenuId")%>';
var showMenuName="${sessionScope['showMenuName']}"; //'<%=request.getParameter("showMenuName")%>';
var proCount = 0;
var openMenuIdParam= "${sessionScope['opendedMenuId']}"; //'<%=request.getParameter("opendedMenuId")%>';
var opendedChildMenuName="${sessionScope['opendedChildMenuName']}"; //'<%=request.getParameter("opendedChildMenuName")%>';
var opendedMenuId=0;
if(openMenuIdParam!=""){
	opendedMenuId=openMenuIdParam; //解决跳转后仍能打开多个父菜单
}

if(showMenuId!="") {
	//页面跳转前，如果展开了父菜单，页面跳转后仍展开显示
	$("#"+showMenuId).css("display","block");
	//红色高亮跳转前点击的子菜单
	if(opendedChildMenuName=='null'){
		//页面跳转前点击的菜单 没有子菜单
		$("#"+showMenuId+"_1").css("color","#09F");
		$("#"+(showMenuId+"_1")).html(unescape(showMenuName)+" ▷");
	}else{
	    proCount++;  //没子菜单的不改变
	    $("#"+(showMenuId+"_1")).html(unescape(showMenuName)+" ◢");
		$("#"+opendedMenuId+"_1").css("color","#09F");
		$("#"+showMenuId+" input").each(function(){
				if(this.value==unescape(opendedChildMenuName)){
					this.style.color="#09F";
				}else{
					this.style.color="black";
				}
			});
	}
	
}

function menuControl(id,name,url) {
	if($("#"+id).html().replace(/(^\s*)|(\s*$)/g, "")==""){
		//没有子菜单
		$.post(rootPath+"/manager/menuAction!setGuideMenuParam.action", { showMenuId:id, showMenuName: escape(name),opendedMenuId:opendedMenuId,opendedChildMenuName:null},
		function(){
			window.location.href=url;
		} );
	}else{
	    
	   //有子菜单
		if(0!=opendedMenuId){
				//只允许展开一个 
				$("#"+opendedMenuId).css("display","none"); //隐藏上一个打开的菜单列表
				$("#"+opendedMenuId+"_1").css("color","black");
				if(id!=opendedMenuId){
					$("#"+id).css("display","block"); //显示刚打开的菜单列表
					$("#"+showMenuId+"_1").css("color","black");  //s
					$("#"+id+"_1").css("color","#09F");
					var opendMenuName=$("#"+(opendedMenuId+"_1")).html().substring(0,$("#"+(opendedMenuId+"_1")).html().length-1);
					$("#"+(opendedMenuId+"_1")).html(opendMenuName+" ▷");
					opendedMenuId=id;
				}else{
					opendedMenuId=0;
				}
				if(proCount==0){
						proCount++;
				}else{
						proCount--;
					}
				if(document.getElementById(opendedMenuId+"_1")!=null){
					var opendMenuName=$("#"+(opendedMenuId+"_1")).html().substring(0,$("#"+(opendedMenuId+"_1")).html().length-1);
					$("#"+(opendedMenuId+"_1")).html(opendMenuName+" ◢");
				}else{
					$("#"+id+"_1").html(name+" ▷");
					
				}
			}else{
			    $("#"+showMenuId+"_1").css("color","black");
				$("#"+id+"_1").css("color","#09F");
				//opendedMenuId=id; //已展开
				if(proCount==0) {
					opendedMenuId=id; //已展开
					$("#"+id).css("display","block");
					$("#"+id+"_1").html(name+" ◢");
					$("#"+id+"_1").css("color","#09F");
					proCount++;
				} else {
					opendedMenuId=0;  //已隐藏
					$("#"+id).css("display","none");
					$("#"+id+"_1").html(name+" ▷");
					$("#"+id+"_1").css("color","black");
					proCount--;
				}
			}
			
		
	}
	
}
/*
*
*string:原始字符串
*substr:子字符串
*isIgnoreCase:忽略大小写
*/
function contains(string,substr,isIgnoreCase)
{
    if(isIgnoreCase)
    {
     string=string.toLowerCase();
     substr=substr.toLowerCase();
    }
     var startChar=substr.substring(0,1);
     var strLen=substr.length;
         for(var j=0;j<string.length-strLen+1;j++)
         {
             if(string.charAt(j)==startChar)//如果匹配起始字符,开始查找
             {
                   if(string.substring(j,j+strLen)==substr)//如果从j开始的字符与str匹配，那ok
                   {
                         return true;
                   }  
             }
         }
         return false;
}

function ChildMenu(id,url,name,childName){
	var and ="?";
	if(contains(url,"?",false)){
		and="&";
	}
	$.post(rootPath+"/manager/menuAction!setGuideMenuParam.action", { showMenuId:id, showMenuName: escape(name),opendedMenuId:opendedMenuId,opendedChildMenuName:escape(childName) },
	function(){
		window.location.href=encodeURI(url);
	});
	
}
</script>