<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AdminMember</title>

</head>

<body>
<div id="header"><h1><a href="/webinn/adminMenu.jsp">新宿トラベル</a></h1></div>

${member.name}さんようこそ

<div align="center">会員情報一覧</div>


<br>
<form action="/webinn/MemberServlet" method="post">
<input type="text" name = "name" placeholder="会員を名前から検索" >
<input type="submit" value="検索" > <br>
<input type="hidden" name="action" value="search">
</form>

<br><br>


<div id="member_id">
  <div style="background:#fcfcff; padding:50px;
  border:1px solid #3b5998; border-radius:20px;
  word-break:break-all;"><table>



	<tr><td>会員一覧</td></tr>

	<c:forEach items="${members}" var="member1">
		<tr><td>${member1.id}</td><td>${member1.name}</td><td>${member1.birthday}</td>
		<td><a href="/webinn/changeMemberInformation2.jsp?id=${member1.id}" >変更</a></td>
		<td><a href="/webinn/deleteMemberInformation2.jsp?id=${member1.id}" >削除</a></td>
		<%-- <td><a href="/webinn/MemberServlet?action=delete&id=${member1.id}" >削除</a></td></tr> --%>
	</c:forEach>

	</table></div>

</div>




<br>
<a href ="/webinn/adminMenu.jsp">
<center><input type="submit" value="戻る" /></center></a>





</body>
</html>