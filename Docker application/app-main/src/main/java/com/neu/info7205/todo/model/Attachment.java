package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name="Attachments", schema = "todo")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column
    private String name;
    @Column
    private String path;
    @Column
    private int size;
    @Column
    private Date attachedDate;
    @Column
    private int taskId;
    @Transient
    private int listId;
}
