package com.webProject.webProject.Menu;

import com.webProject.webProject.Photo.PhotoService;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {
    private final MenuService menuService;
    private final StoreService storeService;
    private final PhotoService photoService;
    @Value("${ImgLocation}")
    public String imgLocation;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addmenu")
    public String addmenu(Integer storeid) {
        Store store = storeService.findstoreById(storeid);
        menuService.setDefaultMenu(store);
        return "redirect:/store/menuList/"+ store.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String update(Integer menuid, String menuName, String pricestring, List<MultipartFile> fileList) throws Exception {
        Menu menu = menuService.findMenu(menuid);
        if (menuName == null || menuName.isEmpty()) {
            menu.setMenuName("이름없음");
        }else {
            menu.setMenuName(menuName);
        }

        if (pricestring == null || pricestring.isEmpty()) {
            menu.setPrice(0);
        }else {
            menu.setPrice(Integer.valueOf(pricestring));
        }

//        String projectPath = imgLocation; // 파일 저장 위치 = projectPath
//        //C:/Users/admin/Desktop/web_project/src/main/resources/static/img/
//        UUID uuid = UUID.randomUUID(); // 식별자. 랜덤으로 이름 생성
//        String fileName;
//
//        if (fileList != null && !fileList.isEmpty() && fileList.get(0).getSize() > 0) {
//            // 실제로 파일이 선택되었을 때만 추가
//            photoService.saveImgsForMenu(menu, fileList);
//        } else {
//            fileName = "no_img.jpg"; // 기본 이미지 파일명
//            File defaultImageFile = new File(projectPath, fileName);
//
//            // 기본 이미지를 static 폴더에서 복사
//            ClassPathResource defaultImageResource = new ClassPathResource("static/no_img.jpg");
//            Files.copy(defaultImageResource.getInputStream(), defaultImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//
//            // 디폴트 이미지를 fileList에 추가
//            MultipartFile defaultImageMultipartFile = new YourCustomMultipartFile(
//                    Files.readAllBytes(defaultImageFile.toPath()), // 파일을 byte 배열로 읽어옴
//                    fileName
//            );
//
//            // fileList를 생성하고 디폴트 이미지를 추가
//            fileList = new ArrayList<>();
//            fileList.add(defaultImageMultipartFile);
//
//            // photoService.saveImgsForMenu 메서드 호출
//            photoService.saveImgsForMenu(menu, fileList);
//        }

        photoService.saveImgsForMenu(menu, fileList);
        menuService.setMenu(menu);
        return "redirect:/store/menuList/"+ menu.getStore().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String delete(Integer menuid) {
        Menu menu = menuService.findMenu(menuid);
        menuService.deleteMenu(menu);
        return "redirect:/store/menuList/"+ menu.getStore().getId();
    }
}
