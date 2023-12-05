package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        menu.setMenuName(">>메뉴명을 입력하세요");
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
        this.menuRepository.delete(menu);
    }

}
