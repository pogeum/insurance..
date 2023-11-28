package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> saveMenus(Store store, List<Menu> menus) {
        for (Menu menu : menus) {
            menu.setStore(store);
            Menu addmenu = menu;

            this.menuRepository.save(addmenu);
        }
        return this.menuRepository.findAll();
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

}
