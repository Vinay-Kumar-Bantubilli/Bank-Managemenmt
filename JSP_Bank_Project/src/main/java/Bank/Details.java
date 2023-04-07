package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Details")
public class Details extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String accountNo = (String) session.getAttribute("accountno");
		System.out.println(accountNo);
		
		//jdbc code for fetching the data from the data base
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("select * from bank.bank where accountnumber = ?");
			ps.setString(1,accountNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				writer.println("<h1 style='color:white;'>Account Number : "+rs.getString(1)+"</h1>");
				writer.println("<h1 style='color:white;'>Account Holder Name : "+rs.getString(2)+"</h1>");
				writer.println("<h1 style='color:white;'>Aadhar Number : "+rs.getString(3)+"</h1>");
				writer.println("<h1 style='color:white;'>Mobile : "+rs.getString(4)+"</h1>");
				writer.println("<h1 style='color:white;'>Email-Id : "+rs.getString(5)+"</h1>");
				writer.println("<h1 style='color:white;'>Pan Number : "+rs.getString(6)+"</h1>");
				writer.println("<h1 style='color:white;'>Gender : "+rs.getString(7)+"</h1>");
				writer.println("<h1 style='color:white;'>Balance : "+rs.getDouble(8)+"</h1>");
				writer.println("<h1 style='color:white;'>Password : "+rs.getString(9)+"</h1>");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Details.html");
				dispatcher.include(req, resp);
			}
			
			connection.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
