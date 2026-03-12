package servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import db.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException {

        String user = (String) r.getSession().getAttribute("user");
        int pid = Integer.parseInt(r.getParameter("pid"));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
              "INSERT INTO cart(user_email,product_id,quantity) VALUES(?,?,1)");
            ps.setString(1, user);
            ps.setInt(2, pid);
            ps.executeUpdate();

            s.sendRedirect("products.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
