package com.neu.info7205.todo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name = "Tokens", schema = "todo")
public class Token {

    @Id
    private String token= UUID.randomUUID().toString();

    @Column
    private String email;

    @Column(name="token_expiry_time")
    private Date tokenExpiryTime=new Date(System.currentTimeMillis() + 900 * 1000);
}
