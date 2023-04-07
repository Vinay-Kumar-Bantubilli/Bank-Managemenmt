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

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.ReplicationMySQLConnection;

@WebServlet("/Mobile")
public class Mobile extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String accountNo = (String) session.getAttribute("accountno");
		System.out.println(accountNo);
		
		//jdbc code
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");
			PreparedStatement ps = connection.prepareStatement("select * from bank.bank where accountnumber = ?");
			ps.setString(1, accountNo);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				PrintWriter writer = resp.getWriter();
				RequestDispatcher dispatcher = req.getRequestDispatcher("MobileUpdate.html");
				dispatcher.include(req, resp);
				writer.println("<h1 style='color:green; position:absolute; top:50px'>Your current Mobile is : "+rs.getString("Mobile")+"</h1>");
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
