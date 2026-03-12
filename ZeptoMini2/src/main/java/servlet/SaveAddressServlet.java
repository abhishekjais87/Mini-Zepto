package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

import db.DBConnection;

@WebServlet("/saveAddress")
public class SaveAddressServlet extends HttpServlet {

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

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO addresses(user_email,name,phone,address,city,pincode) " +
                "VALUES(?,?,?,?,?,?)"
            );

            ps.setString(1, user);
            ps.setString(2, r.getParameter("name"));
            ps.setString(3, r.getParameter("phone"));
            ps.setString(4, r.getParameter("address"));
            ps.setString(5, r.getParameter("city"));
            ps.setString(6, r.getParameter("pincode"));

            ps.executeUpdate();

            // next → payment or place order
            s.sendRedirect("payment.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            s.getWriter().println("ADDRESS SAVE FAILED");
        }
    }
}