package socketsServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	private Connection con;
	public DataBase() {
		con = null;
		String username = "sibd17g27";
		String password = "pedrorenato";
		 try {
	        Class.forName("org.postgresql.Driver");
	         con = DriverManager.getConnection("jdbc:postgresql://dbm.fe.up.pt:5432/sibd17g27",username, password);
	         con.setAutoCommit(false);
	      } catch (Exception e) {
	         System.err.println("Error connecting to database: "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");		
	}
	public boolean insertNewUser(String name,String pass) {
		PreparedStatement stmt = null;
		try {
			String command = "INSERT INTO lpro.users VALUES(?,?)";
			stmt = con.prepareStatement(command);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException  e) {
			if(isConstraintViolation(e)) {
				System.out.println("Username already exists");
				return false;
			}
		}
		finally{
			try {
				System.out.println("Closing statement");
				if(stmt!=null)
					stmt.close();
			} catch (SQLException e) {
				System.exit(0);
			}
		}
		return true;
	}
	private boolean isConstraintViolation(SQLException e) {
	    return e.getSQLState().startsWith("23");
	}
	public boolean userExists(String name,String pass) {
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
			return false;
		}	
		finally {
			try {
				if(stmt!=null)
					stmt.close();
			} catch (SQLException e) {
				System.exit(0);
			}
		}
		return false;
	}
	public boolean removeUser(String name,String pass) {
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
			return false;
		}	
		finally{
			try {
			if(stmt!=null)
				stmt.close();
			} catch (SQLException e) {
				System.exit(0);
			}
		}
		return false;
	}
	public void changeinfo(String name, String pass,int w,int l,int d) {
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
			return;
		}
		finally{
			try {
			if(stmt!=null)
				stmt.close();
			} catch (SQLException e) {
				System.exit(0);
			}
		}
	}
	public int[] getinfo(String name,String pass) {
		String sql = "SELECT lost,won,draw FROM lpro.users WHERE username=? AND password=?;";
		PreparedStatement stmt=null;
		ResultSet rs;
		int[] results = new int[3];
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, pass);
			rs = stmt.executeQuery();
			rs.next();
			results[0] = rs.getInt("won");
			results[1] = rs.getInt("lost");
			results[2] = rs.getInt("draw");
		} catch (SQLException e) {
			System.out.println("There was a problem accessing database: "+e.getMessage());
			return null;
		}
		finally{
			try {
			if(stmt!=null)
				stmt.close();
			} catch (SQLException e) {
				System.exit(0);
			}
		}
		return results;		
	}
}