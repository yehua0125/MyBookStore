<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<Title>书籍分类信息</Title>
  </head>
  
  <body style="text-align:center;">
  	<h2>分类信息</h2>
	<table border="1" width="50%" align="center">
		<tr>
			<td>类别</td>
			<td>描述</td>
			<td>操作</td>
		</tr>
		<c:forEach var="c" items="${categories}">
			<tr>
				<td>${c.name}</td>
				<td>${c.description}</td>
				<td>
				<a href="#">修改</a>
				<a href="#">删除</a>
				</td>
			</tr>
		</c:forEach>

	</table>
  </body>
</html>
