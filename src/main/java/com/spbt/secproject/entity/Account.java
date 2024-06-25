package com.spbt.secproject.entity;

import com.spbt.secproject.constant.AccountRole;
import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.dto.ChangeAccountPwDto;
import lombok.Data;
import jakarta.persistence.*;
import org.apache.commons.codec.digest.DigestUtils;

@Table(name="account")
@Entity
@Data
public class Account {

    @Id
    @Column(name="account_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(unique = true)
    private String userid;

    private String userpw;

    @Column(unique = true)
    private String username;

    private String email;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public static Account createUser(AccountFormDto accountFormDto) {
        Account account = new Account();
        account.setUserid(accountFormDto.getUserId());
        account.setUserpw(accountFormDto.getUserPw());
        account.setUsername(accountFormDto.getUserName());
        account.setEmail(accountFormDto.getEmail());
        // 계정 권한 지정
        account.setRole(AccountRole.valueOf(accountFormDto.getRole()));

        return account;
    }

    public void updateUser(String email, String introduce) {
        this.email = email;
        this.introduce = introduce;
    }

    public void  changePassword(String userNewPw) {
        this.userpw = userNewPw;
    }
}
