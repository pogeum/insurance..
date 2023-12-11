package com.webProject.webProject.Menu;

import com.webProject.webProject.Photo.PhotoService;
import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {
    private final MenuService menuService;
    private final StoreService storeService;
    private final PhotoService photoService;
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
        if (menuName == null || menuName.isEmpty()) {
            menu.setMenuName("--MENU--");
        } else {
            menu.setMenuName(menuName);
        }

        if (pricestring == null || pricestring.isEmpty()) {
            menu.setPrice(0);
        } else {
            menu.setPrice(Integer.valueOf(pricestring));
        }

        if (fileList == null || fileList.isEmpty() || fileList.stream().allMatch(file -> file.isEmpty())) {
            System.out.println("-------------------------------" + fileList);
            photoService.savedefaultImgsForMenu(menu, fileList); // fileList가 비어있을 때 실행될 내용
        } else {
            System.out.println("++++++++++++++++++++++++++++++++++" + fileList);
            photoService.saveImgsForMenu(menu, fileList); // fileList가 비어있지 않을 때 실행될 내용
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
