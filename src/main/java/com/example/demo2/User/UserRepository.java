package com.example.demo2.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query(value = """
             MERGE INTO github_user AS tgt
             USING (VALUES (:login, 1)) AS src (login, request_count)
                 ON tgt.login = src.login
             WHEN MATCHED THEN
                UPDATE SET request_count = tgt.request_count + 1
             WHEN NOT MATCHED THEN
                INSERT (login, request_count) VALUES (:login, 1)
            """, nativeQuery = true)
    public void upsertIncrementCount(@Param("login") String login);
}