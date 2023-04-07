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

@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		double amount = Double.parseDouble(req.getParameter("deposit"));
		
		//jdbc code
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("update bank.bank set balance=? where accountnumber=?");
			PreparedStatement ps1 = connection.prepareStatement("select * from bank.bank where accountnumber=?");
			
			HttpSession session = req.getSession();
			String accountNo = (String) session.getAttribute("accountno");
			System.out.println(accountNo);
			ps1.setString(1, accountNo);
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				double accountBalance = rs.getDouble("Balance");
				double resBalance = accountBalance+amount;
				ps.setDouble(1, resBalance);
				ps.setString(2, accountNo);
				int a = ps.executeUpdate();
				if (a != 0) {
					writer.println("<h1 style='color:green;'>Deposit successfully</h1>");
					RequestDispatcher dispatcher = req.getRequestDispatcher("Options.html");
					dispatcher.include(req, resp);
				}
			}
				
			else {
				writer.println("<h1 style='color:red;'>Invalid credentials</h1>");
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
