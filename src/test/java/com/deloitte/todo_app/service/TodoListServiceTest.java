package com.deloitte.todo_app.service;


import com.deloitte.todo_app.model.TodoList;
import com.deloitte.todo_app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TodoListServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TodoListService todoListService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;
    private TodoList[] todoListArray;

    @BeforeEach
    void beginInit(){
        todoListArray = new TodoList[3];
        user = new User("username", passwordEncoder.encode("password"));

        userService.save(user);
        for(int i=1; i<4; i++){
            TodoList todoList = new TodoList("task " + i,false, user, null);
            todoList.setId((long)i);
            todoListService.save(todoList);
            todoListArray[i-1] = todoList;
        }
    }

    @Test
    void whenFindByExistId_thenReturnTodoList(){
        TodoList testTodoList = todoListService.findById(1L);
        Assertions.assertEquals(testTodoList.toString(), todoListArray[0].toString());
        testTodoList = todoListService.findById(2L);
        Assertions.assertEquals(testTodoList.toString(), todoListArray[1].toString());
        testTodoList = todoListService.findById(3L);
        Assertions.assertEquals(testTodoList.toString(), todoListArray[2].toString());

    }
    @Test
    void whenFindByNonExistId_thenReturnNull(){
        TodoList testTodoList = todoListService.findById(5L);
        Assertions.assertNull(testTodoList);
        testTodoList = todoListService.findById(7L);
        Assertions.assertNull(testTodoList);
        testTodoList = todoListService.findById(10L);
        Assertions.assertNull(testTodoList);
        testTodoList = todoListService.findById(14L);
        Assertions.assertNull(testTodoList);
    }
    @Test
    void whenFindAllByExistUserUsingId_thenReturnListOfTodoList(){
        List<TodoList> lists= todoListService.findAllByUser_Id(userService.findByUsername(user.getUsername()).getId());
        for(int i=0; i<3; i++){
            Assertions.assertEquals(lists.get(i).toString(),todoListArray[i].toString());
        }
    }
    @Test
    void whenFindAllByExistUserUsingIdButNoTask_thenReturnEmptyList(){
        for(int i=0; i<3; i++)
            todoListService.delete(todoListArray[i]);
        List<TodoList> lists= todoListService.findAllByUser_Id(userService.findByUsername(user.getUsername()).getId());
        Assertions.assertEquals(lists, new ArrayList<>());
    }
}
