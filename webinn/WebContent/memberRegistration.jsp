<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Registration</title>
</head>
<body>


<a href ="/webinn/login.jsp"> 新宿トラベル</a>
<br><br>

会員情報入力
<br><br>


<form action="/webinn/MemberServlet" method="post">
<input type="hidden" name="action" value="registration">
名前 : <input type="text" name="name" ><br><br>
郵便番号 : <input type="text" name="postal" ><br><br>
住所 : <input type="text" name="address"><br><br>
電話番号 : <input type="text" name="tel"><br><br>
メールアドレス : <input type="text" name="email"><br><br>
生年月日 :
<select size="1" name="year">
	<c:forEach begin="1900" end="2020" step="1" varStatus="status">
		<option value="${status.index}">${status.index}</option>
	</c:forEach>
</select>
年
<select size="1" name="month">
	<c:forEach begin="1" end="12" step="1" varStatus="status">
		<option value="${status.index}">${status.index}</option>
	</c:forEach>
</select>
月
<select size="1" name="day">
	<c:forEach begin="1" end="31" step="1" varStatus="status">
		<option value="${status.index}">${status.index}</option>
	</c:forEach>
</select>
日
<br><br>



パスワード : <input type="text" name="passWord"><br><br>
<input type="submit" value="確認画面へ進む">
</form>

<form action="/webinn/login.jsp">
<input type="submit" value="キャンセル">
</form>


</body>
</html>