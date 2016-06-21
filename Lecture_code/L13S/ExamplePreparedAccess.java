import java.sql.*;

public class ExamplePreparedAccess {
	
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection
				( "jdbc:mysql://" + server, account ,password);
			
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);

			String viaContinentString =
					"SELECT * FROM metropolises WHERE continent= ?";
				// ? in the string will be replaced by parameters later
			PreparedStatement viaContinentStatement = 
					con.prepareStatement(viaContinentString);
					
			viaContinentStatement.setString(1,"Europe");
				// here we've provided the parameter Europe
						
			ResultSet rs = viaContinentStatement.executeQuery();
				// this carries out the query with the parameters we've set
			
			while(rs.next()) {
				System.out.println(rs.getString("metropolis"));
			}
			
			// We can reuse it with another value for the parameter
			System.out.println("\nNew Search:");
			viaContinentStatement.setString(1,"North America");
			rs = viaContinentStatement.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("metropolis"));
			}
		
			
			// Here's another one with two parameters
			String viaContinentAndPopString =
					"SELECT * FROM metropolises WHERE continent= ? AND population> ?";
			PreparedStatement viaContinentAndPopStatement = 
					con.prepareStatement(viaContinentAndPopString);		
			viaContinentAndPopStatement.setString(1,"Europe");
			viaContinentAndPopStatement.setInt(2,8000000);
			System.out.println("\nNew Search:");
			rs = viaContinentAndPopStatement.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("metropolis"));
			}
			
			viaContinentAndPopStatement.setString(1,"North America");
			viaContinentAndPopStatement.setInt(2,6000000);
			System.out.println("\nNew Search:");
			rs = viaContinentAndPopStatement.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("metropolis"));
			}
			
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			  

	}

}
