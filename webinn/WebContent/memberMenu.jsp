<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Menu</title>
</head>
<body>

<a href ="/webinn/login.jsp"> 新宿トラベル</a>
<br>

${member.name}さんようこそ
<br>
ID${member.id }
<br><br>

写真
<br>
<br>

<a href ="/webinn/ReservationServlet"> 宿検索・予約</a>
<a href ="/webinn/ReservationServlet?action=checkInformation"> 予約内容の確認</a>
<a href ="/webinn/memberInformation.jsp"> 会員情報</a>

<jsp:include page="/recommendedInn.jsp"/>

<jsp:include page="/reviews.jsp"/>

<br>

<a href="/webinn/LoginServlet?action=logout">ログアウト</a>

</body>
</html>