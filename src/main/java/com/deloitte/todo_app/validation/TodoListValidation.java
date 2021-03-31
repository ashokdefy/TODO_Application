package com.deloitte.todo_app.validation;

import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TodoListValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TodoList.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TodoList todoList = (TodoList) target;
        if (todoList.getHeader().length() == 0) {
            errors.rejectValue("header", "todoList.header.error","Task name must have at least a character.");
        }
    }
}
