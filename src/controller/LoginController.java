package controller;

import model.User;
import model.UserDAO;

public class LoginController {

    public String login(String username, String password) {
        
        if (username == null || username.isEmpty()) {
            return "Username is required";
        }
        
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        
        if (username.length() < 3) {
            return "Username must contain at least 3 characters";
        }
        
        if (password.length() < 5) {
            return "Password must contain at least 5 characters";
        }

        UserDAO dao = new UserDAO();
        User loggedInUser = dao.authenticateUser(username, password);

 
        if (loggedInUser != null) {
            return "LOGIN_SUCCESS";
        } else {
            return "INVALID_CREDENTIALS";
        }
    }
}