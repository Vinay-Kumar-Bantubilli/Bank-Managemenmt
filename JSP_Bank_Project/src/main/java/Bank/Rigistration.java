package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Rigistration")
public class Rigistration extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//fetch the data from web page
		String name = req.getParameter("name");
		String aadharNo = req.getParameter("aadhar");
		String mobile = req.getParameter("mobile");
		String email = req.getParameter("email");
		String pan = req.getParameter("pan");
		String gender = req.getParameter("g");
		String password = req.getParameter("password");
		String cPassword = req.getParameter("cpassword");
		String accountNo = "";
		//creating the accunt number		
		PrintWriter writer = resp.getWriter();
		
		//jdbc code
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=12345");			
			PreparedStatement ps1 = connection.prepareStatement("select * from bank.bank");
			
			ResultSet rs = ps1.executeQuery();
			ArrayList al = new ArrayList();
			//check wheather the data present in the data base or not
			if (rs.last()) {
				rs.beforeFirst();
				while (rs.next()) {
					al.add(rs.getString("AccountNumber"));
				}
				System.out.println(al);
			}
			else {
				writer.println("<h1 style='color:green;'>No accounts found</h1>");
			}
			
			
			PreparedStatement ps = connection.prepareStatement("insert into bank.bank values(?,?,?,?,?,?,?,?,?)");
			Random random = new Random();
			int temp = random.nextInt(1000);
			String number = Integer.toString(temp);
			while (al.indexOf(accountNo) != -1 || accountNo == null) {
				temp = random.nextInt(1000);
				number = Integer.toString(temp);
				if (temp < 100) {
					number="0"+number;
				}
				else if (temp < 10) {
					number="00"+number;
				}
				accountNo = "JSP"+number;
				System.out.println(accountNo);
			}
			ps.setString(1, accountNo);
			ps.setString(2, name);
			ps.setString(3, aadharNo);
			ps.setString(4, mobile);
			ps.setString(5, email);
			ps.setString(6, pan);
			ps.setString(7, gender);
			ps.setDouble(8, 0);
			ps.setString(9, password);
			int a = ps.executeUpdate();
			if (a != 0) {
				writer.println("<h1  style='color:blue;'>Your account No. is : "+accountNo+"</h1>");
				writer.println("<h1 style='color:green;'>Registration Successfully</h1>");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Login.html");
				dispatcher.include(req, resp);
			}
			else {
				writer.println("<h1  style='color:red;'>Error</h1>");
				RequestDispatcher dispatcher = req.getRequestDispatcher("Registration.html");
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
