package com.deloitte.todo_app.service.imp;

import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.repository.TodoListRepository;
import com.deloitte.todo_app.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServiceImp implements TodoListService {

    @Autowired
    TodoListRepository todoListRepository;

    @Override
    public List<TodoList> findAllByUser_Id(Long UserId) {
        return todoListRepository.findAllByUser_Id(UserId);
    }

    @Override
    public TodoList findById(Long Id) {
        return todoListRepository.findById(Id).orElse(null);
    }

    @Override
    public void save(TodoList todoList) {
        todoListRepository.save(todoList);
    }

    @Override
    public void delete(TodoList todoList) {
        todoListRepository.delete(todoList);
    }
}
