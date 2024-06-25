package com.spbt.secproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile_img")
public class ProfileImg {

    @Id
    @Column(name="profile_img_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String oriImgName; //원본 이미지 파일명

    private String imgName; //이미지 파일명

    private String imgUrl; //이미지 조회 경로

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    private Account account;

    public void updateProfileImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this. imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
