<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%

String hostName = request.getServerName();
if("lbsg.nanjigu.com".equals(hostName)){ 
	response.sendRedirect("/lbsg");
	
	Thread.sleep(100000);
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background:
		url(${pageContext.request.contextPath}/images/adminD_02-15.jpg)
		repeat-y center;
	background-color: #d3eafb;
}

body,td,th {
	font-size: 13px;
}

.rightC {
	width: 40%;
}

.input1 {
	width: 68%;
	height: 20px;
}

.input2 {
	width: 30%;
	height: 20px;
}
</style>
		<link href="${pageContext.request.contextPath}/css/login.css"
			type=text/css rel=stylesheet>
		<script type="text/javascript" src="./js/login.js"></script>
		<script type="text/javascript">
		      function login() {
		          var nameObj = document.getElementById("name");
		          //alert(nameObj.value);
		          if (nameObj.value == "") {
		              alert("请输入用户名");
		              return ;
		          }
		          
		          var passwordObj = document.getElementById("password");
		          //alert(passwordObj.value);
		          if (passwordObj.value == "") {
		              alert("请输入密码");
		              return ;
		          }
		          
		          var form = document.forms[0];
		          form.method = "post";
		          form.action = "/adserver/dologin";
		          form.submit();
		      }
		
        </script>
	</head>

	<body id=userlogin_body>
		<form action="${pageContext.request.contextPath}/doLogin"
			method="post" id="mainform">
			<table id="__01" width="591" height="250" border="0" cellpadding="0"
				cellspacing="0" align="center" style="margin-top: 180px;">
				<tr height="91">
					<td width="412"
						style="background: url('${pageContext.request.contextPath}/manager/images/login_01.gif');">
						<img
							src="${pageContext.request.contextPath}/manager/images/login_logo_01.gif">
					</td>
					<td>
						<img
							src="${pageContext.request.contextPath}/manager/images/login_02.gif"
							width="179" height="91" alt="">
					</td>
				</tr>
				<tr height="166">
					<td width="412"
						style="background: url('${pageContext.request.contextPath}/manager/images/login_03.jpg');">
						<table border="0" cellpadding="0" cellspacing="0" width="100%"
							height="120">
							<tr>
								<td class="rightC" align="right">
									用户名：
								</td>
								<td>
									<input type="hidden" tabindex="1" class="input1"
										name="loginIdentity" id="manager" value="manager" ${empty
										loginIdentity==true||loginIdentity== 'manager'?'checked':''}/>
									<input value="${user.name}" name="name"
										class="input1" type="text" id="name" size="16"
										tabindex="4" />


									<script type="text/javascript">
                document.getElementById("username").focus();
                </script>
								</td>
							</tr>
							<tr>
								<td class="rightC" align="right">
									密&nbsp;&nbsp;码：
								</td>
								<td>
									<input tabindex="5" name="password" type="password" value="${user.password}"
										id="password" class="input1" size="16" />
								</td>
							</tr>
							<tr>
								<td class="rightC" align="right">
									验证码：
								</td>
								<td>
									<input tabindex="6" type="text" maxlength=4 size=4
										name="validateCode" id="validateCode" class="input2" />
									<img
										src="./imageCode.jsp"
										id="checkCode"
										style="left: 405px; width: 54px; top: 250px; height: 22px;" />
									<a href="###"
										onclick='document.getElementById("checkCode").src="./imageCode.jsp#"+new Date().getTime()'>看不清,
										换一张</a>

									<font color="red" size="2">
									        <b id="actionmessage">
									        <!-- 
									        <s:if test="#request.actionMessages.size>0">
												<br />
												<s:iterator value="%{#request.actionMessages}"
													id="actionMessage">${actionMessage}</s:iterator>
											</s:if> 
											${loginResult }
											-->
											${loginResult }
											</b> 
									</font>
									
								</td>
							</tr>
						</table>
					</td>
					<td width="179"
						style="background: url('${pageContext.request.contextPath}/manager/images/login_04.jpg');"
						valign="top">
						<input type="image"
							src="${pageContext.request.contextPath}/manager/images/login_but_07.gif"
							style="margin: 27px 18px;"
							onclick='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("IbtnEnter", "", true, "", "", false, false))' />
					</td>
				</tr>
				
				<tr>
				<td align="center">
                <input type="button" value="sign in" onclick="login();" style="font-size: 20px" />
				
				</td>
				</tr>
			</table>

			<div id="loginResult" style="DISPLAY: none; COLOR: red" ></div>
            

		</form>
	</body>
</html>
