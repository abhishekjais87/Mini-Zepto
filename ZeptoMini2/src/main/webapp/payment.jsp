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
    <title>Payment</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<h2>💳 Payment (Dummy)</h2>

<form action="paymentSuccess.jsp" method="post">
    Card Number:
    <input type="text" required><br><br>

    Expiry Date:
    <input type="text" required><br><br>

    CVV:
    <input type="password" required><br><br>

    <input type="submit" value="Pay Now">
</form>

</body>
</html>