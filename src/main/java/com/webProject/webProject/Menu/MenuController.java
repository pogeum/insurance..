package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/menu")
@RequiredArgsConstructor
@Controller
public class MenuController {
    private final MenuService menuService;
    private final StoreService storeService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addmenu")
    public String addmenu(Integer storeid) {
        Store store = storeService.findstoreById(storeid);
        menuService.setDefaultMenu(store);
        return "redirect:/store/menuList/"+ store.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String update(Integer menuid, String menuName, String pricestring) {
        Menu menu = menuService.findMenu(menuid);
        if (menuName == null || menuName.isEmpty()) {
            menu.setMenuName("메뉴명을입력하세요");
        }else {
            menu.setMenuName(menuName);
        }

        if (pricestring == null || pricestring.isEmpty()) {
            menu.setPrice(000);
        }else {
            menu.setPrice(Integer.valueOf(pricestring));
        }
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
