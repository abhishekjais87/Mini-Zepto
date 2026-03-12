package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import db.DBConnection;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException {

        HttpSession session = r.getSession();
        String user = (String) session.getAttribute("user");

        if (user == null) {
            s.sendRedirect("login.jsp");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            /* =======================
               1️⃣ Calculate total
               ======================= */
            PreparedStatement psTotal = con.prepareStatement(
                "SELECT SUM(p.price * c.quantity) " +
                "FROM cart c JOIN products p ON c.product_id = p.id " +
                "WHERE c.user_email=?"
            );
            psTotal.setString(1, user);
            ResultSet rsTotal = psTotal.executeQuery();

            int total = 0;
            if (rsTotal.next()) {
                total = rsTotal.getInt(1);
            }

            /* =======================
               2️⃣ Insert into orders
               ======================= */
            PreparedStatement psOrder = con.prepareStatement(
                "INSERT INTO orders(user_email, total_amount) VALUES(?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            psOrder.setString(1, user);
            psOrder.setInt(2, total);
            psOrder.executeUpdate();

            /* =======================
               3️⃣ Get order_id
               ======================= */
            ResultSet rsOrderId = psOrder.getGeneratedKeys();
            rsOrderId.next();
            int orderId = rsOrderId.getInt(1);

            /* =======================
               4️⃣ Insert order items
               ======================= */
            PreparedStatement psCart = con.prepareStatement(
                "SELECT p.name,p.price,c.quantity " +
                "FROM cart c JOIN products p ON c.product_id=p.id " +
                "WHERE c.user_email=?"
            );
            psCart.setString(1, user);
            ResultSet rsCart = psCart.executeQuery();

            PreparedStatement psItem = con.prepareStatement(
                "INSERT INTO order_items(order_id,product_name,price,quantity) " +
                "VALUES(?,?,?,?)"
            );

            while (rsCart.next()) {
                psItem.setInt(1, orderId);
                psItem.setString(2, rsCart.getString("name"));
                psItem.setInt(3, rsCart.getInt("price"));
                psItem.setInt(4, rsCart.getInt("quantity"));
                psItem.executeUpdate();
            }

            /* =======================
               5️⃣ Clear cart
               ======================= */
            PreparedStatement psClear = con.prepareStatement(
                "DELETE FROM cart WHERE user_email=?"
            );
            psClear.setString(1, user);
            psClear.executeUpdate();

            /* =======================
               6️⃣ Redirect success
               ======================= */
            s.sendRedirect("orderSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            s.getWriter().println("ORDER FAILED");
        }
    }
}