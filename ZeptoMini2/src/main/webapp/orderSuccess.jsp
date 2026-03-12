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
    <title>Order Success</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>🎉 Order Placed Successfully!</h2>
<p>Thank you <b><%= user %></b> for shopping with us.</p>

<a href="products.jsp">Shop More</a> |
<a href="orderHistory.jsp">View Orders</a>

</body>
</html>