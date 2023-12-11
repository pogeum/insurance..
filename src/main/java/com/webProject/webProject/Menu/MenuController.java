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

    @PostMapping("/update")
    public String update(Integer menuid, String menuName, String pricestring, List<MultipartFile> fileList) throws Exception {
        Menu menu = menuService.findMenu(menuid);

//        String existingImagePath = imgLocation +
//        boolean isDeleted = this.menuService.deleteExistingFile(existingImagePath);

        if (menuName == null || menuName.isEmpty()) {
            menu.setMenuName("메뉴이름");
        } else {
            menu.setMenuName(menuName);
        }

        if (pricestring == null || pricestring.isEmpty()) {
            menu.setPrice(0);
        } else {
            menu.setPrice(Integer.valueOf(pricestring));
        }
        boolean filesSelected = fileList.stream().anyMatch(file -> !file.isEmpty());    // false -> true
        if (!filesSelected) {   //false
            photoService.savedefaultImgsForMenu(menu, fileList);
        } else {
            photoService.saveImgsForMenu(menu, fileList);
        }
        menuService.setMenu(menu);
        return "redirect:/store/menuList/" + menu.getStore().getId();
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String delete(Integer menuid) {
        Menu menu = menuService.findMenu(menuid);
        menuService.deleteMenu(menu);
        return "redirect:/store/menuList/"+ menu.getStore().getId();
    }
}
