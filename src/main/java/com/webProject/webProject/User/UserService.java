package com.webProject.webProject.User;

import com.webProject.webProject.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    public User create(String userId, String email, String password, String nickname, String role, MultipartFile file) throws Exception {
        String projectPath = imgLocation; // 파일 저장 위치 = projectPath
        UUID uuid = UUID.randomUUID(); // 식별자. 랜덤으로 이름 생성
        String fileName;

        if (file == null || file.isEmpty()) {
            fileName = "no_img.jpg"; // 기본 이미지 파일명
            File defaultImageFile = new File(projectPath, fileName);

            // 기본 이미지를 static 폴더에서 복사
            ClassPathResource defaultImageResource = new ClassPathResource("static/no_img.jpg");
            Files.copy(defaultImageResource.getInputStream(), defaultImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            fileName = uuid + "_" + file.getOriginalFilename(); // 저장될 파일 이름 지정 = 랜덤 식별자_원래 파일 이름
            File saveFile = new File(projectPath, fileName); // 빈 껍데기 생성, 이름은 fileName, projectPath라는 경로에 담김
            file.transferTo(saveFile);
        }

        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        user.setRole(role);
        user.setCreateDate(LocalDateTime.now());

        user.setFileName(fileName); // 파일 이름
        user.setFilePath(projectPath + fileName); // 저장 경로, 파일 이름

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

    public User modify(User user, String nickname, String email, MultipartFile file) throws IOException {
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
        return userRepository.save(user);
    }

    public boolean deleteExistingFile(String existingFilePath) {
        if (existingFilePath != null && !existingFilePath.isEmpty()) {
            File existingFile = new File(existingFilePath);
            if (existingFile.exists()) {
                existingFile.delete();
            }
        }
        return false;
    }

    public String uploadFile(MultipartFile file, String projectPath) throws IOException {
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
            return fileName;
        }
        return null;
    }

    public String uploadImg(MultipartFile file, String projectPath, String projectUploadPath) throws IOException {
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
