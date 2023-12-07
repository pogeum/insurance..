package com.webProject.webProject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class CustomUser extends User implements OAuth2User {
    private String nickname;
    private String role;
    private String fileName;

    public CustomUser(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getAttributes();
    }

    @Override
    public String getName() {
        return this.nickname;
    }
}
