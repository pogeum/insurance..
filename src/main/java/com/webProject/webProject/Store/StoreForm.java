package com.webProject.webProject.Store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class StoreForm {

    private String name;
    private String content; //설명 없을시 설명이 없습니다. 라고 뜨도록?
    private String category;
    private String roadAddress;

    private List<MultipartFile> fileList; //    사장이 등록할 사진

}
