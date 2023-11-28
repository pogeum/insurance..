package com.webProject.webProject.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerCreateForm {
    private String p_nm;    // 사업자 명
    private String b_no;    // 사업자번호
    private String start_dt;    // 사업자 등록일
}
