package com.webProject.webProject.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerCreateForm {
    private String p_nm;
    private String start_dt;
    private String b_no;
}
