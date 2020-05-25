<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check Change Member Information</title>
</head>
<body>

<a href ="/webinn/adminMenu.jsp"> 新宿トラベル</a>
<br>

以下の内容で会員情報を変更します。よろしいですか？<br><br>

名前 : ${newMember.name} <br><br>
郵便番号 : ${newMember.postal } <br><br>
住所 : ${newMember.address} <br><br>
電話番号 : ${newMember.tel } <br><br>
メールアドレス : ${newMember.mailAddress} <br><br>
生年月日 : ${newMember.birthday } <br><br>
パスワード : ${newMember.passWord} <br><br>


<form action="/webinn/MemberServlet" >
<input type="hidden" name="action" value="change2">
<input type="submit" value="変更する">
</form>

<form action="/webinn/changeMemberInformation2.jsp" >
<input type="hidden" name="id" value="${newMember.id}">
<input type="submit" value="訂正する">
</form>



</body>
</html>