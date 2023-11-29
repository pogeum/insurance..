package com.webProject.webProject.User;

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

    public void modify(User user, String nickname, String email) {
//        String projectPath = imgLocation;
//
//        if (file == null || file.isEmpty()) {
//            // No new file provided
//            user.setFileName(user.getFileName());
//            user.setFilePath(user.getFilePath());
//        } else {
//            File f = new File(user.getFilePath());
//
//            if (f.exists()) {
//                // File exists, delete it
//                f.delete();
//            }
//
//            UUID uuid = UUID.randomUUID();
//            String fileName = uuid + "_" + file.getOriginalFilename();
//            File saveFile = new File(projectPath, fileName);
//            file.transferTo(saveFile);
//
//            user.setFileName(fileName);
//            user.setFilePath(projectPath + File.separator + fileName);
//        }

        user.setNickname(nickname);
        user.setEmail(email);
        this.userRepository.save(user);
    }

    public void modifyPw(User user, String pw){
        user.setPassword(passwordEncoder.encode(pw));
        user.setModifyDate(LocalDateTime.now());
        this.userRepository.save(user);
    }

    public String saveImage(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            return null; // 이미지가 없는 경우 처리
        }

        try {
            // 이미지 파일의 고유한 파일명 생성
            String fileName = generateUniqueFileName(imageFile.getOriginalFilename());

            // 이미지를 지정된 디렉토리에 저장
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(imgLocation + fileName);
            Files.write(path, bytes);

            // 저장된 이미지 파일의 경로를 반환
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장 중 예외 발생 시 처리
            return null;
        }
    }

    // 이미지 파일의 고유한 파일명 생성
    private String generateUniqueFileName(String originalFileName) {
        String extension = StringUtils.getFilenameExtension(originalFileName);
        return UUID.randomUUID().toString() + "." + extension;
    }

    public void updateUserImage(User user, String imagePath) {
        // 사용자 정보의 이미지 경로를 새로운 이미지 경로로 설정합니다.
        user.setFilePath(imagePath);

        // 여기에서 사용자 정보를 저장하거나 업데이트하는 로직을 추가할 수 있습니다.
        // 예를 들어, JPA를 사용한다면 user 객체를 다시 저장하면 됩니다.
        // userRepository.save(user); // userRepository는 실제 사용하는 Repository에 따라 다를 수 있습니다.
    }
}
