package com.neu.info7205.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
@Table(name = "Users", schema = "todo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private int id;
    @Schema(type = "String", required = true, example = "John", description = "First name of the user")
    private String firstName;
    @Schema(type = "String", required = false, example = "M", description = "Middle name of the user")
    private String middleName;
    @Schema(type = "String", required = true, example = "Doe", description = "Last name of the user")
    private String lastName;
    @Schema(type = "String", required = true, example = "john@email.com", description = "Email of the user")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(type = "String", required = true, example = "bCryptpassword", description = "Password of the user")
    private String password;

    @Column(name="is_verified")
    private boolean isVerified;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
//    @Column(name = "firstName")
//    private String firstName;
//    @Column(name="middleName")
//    private String middleName;
//    @Column(name="lastName")
//    private String lastName;
//    @Column(name="email")
//    private String email;
//    @Column(name="passwords")
//    private String password;

}
