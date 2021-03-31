package com.deloitte.todo_app.repository;

import com.deloitte.todo_app.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findAllByUser_Id(long UserId);
}
