<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shinjuku Travel Login</title>
</head>
<body>

<a href ="/webinn/login.jsp">新宿トラベル</a>

<br>
メールアドレス、パスワードを入力
<br>

<form action="/webinn/LoginServlet" method="post">
	メールアドレス : <input type="text" name = mailAddress placeholder="info@example.com" > <br>
	パスワード : <input type="text" name = passWord placeholder="パスワードを入力"> <br>
	<input type="hidden" name="action" value="login">
	<input type="submit" value="ログイン">
</form>

<br>

<form action="/webinn/memberRegistration.jsp" >
	<input type="submit" value="新規会員登録">
</form>

<br>

<form action="/webinn/adminLogin.jsp" method="get">
	<input type="submit" value="管理者用ログイン">
</form>

</body>
</html>