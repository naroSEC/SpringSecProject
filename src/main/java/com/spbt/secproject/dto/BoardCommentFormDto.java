package com.spbt.secproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BoardCommentFormDto {

    private Long id;

    private String userId;

    private String userName;

    private String commentText;

    private String saveTime;

    public BoardCommentFormDto(Long id, String userId, String userName, String commentText, String saveTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.commentText = commentText;
        this.saveTime = saveTime;
    }

}
