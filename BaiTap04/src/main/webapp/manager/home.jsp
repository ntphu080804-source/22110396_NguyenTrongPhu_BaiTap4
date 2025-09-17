<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Category List</h2>

	<table border="1">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Action</th>
		</tr>
		<c:forEach var="c" items="${categories}">
			<tr>
				<td>${c.id}</td>
				<td>${c.name}</td>
				<td><a href="home?action=edit&id=${c.id}">Edit</a> <a
					href="home?action=delete&id=${c.id}"
					onclick="return confirm('Bạn có chắc muốn xóa không?');">Delete</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h3>${category != null ? 'Edit Category' : 'Add Category'}</h3>
	<form action="home" method="post">
		<input type="hidden" name="id"
			value="${category != null ? category.id : ''}"> <input
			type="text" name="name" placeholder="Category name"
			value="${category != null ? category.name : ''}" required>
		<button type="submit">${category != null ? 'Update' : 'Add'}</button>
	</form>

</body>
</html>