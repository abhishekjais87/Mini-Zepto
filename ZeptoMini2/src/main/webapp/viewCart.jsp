<%@ page import="java.sql.*,db.DBConnection" %>
<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}

Connection con = DBConnection.getConnection();
PreparedStatement ps = con.prepareStatement(
"SELECT p.name,p.price,c.quantity FROM cart c JOIN products p ON c.product_id=p.id WHERE c.user_email=?");
ps.setString(1, user);
ResultSet rs = ps.executeQuery();

int total = 0;
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Cart</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>Your Cart 🛍️</h2>

<%
while(rs.next()){
    int price = rs.getInt("price");
    int qty = rs.getInt("quantity");
    total += price * qty;
%>
<p>
<%= rs.getString("name") %> × <%= qty %> = ₹<%= price * qty %>
</p>
<%
}
%>

<h3>Total Amount: ₹<%= total %></h3>

<form action="address.jsp" method="get">
    <input type="submit" value="Proceed to Address">
</form>

<br>
<a href="products.jsp">Back to Products</a>

</body>
</html>