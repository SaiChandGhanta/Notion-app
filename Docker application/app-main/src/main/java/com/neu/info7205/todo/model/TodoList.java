package com.neu.info7205.todo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name="Lists",schema = "todo")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column
    private String name;
    @Column(updatable = false)
    private Date created=new Date();
    @Column
    private Date lastModified=new Date();


    @Column(name = "user_id")
    private int userId;

    public TodoList(String name) {
        this.name = name;
    }
    public TodoList() {

    }

}
