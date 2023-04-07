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

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

@WebServlet("/Login")
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//fetch data from web page
		String accountNo = req.getParameter("accountno");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		session.setAttribute("accountno", accountNo);
		
		//jdbc code
		PrintWriter writer = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("select * from bank.bank where accountNumber=? and password=?");
			ps.setString(1, accountNo);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				session.setAttribute(rs.getString("Mobile"), "mobile");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Options.html");
				dispatcher.include(req, resp);
				writer.println("<h1 style='color:green;'>Login successfully</h1>");
			}
			else {
				RequestDispatcher dispatcher = req.getRequestDispatcher("Login.html");
				dispatcher.include(req, resp);
//				writer.println("<h1 style='color:red;'>Invalid Credintials</h1>");
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
