package com.spbt.secproject.service;

import com.spbt.secproject.Mapper.AccountMapper;
import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyBatisAccountService {

    private final AccountMapper accountMapper;

    public Account login(AccountFormDto accountFormDto) {
        String userId = accountFormDto.getUserId();
        String userPw = accountFormDto.getUserPw();

        return accountMapper.findByUserAndUserpw(userId, userPw);
    }
}
