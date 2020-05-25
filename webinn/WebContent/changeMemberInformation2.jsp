<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>Change Member Information2</title>
</head>
<body>

<a href ="/webinn/adminMenu.jsp"> 新宿トラベル</a>
<br><br>

会員情報変更
<br><br>

<c:forEach items="${members}" var="member1">
<c:choose>
	<c:when test="${member1.id eq selectedId }">

		<form action="/webinn/MemberServlet" method="post">
		<input type="hidden" name="action" value="change">
		<input type="hidden" name="id" value= "${member1.id}" >
		名前 : <input type="text" name="name" value="${member1.name}"><br><br>
		郵便番号 : <input type="text" name="postal" value="${member1.postal}"><br><br>
		住所 : <input type="text" name="address" value="${member1.address}"><br><br>
		電話番号 : <input type="text" name="tel" value="${member1.tel}"><br><br>
		メールアドレス : <input type="text" name="email" value="${member1.mailAddress}"><br><br>
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
		日<br><br>


		パスワード : <input type="text" name="passWord" value="${member1.passWord}"><br><br>
		<input type="submit" value="確認画面へ進む">
		</form>

	</c:when>
	</c:choose>
</c:forEach>

<form action="/webinn/adminMenu.jsp">
<input type="submit" value="キャンセル">
</form>

</body>
</html>