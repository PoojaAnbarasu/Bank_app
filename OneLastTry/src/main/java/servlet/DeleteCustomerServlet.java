package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCustomerServlet")
public class DeleteCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            PreparedStatement ps = con.prepareStatement("DELETE FROM customers WHERE id=?");
            ps.setString(1, id);

            int i = ps.executeUpdate();
            if (i > 0) {
                out.println("Customer deleted successfully");
            } else {
                out.println("Failed to delete customer");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();
    }
}

