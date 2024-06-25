package com.spbt.secproject.service;

import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.dto.ChangeAccountInfoDto;
import com.spbt.secproject.dto.ChangeAccountPwDto;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.repository.AccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService  {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        validateDuplicateAccount(account);

        return accountRepository.save(account);
    }

    private void validateDuplicateAccount(Account account) {
        Account findAccount = accountRepository.findByUserid(account.getUserid());
        Account findUsername = accountRepository.findByUsername(account.getUsername());

        // 회원 가입 아이디 중복 체크
        if (findAccount != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        if (findUsername != null) {
            throw new IllegalStateException("중복 사용자 이름입니다.");
        }
    }

    public Account getAccount(User user) {
        String userId = user.getUsername();

        Account account = accountRepository.findByUserid(userId);

        if (account == null) {
            throw new IllegalStateException("사용자 정보를 가져오지 못 했습니다.");
        }

        return account;
    }

    public Account getAccount(AccountFormDto accountFormDto) {
        Account account = accountRepository.findByUserid(accountFormDto.getUserId());

        if (account == null) {
            throw new IllegalStateException("사용자 정보를 가져오지 못 했습니다.");
        }

        return account;
    }

    public void validateAllowUser(String userId, User user) {
        if (!userId.equals(user.getUsername())) {
            throw new IllegalStateException("권한이 존재하지 않습니다.");
        }
    }

    public void changePassword(User user, ChangeAccountPwDto changeAccountPwDto) {
        Account account = getAccount(user);
        String userPw = changeAccountPwDto.getCurrentPw();

        if (!userPw.equals(account.getUserpw())) {
            throw new IllegalStateException("패스워드가 틀렸습니다.");
        }

        if (!changeAccountPwDto.getNewPw().equals(changeAccountPwDto.getConfirmPw())) {
            throw new IllegalStateException("입력하신 새로운 패스워드를 확인해주세요.");
        }

        String userNewPw = changeAccountPwDto.getNewPw();

        if (userNewPw.equals(account.getUserpw())) {
            throw new IllegalStateException("사용 중인 패스워드와 동일한 패스워드는 사용 할 수 없습니다.");
        }

        account.changePassword(userNewPw);
    }

    public void changeInfo(User user, ChangeAccountInfoDto changeAccountInfoDto) {
        Account account = getAccount(user);
        String email = changeAccountInfoDto.getEmail();
        String introduce = changeAccountInfoDto.getIntroduce();
        account.updateUser(email, introduce);
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserid(userid);

        if(account == null) {
            throw new UsernameNotFoundException(userid);
        }

        System.out.println("==============================================");
        System.out.println("사용자 입력 아이디: " + userid);
        System.out.println("데이터베이스 정보: " + account.toString());
        System.out.println("==============================================");

        return User.builder()
                .username(account.getUserid())
                .password(account.getUserpw())
                .roles(account.getRole().toString())
                .build();
    }
}
