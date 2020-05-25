<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shinjuku Travel Admin Login</title>
</head>
<body>

<br>
管理者ID、パスワードを入力
<br>
<br>
<form action="/webinn/LoginServlet" method="post">
	管理者ID : <input type="text" name = mailAddress > <br>
	パスワード : <input type="text" name = passWord> <br>
	<input type="hidden" name="action" value="adminLogin">
	<input type="submit" value="ログイン">
</form>


</body>
</html>