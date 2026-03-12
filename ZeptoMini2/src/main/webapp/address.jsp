<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Delivery Address</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>📍 Delivery Address</h2>

<form action="saveAddress" method="post">
    Name:
    <input type="text" name="name" required><br><br>

    Phone:
    <input type="text" name="phone" required><br><br>

    Address:
    <textarea name="address" required></textarea><br><br>

    City:
    <input type="text" name="city" required><br><br>

    Pincode:
    <input type="text" name="pincode" required><br><br>

    <input type="submit" value="Save & Continue">
</form>

</body>
</html>
</body>
</html>