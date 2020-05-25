<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>completeChangeMemberInformation</title>
</head>
<body>

<a href ="/adminMenu.jsp"> 新宿トラベル</a>
<br><br>

${oldMember.name }さんの会員情報を変更しました
<br><br>

<form action ="/webinn/searchMember.jsp">
<input type="submit" value="会員一覧へ戻る">
</form>

</body>
</html>