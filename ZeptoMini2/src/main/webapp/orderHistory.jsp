<%@ page import="java.sql.*,db.DBConnection" %>
<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}

Connection con = DBConnection.getConnection();
PreparedStatement ps = con.prepareStatement(
"SELECT * FROM orders WHERE user_email=? ORDER BY order_date DESC");
ps.setString(1, user);
ResultSet rs = ps.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>📦 Order History</h2>

<%
while(rs.next()){
%>
<hr>
<b>Order ID:</b> <%= rs.getInt("id") %><br>
<b>Total:</b> ₹<%= rs.getInt("total_amount") %><br>
<b>Date:</b> <%= rs.getTimestamp("order_date") %><br>

<%
PreparedStatement ps2 = con.prepareStatement(
"SELECT * FROM order_items WHERE order_id=?");
ps2.setInt(1, rs.getInt("id"));
ResultSet items = ps2.executeQuery();
%>

<ul>
<%
while(items.next()){
%>
<li>
<%= items.getString("product_name") %> × <%= items.getInt("quantity") %>
</li>
<%
}
%>
</ul>
<%
}
%>

<a href="products.jsp">Back to Products</a>

</body>
</html>