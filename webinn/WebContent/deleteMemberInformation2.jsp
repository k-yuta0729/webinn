<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
 //String birthday[] = (String[])session.getAttribute("birthday");

request.setCharacterEncoding("UTF-8");
String strId = request.getParameter("id");
Integer id = Integer.parseInt(strId);
request.setAttribute("selectedId",id);

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteMemberInformation2</title>
</head>
<body>

	<a href ="/adminMenu.jsp"> 新宿トラベル</a>
	<br><br>

	<c:forEach items="${members}" var="member1">
		<c:choose>
			<c:when test="${member1.id eq selectedId }">

			以下の内容の会員を削除します。よろしいですか？
			<br><br>
			名前 ： ${member1.name}<br>
			郵便番号 ： ${member1.postal}<br>
			住所 : ${member1.address}<br>
			電話番号 : ${member1.tel}<br>
			メールアドレス : ${member1.mailAddress}<br>
			生年月日 : ${member1.birthday}<br>
			<br>

			<div style="display: inline-flex">
				<form action ="/webinn/MemberServlet?action=delete&id=${member1.id}" method="post" style="margin:10px;">
					<input type="submit" value="削除する">
					<input type="hidden" name="action" value="delete">
				</form>

				<form action ="/webinn/searchMember.jsp" method="post" style="margin:10px;">
					<input type="submit" value="キャンセル">
				</form>
			</div>

			</c:when>
		</c:choose>
	</c:forEach>



</body>
</html>