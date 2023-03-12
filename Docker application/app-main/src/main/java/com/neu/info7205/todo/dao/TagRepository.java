package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
    @Query( "select t from Tag t where t.userid =:userId" )
    List<Tag> findAllTagsByUserId(@Param("userId")int userId);
    @Query( "select t from Tag t where t.userid =:userId and t.id =:tagId" )
    Tag findTagByUser(@Param("userId")int userId, @Param("tagId")int tagId);
    @Query( "select t from Tag t where t.userid =:userId and t.name =:tagName" )
    Tag findTagByUserName(@Param("userId")int userId, @Param("tagName")String tagName);

}
