package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name = "Tasks", schema = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column
    private String description;

    @Column
    private String summary;

    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    private int listId;

    @Column
    private Date lastModified;

    @Column
    private Date created;

    @Column
    private Date dueDate;

}
