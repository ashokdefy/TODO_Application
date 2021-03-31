package com.deloitte.todo_app.service;

import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoListService {

    List<TodoList> findAllByUser_Id(Long UserId);
    TodoList findById(Long Id);
    void save(TodoList todoList);
    void delete(TodoList todoList);
}
