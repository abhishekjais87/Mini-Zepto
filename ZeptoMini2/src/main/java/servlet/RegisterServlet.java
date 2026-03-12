package servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import db.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
              "INSERT INTO users(email,password) VALUES(?,?)");
            ps.setString(1, r.getParameter("email"));
            ps.setString(2, r.getParameter("password"));
            ps.executeUpdate();
            s.sendRedirect("login.jsp");
        } catch(Exception e) { e.printStackTrace(); }
    }
}
