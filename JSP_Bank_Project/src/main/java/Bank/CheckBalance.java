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

@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String accountNo = (String) session.getAttribute("accountno");
		
		//jdbc code
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("select balance from bank.bank where accountnumber=?");
			ps.setString(1, accountNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				double balance = rs.getDouble("Balance");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Options.html");
				dispatcher.include(req, resp);
				writer.println("<h1 style='color:green;'>Your Balance is : rs."+balance+"</h1>");
			}
			else {
				writer.println("<h1 style='color:green;'>Invalid Credentials</h1>");
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
