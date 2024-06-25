package com.spbt.secproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangeAccountPwDto {

    private String currentPw;

    @NotEmpty(message = "새로운 비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Length(min=4, max=16, message = "비밀번호는 2자 이상, 16자 이하로 입력해주세요.")
    private String newPw;

    @NotEmpty(message = "새로운 확인 비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Length(min=4, max=16, message = "비밀번호는 2자 이상, 16자 이하로 입력해주세요.")
    private String confirmPw;

}
