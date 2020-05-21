package model;

import ext.sql.Column;
import ext.sql.Entity;
import ext.sql.Primary;

@Entity(model = "users")
public class User {
    @Primary
    @Column(name = "user_id")
    private long userId;

    private String name;

    @Column(name = "nick_name")
    private String nickname;

    private String password;

    private String email;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
