package controller;

import model.User;
import model.UserDAO;

public class UserController {

    public String registerUser(String username, String password, String role) {
        
        // 1. Basic Validation
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty.";
        }
        if (role == null || role.equals("Select Role")) {
            return "Please select a valid role.";
        }

        // 2. Pack the validated data into the User model
        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(password.trim());
        newUser.setRole(role);

        // 3. Send the model to the Data Access Object (DAO)
        UserDAO dao = new UserDAO();
        boolean isSaved = dao.createUser(newUser);

        // 4. Return the result back to the View
        if (isSaved) {
            return "SUCCESS";
        } else {
            return "Failed to save user to the database.";
        }
    }
}