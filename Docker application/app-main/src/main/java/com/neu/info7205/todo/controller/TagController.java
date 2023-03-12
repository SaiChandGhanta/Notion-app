package com.neu.info7205.todo.controller;

import com.neu.info7205.todo.model.Tag;
import com.neu.info7205.todo.model.User;
import com.neu.info7205.todo.service.TagServiceImpl;
import com.neu.info7205.todo.service.UserServiceImpl;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.SystemException;
import com.neu.info7205.todo.util.TodoPrecondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/todo/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/tag")
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) {
        try {
            User user = userService.getLoggedInUser();
            TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
            tag.setUserid(user.getId());
            TodoPrecondition.assertNotBlank(tag.getName(), ErrorCode.TAG_NAME_EMPTY);
            TodoPrecondition.assertNull(tag.getTag_created(), ErrorCode.INVALID_REQUEST_BODY);
            TodoPrecondition.assertNull(tag.getTag_modified(), ErrorCode.INVALID_REQUEST_BODY);
            Tag tag1 = tagService.createTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(tag1);
        } catch (SystemException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), ErrorCode.UNAUTHORIZED_USER);
        }
    }

    @GetMapping("/user/tags")
    public ResponseEntity getTagsByUser() {
        try {
            User user = userService.getLoggedInUser();
            TodoPrecondition.assertNotNull(user, ErrorCode.UNAUTHORIZED_USER);
            return ResponseEntity.ok(tagService.getAllTagsByUser(user.getId()));
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), ErrorCode.INVALID_REQUEST);
        }
    }

    @PutMapping("/tag/{id}")
    public ResponseEntity updateTag(@RequestBody Tag tag, @PathVariable("id") int tagId) {
        TodoPrecondition.assertFalse(tag.getId() != 0 || tag.getTag_created() != null || tag.getTag_modified() != null || StringUtils.isBlank(tag.getName()), ErrorCode.NO_PERMISSION);
        User user = userService.getLoggedInUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(tagService.updateTag(user.getId(), tagId, tag.getName()));
    }

}
