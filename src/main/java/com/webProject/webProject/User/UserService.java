package com.webProject.webProject.User;

import com.webProject.webProject.CustomUser;
import com.webProject.webProject.DataNotFoundException;
import com.webProject.webProject.Review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${ImgLocation}")
    public String imgLocation;

    public List<User> getUserList() {
        return this.userRepository.findAll();
    }

    public User create(String userId, String email, String password, String nickname, String role) {
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        user.setRole(role);
        user.setCreateDate(LocalDateTime.now());
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

    public void modify(User user, String nickname, String email, MultipartFile file) throws IOException {
        String projectPath = imgLocation;
        String existingFilePath = user.getFilePath();

        if (file != null && !file.isEmpty()) {
            deleteExistingFile(existingFilePath);

            String fileName = uploadFile(file, projectPath);
            user.setFileName(fileName);
            user.setFilePath(projectPath + File.separator + fileName);
        }

        // 사용자 정보 업데이트
        user.setNickname(nickname);
        user.setEmail(email);
        userRepository.save(user);
    }

    private void deleteExistingFile(String existingFilePath) {
        if (existingFilePath != null && !existingFilePath.isEmpty()) {
            File existingFile = new File(existingFilePath);
            if (existingFile.exists()) {
                existingFile.delete();
            }
        }
    }

    private String uploadFile(MultipartFile file, String projectPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            return fileName;
        }
        return null;
    }

    public void modifyPw(User user, String pw) {
        user.setPassword(passwordEncoder.encode(pw));
        user.setModifyDate(LocalDateTime.now());
        this.userRepository.save(user);
    }
}
