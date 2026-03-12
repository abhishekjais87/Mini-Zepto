<%@ page import="java.sql.*,db.DBConnection" %>
<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
Connection con = DBConnection.getConnection();
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM products");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>Products 🛒</h2>

<%
while(rs.next()){
%>
<form action="addToCart" method="post">
    <b><%= rs.getString("name") %></b>
    - ₹<%= rs.getInt("price") %>

    <input type="hidden" name="pid" value="<%= rs.getInt("id") %>">
    <input type="submit" value="Add to Cart">
</form>
<br>
<%
}
%>

<hr>
<a href="viewCart.jsp">View Cart</a> |
<a href="orderHistory.jsp">Order History</a> |
<a href="logout">Logout</a>

</body>
</html>