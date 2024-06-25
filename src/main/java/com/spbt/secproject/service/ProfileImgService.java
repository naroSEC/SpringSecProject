package com.spbt.secproject.service;

import com.spbt.secproject.entity.Account;
import com.spbt.secproject.entity.ProfileImg;
import com.spbt.secproject.repository.ProfileImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileImgService {

    @Value("${profileImgLocation}")
    private String profileImgLocation;

    private final FileService fileService;

    private final ProfileImgRepository profileImgRepository;

    public ProfileImg getProfileImg(Account account) {
        return profileImgRepository.findByAccountId(account.getId());
    }

    public void saveProfileImg(ProfileImg profileImg, MultipartFile profileImgFile) throws Exception {
        String oriImgName = profileImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(profileImgLocation, oriImgName, profileImgFile.getBytes());
            imgUrl = "/rsc/img/user/" + imgName;
        }

        //상품 이미지 정보 저장
        profileImg.updateProfileImg(oriImgName, imgName, imgUrl);
        profileImgRepository.save(profileImg);
    }

    public void updateProfileImg(ProfileImg existProfileImg, MultipartFile profileImgFile) throws Exception{
        if(!profileImgFile.isEmpty()){

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(existProfileImg.getImgName())) {
                fileService.deleteFile(profileImgLocation+"/"+existProfileImg.getImgName());
            }

            String oriImgName = profileImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(profileImgLocation, oriImgName, profileImgFile.getBytes());
            String imgUrl = "/rsc/img/user/" + imgName;
            existProfileImg.updateProfileImg(oriImgName, imgName, imgUrl);
        }
    }
}
