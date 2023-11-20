package com.webProject.webProject.Store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreForm {

    private String name;
    private String content; //설명 없을시 설명이 없습니다. 라고 뜨도록?
    private String category;
    private String roadAddress;
//    사장이 등록할 사진.. 뭘로 해야할지 모르겠음.

}
