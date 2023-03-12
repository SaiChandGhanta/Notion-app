package com.neu.info7205.todo.service;

import com.neu.info7205.todo.dao.TagRepository;
import com.neu.info7205.todo.model.Tag;
import com.neu.info7205.todo.util.ErrorCode;
import com.neu.info7205.todo.util.TodoPrecondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        Tag tag1 = tagRepository.findTagByUserName(tag.getUserid(), tag.getName());
        TodoPrecondition.assertNull(tag1, ErrorCode.TAG_EXISTS);

        tag.setTag_created(new Date());
        tag.setTag_modified((new Date()));
        return tagRepository.save(tag);


    }

    @Override
    public List<Tag> getAllTagsByUser(int userId) {
        return tagRepository.findAllTagsByUserId(userId);
    }

    @Override
    public Tag updateTag(int userId, int tagId, String name) {
        Tag oldTag = tagRepository.findTagByUser(userId, tagId);
        TodoPrecondition.assertNotNull(oldTag, ErrorCode.TAG_DOES_NOT_EXIST);
        oldTag.setName(name);
        oldTag.setTag_modified(new Date());
        return tagRepository.save(oldTag);

    }

    @Override
    public Tag findById(int id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag findTagByUserId(int userId, int tagId) {
        return tagRepository.findTagByUser(userId, tagId);
    }
}
