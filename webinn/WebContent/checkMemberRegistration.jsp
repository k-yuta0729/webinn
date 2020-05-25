<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check Member Registration</title>
</head>
<body>

<a href ="/webinn/login.jsp"> 新宿トラベル</a>
<br>

以下の内容で登録します。よろしいですか？<br><br>

名前 : ${member.name } <br><br>
郵便番号 : ${member.postal } <br><br>
住所 : ${member.address} <br><br>
電話番号 : ${member.tel } <br><br>
メールアドレス : ${member.mailAddress} <br><br>
生年月日 : ${member.birthday } <br><br>
パスワード : ${member.passWord} <br><br>


<form action="/webinn/MemberServlet" method="post">
<input type="hidden" name="action" value="registration2">
<input type="submit" value="登録する">
</form>

<form action="/webinn/changeMemberInformation.jsp" method="post">
<input type="submit" value="訂正する">
</form>

</body>
</html>