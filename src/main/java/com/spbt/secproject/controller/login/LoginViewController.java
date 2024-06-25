package com.spbt.secproject.controller.login;

import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.service.MyBatisAccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class LoginViewController {

    @Autowired
    private final MyBatisAccountService myBatisAccountService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/")
    public String selectLoginPage() {

        return "/login/selectLogin";
    }

    @GetMapping(value = "/login")
    public String loginPage(Model model) {
        model.addAttribute("accountFormDto", new AccountFormDto());

        return "/login/login";
    }

    // SQL Injection Login Page
    @GetMapping(value = "/loginVul")
    public String sqliLoginPage(Model model) {
        model.addAttribute("accountFormDto", new AccountFormDto());

        return "/login/loginVul";
    }

    @PostMapping(value = "/api/loginVulProc")
    public ResponseEntity<String> loginVulPage(HttpSession session, AccountFormDto accountFormDto, Model model) {
        model.addAttribute("accountFormDto", new AccountFormDto());
        HttpHeaders headers = new HttpHeaders();

        Account account = myBatisAccountService.login(accountFormDto);

        if (account != null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("userId", account.getUserid());
            requestBody.add("userPw", account.getUserpw());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            return restTemplate.exchange("http://localhost:7777/api/loginProc", HttpMethod.POST, requestEntity, String.class);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        String responseBody = "{\"fail\": \"아이디 또는 패스워드가 틀렸습니다.\"}";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(responseBody);
    }
}
