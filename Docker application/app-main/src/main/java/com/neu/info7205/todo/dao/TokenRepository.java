package com.neu.info7205.todo.dao;

import com.neu.info7205.todo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByEmail(String email);
}
