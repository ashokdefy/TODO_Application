package com.deloitte.todo_app.service.imp;

import com.deloitte.todo_app.model.User;
import com.deloitte.todo_app.repository.UserRepository;
import com.deloitte.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
