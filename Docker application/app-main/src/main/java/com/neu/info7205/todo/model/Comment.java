package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name="Comments",schema = "todo")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String comment;
    @Column(name="created")
    private Date comment_created;
    @Column(name="lastModified")
    private Date comment_modified;
    @Column
    private int taskId;

}
