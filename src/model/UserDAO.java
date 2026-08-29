package model;

import util.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticateUser(String username, String password) {
        User loggedInUser = null;

        try {
            Connection con = DBconnection.getConnection();
            
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            

            if (rs.next()) {
                loggedInUser = new User();
                loggedInUser.setId(rs.getInt("id"));
                loggedInUser.setUsername(rs.getString("username"));
                loggedInUser.setRole(rs.getString("role"));
                
                System.out.println("User Authenticated. Role: " + loggedInUser.getRole());
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return loggedInUser;
    }
}