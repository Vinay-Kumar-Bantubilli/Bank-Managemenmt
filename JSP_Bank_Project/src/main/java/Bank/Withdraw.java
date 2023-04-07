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

import org.omg.CORBA.Request;

import com.mysql.jdbc.ReplicationMySQLConnection;

@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//fetch the data from web page
		int amount = Integer.parseInt(req.getParameter("withdraw"));
		
		//jdbc to update the remaining balance
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
				if (accountBalance >= amount) {
					double resBalance = accountBalance-amount;
					ps.setDouble(1, resBalance);
					ps.setString(2, accountNo);
					int a = ps.executeUpdate();
					if (a != 0) {
						writer.println("<h1 style='color:green;'>withdraw successfully</h1>");
						RequestDispatcher dispatcher = req.getRequestDispatcher("Options.html");
						dispatcher.include(req, resp);
					}
				}
				else {
					
					RequestDispatcher dispatcher = req.getRequestDispatcher("Withdraw.html");
					dispatcher.include(req, resp);
					writer.println("<h1 style='color:red; position:absolute; top:80px;'>Insuffifient Balance......!</h1>");
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
