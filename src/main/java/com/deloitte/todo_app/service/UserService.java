package com.deloitte.todo_app.service;

import com.deloitte.todo_app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsername(String userName);
    void save(User user);
}
