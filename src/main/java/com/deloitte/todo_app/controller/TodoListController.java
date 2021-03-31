package com.deloitte.todo_app.controller;

import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.service.UserService;
import com.deloitte.todo_app.service.TodoListService;
import com.deloitte.todo_app.validation.TodoListValidation;
import com.deloitte.todo_app.validation.UserValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/list")
public class TodoListController {

    private final UserService userService;
    private final TodoListService todoListService;
    private final TodoListValidation todoListValidation;


    public TodoListController(com.deloitte.todo_app.service.UserService userService, TodoListService todoListService, UserValidation userValidation, TodoListValidation todoListValidation) {
        this.userService = userService;
        this.todoListService = todoListService;
        this.todoListValidation = todoListValidation;

    }

    @GetMapping("/add")
    public String getAdd(Model model, Principal principal){
        log.trace("GET request received for Task UPDATE by user" + principal.getName());
        model.addAttribute( "task", new TodoList());
        return "add-new";
    }
    @PostMapping("/add")
    public String postAdd(@ModelAttribute("task") TodoList list,
                          Principal principal, BindingResult bindingResult){
        list.setDate(new Date());
        list.setUser(userService.findByUsername(principal.getName()));
        todoListValidation.validate(list, bindingResult);
        if(bindingResult.hasErrors()) {
            if(list.getId()==null)
                log.info("New task is not added for user: "+ principal.getName() +" due to Error on validation : \n" + bindingResult.toString());
            else
                log.info("Task is not Updated for user: "+ principal.getName() +" due to Error on validation : \n" + bindingResult.toString());
            return "add-new";
        }
        if(list.getId()==null)
            log.info("New task has been added successfully by user: "+ principal.getName());
        else
            log.info("task "+ list.getId() +" has been updated successfully by user: "+ principal.getName());
        todoListService.save(list);
        return "redirect:/";
    }
    @GetMapping("/update")
    public String getUpdate(Model model, Principal principal,
                            @RequestParam("task_id") Long toDoListId){
        log.trace("GET request received for Task UPDATE by user" + principal.getName());
        TodoList toDoList = todoListService.findById(toDoListId);
        if(toDoList == null) {
            log.error("404 error sent for the wrong task update request by " + principal.getName());
            return "error/404";
        }
        model.addAttribute("task", toDoList);
        return "add-new";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String updateCheck(@ModelAttribute("task_id") Long toDoListId, Principal principal){
        TodoList toDoList = todoListService.findById(toDoListId);
        if(toDoList != null) {
            toDoList.setCheckBox(! toDoList.isCheckBox());
            todoListService.save(toDoList);
            log.info("task "+ toDoList.getId() +" was checked "+ toDoList.isCheckBox() +" successfully by user: "+ toDoList.getUser().getUsername());
            return "redirect:/";
        }
        log.error("404 error sent for the wrong task check request by " + principal.getName());
        return "error/404";
    }
    @RequestMapping(value="/delete" , method = RequestMethod.GET)
    public String deleteTask(@ModelAttribute("task_id") Long toDoListId, Principal principal) {
        TodoList todoDelete = todoListService.findById(toDoListId);
        if (todoDelete != null) {
            todoListService.delete(todoDelete);
            log.info("task " + todoDelete.getId() + " was deleted successfully by user: " + todoDelete.getUser().getUsername());
            return "redirect:/";
        }
        log.error("404 error sent for the wrong task delete request by " + principal.getName());
        return "error/404";
    }
}
