package com.deloitte.todo_app.controller;

import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.model.User;
import com.deloitte.todo_app.service.TodoListService;
import com.deloitte.todo_app.service.UserService;
import com.deloitte.todo_app.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class UserController {

    private final UserValidation userValidation;
    private final UserService userService;
    private final TodoListService todoListService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserValidation userValidation,
                          UserService userService, TodoListService todoListService) {
        this.userValidation = userValidation;
        this.userService = userService;
        this.todoListService = todoListService;
    }

    @GetMapping("/login")
    public String getLogin(HttpSession httpSession, Model model, Error error){
        httpSession.invalidate();
        log.trace("GET request received for LOGIN");
        if (error != null)
            model.addAttribute("error", "The username or password you entered is incorrect.");
        return "login";
    }

    @PostMapping("/loginProcess")
    public String postLogin(){
        log.info("Login Successful");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister(Model model, HttpSession httpSession){
        httpSession.invalidate();
        log.trace("GET request received for REGISTER");
        model.addAttribute("userData", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("userData") User userData,
                               RedirectAttributes redirectAttributes,
                               BindingResult bindingResult){
        userValidation.validate(userData, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("User not registered due to Error on validation : \n" + bindingResult.toString());
            return "register";
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        userService.save(userData);
        log.info(userData.getUsername() + " is successfully registered");
        redirectAttributes.addFlashAttribute("registered", true);
        return "redirect:/login";
    }

    @GetMapping({"/","","/index"})
    public String myHome( Model model, Principal principal) {
        model.addAttribute("user", principal);
        List<TodoList> todoLists = todoListService.findAllByUser_Id(
                userService.findByUsername(principal.getName()).getId());
        model.addAttribute("list", todoLists);
        return "index";
    }

}
