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

    public void saveMenu(Store tempStore, String menuName, Integer price) {
        Menu menu = new Menu();
        menu.setStore(tempStore);
        menu.setMenuName(menuName);
        menu.setPrice(price);
//        menu.setPrice(Integer.valueOf(price));
        this.menuRepository.save(menu); //store는나중에 연결..
    }

    public List<Menu> saveMenus(Store store, List<String> menuNames, List<String> prices) {

        List<Menu> menuList = new ArrayList<>();
        for (int i =0; i< menuNames.size(); i++) {
            Menu menu = new Menu();
            menu.setStore(store);
            menu.setMenuName(menuNames.get(i));
            menu.setPrice(Integer.valueOf(prices.get(i)));
            menuList.add(menu);
            this.menuRepository.save(menu);
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
