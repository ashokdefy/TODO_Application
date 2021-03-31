package com.deloitte.todo_app.validation;

import com.deloitte.todo_app.model.User;
import com.deloitte.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidation implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        if (user.getUsername().length() < 4) {
            errors.rejectValue("username", "user.username.error","Username must have at least 4 characters.");
        }
        else if(user.getUsername().length() > 15){
            errors.rejectValue("username","user.username.error","Username can't have more than 15 characters.");
        }
        if(user.getUsername().matches("^.*[^a-zA-Z0-9].*$")){
            errors.rejectValue("username","user.username.error","Username should be alphanumeric.");
        }
        //Username can't be duplicated
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "user.username.error", "This username is already in use.");
        }
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "user.password.error","Password must have at least 6 characters.");
        }
        else if (user.getPassword().length() > 32){
            errors.rejectValue("password","user.password.error", "Password can't have more than 32 characters.");
        }
   /*     else if (!(user.getPassword().matches("(?=.*[0-9]).*")
                && user.getPassword().matches("(?=.*[a-z]).*")
                && user.getPassword().matches("(?=.*[A-Z]).*")
                && user.getPassword().matches("(?=.*[~!@#$%^&*()_-]).*"))){
            errors.rejectValue("password","user.password.error", "Password must have atleast one Uppercase, Lowercase, Numeric and a Symbol.");
        } **/
        //password verification
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm","user.password.error", "Passwords needs to be the same.");
        }

    }
}
