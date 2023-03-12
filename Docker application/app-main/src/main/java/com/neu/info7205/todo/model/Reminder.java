package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name="Reminders",schema = "todo")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date reminderDate;
    @Column(name="created")
    private Date reminder_created;
    @Column(name="lastModified")
    private Date reminder_modified;
    @Column
    private int taskId;
}
