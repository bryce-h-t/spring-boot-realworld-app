package io.spring.application.data;

import lombok.Getter;

@Getter
public class UserWithToken {
    private String email;
    private String username;
    private String bio;
    private String image;
    private String token;

    public UserWithToken(UserData userData, String token) {
        this.email = userData.email();
        this.username = userData.username();
        this.bio = userData.bio();
        this.image = userData.image();
        this.token = token;
    }

}
