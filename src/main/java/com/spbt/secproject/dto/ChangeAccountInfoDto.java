package com.spbt.secproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeAccountInfoDto {

    @NotEmpty(message = "이메일은 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "자기소개는 필수 입력 값 입니다.")
    private String introduce;

}
