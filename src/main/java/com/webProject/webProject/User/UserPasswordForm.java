package com.webProject.webProject.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordForm {
    @NotEmpty(message = "현재PW는 필수항목입니다.")
    private String Password;    // 기존 패스워드

    @NotEmpty(message = "변경할 PW는 필수항목입니다.")
    private String newPassword1;    // 새로운 패스워드

    @NotEmpty(message = "PW확인은 필수항목입니다.")
    private String newPassword2;    // 새로운 패스워드 확인용
}
