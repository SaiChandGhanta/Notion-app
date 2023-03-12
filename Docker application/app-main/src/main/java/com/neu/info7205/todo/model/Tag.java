package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name="Tags", schema = "todo")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name="created")
    private Date tag_created;
    @Column(name="lastModified")
    private Date tag_modified;
    @Column
    private int userid;
}
