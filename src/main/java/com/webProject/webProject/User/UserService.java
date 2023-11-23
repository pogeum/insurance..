package com.webProject.webProject.User;

import com.webProject.webProject.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUserList() {
        return this.userRepository.findAll();
    }

    public User create(String userId, String email, String password, String nickname) {
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        this.userRepository.save(user);
        return user;
    }

    public User getUser(String userId) {
        Optional<User> user = this.userRepository.findByuserId(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
