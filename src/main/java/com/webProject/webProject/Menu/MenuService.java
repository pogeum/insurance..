package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Photo.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public void saveMenus(Store store, List<Menu> menuList) {

        for (Menu menu : menuList) {
            menu.setStore(store);
            this.menuRepository.save(menu);
        }
    }

    public List<Menu> getstoreMenu(Store store) {
        List<Menu> storeMenu = new ArrayList<>();
        for (Menu menu : this.menuRepository.findAll()) {
            if (menu.getStore() != null) {
                if (menu.getStore().getId() == store.getId()) {
                    storeMenu.add(menu);
                }
            }
        }
        return storeMenu;
    }

    public void setDefaultMenu(Store store) {
        Menu menu = new Menu();
        menu.setMenuName("");
        menu.setPrice(0);
        menu.setStore(store);
        this.menuRepository.save(menu);
    }

    public void setMenu(Menu menu) {
        this.menuRepository.save(menu);
    }

    public Menu findMenu(Integer menuid) {
        return this.menuRepository.findById(menuid).get();
    }

    public void deleteMenu(Menu menu) {
        // Menu 객체에서 Photo 리스트를 가져옴
        Photo photo = menu.getPhoto();

        // 각 Photo 객체에 대해 파일 삭제 수행
        // Photo 클래스에 파일 경로를 가져올 수 있는 메서드가 있다고 가정
        String filePath = photo.getFilePath();

        // 파일 경로가 있다면 파일을 삭제
        if (filePath != null && !filePath.isEmpty()) {
            deleteExistingFile(filePath);
        }

        this.menuRepository.delete(menu);
    }

    public List<Menu> searchedmenu_storelist(String keyword) {
        return this.menuRepository.findMenusByKeyword(keyword);
    }

    public boolean deleteExistingFile(String existingFilePath) {
        if (existingFilePath != null && !existingFilePath.isEmpty()) {
            File existingFile = new File(existingFilePath);
            if (existingFile.exists()) {
                // 파일 삭제 작업이 성공하면 true 반환
                return existingFile.delete();
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


}
