package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.User;

public interface UserService {
    public User getUserByEmail(String email);
    public User saveUser(User user);
    public User getLoggedInUser();
   public void  sendEmailVerification(User user);
   void changeEmail(User user, String newEmail);
    void updateUserEmail(User user, String newEmail);
    User updateUser(User user);
}
