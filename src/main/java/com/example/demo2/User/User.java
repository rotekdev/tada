package com.example.demo2.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "githubUser")
@Getter
public class User {

    @Id
    private String login;
    private Long requestCount;

    public void increment() {
        requestCount++;
    }
}
