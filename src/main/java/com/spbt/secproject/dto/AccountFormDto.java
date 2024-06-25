package com.spbt.secproject.dto;

import com.spbt.secproject.entity.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

@Data
public class AccountFormDto {

    @NotEmpty(message = "아이디는 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Pattern(regexp="[a-zA-Z0-9]+", message="아이디는 영문과 숫자만 입력 가능합니다.")
    @Length(min=4, max=12, message = "아이디는 4자 이상, 16자 이하로 입력해주세요.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Length(min=4, max=16, message = "비밀번호는 2자 이상, 16자 이하로 입력해주세요.")
    private String userPw;

    private String confirmPw;

    @NotEmpty(message = "이름은 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Pattern(regexp="^[가-힣]*$", message="이름은 한글만 입력 가능합니다.")
    @Length(min=2, max=5, message = "이름은 2자 이상, 5자 이하로 입력해주세요.")
    private String userName;

    @NotEmpty(message = "이메일은 필수 입력 값 입니다.")
    @Pattern(regexp="^\\S+$", message="공백이 포함된 문자열은 입력할 수 없습니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "권한은 필수 입력 값 입니다.")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "권한 설정은 ADMIN 또는 USER만 가능합니다.")
    private String role;

    private String introduce;

    private static ModelMapper modelMapper = new ModelMapper();

    public static AccountFormDto createAccountDto(Account account) {
        return modelMapper.map(account, AccountFormDto.class);
    }

    public static Account createAccountEntity(AccountFormDto accountFormDto) {
        return modelMapper.map(accountFormDto, Account.class);
    }
}
