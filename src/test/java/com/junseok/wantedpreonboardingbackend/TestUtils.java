package com.junseok.wantedpreonboardingbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junseok.wantedpreonboardingbackend.module.post.domain.Post;
import com.junseok.wantedpreonboardingbackend.module.user.domain.User;

public class TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static User makeUser(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    public static Post makePost(String title, String content, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("[JsonUtils] -> toJson error\n");
        }
    }
}
