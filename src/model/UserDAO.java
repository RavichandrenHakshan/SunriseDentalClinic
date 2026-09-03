package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Method 1: Used by LoginController to authenticate users
    public User authenticateUser(String username, String password) {
        User loggedInUser = null;
        try {
            Connection con = util.DBconnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                loggedInUser = new User();
                loggedInUser.setId(rs.getInt("id"));
                loggedInUser.setUsername(rs.getString("username"));
                loggedInUser.setPassword(rs.getString("password"));
                loggedInUser.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e);
        }
        return loggedInUser;
    }

    // Method 2: Used by UserController to save new accounts to the database
    public boolean createUser(User user) {
        boolean isSuccess = false;
        try {
            Connection con = util.DBconnection.getConnection();
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error creating user: " + e);
        }
        return isSuccess;
    }
    
    public boolean updateUser(String username, String newPassword, String newRole) {
        boolean isSuccess = false;
        try {
            java.sql.Connection con = util.DBconnection.getConnection();
            String sql = "UPDATE users SET password = ?, role = ? WHERE username = ?";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newPassword);
            pst.setString(2, newRole);
            pst.setString(3, username);
            
            if (pst.executeUpdate() > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error updating user: " + e);
        }
        return isSuccess;
    }

    public boolean deleteUser(String username) {
        boolean isSuccess = false;
        try {
            java.sql.Connection con = util.DBconnection.getConnection();
            String sql = "DELETE FROM users WHERE username = ?";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            
            if (pst.executeUpdate() > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e);
        }
        return isSuccess;
    }
}