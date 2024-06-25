package com.spbt.secproject.controller.main.user;

import com.spbt.secproject.dto.ChangeAccountInfoDto;
import com.spbt.secproject.dto.ChangeAccountPwDto;
import com.spbt.secproject.dto.ProfileImgFormDto;
import com.spbt.secproject.dto.AccountFormDto;
import com.spbt.secproject.entity.Account;
import com.spbt.secproject.entity.ProfileImg;
import com.spbt.secproject.service.BoardService;
import com.spbt.secproject.service.CustomUserDetailService;
import com.spbt.secproject.service.ProfileImgService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final CustomUserDetailService customUserDetailService;
    private final BoardService boardService;
    private final ProfileImgService profileImgService;

    @GetMapping(value = "/main/user/mypage")
    public String mypage(@AuthenticationPrincipal User user, Model model) {
        Account account = customUserDetailService.getAccount(user);
        ProfileImg profileImg = profileImgService.getProfileImg(account);

        if (profileImg != null) {
            ProfileImgFormDto profileImgFormDto = ProfileImgFormDto.createProfileDto(profileImg);
            model.addAttribute("profileImgFormDto", profileImgFormDto);
        }

        AccountFormDto accountFormDto = AccountFormDto.createAccountDto(account);

        Long totalWriteBoard = boardService.getUserWriteTotalBoard(account.getId());
        Long totalWriteComment = boardService.getUserWriteTotalComment(account.getId());

        model.addAttribute("accountFormDto", accountFormDto);
        model.addAttribute("totalWriteBoard", totalWriteBoard);
        model.addAttribute("totalWriteComment", totalWriteComment);

        return "/main/user/mypage";
    }

    @GetMapping(value = "/main/user/mypage/info")
    public String userInfo(@AuthenticationPrincipal User user, Model model) {
        Account account = customUserDetailService.getAccount(user);
        ProfileImg profileImg = profileImgService.getProfileImg(account);

        if (profileImg != null) {
            ProfileImgFormDto profileImgFormDto = ProfileImgFormDto.createProfileDto(profileImg);
            model.addAttribute("profileImgFormDto", profileImgFormDto);
        }

        AccountFormDto accountFormDto = AccountFormDto.createAccountDto(account);
        model.addAttribute("accountFormDto", accountFormDto);

        return "/main/user/userinfo";
    }

    @GetMapping(value = "/main/user/mypage/pass")
    public String userPwChange() {

        return "/main/user/pass";
    }

    @PostMapping(value = "/main/user/profile")
    public ResponseEntity<Object> profileImgUpload(@AuthenticationPrincipal User user, MultipartFile profileImgFile) {
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            Account account = customUserDetailService.getAccount(user);
            ProfileImg existProfileImg = profileImgService.getProfileImg(account);

            if (existProfileImg != null) {
                profileImgService.updateProfileImg(existProfileImg, profileImgFile);

            } else {
                ProfileImg profileImg = new ProfileImg();
                profileImg.setAccount(account);

                profileImgService.saveProfileImg(profileImg, profileImgFile);
            }

            hashMap.put("success", "프로필 이미지가 변경 되었습니다.");
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }

    @PostMapping(value = "/main/user/mypage/info")
    public ResponseEntity<Object> changeInfo(@AuthenticationPrincipal User user, @Valid ChangeAccountInfoDto changeAccountInfoDto, BindingResult bindingResult) {
        HashMap<String, String> hashMap = new HashMap<>();

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
            customUserDetailService.changeInfo(user, changeAccountInfoDto);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "사용자 정보가 변경 되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    @PostMapping(value = "/main/user/mypage/pass")
    public ResponseEntity<Object> changePw(@AuthenticationPrincipal User user, @Valid ChangeAccountPwDto changeAccountPwDto, BindingResult bindingResult) {
        HashMap<String, String> hashMap = new HashMap<>();

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
            customUserDetailService.changePassword(user, changeAccountPwDto);

        } catch (Exception e) {
            hashMap.put("fail", e.getMessage());
            JSONObject jsonResponse = new JSONObject(hashMap);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }

        hashMap.put("success", "패스워드가 변경 되었습니다.");
        JSONObject jsonResponse = new JSONObject(hashMap);

        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }
}
