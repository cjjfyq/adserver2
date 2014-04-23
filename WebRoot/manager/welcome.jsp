<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>**平台</title>
<style type="text/css" ></style>
<link href="${pageContext.request.contextPath}/manager/css/index.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function autoHeight() {
		if (window.innerHeight) {//FF
			nowHeight = window.innerHeight;
		} else {
			nowHeight = document.documentElement.clientHeight;
		}
		var jianHeight = 230;
		if (nowHeight > jianHeight) {
			document.getElementById('aaf').style.height = nowHeight - jianHeight + 'px';
			document.getElementById('traa').style.height = nowHeight - jianHeight + 'px';
		} else {
			document.getElementById('aaf').style.height = jianHeight + 'px';
			document.getElementById('traa').style.height = jianHeight + 'px';
		}
	}
	autoHeight();
	window.onresize = autoHeight;
</script>
</head>

<body onload="autoHeight();">
<div id="wrap">
<%@include file="head.jsp" %>
<div class="right-hand"  align="left" id="aaf"> 
  <div class="r-contents">
    <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" style="margin-bottom: 5px;" bgcolor="#e3f0fa">
    	<tr id="traa"><td bgcolor="#FAF0E6"  align="center"><img src="images/c-1.jpg" /></td></tr>
    </table>
  </div>
</div>
</div>
<%@include file="bottom.jsp"%>
</body>
</html>

