<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<style type="text/css">

</style>
<link href="css/index.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
/*document.write("<scr"+"ipt src=\"${pageContext.request.contextPath}/manager/scripts/jquery-1.4.2.js\" language=\"JavaScript\" charset=\"utf-8\"></sc"+"ript>")
document.write("<scr"+"ipt src=\"${pageContext.request.contextPath}/manager/scripts/userManager.js\" language=\"JavaScript\" charset=\"utf-8\"></sc"+"ript>")
document.write("<scr"+"ipt src=\"${pageContext.request.contextPath}/manager/scripts/md5.js\" language=\"JavaScript\" charset=\"utf-8\"></sc"+"ript>")*/
</script>

<script type="text/javascript" src="/adserver/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/adserver/login/js/userManager.js"></script>
<script type="text/javascript" src="/adserver/login/js/md5.js"></script>

<script type="text/javascript">
    //添加用户
    function addUser(param) {
        alert(param);
    }    
    
    function validName() {
        var name = document.getElementById("name").value;
        alert(name);
        var data = {name:name};
	    $.ajax({
	            url:"/adserver/user/namevalid",
	            type:"post",
	            data:data,
	            success:function(a){
	                 alert(a);
	            }
	     });
    }
    
    function deleUser(id, name) {
        alert("准备删除用户： " + id);
        if (confirm("确定删除用户: " + name)) {
	        var url = "/adserver/user/deluser?id=" + id;
	        alert(url);
	        $.ajax({
	            url:url,
	            type:"get",
	            success:function(ret) {
	                alert(ret);
	                if (ret.result == "success") {
	                   alert("删除成功");
	                   window.location.reload();    
	                } else {
	                   alert("删除失败");
	                }
	            }
	        });
        }
        
    }
    
    function tryupdate(user) {
//        alert("更新用户资料");
           gain("cancle").style.display = "";
    gain("ready").style.display = "none";
    gain("sure").style.display = "none";
    gain("userTab").style.display = "";
    gain("sc").style.display = "";
    gain("password").focus();
    gain("password2").select();
    
   
        //var s = '{"name":"abc", "age":12}';
        //alert(s);
        //var u = eval('(' + s + ')');
        //alert(u.name);
        //alert(u.age);
        alert(user);
        var obj = eval('(' + user + ')');
        alert("用户：" + obj.name);
        //初始化 只能改密码跟菜单 
        var objName = document.getElementById("name");
        objName.readOnly = true;
        objName.value = obj.name;
        //id
        var objId = document.getElementById("id");
        objId.readOnly = true;
        objId.value = obj.id;
        var objNickName = document.getElementById("nickName");
        objNickName.value = obj.nickName;
        objNickName.readOnly = true;
        //密码
        var objPW = document.getElementById("password");
        var objPW2 = document.getElementById("password2");
        objPW2.value = "";
        objPW.value = "";
        
        var objPro = document.getElementById("projectIds");
        objPro.value = obj.projectIds;
        
        
       // if(null!=gain("userscope"+userscope) && "undefined"!=gain("userscope"+userscope)){
         //   gain("userscope"+userscope).checked=true;
        //}
    if(null!=gain("usertype"+obj.usertype) && "undefined"!=gain("usertype"+obj.usertype)){
            gain("usertype"+obj.usertype).checked=true;
    }
    
    if ("" != obj.menus) {
        var ms = obj.menus.split(",");
        var allMenu = document.body.getElementsByTagName("INPUT");
        for (var i = 0; i < ms.length; i++) {
            for (var j = 0; j < allMenu.length; j++) {
                // alert(allMenu[i].type);
                // 所有菜单权限
                if (allMenu[j].type == "checkbox") {
                    if (ms[i] == allMenu[j].value) {
                        // 选中已有权限
                        
                        allMenu[j].checked = true;
                    }
                }
            }
            
        }
    }
        
    }
    
    function update() {
        alert("更新用户");
         gain("menus").value = getMenus();
                     alert("用户menus：" + getMenus());
                     //用户类型
                     var userTypeObj=document.getElementById("userType");
//                     alert("userType初始值：  " + userTypeObj.value);
                     var types=document.getElementsByName("usertype");
//                     alert("长度： " + types.length);
                     for (var i = 0; i < types.length; i++) {
//                         alert(types[i].value);
//                         alert("是否被选中： " + types[i].checked);
                         if (types[i].checked) {
                             userTypeObj.value = types[i].value;
                         }
                     }
//                     alert("userType最后值：  " + userTypeObj.value);
        if (gainValue("password") != gainValue("password2")) {
            alert("两次输入密码不一致，请重新输入");
            gain("password").focus();
            return ;
        }
                     var user = $("#formMain").serialize();
         $.ajax({
            url:"/adserver/user/updateuser",
            type:"post",
            data:user,
            success:function(ret){
                alert(ret);
                if (ret.result == "success") {
                    alert("更改成功");
                    window.location.reload();
                } else {
                    alert("更改失败");
                }
            }
        });
    }
    
    function myfunction(param) {
             alert(param);
          //var obj = eval("("+ param + ")");
          //alert(obj.name);
          var objId = document.getElementById("id");
          alert("当前用户id：" + objId.value);
    }
    
</script>

</head>

<body>
<div id="wrap">
<div class="right-hand"  align="left"> 

     <div class="r-contentz"> 
     <!-- action="${pageContext.request.contextPath}/user/adduser" -->
         <form id="formMain" method="post"
						 >
                        <input type="text"" value="aa" id="mytest"  />
						 <input type="button" onclick='myfunction("{\"name\":\"abc\"}")' value="测试" />
						 
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-bottom: 5px;">
								<tr>
									<td width="36">
										<img src="images/ht_12.png" width="36" height="40" />
									</td>
									<td width="100%" background="images/ht_13.png">
										<strong style="color: #09F"> 当前位置 &gt;</strong>
										<strong><a style="color: #09F"
											href="${pageContext.request.contextPath}/manager/userManagerAction!view.action">系统配置</a>
										</strong>
										<strong style="color: #09F"> &gt; </strong>
										<strong><a style="color: #09F"
											href="${pageContext.request.contextPath}/manager/userManagerAction!view.action">用户管理</a>
										</strong>
									</td>
									<td width="6" align="right">
										<img src="${pageContext.request.contextPath}/manager/images/ht_15.png" width="6" height="40" />
									</td>
								</tr>
							</table>
							<%--          <div id="apDiv1">--%>
							<table width="100%"  border="0" align="left"
								cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<table width="100%"  border="0" cellspacing="1"
											cellpadding="0" bgcolor="#abdbf1">
										<tr>
													<td colspan="2" width="78%">
													<input type="button" value=" 添  加 " class="anniu02" id="ready"
														onmouseover="this.style.background='url(images/anniu02.png) no-repeat'"
														onmouseout="this.style.background='url(images/anniu12.png) no-repeat'"
														onclick="viewControll('ready')" 
														/>
												</td>
											<td colspan="2" width="22%" align="center"></td>
										</tr>
											<tr>
												<td bgcolor="#FFFFFF" style="padding: 5px; display: none"
													colspan=7 id="userTab">
													<table width="100%" border="0" cellspacing="1"
														cellpadding="0" bgcolor="#FFFFFF">
														
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																用户账号：
															</td>
															<td>
																<input size=30 type="text" name="name"
																	id="name" />
															</td>
														<tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																用户名：
															</td>
															<td>
																<input size=30 type="text" name="nickName"
																	id="nickName"/>
															</td>
														<tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																密&nbsp;&nbsp;&nbsp;&nbsp;码：
															</td>
															<td>
																<input size=30 onfocus="this.select();" type="password" id="password" name="password" align="left" width="88%">
															</td>
														</tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																再次输入：
															</td>
															<td>
																<input size=30 onfocus="this.select();" type="password" name="password2" id="password2" width="88%"/>
															</td>
														</tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																用户组：
															</td>
															  1:内部员工, 2:手机厂家  
															<td>
																<label for="userscope1"><input id="userscope1" name="manager.userscope"  type="radio" value="1"  checked="checked"/>&nbsp;&nbsp;内部员工</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<label for="userscope2"><input id="userscope2" name="manager.userscope"  type="radio" value="2" />&nbsp;&nbsp;手机厂家&nbsp;&nbsp;</label>

															</td>
															
														</tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																用户类型：
															</td>
                                                                 <input type="hidden" id="userType" name="userType" value="-1"/>
                                                                 <!--  1:管理员, 2:客户, 3:商务人员, 4:广告平台客户, 5:酷果平台管理员（包括业务员）  -->
                                                            <td>
                                                                <label for="usertype1"><input id="usertype1" name="usertype"  type="radio" value="1"  checked="checked"/>&nbsp;&nbsp;管理员&nbsp;&nbsp;</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <label for="usertype2"><input id="usertype2" name="usertype"  type="radio" value="2" />&nbsp;&nbsp;客户&nbsp;&nbsp;</label>
                                                                <label for="usertype3"><input id="usertype3" name="usertype"  type="radio" value="3"  checked="checked"/>&nbsp;&nbsp;商务人员&nbsp;&nbsp;</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <label for="usertype4"><input id="usertype4" name="usertype"  type="radio" value="4" />&nbsp;&nbsp;广告平台客户&nbsp;&nbsp;</label>
                                                                <label for="usertype5"><input id="usertype5" name="usertype"  type="radio" value="5"  checked="checked"/>&nbsp;&nbsp;酷果平台管理员&nbsp;&nbsp;</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                            </td>
															
														</tr>
															<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																align="left" width="12%">
																项目ID：
															</td>
															<td>
																<input size=30 type="text" name="projectIds" id="projectIds" width="88%"/><div style="color:gray;display:inline;padding-left:25px;font-size:14px;">如:100008;100034</div>
															</td>
														</tr>
														<tr>
															<td style="border-left-width: 1px; padding: 5px;"
																width="12%">
																菜单权限：
															</td>
															<td width="88%">
																<c:forEach items="${menus}" var="m">
																	<c:forEach items="${m}" var="mn" varStatus="status">
                                                                        
																		<c:if test="${mn.parentId eq -1}">
																			<div id="pranetDiv_${mn.id }">
																				<label for="parent_${mn.id}" style="color:black;font-size:14px;">
																					<input onclick="clickParentMenu('${mn.menuId }');"
																						id="parent_${mn.menuId}" type="checkbox"
																						name="parent_${mn.menuId}" value="${mn.menuId}" />
																					【${mn.name}】
																				</label>
																				&nbsp;&nbsp;
																		</c:if>
																		<c:if test="${mn.parentId > 0}">
																			<label for="child_${mn.id}">
																				<input
																					onclick="clickChildMenu('${mn.id}','${mn.parentId}');"
																					id="child_${mn.id}" type="checkbox"
																					name="parent_${mn.parentId }" value="${mn.id}" />
																				${mn.name}
																			</label>
																		</c:if>
																		<c:if test="${status.last}">
																			</div>
																		</c:if>
																	</c:forEach>
																</c:forEach>
															</td>
														</tr>

														<tr>
															<td colspan="3" align="left"  style="padding-top: 5px;padding-bottom: 5px;padding-left: 160px;">
																<input type="button""
																	onclick="validateForm();"
																	value=" 确认添加 " class="anniu02" id="sure"
																	style="display: none"
																	onmouseover="this.style.background='url(images/anniu02.png) no-repeat'"
																	onmouseout="this.style.background='url(images/anniu12.png) no-repeat'" />
																<input type="button" value=" 保存修改 " class="anniu02"
																	id="sc" style="display: none"
																	onclick="update();"
																	onmouseover="this.style.background='url(images/anniu02.png) no-repeat'"
																	onmouseout="this.style.background='url(images/anniu12.png) no-repeat'" />
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																<input type="button" value=" 取  消 " class="anniu02"
																	id="cancle" style="display: none"
																	onmouseover="this.style.background='url(images/anniu02.png) no-repeat'"
																	onmouseout="this.style.background='url(images/anniu12.png) no-repeat'"
																	onclick="viewControll('cancle')" />

																
																<input type="hidden" id="pwdHidden"
																	values="${user.password }" />

																<input type="hidden" name="id" value="0" id="id" />
																<input type="hidden" name="menus" id="menus" values="${manager.menus }"/>

															</td>
														</tr>
														<tr>
															<td></td>
															<td align="cener">
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<input type="hidden" id="pSize" value="${page.pageSize}" />
											<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}"/>
										</form>
										
											<td width="10%"  style="border-left-width: 1px;" bgcolor="#e7ffce" align="center">
													<b>用户名 
												</td>
												<td  width="13%" style="border-left-width: 1px;"  bgcolor="#e7ffce" align="center">
													<b>用户类型 
												</td>
											
												<td width="30%"  bgcolor="#e7ffce"
													style="padding-top: 5px; padding-bottom: 5px; line-height: 1"
													align="center">
													<b>菜单权限 
												</td>
												<td width="15%" style="border-left-width: 1px;" bgcolor="#e7ffce" align="center">
													<b15>项目ID 
												</td>
												<td width="10%"  bgcolor="#e7ffce"
													style="padding-top: 5px; padding-bottom: 5px; line-height: 1"
													align="center">
													<b>操作 
												</td>
		                				<tr align="center">
											
											<c:forEach items="${users}" var="a">
												<tr  bgcolor="#FFFFFF" onmousemove="this.bgColor='#C2FED6';" onmouseout="this.bgColor='#ffffff';" >
													
													<td
														style="padding-top: 5px; padding-bottom: 5px; line-height: 1"
														align="center">
														${a.name}(${a.nickName })
													</td>
													<td
														style="padding-top: 5px; padding-bottom: 5px; line-height: 1"
														align="center">
														  1:内部员工, 2:手机厂家  
														<c:set value="" var="utype"></c:set>
														<c:if test="${a.userType==1 }"><c:set var="utype" value="管理员"></c:set></c:if>
														<c:if test="${a.userType==2 }"> <c:set var="utype" value="客户"></c:set></c:if>
														<c:if test="${a.userType==3 }"><c:set var="utype" value="商务人员"></c:set></c:if>
														<c:if test="${a.userType==4 }"><c:set var="utype" value="广告平台客户"></c:set></c:if>
														<c:if test="${a.userType==5 }"><c:set var="utype" value="酷果平台管理员"></c:set></c:if>
														 
														<c:if test="${a.userscope eq 1}">内部员工
															<c:if test="${!empty utype}">
															<span style='color:#09F;'>(${utype})</span></c:if>
														</c:if>
														<c:if test="${a.userscope eq 2}">手机厂家
															<c:if test="${!empty utype}">
																<span style='color:#09F;'>(${utype})</span></c:if>
														</c:if>
														 
													</td>
													<td
														style="padding-top: 5px; padding-bottom: 5px; text-align: center">
														<c:forEach var="i" begin="0" end="${(fn:length(a.menus))/75}" step="1">  
      														${fn:substring(a.menus,i*75,(i+1)*75)}<br/>
    				   									</c:forEach>
													</td>
													<td
														style="padding-top: 5px; padding-bottom: 5px; line-height: 1"
														align="center">
														${a.projectIds}
													</td>
												
													<td  style="line-height: 1" align="center">
														<input type="button" value="修改" class="anniu"
															onmouseover="this.style.background='url(images/anniu.png) no-repeat'"
															onmouseout="this.style.background='url(images/anniu1.png) no-repeat'"
															 onclick='tryupdate("${a}");'
															/>
                                                    <!--onclick="modify('${a.id}','${a.name}','${a.nickName}','${a.menus }','${a.password}',${a.userscope },'${a.projectIds}',${a.userType })"  -->
															<input type="button" value="删除" class="anniu"
																onmouseover="this.style.background='url(images/anniu.png) no-repeat'"
																onmouseout="this.style.background='url(images/anniu1.png) no-repeat'"
																onclick="deleUser('${a.id}', '${a.name}')"
																/>

														</td>
													</tr>
												</c:forEach>
											<tr>
												<td colspan="5" bgcolor="#FFFFFF" style="padding:5px;" align="center">
												 <%@include file="paging.jsp" %>
												</td>
											</tr>
									               </table>
									            </td></tr>
								
											
										</table>
									</td>
								</tr>
							</table>
     </div>
</div>
</div>

<script type="text/javascript">
function setPage(pageNo){
		var rootPath="${pageContext.request.contextPath}";
		document.getElementById("pageNo").value=pageNo;
		document.getElementById("formMain").action=rootPath+"/manager/userManagerAction!view.action";
		document.getElementById("formMain").submit();
}
</body>
</script>
</html>
