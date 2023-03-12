package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.EmailDetails;

public interface EmailService {

    void sendMail(EmailDetails details);

}