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

@WebServlet("/MobileUpdate")
public class MobileUpdate extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//fetch the data from web page
		String mobile = req.getParameter("mobile");
		
		//jdbc to update the remaining balance
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("update bank.bank set mobile=? where accountnumber=?");
			
			HttpSession session = req.getSession();
			String accountNo = (String) session.getAttribute("accountno");
			System.out.println(accountNo);
			ps.setString(1, mobile);
			ps.setString(2, accountNo);
			int a = ps.executeUpdate();
			if (a != 0) {
				writer.println("<h1 style='color:green;'>Mobile update Successfully</h1>");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Options.html");
				dispatcher.include(req, resp);
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
