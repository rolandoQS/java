package com.example.java.repositories;

import com.example.java.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("update UserEntity u set u.last_login = :last_login where u.email = :email ")
    void updateLastLoginByEmail(@Param(value = "last_login") Timestamp last_login, @Param(value = "email") String email);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.token = :token where u.email = :email ")
    void updateTokenByEmail(@Param(value = "token") String token, @Param(value = "email") String email);

}
