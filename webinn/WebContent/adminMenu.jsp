<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>adminMenu</title>
</head>
<body>

<a href ="/webinn/adminMenu.jsp"> 新宿トラベル</a>
<br><br>


<h1>管理者トップページ</h1>
<br><br>

<%-- <a href="/memberPage.jsp" ><input type="submit" value="会員管理"></a><br><br>  --%>

<a href ="/webinn/MemberServlet?action=search"><input type="submit" value="会員管理"></a>
<a href="/webinn/innMenu.jsp"><input type="submit" value="宿管理"></a><br><br>
<a href="/webinn/LoginServlet?action=logout"><input type="submit" value="ログアウト"></a>


</body>
</html>