package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ModifyCustomerServlet")
public class ModifyCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String newName = request.getParameter("newName");
        String newAddress = request.getParameter("newAddress");
        String newMobileNo = request.getParameter("newMobileNo");
        String newEmail = request.getParameter("newEmail");
        String newAccountType = request.getParameter("newAccountType");
        String newDateOfBirth = request.getParameter("newDateOfBirth");
        String newIdProof = request.getParameter("newIdProof");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Logindb", "root", "12345");

            PreparedStatement ps = con.prepareStatement("UPDATE customers SET full_name=?, address=?, mobile_no=?, email=?, account_type=?, date_of_birth=?, id_proof=? WHERE id=?");
            ps.setString(1, newName);
            ps.setString(2, newAddress);
            ps.setString(3, newMobileNo);
            ps.setString(4, newEmail);
            ps.setString(5, newAccountType);
            ps.setDate(6, Date.valueOf(newDateOfBirth));
            ps.setString(7, newIdProof);
            ps.setString(8, id);

            int i = ps.executeUpdate();
            if (i > 0) {
                out.println("Customer details updated successfully");
            } else {
                out.println("Failed to modify customer");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.close();
    }
}
