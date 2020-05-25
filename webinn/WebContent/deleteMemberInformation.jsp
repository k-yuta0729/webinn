<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


${sessionScope.member.name }さん<br><br>

退会しますか？
<br>
退会するボタンを選択するともとには戻れません。
<br><br>


<form action="/webinn/MemberServlet" method="post">
<input type="submit" value="退会する">
<input type="hidden" name="action" value="delete">
</form>

<form action="/webinn/memberInformation.jsp" >
<input type="submit" value="キャンセル">
</form>

</body>
</html>