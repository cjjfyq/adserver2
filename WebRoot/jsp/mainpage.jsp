<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/4/16
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

    <script type="text/javascript">
        function addUser() {
            var form = document.forms[0];
            form.method = "post";
            form.action = "/adserver/user/addUser"
            form.submit();
        }
    </script>

</head>
<body>

<form id="form1" >
<input type="text" name="name"><br>
<input type="text" name="password"><br>
<input type="button" value="添加管理员" onclick="addUser()">
</form>

</body>
</html>
