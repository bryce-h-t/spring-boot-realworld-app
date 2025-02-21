package io.spring.core.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {
    private String id;
    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;

    public User(String email, String username, String password, String bio, String image) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public void update(String email, String username, String password, String bio, String image) {
        if (!Objects.equals("", email)) {
            this.email = email;
        }

        if (!Objects.equals("", username)) {
            this.username = username;
        }

        if (!Objects.equals("", password)) {
            this.password = password;
        }

        if (!Objects.equals("", bio)) {
            this.bio = bio;
        }

        if (!Objects.equals("", image)) {
            this.image = image;
        }
    }
}
