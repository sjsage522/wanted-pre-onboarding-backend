package com.junseok.wantedpreonboardingbackend.module.user.domain;

import com.junseok.wantedpreonboardingbackend.global.exception.AuthenticationException;
import com.junseok.wantedpreonboardingbackend.global.exception.CustomException;
import com.junseok.wantedpreonboardingbackend.global.exception.dto.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import static com.junseok.wantedpreonboardingbackend.global.util.ByteUtils.bytesToHex;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Builder
    private User(Long id, String email, String password) {
        validationSignUpFormat(email, password);
        this.id = id;
        this.email = email;
        this.salt = this.generateSalt();
        this.password = this.hashPassword(password, this.salt); // password 암호화
    }

    /**
     * hashing user password using SHA-256 hash algorithm
     * @param original unencrypted password
     * @param salt     salt that adding to original
     * @return encrypted password
     */
    public String hashPassword(String original, String salt) {
        String temp = original + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(temp.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new CustomException(ErrorCode.INVALID_ALGORITHM);
        }
    }

    public void validationMatchPassword(String target) {
        if (!this.password.equals(target)) {
            throw new AuthenticationException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    /**
     * Validation when signing up
     * @param email    user email
     * @param password user original password
     */
    public static void validationSignUpFormat(String email, String password) {
        if (!email.contains("@") || password.length() < 8) {
            throw new CustomException(ErrorCode.INVALID_SIGNUP_FORMAT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, salt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + "[PROTECTED]" + '\'' +
                ", salt='" + "[PROTECTED]" + '\'' +
                '}';
    }
}
