package com.webProject.webProject.Menu;

import com.webProject.webProject.Store.Store;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MenuForm {
    private String menuName;
    private Integer price;

    private MultipartFile file;
}
