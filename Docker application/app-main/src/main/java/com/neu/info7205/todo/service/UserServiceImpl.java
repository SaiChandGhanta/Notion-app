package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.TokenRepository;
import com.neu.info7205.todo.dao.UserRepository;
import com.neu.info7205.todo.model.EmailDetails;
import com.neu.info7205.todo.model.Token;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.TodoPrecondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByEmail(username);
        TodoPrecondition.assertNotNull(user, ErrorCode.USER_NOT_FOUND);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {

        TodoPrecondition.assertNotBlank(user.getEmail(), ErrorCode.MAIL_EMPTY);
        TodoPrecondition.assertNotBlank(user.getPassword(), ErrorCode.PASSWORD_EMPTY);
        TodoPrecondition.assertNotBlank(user.getFirstName(), ErrorCode.FIRST_NAME_EMPTY);
        TodoPrecondition.assertNotBlank(user.getLastName(), ErrorCode.LAST_NAME_EMPTY);
        TodoPrecondition.assertTrue(checkIfMailIsValid(user.getEmail()), ErrorCode.INVALID_MAIL);

        User user1 = userRepository.findByEmail(user.getEmail());

        TodoPrecondition.assertNull(user1, ErrorCode.EMAIL_ALREADY_EXISTS);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        sendEmailVerification(user);
        return savedUser;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getLoggedInUser() {
        String email = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        User user = getUserByEmail(email);
        TodoPrecondition.assertTrue(user.isVerified(), ErrorCode.NOT_VERIFIED);
        return user;
    }


    @Override
    public void sendEmailVerification(User user) {
        EmailDetails emailDetails = buildEmailDetails(user);
        emailService.sendMail(emailDetails);
    }


    private EmailDetails buildEmailDetails(User user) {


        Token token;
        token = tokenRepository.findByEmail(user.getEmail());
        if (token == null) {
            token = new Token();
        }
        token.setTokenExpiryTime(new Date(System.currentTimeMillis() + 900 * 1000));
        token.setEmail(user.getEmail());
        tokenRepository.save(token);
        String url = "http://localhost:8080/todo/v1/user/verify?token=" + token.getToken() + "&email=" + user.getEmail();
        EmailDetails emailDetails = new EmailDetails(user.getEmail(), url, "Please Verify Your Email Address");
        return emailDetails;
    }

    public boolean checkIfMailIsValid(String email) {
        String regex = "^\\S+@\\S+\\.\\S+$";
        return Pattern.compile(regex)
                .matcher(email)
                .matches();
    }

    @Override
    public void changeEmail(User user, String newEmail) {
        user.setVerified(false);
        Token token = new Token();
        token.setEmail(user.getEmail());
        tokenRepository.save(token);
        String url = "http://localhost:8080/todo/v1/user/verify/changeEmail?token=" + token.getToken() + "&email=" + user.getEmail() + "&newEmail=" + newEmail;
        EmailDetails emailDetails = new EmailDetails(user.getEmail(), "You have requested a change to your email, " +
                "please verify your request: " + url, "New Email Change: Please verify your email");
        emailService.sendMail(emailDetails);
    }

    @Override
    public void updateUserEmail(User user, String newEmail) {
        checkIfMailIsValid(newEmail);
        user.setEmail(newEmail);
        User user1 = userRepository.save(user);
        sendEmailVerification(user1);
    }
}
