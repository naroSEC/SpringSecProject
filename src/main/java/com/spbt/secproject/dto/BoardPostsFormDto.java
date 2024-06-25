package com.spbt.secproject.dto;

import com.spbt.secproject.constant.AccountRole;
import lombok.Data;

@Data
public class BoardPostsFormDto {

    private Long Id;

    private String postsTitle;

    private String userName;

    private String role;

    private String saveTime;

    public BoardPostsFormDto(Long Id, String postsTitle, String userName, AccountRole role, String saveTime) {
        this.Id = Id;
        this.postsTitle = postsTitle;
        this.userName = userName;
        this.role = role.toString();
        this.saveTime = saveTime;
    }

}
