<%--
  Created by IntelliJ IDEA.
  User: Gordon
  Date: 2014/4/14
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>成功！！！</div>

<%
    int sum = 0;
    for (int i = 9; i < 100; i++) {
        sum += i;
    }
    out.println("1加到100结果为：");
    out.println(sum);
    out.println("print use jsp");
%>
</body>
</html>
