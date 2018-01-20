package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	private Connection con;
	/**
	 * Starts new database connection
	 * @throws SQLException
	 */
	public DataBase() throws SQLException{
		con = null;
		String username = "sibd17g27";
		String password = "pedrorenato";
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://dbm.fe.up.pt:5432/sibd17g27",username, password);
			con.setAutoCommit(false);
			System.out.println("Opened database successfully");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * Insert new user to database
	 * @param name 								new username
	 * @param pass								new password
	 * @return 									<code>true</code> if the query was successful
	 * 											i.e new username doesn't exist
	 * @throws SQLException
	 */
	public boolean insertNewUser(String name,String pass) throws SQLException{
		PreparedStatement stmt = null;
		try {
			String command = "INSERT INTO lpro.users VALUES(?,?)";
			stmt = con.prepareStatement(command);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException  e) {
			if(isConstraintViolation(e))
				System.out.println("Username already exists");
			con.rollback();
			return false;
		}
		finally{
			if(stmt != null)
				stmt.close();
		}
		return true;
	}
	private boolean isConstraintViolation(SQLException e){
		return e.getSQLState().startsWith("23");
	}
	/**
	 * Check if combination exists in the database i.e login
	 * @param name					username
	 * @param pass					password
	 * @return						<code>true</code> for valid login
	 * @throws SQLException
	 */
	public boolean userExists(String name,String pass)throws SQLException {
		ResultSet rs;
		PreparedStatement stmt = null;
		String query = "SELECT username,password FROM lpro.users WHERE username=? AND password=?";
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			rs = stmt.executeQuery();
			con.commit();
			if(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				if(username.equals(name) && pass.equals(password))
					return true;
			}
		} catch (SQLException e) {
			System.out.println("Error accessing database: "+e.getMessage());
			con.rollback();
			return false;
		}	
		finally {
			if(stmt!=null)
				stmt.close();
		}
		return false;
	}
	/**
	 * Remove user from database
	 * @param name					username to remove
	 * @param pass					user password
	 * @return						<code>true</true> for successful removal
	 * @throws SQLException
	 */
	public boolean removeUser(String name,String pass) throws SQLException{
		String query = "DELETE FROM lpro.users WHERE username=? AND password=?";
		PreparedStatement stmt=null;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			int r = stmt.executeUpdate();
			con.commit();
			if(r == 1) return true;
		} catch (SQLException e) {
			System.out.println("Error accessing database: "+e.getMessage());
			con.rollback();
			return false;
		}	
		finally{
			if(stmt!=null)
				stmt.close();
		}
		return false;
	}
	/**
	 * Change user'statistics
	 * @param name					username to change stats
	 * @param pass					user's password
	 * @param w						new win count
	 * @param l						new loss count
	 * @param d						new draw count
	 * @throws SQLException
	 */
	public void changeinfo(String name, String pass,int w,int l,int d)throws SQLException {
		String sql = "UPDATE lpro.users SET	lost=?,won=?,draw=? WHERE username=? AND password=?;";
		PreparedStatement stmt=null;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1,l);
			stmt.setInt(2,w);
			stmt.setInt(3,d);
			stmt.setString(4,name);
			stmt.setString(5, pass);
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			System.out.println("There was a problem accessing database: "+e.getMessage());
			con.rollback();
			return;
		}
		finally{
			if(stmt!=null)
				stmt.close();
		}
	}
	/**
	 * Get user's statistics from database
	 * @param name					username
	 * @param pass					password
	 * @return						array containing user'statistics [wins,losses,draws]
	 * @throws SQLException
	 */
	public int[] getinfo(String name,String pass) throws SQLException{
		String sql = "SELECT lost,won,draw FROM lpro.users WHERE username=? AND password=?;";
		PreparedStatement stmt=null;
		ResultSet rs;
		int[] results = new int[3];
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			rs = stmt.executeQuery();
			if(rs.next()) {
				results[0] = rs.getInt("won");
				results[1] = rs.getInt("lost");
				results[2] = rs.getInt("draw");
			}
		} catch (SQLException e) {
			System.out.println("There was a problem accessing database: "+e.getMessage());
			con.rollback();
			return null;
		}
		finally{
			if(stmt!=null)
				stmt.close();
		}
		return results;		
	}
}