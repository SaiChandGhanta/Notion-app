package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.dao.TokenRepository;
import com.neu.info7205.todo.model.TodoList;
import com.neu.info7205.todo.model.Token;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.ListService;
import com.neu.info7205.todo.service.UserService;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.ResponseObj;
import com.neu.info7205.todo.util.TodoPrecondition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "webservice-user-api")
public class UserController {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ListService listService;

    /*   @RequestMapping(method = RequestMethod.GET, value = "/abc")
       public String sayHello() {
           return "Healthy";
       }
   */
 /*   @Operation(summary = "Registers a new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to add.",
                    content = @Content(
                            schema = @Schema(implementation = User.class)), required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content)})*/
    @PostMapping("/user")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        TodoList list = new TodoList("Default");
        list.setUserId(savedUser.getId());
        listService.save(list);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @GetMapping("/user/verify")
    public ResponseEntity verifyUser(@RequestParam String token, @RequestParam String email) {
        Optional<Token> token1 = tokenRepository.findById(token);
        TodoPrecondition.assertNotNull(token1, ErrorCode.INVALID_TOKEN);
        TodoPrecondition.assertFalse(new Date().after(token1.get().getTokenExpiryTime()), ErrorCode.TOKEN_EXPIRED);
        tokenRepository.deleteById(token);
        User user = userService.getUserByEmail(email);
        user.setVerified(true);
        userService.updateUser(user);
        return ResponseEntity.ok("OTP verified");
    }

    @GetMapping("/user/verify/changeEmail")
    public ResponseEntity verifyChangeEmailRequest(@RequestParam String token, @RequestParam String email, @RequestParam String newEmail) {
        Optional<Token> token1 = tokenRepository.findById(token);
        TodoPrecondition.assertNotNull(token1, ErrorCode.INVALID_TOKEN);
        TodoPrecondition.assertFalse(new Date().after(token1.get().getTokenExpiryTime()), ErrorCode.TOKEN_EXPIRED);
        tokenRepository.deleteById(token);
        User user = userService.getUserByEmail(email);
        userService.updateUserEmail(user, newEmail);
        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/user/resend/email")
    public ResponseEntity resendEmail(@RequestParam String email) {
        TodoPrecondition.assertNotBlank(email, ErrorCode.MAIL_EMPTY);
        Token token = tokenRepository.findByEmail(email);
        User user = userService.getUserByEmail(email);
        TodoPrecondition.assertFalse(new Date().before(token.getTokenExpiryTime()), ErrorCode.MAIL_SENT);
        userService.sendEmailVerification(user);
        return ResponseEntity.ok("Email Verification Sent");
    }

    @PostMapping("/user/changeEmail")
    public ResponseEntity changeEmail(@RequestParam("newEmail") String newEmail) {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        userService.changeEmail(user, newEmail);
        return ResponseEntity.ok("Email verification sent to previous and current mail");
    }

    @GetMapping("/user/self")
    public ResponseEntity getUserInfo() {
        User user = userService.getLoggedInUser();
        TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test/verify")
    public void setVerifiedTrue(){
        String email = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email);
        user.setVerified(true);
        userService.updateUser(user);
    }
}
