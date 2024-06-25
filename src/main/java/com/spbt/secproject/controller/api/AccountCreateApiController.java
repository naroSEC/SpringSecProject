package com.spbt.secproject.controller.api;

import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.service.CustomUserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountCreateApiController {

    private final CustomUserDetailService customUserDetailsService;

    @PostMapping(value = "/api/CreateAccount")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountFormDto accountFormDto,
                                                BindingResult bindingResult)
    {
        HashMap<String, String> hashMap = new HashMap<>();

        // 사용자 패스워드, 확인 패스워드 비교
        if (!accountFormDto.getUserPw().equals(accountFormDto.getConfirmPw())) {
            hashMap.put("fail", "패스워드가 일치하지 않습니다.");
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            hashMap.put("fail", errorMessage.toString());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        try {
            Account account = Account.createUser(accountFormDto);
            customUserDetailsService.createAccount(account);

        } catch (IllegalStateException e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "회원가입 성공");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toJSONString());
    }

}
