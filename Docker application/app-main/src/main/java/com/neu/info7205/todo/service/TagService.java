package com.neu.info7205.todo.service;

import com.neu.info7205.todo.model.Tag;
import com.neu.info7205.todo.model.Task;

import java.util.List;

public interface TagService {
    public Tag createTag(Tag tag);

    public List<Tag> getAllTagsByUser(int userId);

    public Tag updateTag(int userId, int tagId, String name);

    public Tag findById(int id);
    public Tag findTagByUserId(int userId, int tagId);

}
