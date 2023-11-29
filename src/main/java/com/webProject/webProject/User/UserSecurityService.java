package com.webProject.webProject.User;


import com.webProject.webProject.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> _user = this.userRepository.findByuserId(userId);
        if (_user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        User user = _user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("owner".equals(user.getRole())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.OWNER.getValue()));
        } else if("admin".equals(user.getRole())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        CustomUser customUser = new CustomUser(user.getUserId(), user.getPassword(), authorities);
        customUser.setNickname(user.getNickname());
        customUser.setRole(user.getRole());
        customUser.setFileName(user.getFileName());
        return customUser;
    }
}