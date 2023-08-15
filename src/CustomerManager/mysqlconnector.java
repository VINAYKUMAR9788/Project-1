package CustomerManager;

import java.sql.Connection;
import java.sql.DriverManager;

public class mysqlconnector 
{
	static Connection con;

	public static Connection doConnect() 
	{
		
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost/newspaperagency","root","Vk@28708");
				System.out.println("Connected");
			} 
			
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			
	
		return con;
	}
	
	public static void main(String[] args)  
	{
		// TODO Auto-generated method stub
		doConnect();

	}

}