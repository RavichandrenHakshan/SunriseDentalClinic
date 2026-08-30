package model;

import util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticateUser(String username, String password) {
        User loggedInUser = null;
        try {
            java.sql.Connection con = util.DBconnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                loggedInUser = new User();
                loggedInUser.setId(rs.getInt("id"));
                loggedInUser.setUsername(rs.getString("username"));
                loggedInUser.setPassword(rs.getString("password"));
                
                // Add this new line to grab the role!
                loggedInUser.setRole(rs.getString("role")); 
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e);
        }
        return loggedInUser;
    }
    
}